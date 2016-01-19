package com.example.application.view;

import com.vaadin.server.*;
import org.vaadin.spring.i18n.*;
import org.vaadin.viritin.button.*;

public class I18nConfirmButton extends ConfirmButton {

    public I18nConfirmButton(I18N i18n, Resource icon, String confirmationText, ClickListener listener) {
        super(icon, confirmationText, listener);
        setCaptions(i18n);
    }

    public I18nConfirmButton(I18N i18n, String buttonCaption, String confirmationText, ClickListener listener) {
        super(buttonCaption, confirmationText, listener);
        setCaptions(i18n);
    }

    private void setCaptions(I18N i18n) {
        setOkCaption(i18n.get("confirm.ok"));
        setCancelCaption(i18n.get("confirm.cancel"));
        setConfirmWindowCaption(i18n.get("confirm.caption"));
    }
}
