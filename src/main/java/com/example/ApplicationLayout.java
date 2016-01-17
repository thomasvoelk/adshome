package com.example;

import com.vaadin.navigator.*;
import com.vaadin.spring.annotation.*;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.*;
import org.vaadin.spring.i18n.*;

import javax.annotation.*;

@UIScope
@SpringView(name = ApplicationLayout.VIEW_NAME)
public class ApplicationLayout extends HorizontalLayout implements View {

    public static final String VIEW_NAME = "layout";
    protected Panel content;
    private NavigationBar navBar;
    private I18N i18n;

    @Autowired
    public ApplicationLayout(NavigationBar navigationBar, I18N i18n) {
        this.navBar = navigationBar;
        this.i18n = i18n;
    }

    @PostConstruct
    private void init() {
        setSizeFull();
        initLayouts();
    }

    private void initLayouts() {
        registerView(HomeView.class);
        registerView(UnitView.class);
        registerView(TicketView.class);
        content = new Panel();
        content.setSizeFull();
        content.addStyleName(MyTheme.PANEL_BORDERLESS);
        addComponents(navBar, content);
        setExpandRatio(content, 1);
    }

    private void registerView(Class<? extends View> viewClass) {
        ViewConfig viewConfig = viewClass.getAnnotation(ViewConfig.class);
        navBar.addView(viewConfig.viewName(), i18n.get(viewConfig.messageKey()));
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {

    }
}