package com.example;

import com.vaadin.navigator.*;
import com.vaadin.server.*;
import com.vaadin.shared.ui.label.*;
import com.vaadin.spring.annotation.*;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.*;

import java.util.*;

@UIScope
@SpringComponent
public class NavigationBar extends CssLayout implements ViewChangeListener {

    private Map<String, Button> buttonMap = new HashMap<>();
    private VerticalLayout layout;

    public NavigationBar() {
        setHeight("100%");
        layout = new VerticalSpacedLayout();
        addComponent(layout);
        layout.addComponent(new Label("<strong>ADS</strong>", ContentMode.HTML));
        addLogoutButton();
    }

    public void addView(String viewName, String caption) {
        Button button = new Button(caption, click -> EventBus.post(new NavigationEvent(viewName)));
        button.addStyleName(ValoTheme.BUTTON_SMALL);
        button.addClickListener(event -> getUI().getNavigator().navigateTo(viewName));
        button.setIcon(FontAwesome.HOME);
        buttonMap.put(viewName, button);
        layout.addComponent(button, layout.getComponentCount() - 1);

    }

    @Override
    public boolean beforeViewChange(ViewChangeEvent event) {
        return true;
    }

    @Override
    public void afterViewChange(ViewChangeEvent event) {
        //buttonMap.values().forEach(button -> button.removeStyleName(MyTheme.SELECTED));
        Button button = buttonMap.get(event.getViewName());
        //if (button != null) button.addStyleName(MyTheme.SELECTED);
    }



    private void addLogoutButton() {
        Button logout = new Button("Log out", click -> EventBus.post(new LogoutEvent()));
        layout.addComponent(logout);
        logout.addStyleName(ValoTheme.BUTTON_SMALL);
        logout.setIcon(FontAwesome.SIGN_OUT);
    }
}