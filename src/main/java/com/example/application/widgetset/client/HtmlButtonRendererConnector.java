package com.example.application.widgetset.client;

import com.example.application.widgetset.*;
import com.vaadin.client.connectors.*;
import com.vaadin.shared.ui.*;

/**
 * Created by thomas on 11/02/16.
 */
@Connect(HtmlButtonRenderer.class)
public class HtmlButtonRendererConnector extends ButtonRendererConnector {
    @Override
    public VHtmlButtonRenderer getRenderer() {
        return (VHtmlButtonRenderer) super.getRenderer();
    }
}
