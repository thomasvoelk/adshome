package com.example;

import com.vaadin.navigator.*;
import com.vaadin.spring.annotation.*;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.*;

@UIScope
@SpringView(name = ApplicationLayout.VIEW_NAME)
public class ApplicationLayout extends HorizontalLayout implements View {

    public static final String VIEW_NAME = "layout";
    protected Panel content;
    private NavigationBar navBar;


    @Autowired
    public ApplicationLayout(NavigationBar navigationBar) {
        this.navBar = navigationBar;
        setSizeFull();
        initLayouts();
    }

    private void initLayouts() {
        navBar.addView(HomeView.VIEW_NAME, "Home");
        navBar.addView(UnitView.VIEW_NAME, "Units");
        navBar.addView(TicketView.VIEW_NAME, "Tickets");
        content = new Panel();
        content.setSizeFull();
        content.addStyleName(MyTheme.PANEL_BORDERLESS);
        addComponents(navBar, content);
        setExpandRatio(content, 1);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {

    }
}