package com.example.application.view.characteristics;

import com.example.application.view.*;
import com.vaadin.data.*;
import com.vaadin.data.util.*;
import com.vaadin.navigator.*;
import com.vaadin.ui.*;

import javax.annotation.*;
import java.util.*;

@ViewConfig(viewName = CharacteristicsView.VIEW_NAME, messageKey = "view.characteristics")
public class CharacteristicsView extends VerticalLayout implements View {
    public static final String VIEW_NAME = "characteristicsView";

    List<Characteristic> characteristics = new ArrayList<>();
    private Table table;
    private BeanItemContainer<Characteristic> beanItemContainer;
    private Map<Object, Field> fields = new HashMap<>();

    @PostConstruct
    private void init() {
        removeAllComponents();
        table = new Table("Test");
        addComponent(table);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
        if (characteristics.isEmpty()) {
            Characteristic c = new Characteristic();
            characteristics.add(c);
        }

        for (Characteristic characteristic : characteristics) {
            table.addItem(characteristic);
        }
        beanItemContainer = new BeanItemContainer<>(Characteristic.class, characteristics);
        table.setTableFieldFactory(new DefaultFieldFactory() {
            @Override
            public Field createField(Container container, Object itemId, Object propertyId, Component uiContext) {
                TextField field = new TextField();
                field.addFocusListener(event -> onFieldFocus(itemId));
                fields.put(itemId, field);
                field.setInputPrompt("Please enter a value");
                return field;
            }
        });
        table.setContainerDataSource(beanItemContainer);
        table.setVisibleColumns("value");
        table.setEditable(true);
        table.setSelectable(false);
    }

    private void onFieldFocus(Object itemId) {
        if (beanItemContainer.isLastId(itemId))
            beanItemContainer.addItem(new Characteristic());
        fields.get(itemId).focus();
    }


}
