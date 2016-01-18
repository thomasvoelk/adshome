package com.example.application;

import com.example.application.navigation.*;
import com.vaadin.spring.annotation.*;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.*;

import javax.annotation.*;

@UIScope
@SpringComponent
public class ApplicationLayout extends HorizontalLayout {

    private ContentArea contentArea;
    private NavigationBar navigationBar;

    @Autowired
    public ApplicationLayout(NavigationBar navigationBar, ContentArea contentArea) {
        this.navigationBar = navigationBar;
        this.contentArea = contentArea;
    }

    @PostConstruct
    private void init() {
        setSizeFull();
        addComponents(navigationBar, contentArea);
        setExpandRatio(contentArea, 1);
    }

    protected ComponentContainer getContentArea() {
        return this.contentArea;
    }


}