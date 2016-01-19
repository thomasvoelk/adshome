package com.example.application.view.unit;

import com.example.application.backend.*;
import com.example.application.view.*;
import com.vaadin.ui.*;
import org.vaadin.spring.i18n.*;
import org.vaadin.viritin.fields.*;
import org.vaadin.viritin.layouts.*;

public class UnitDataForm extends I18nAbstractForm<UnitData> {

    TextField name = new MTextField("Name");


    UnitDataForm(UnitData unitData, I18N i18n) {
        super(i18n);
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
