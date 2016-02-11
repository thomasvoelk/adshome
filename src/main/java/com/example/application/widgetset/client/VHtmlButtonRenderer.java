package com.example.application.widgetset.client;

import com.google.gwt.user.client.ui.*;
import com.vaadin.client.renderers.*;
import com.vaadin.client.widget.grid.*;

/**
 * Created by thomas on 11/02/16.
 */
public class VHtmlButtonRenderer extends ButtonRenderer {
    @Override
    public void render(RendererCellReference cell, String text, Button button) {
        if (text != null) {
            button.setHTML(text);
        }
        button.addStyleName("v-button");
        button.addStyleName("v-button-borderless");
        button.addStyleName("no-padding");
        button.setVisible(text != null);
    }

}
