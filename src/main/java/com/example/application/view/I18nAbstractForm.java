package com.example.application.view;

import com.vaadin.ui.*;
import org.vaadin.spring.i18n.*;
import org.vaadin.viritin.button.*;
import org.vaadin.viritin.form.*;

public abstract class I18nAbstractForm<T> extends AbstractForm<T> {


    protected I18N i18n;

    public I18nAbstractForm(I18N i18n) {
        this.i18n = i18n;
    }

    @Override
    protected Button createCancelButton() {
        return (new MButton(i18n.get("form.cancel"))).withVisible(false);
    }

    @Override
    protected Button createSaveButton() {
        return (new PrimaryButton(i18n.get("form.save"))).withVisible(false);
    }

    @Override
    protected Button createDeleteButton() {
        return (new DeleteButton(i18n.get("form.delete"))).withVisible(false);
    }

}
