package com.example.application;

import com.example.application.navigation.NavigationBar;
import com.example.application.styling.HorizontalSpacedLayout;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.HorizontalLayout;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

@UIScope
@SpringComponent
public class ApplicationLayout extends HorizontalLayout {

    private HorizontalLayout contentArea;
    private NavigationBar navigationBar;

    @Autowired
    public ApplicationLayout(NavigationBar navigationBar) {
        this.navigationBar = navigationBar;
        this.contentArea = new HorizontalSpacedLayout();
    }

    @PostConstruct
    private void init() {
        setSizeFull();
        styleContentArea();
        addComponents(navigationBar, contentArea);
        setExpandRatio(contentArea, 1);
    }

    protected ComponentContainer getContentArea() {
        return this.contentArea;
    }

    private void styleContentArea() {
        contentArea.setSizeFull();
        contentArea.addStyleName(AdsTheme.PANEL_BORDERLESS);
    }
}