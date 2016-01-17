package com.example.application.view;

import com.example.application.*;
import com.vaadin.navigator.*;
import com.vaadin.spring.annotation.*;
import com.vaadin.ui.*;

import javax.annotation.*;

@ViewConfig(viewName = UnitView.VIEW_NAME, messageKey = "view.unit")
@SpringView(name = UnitView.VIEW_NAME)
public class UnitView extends HorizontalLayout implements View {
    public static final String VIEW_NAME = "unitView";

    @PostConstruct
    void init() {
        addComponents(new Label(ApplicationUi.getI18N().get("view.unit")));
    }
    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
    }
}
