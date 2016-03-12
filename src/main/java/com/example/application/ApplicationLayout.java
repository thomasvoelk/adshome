package com.example.application;

import com.example.application.header.*;
import com.example.application.navigation.*;
import com.vaadin.spring.annotation.*;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.*;

import javax.annotation.*;

@UIScope
@SpringComponent
public class ApplicationLayout extends VerticalLayout {

    private ContentArea contentArea;
    private NavigationBar navigationBar;
    private HeaderBar headerBar = new HeaderBar();

    @Autowired
    public ApplicationLayout(NavigationBar navigationBar, ContentArea contentArea) {
        this.navigationBar = navigationBar;
        this.contentArea = contentArea;
    }

    @PostConstruct
    private void init() {
        setSizeFull();
        HorizontalLayout horizontalLayout = new HorizontalLayout(navigationBar, contentArea);
        horizontalLayout.setHeight("100%");
        addComponents(headerBar, horizontalLayout);
        setExpandRatio(horizontalLayout, 1);
        horizontalLayout.setExpandRatio(contentArea, 1);
    }

    protected ComponentContainer getContentArea() {
        return this.contentArea;
    }


}