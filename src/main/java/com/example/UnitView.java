package com.example;

import com.vaadin.navigator.*;
import com.vaadin.shared.ui.label.*;
import com.vaadin.spring.annotation.*;
import com.vaadin.ui.*;

@ViewConfig(viewName = UnitView.VIEW_NAME, messageKey = "view.unit")
@SpringView(name = UnitView.VIEW_NAME)
public class UnitView extends HorizontalLayout implements View {
    public static final String VIEW_NAME = "unitView";

    public UnitView() {
        Label caption = new Label("Welcome, ");
        Label description = new Label(
                "This project contains a collection of tips and tricks that will hopefully make it easier and more fun for you to work with Vaadin. Please read the readme file at <a href='https://github.com/vaadin-marcus/vaadin-tips'>https://github.com/vaadin-marcus/vaadin-tips</a>.", ContentMode.HTML);
        addComponents(caption, description);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {

    }
}
