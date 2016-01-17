package com.example;

import com.vaadin.navigator.*;
import com.vaadin.server.*;
import com.vaadin.ui.*;

public class ErrorView extends VerticalLayout implements View {
    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        setSizeFull();
        setMargin(true);
        Label label = new Label(ApplicationUi.getI18N().get("view.error.text"));
        label.addStyleName(AdsTheme.LABEL_FAILURE);
        addComponent(label);
        setComponentAlignment(label, Alignment.MIDDLE_CENTER);
        Page.getCurrent().setTitle(ApplicationUi.getI18N().get("view.error"));
    }
}
