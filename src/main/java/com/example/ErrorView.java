package com.example;

import com.vaadin.navigator.*;
import com.vaadin.ui.*;

@ViewConfig(viewName = ErrorView.VIEW_NAME, messageKey = "view.error")
public class ErrorView extends VerticalLayout implements View {
    public static final String VIEW_NAME = "error";

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        setSizeFull();
        Label label = new Label(ApplicationUi.getI18N().get("view.error.text"));
        label.addStyleName(AdsTheme.LABEL_FAILURE);
        addComponent(label);
        setComponentAlignment(label, Alignment.MIDDLE_CENTER);
    }
}
