package com.example.application;

import com.example.application.navigation.*;
import com.example.application.styling.*;
import com.vaadin.navigator.*;
import com.vaadin.spring.annotation.*;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.*;

import javax.annotation.*;

@UIScope
@SpringView(name = ApplicationLayout.VIEW_NAME)
public class ApplicationLayout extends HorizontalLayout implements View {

    public static final String VIEW_NAME = "layout";
    protected HorizontalLayout contentArea;
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

    private void styleContentArea() {
        contentArea.setSizeFull();
        contentArea.addStyleName(AdsTheme.PANEL_BORDERLESS);
    }


    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {

    }
}