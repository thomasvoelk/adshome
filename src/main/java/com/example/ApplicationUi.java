package com.example;

import com.google.common.eventbus.*;
import com.vaadin.annotations.*;
import com.vaadin.navigator.*;
import com.vaadin.server.*;
import com.vaadin.spring.annotation.*;
import com.vaadin.spring.navigator.*;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.*;
import org.vaadin.spring.i18n.*;

@Theme("ads")
@SpringUI
public class ApplicationUi extends UI {

    private final ApplicationLayout layout;
    private com.google.common.eventbus.EventBus eventBus;
    private I18N i18n;
    private SpringViewProvider viewProvider;
    private Navigator navigator;
    private NavigationBar navigationBar;

    @Autowired
    public ApplicationUi(I18N i18n, ApplicationLayout layout, SpringViewProvider viewProvider, NavigationBar navigationBar) {
        this.i18n = i18n;
        this.layout = layout;
        this.viewProvider = viewProvider;
        this.navigationBar = navigationBar;
    }

    public static ApplicationUi getCurrent() {
        return (ApplicationUi) UI.getCurrent();
    }

    public static com.google.common.eventbus.EventBus getEventBus() {
        return getCurrent().eventBus;
    }

    public static I18N getI18N() {
        return getCurrent().i18n;
    }


    @Override
    protected void init(VaadinRequest vaadinRequest) {
        setupEventBus();
        setContent(layout);
        setupNavigator();
        Page.getCurrent().setTitle(i18n.get("application.title"));
    }

    private void setupNavigator() {
        navigator = new Navigator(this, layout.content);
        navigator.addProvider(viewProvider);
        navigator.addViewChangeListener(navigationBar);
    }

    private void setupEventBus() {
        eventBus = new com.google.common.eventbus.EventBus((throwable, subscriberExceptionContext) -> {
            // log error
            throwable.printStackTrace();
        });
        eventBus.register(this);
    }

    @Subscribe
    public void navigateTo(NavigationEvent view) {
        getNavigator().navigateTo(view.getViewName());
    }
}
