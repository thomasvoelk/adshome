package com.example;

import com.vaadin.navigator.*;
import com.vaadin.server.*;
import org.vaadin.spring.i18n.*;


public class PageTitleUpdater implements ViewChangeListener {

    private I18N i18n;

    public PageTitleUpdater(I18N i18n) {
        this.i18n = i18n;
    }

    @Override
    public boolean beforeViewChange(ViewChangeEvent event) {
        return true;
    }

    @Override
    public void afterViewChange(ViewChangeEvent event) {

        View view = event.getNewView();
        ViewConfig viewConfig = view.getClass().getAnnotation(ViewConfig.class);

        if (viewConfig != null) {
            Page.getCurrent().setTitle(String.format("%s - %s", i18n.get("application.title"), i18n.get(viewConfig.messageKey())));
        }

    }
}