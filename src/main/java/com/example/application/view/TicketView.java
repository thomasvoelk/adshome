package com.example.application.view;

import com.example.application.*;
import com.vaadin.navigator.*;
import com.vaadin.spring.annotation.*;
import com.vaadin.ui.*;

import javax.annotation.*;

@ViewConfig(viewName = TicketView.VIEW_NAME, messageKey = "view.ticket")
@SpringView(name = TicketView.VIEW_NAME)
public class TicketView extends HorizontalLayout implements View {
    public static final String VIEW_NAME = "ticketView";


    @PostConstruct
    void init() {
        addComponents(new Label(ApplicationUi.getI18N().get("view.ticket")));
    }
    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
    }
}
