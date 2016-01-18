package com.example.application;

import com.example.application.styling.*;
import com.vaadin.spring.annotation.*;

import javax.annotation.*;

@UIScope
@SpringComponent
public class ContentArea extends HorizontalSpacedLayout {

    @PostConstruct
    private void init() {
        setSizeFull();
        addStyleName(AdsTheme.PANEL_BORDERLESS);
    }
}
