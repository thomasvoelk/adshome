package com.example.application.header;

import com.vaadin.ui.*;

public class HeaderBar extends CssLayout {
    public HeaderBar() {
        setSizeUndefined();
        addStyleName("header-bar");
        CssLayout logo = new CssLayout();
        logo.setSizeUndefined();
        logo.addStyleName("header-bar-logo");
        addComponent(logo);
        CssLayout content = new CssLayout();
        content.setSizeUndefined();
        content.addStyleName("header-bar-content");
        addComponent(content);
    }
}
