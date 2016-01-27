package com.example.application.view.ticket;

import com.example.application.backend.*;
import com.example.application.view.*;
import com.vaadin.ui.*;
import org.vaadin.spring.i18n.*;
import org.vaadin.viritin.fields.*;
import org.vaadin.viritin.layouts.*;

public class TicketForm extends I18nAbstractForm<UnitData> {

    TextField unitName;
    TextField unitCode;


    TicketForm(UnitData unitData, I18N i18n) {
        super(i18n);
        unitName = new MTextField(i18n.get("view.unit.form.name"));
        unitCode = new MTextField(i18n.get("view.unit.form.code"));
        setSizeUndefined();
        setEntity(unitData);
    }

    @Override
    protected Component createContent() {
        return new MVerticalLayout(
                new MFormLayout(
                        unitCode,
                        unitName
                ).withWidth(""),
                getToolbar()
        ).withWidth("");
    }

}
