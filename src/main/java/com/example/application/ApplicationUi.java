package com.example.application;

import com.example.application.navigation.*;
import com.example.application.view.*;
import com.example.application.view.ticket.*;
import com.example.application.view.unit.*;
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

    private ApplicationLayout applicationLayout;
    private com.google.common.eventbus.EventBus eventBus;
    private I18N i18n;
    private SpringViewProvider viewProvider;
    private Navigator navigator;
    private NavigationBar navigationBar;

    @Autowired
    public ApplicationUi(I18N i18n, ApplicationLayout applicationLayout, @SuppressWarnings("SpringJavaAutowiringInspection") SpringViewProvider viewProvider, NavigationBar navigationBar) {
        this.i18n = i18n;
        this.applicationLayout = applicationLayout;
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
        setContent(applicationLayout);
        registerViews();
        setupNavigator();
    }

    @Subscribe
    public void navigateTo(NavigationEvent view) {
        getNavigator().navigateTo(view.getViewName());
    }

    @Subscribe
    public void logout(LogoutEvent logoutEvent) {
        VaadinSession.getCurrent().getSession().invalidate();
        VaadinSession.getCurrent().close();
        Page.getCurrent().reload();
    }

    private void setupEventBus() {
        eventBus = new com.google.common.eventbus.EventBus((throwable, subscriberExceptionContext) -> {
            //FIXME: log error
            throwable.printStackTrace();
        });
        eventBus.register(this);
    }

    private void registerViews() {
        registerView(HomeView.class);
        registerView(UnitView.class);
        registerView(TicketView.class);
    }

    private void setupNavigator() {
        navigator = new Navigator(this, applicationLayout.getContentArea());
        navigator.addProvider(viewProvider);
        navigator.addViewChangeListener(navigationBar);
        navigator.addViewChangeListener(new PageTitleUpdater(i18n));
        navigator.setErrorView(ErrorView.class);
        navigator.navigateTo(navigator.getState());
    }


    private void registerView(Class<? extends View> viewClass) {
        ViewConfig viewConfig = viewClass.getAnnotation(ViewConfig.class);
        navigationBar.addView(viewConfig.viewName(), i18n.get(viewConfig.messageKey()));
    }


}
