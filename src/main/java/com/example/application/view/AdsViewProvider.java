package com.example.application.view;

import com.vaadin.navigator.*;
import com.vaadin.spring.access.*;
import com.vaadin.spring.annotation.*;
import com.vaadin.spring.internal.*;
import com.vaadin.ui.*;
import org.slf4j.*;
import org.springframework.beans.factory.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.beans.factory.config.*;
import org.springframework.beans.factory.support.*;
import org.springframework.context.*;
import org.springframework.core.annotation.*;
import org.springframework.util.*;

import javax.annotation.*;
import java.util.*;
import java.util.concurrent.*;

public class AdsViewProvider implements ViewProvider {

    private static final long serialVersionUID = 6906237177564157222L;

    /*
     * Note! This is a singleton bean!
     */
    private static final Logger LOGGER = LoggerFactory
            .getLogger(AdsViewProvider.class);
    // We can have multiple views with the same view name, as long as they
    // belong to different UI subclasses
    private final Map<String, Set<String>> viewNameToBeanNamesMap = new ConcurrentHashMap<>();
    private final ApplicationContext applicationContext;
    private final BeanDefinitionRegistry beanDefinitionRegistry;
    private Class<? extends View> accessDeniedViewClass;

    @Autowired
    public AdsViewProvider(ApplicationContext applicationContext,
                           BeanDefinitionRegistry beanDefinitionRegistry) {
        // super(applicationContext, beanDefinitionRegistry);
        this.applicationContext = applicationContext;
        this.beanDefinitionRegistry = beanDefinitionRegistry;
        final String[] viewBeanNames = applicationContext
                .getBeanNamesForAnnotation(SpringView.class);
        for (String beanName : viewBeanNames) {
            final Class<?> type = applicationContext.getType(beanName);
            SpringView mergedAnnotation = AnnotatedElementUtils.findMergedAnnotation(type, SpringView.class);
            mergedAnnotation.name();
        }
    }

    /**
     * Returns the class of the access denied view. If set, a bean of this type
     * will be fetched from the application context and showed to the user when
     * a {@link com.vaadin.spring.access.ViewAccessControl} or a
     * {@link com.vaadin.spring.access.ViewInstanceAccessControl} denies access
     * to a view.
     *
     * @return the access denied view class, or {@code null} if not set.
     */
    public Class<? extends View> getAccessDeniedViewClass() {
        return accessDeniedViewClass;
    }

    /**
     * Sets the class of the access denied view. If set, a bean of this type
     * will be fetched from the application context and showed to the user when
     * a {@link com.vaadin.spring.access.ViewAccessControl} or a
     * {@link com.vaadin.spring.access.ViewInstanceAccessControl} denies access
     * to a view.
     *
     * @param accessDeniedViewClass the access denied view class, may be {@code null}.
     */
    public void setAccessDeniedViewClass(
            Class<? extends View> accessDeniedViewClass) {
        this.accessDeniedViewClass = accessDeniedViewClass;
    }

    @PostConstruct
    void init() {
        LOGGER.info("Looking up SpringViews");
        int count = 0;

        final String[] viewBeanNames = applicationContext
                .getBeanNamesForAnnotation(SpringView.class);
        for (String beanName : viewBeanNames) {
            final Class<?> type = applicationContext.getType(beanName);
            if (View.class.isAssignableFrom(type)) {
                final SpringView annotation = AnnotatedElementUtils.findMergedAnnotation(type, SpringView.class);
                final String viewName = getViewNameFromAnnotation(type,
                        annotation);
                LOGGER.debug("Found SpringView bean [{}] with view name [{}]",
                        beanName, viewName);
                if (applicationContext.isSingleton(beanName)) {
                    throw new IllegalStateException("SpringView bean ["
                            + beanName + "] must not be a singleton");
                }
                Set<String> beanNames = viewNameToBeanNamesMap.get(viewName);
                if (beanNames == null) {
                    beanNames = new ConcurrentSkipListSet<>();
                    viewNameToBeanNamesMap.put(viewName, beanNames);
                }
                beanNames.add(beanName);
                count++;
            } else {
                LOGGER.error("The view bean [{}] does not implement View",
                        beanName);
                throw new IllegalStateException("SpringView bean [" + beanName
                        + "] must implement View");
            }
        }
        if (count == 0) {
            LOGGER.warn("No SpringViews found");
        } else if (count == 1) {
            LOGGER.info("1 SpringView found");
        } else {
            LOGGER.info("{} SpringViews found", count);
        }
    }

    protected String getViewNameFromAnnotation(Class<?> beanClass,
                                               SpringView annotation) {
        return Conventions.deriveMappingForView(beanClass, annotation);
    }

    @Override
    public String getViewName(String viewAndParameters) {
        LOGGER.trace("Extracting view name from [{}]", viewAndParameters);
        String viewName = null;
        if (isViewNameValidForCurrentUI(viewAndParameters)) {
            viewName = viewAndParameters;
        } else {
            int lastSlash;
            String viewPart = viewAndParameters;
            while ((lastSlash = viewPart.lastIndexOf('/')) > -1) {
                viewPart = viewPart.substring(0, lastSlash);
                LOGGER.trace("Checking if [{}] is a valid view", viewPart);
                if (isViewNameValidForCurrentUI(viewPart)) {
                    viewName = viewPart;
                    break;
                }
            }
        }
        if (viewName == null) {
            LOGGER.trace("Found no view name in [{}]", viewAndParameters);
        } else {
            LOGGER.trace("[{}] is a valid view", viewName);
        }
        return viewName;
    }

    private boolean isViewNameValidForCurrentUI(String viewName) {
        final Set<String> beanNames = viewNameToBeanNamesMap.get(viewName);
        if (beanNames != null) {
            for (String beanName : beanNames) {
                if (isViewBeanNameValidForCurrentUI(beanName)) {
                    // if we have an access denied view, this is checked by
                    // getView()
                    return getAccessDeniedView() != null
                            || isAccessGrantedToBeanName(beanName);
                }
            }
        }
        return false;
    }

    private boolean isViewBeanNameValidForCurrentUI(String beanName) {
        try {
            final Class<?> type = applicationContext.getType(beanName);

            Assert.isAssignable(View.class, type,
                    "bean did not implement View interface");

            final UI currentUI = UI.getCurrent();
            final SpringView annotation = applicationContext
                    .findAnnotationOnBean(beanName, SpringView.class);

            Assert.notNull(annotation,
                    "class did not have a SpringView annotation");

            if (annotation.ui().length == 0) {
                LOGGER.trace(
                        "View class [{}] with view name [{}] is available for all UI subclasses",
                        type.getCanonicalName(),
                        getViewNameFromAnnotation(type, annotation));
            } else {
                Class<? extends UI> validUI = getValidUIClass(currentUI,
                        annotation.ui());
                if (validUI != null) {
                    LOGGER.trace(
                            "View class [%s] with view name [{}] is available for UI subclass [{}]",
                            type.getCanonicalName(),
                            getViewNameFromAnnotation(type, annotation),
                            validUI.getCanonicalName());
                } else {
                    return false;
                }
            }

            return true;
        } catch (NoSuchBeanDefinitionException ex) {
            return false;
        }
    }

    private Class<? extends UI> getValidUIClass(UI currentUI,
                                                Class<? extends UI>[] validUIClasses) {
        for (Class<? extends UI> validUI : validUIClasses) {
            if (validUI.isAssignableFrom(currentUI.getClass())) {
                return validUI;
            }
        }
        return null;
    }

    @Override
    public View getView(String viewName) {
        final Set<String> beanNames = viewNameToBeanNamesMap.get(viewName);
        if (beanNames != null) {
            for (String beanName : beanNames) {
                if (isViewBeanNameValidForCurrentUI(beanName)) {
                    return getViewFromApplicationContext(viewName, beanName);
                }
            }
        }
        LOGGER.warn("Found no view with name [{}]", viewName);
        return null;
    }

    private View getViewFromApplicationContext(String viewName, String beanName) {
        View view = null;
        if (isAccessGrantedToBeanName(beanName)) {
            final BeanDefinition beanDefinition = beanDefinitionRegistry
                    .getBeanDefinition(beanName);
            if (beanDefinition.getScope().equals(
                    ViewScopeImpl.VAADIN_VIEW_SCOPE_NAME)) {
                LOGGER.trace("View [{}] is view scoped, activating scope",
                        viewName);
                final ViewCache viewCache = ViewScopeImpl
                        .getViewCacheRetrievalStrategy().getViewCache(
                                applicationContext);
                viewCache.creatingView(viewName);
                try {
                    view = getViewFromApplicationContextAndCheckAccess(beanName);
                } finally {
                    viewCache.viewCreated(viewName, view);
                }
            } else {
                view = getViewFromApplicationContextAndCheckAccess(beanName);
            }
        }
        if (view != null) {
            return view;
        } else {
            return getAccessDeniedView();
        }
    }

    private View getViewFromApplicationContextAndCheckAccess(String beanName) {
        final View view = (View) applicationContext.getBean(beanName);
        if (isAccessGrantedToViewInstance(beanName, view)) {
            return view;
        } else {
            return null;
        }
    }

    private View getAccessDeniedView() {
        if (accessDeniedViewClass != null) {
            return applicationContext.getBean(accessDeniedViewClass);
        } else {
            return null;
        }
    }

    private boolean isAccessGrantedToBeanName(String beanName) {
        final UI currentUI = UI.getCurrent();
        final Map<String, ViewAccessControl> accessDelegates = applicationContext
                .getBeansOfType(ViewAccessControl.class);
        for (ViewAccessControl accessDelegate : accessDelegates.values()) {
            if (!accessDelegate.isAccessGranted(currentUI, beanName)) {
                LOGGER.debug(
                        "Access delegate [{}] denied access to view with bean name [{}]",
                        accessDelegate, beanName);
                return false;
            }
        }
        return true;
    }

    private boolean isAccessGrantedToViewInstance(String beanName, View view) {
        final UI currentUI = UI.getCurrent();
        final Map<String, ViewInstanceAccessControl> accessDelegates = applicationContext
                .getBeansOfType(ViewInstanceAccessControl.class);
        for (ViewInstanceAccessControl accessDelegate : accessDelegates
                .values()) {
            if (!accessDelegate.isAccessGranted(currentUI, beanName, view)) {
                LOGGER.debug("Access delegate [{}] denied access to view [{}]",
                        accessDelegate, view);
                return false;
            }
        }
        return true;
    }
}
