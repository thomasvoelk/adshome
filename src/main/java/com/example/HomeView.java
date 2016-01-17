package com.example;

import com.vaadin.navigator.*;
import com.vaadin.spring.annotation.*;
import com.vaadin.ui.*;

import javax.annotation.*;

@SpringView(name = HomeView.VIEW_NAME)
public class HomeView extends VerticalLayout implements View {
    public static final String VIEW_NAME = "";

    @PostConstruct
    void init() {
        addComponent(new Label("This is the home view"));
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
    }
}