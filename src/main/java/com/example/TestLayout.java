package com.example;

import com.vaadin.navigator.*;
import com.vaadin.server.*;
import com.vaadin.spring.annotation.*;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.*;

//@UIScope
//@SpringView(name = TestLayout.VIEW_NAME)
public class TestLayout extends HorizontalLayout implements View {

    public static final String VIEW_NAME = "layout";
    private NavigationBar navBar;
    protected Panel content;


//    @Autowired
    public TestLayout(NavigationBar navigationBar) {
        this.navBar = navigationBar;
        setSizeFull();
        initLayouts();
    }

    private void initLayouts() {
        VerticalSpacedLayout l = new VerticalSpacedLayout();
        l.addComponent(new Label("Test"));
        l.setWidthUndefined();
        navBar.addView(HomeView.VIEW_NAME, "Home");
        navBar.addView(UnitView.VIEW_NAME, "Units");
        navBar.addView(TicketView.VIEW_NAME, "Tickets");
        content = new Panel();
        content.setSizeFull();
        addComponents(l, content);
        //setExpandRatio(content, 1);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {

    }
}