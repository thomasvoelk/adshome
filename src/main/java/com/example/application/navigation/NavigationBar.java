package com.example.application.navigation;

import com.example.application.*;
import com.example.application.styling.*;
import com.vaadin.navigator.*;
import com.vaadin.server.*;
import com.vaadin.spring.annotation.*;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.*;
import org.vaadin.spring.i18n.*;

import javax.annotation.*;
import java.util.*;

@UIScope
@SpringComponent
public class NavigationBar extends CssLayout implements ViewChangeListener {

    private Map<String, Button> buttonMap = new HashMap<>();
    private VerticalLayout menuArea;
    private I18N i18n;

    @Autowired
    public NavigationBar(I18N i18n) {
        this.i18n = i18n;
    }

    @PostConstruct
    private void init() {
        createMenuArea();
        addStyling();
        addLogo();
        addLogoutButton();
    }


    @Override
    public boolean beforeViewChange(ViewChangeEvent event) {
        return true;
    }

    @Override
    public void afterViewChange(ViewChangeEvent event) {
        buttonMap.values().forEach(button -> button.removeStyleName(AdsTheme.SELECTED));
        Button button = buttonMap.get(event.getViewName());
        if (button != null) button.addStyleName(AdsTheme.SELECTED);
    }

    public void addView(String viewName, String caption) {
        Button button = new Button(caption, click -> EventBus.post(new NavigationEvent(viewName)));
        button.addStyleName(AdsTheme.MENU_ITEM);
        button.addStyleName(AdsTheme.BUTTON_BORDERLESS);
        buttonMap.put(viewName, button);
        menuArea.addComponent(button, menuArea.getComponentCount() - 1);

    }


    private void createMenuArea() {
        menuArea = new VerticalSpacedLayout();
        addComponent(menuArea);
    }

    private void addStyling() {
        setHeight("100%");
        addStyleName(AdsTheme.MENU_ROOT);
        addStyleName(AdsTheme.NAVIGATION_BAR);
    }


    private void addLogo() {
        Label logo = new Label(i18n.get("application.title"));
        logo.addStyleName(AdsTheme.MENU_TITLE);
        menuArea.addComponent(logo);
    }


    private void addLogoutButton() {
        Button logout = new Button("Log out", click -> EventBus.post(new LogoutEvent()));
        menuArea.addComponent(logout);
        logout.addStyleName(AdsTheme.BUTTON_LOGOUT);
        logout.addStyleName(AdsTheme.BUTTON_BORDERLESS);
        logout.setIcon(FontAwesome.SIGN_OUT);
    }
}