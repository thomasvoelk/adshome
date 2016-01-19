package com.example.application.view.unit;

import com.example.application.backend.*;
import com.vaadin.ui.*;
import org.vaadin.viritin.fields.*;
import org.vaadin.viritin.form.*;
import org.vaadin.viritin.layouts.*;

public class UnitDataForm extends AbstractForm<UnitData> {

    TextField name = new MTextField("Name");


    UnitDataForm(UnitData unitData) {
        setSizeUndefined();
        setEntity(unitData);
    }

    @Override
    protected Component createContent() {
        return new MVerticalLayout(
                new MFormLayout(
                        name
                ).withWidth(""),
                getToolbar()
        ).withWidth("");
    }

}
