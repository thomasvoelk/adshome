package com.example;

import com.vaadin.navigator.*;
import com.vaadin.shared.ui.label.*;
import com.vaadin.spring.annotation.*;
import com.vaadin.ui.*;

@ViewConfig(viewName = TicketView.VIEW_NAME, messageKey = "view.ticket")
@SpringView(name = TicketView.VIEW_NAME)
public class TicketView extends HorizontalLayout implements View {
    public static final String VIEW_NAME = "ticketView";

    public TicketView() {
        Label caption = new Label("Welcome, ");
        Label description = new Label(
                ApplicationUi.getI18N().get("test"), ContentMode.HTML);
        addComponents(caption, description);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {

    }
}
