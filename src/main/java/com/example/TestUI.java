package com.example;

import com.vaadin.server.*;
import com.vaadin.spring.annotation.*;
import com.vaadin.ui.*;

//@SpringUI
public class TestUI extends UI {
    @Override
    protected void init(VaadinRequest vaadinRequest) {
        HorizontalLayout content = new HorizontalLayout();
        setContent(new TestLayout(new NavigationBar()));


        content.addComponent(new Tree("Major Planets and Their Moons"));
        content.addComponent(new Panel("Panel"));
    }
}
