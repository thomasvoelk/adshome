package com.example.application.view;

import com.example.application.*;
import com.vaadin.navigator.*;
import com.vaadin.ui.*;

import javax.annotation.*;

@ViewConfig(viewName = HomeView.VIEW_NAME, messageKey = "view.home")
public class HomeView extends VerticalLayout implements View {
    public static final String VIEW_NAME = "";

    @PostConstruct
    void init() {
        addComponents(new Label(ApplicationUi.getI18N().get("view.home")));
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
    }
}