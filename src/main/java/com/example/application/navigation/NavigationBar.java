package com.example.application.navigation;

import com.example.application.*;
import com.example.application.EventBus;
import com.example.application.backend.*;
import com.example.application.styling.*;
import com.google.common.eventbus.*;
import com.vaadin.navigator.*;
import com.vaadin.server.*;
import com.vaadin.spring.annotation.*;
import com.vaadin.ui.*;
import com.vaadin.ui.MenuBar.*;
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
    private MenuBar.MenuItem settingsItem;


    @Autowired
    public NavigationBar(I18N i18n) {
        this.i18n = i18n;
    }

    @PostConstruct
    private void init() {
        createMenuArea();
        addStyling();
        addLogo();
        addUserMenu();
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
        button.setPrimaryStyleName(AdsTheme.MENU_ITEM);
        //button.addStyleName(AdsTheme.BUTTON_BORDERLESS);
        buttonMap.put(viewName, button);
        menuArea.addComponent(button, menuArea.getComponentCount() - 1);

    }


    private void createMenuArea() {
        menuArea = new VerticalSpacedLayout();
        menuArea.setStyleName("ads-menu-area");
        addComponent(menuArea);
    }

    private void addStyling() {
        setHeight("100%");
        addStyleName(AdsTheme.MENU_ROOT);
        addStyleName(AdsTheme.NAVIGATION_BAR);
    }


    private void addLogo() {
        Label logo = new Label(i18n.get("application.subtitle"));
        logo.setSizeUndefined();
        HorizontalLayout logoWrapper = new HorizontalLayout(logo);
        logoWrapper.setComponentAlignment(logo, Alignment.MIDDLE_CENTER);
        logoWrapper.addStyleName(AdsTheme.MENU_TITLE);
        menuArea.addComponent(logoWrapper);
//        Label sublogo = new Label(i18n.get("application.subtitle"));
//        sublogo.setSizeUndefined();
//        HorizontalLayout sublogoWrapper = new HorizontalLayout(sublogo);
//        sublogoWrapper.setComponentAlignment(sublogo, Alignment.MIDDLE_CENTER);
//        sublogoWrapper.addStyleName(AdsTheme.MENU_SUBTITLE);
//        menuArea.addComponent(sublogoWrapper);
    }

    private void addUserMenu() {
        final MenuBar settings = new MenuBar();
        settings.addStyleName("user-menu");
        final AdsUser user = getCurrentUser();
        settingsItem = settings.addItem("", new ThemeResource(
                "img/profile-pic-300px.jpg"), null);
        updateUserName(null);
        settingsItem.addItem("Edit Profile", new Command() {
            @Override
            public void menuSelected(final MenuBar.MenuItem selectedItem) {
                // ProfilePreferencesWindow.open(user, false);
            }
        });
        settingsItem.addItem("Preferences", new Command() {
            @Override
            public void menuSelected(final MenuItem selectedItem) {
                //ProfilePreferencesWindow.open(user, true);
            }
        });
        settingsItem.addSeparator();
        settingsItem.addItem("Sign Out", new Command() {
            @Override
            public void menuSelected(final MenuItem selectedItem) {
                EventBus.post(new UserLoggedOutEvent());
            }
        });
        menuArea.addComponent(settings);
    }

    private AdsUser getCurrentUser() {
        return new AdsUser("Thomas");
    }


    private void addLogoutButton() {
        Button logout = new Button("Log out", click -> EventBus.post(new LogoutEvent()));
        menuArea.addComponent(logout);
        logout.addStyleName(AdsTheme.BUTTON_LOGOUT);
        logout.addStyleName(AdsTheme.BUTTON_BORDERLESS);
        logout.setIcon(FontAwesome.SIGN_OUT);
    }

    @Subscribe
    public void updateUserName(ProfileUpdatedEvent event) {
        AdsUser user = getCurrentUser();
        settingsItem.setText(user.getName());
    }
}