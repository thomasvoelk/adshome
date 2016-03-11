package com.example.application.view.characteristics;

import com.example.application.view.*;
import com.vaadin.data.*;
import com.vaadin.navigator.*;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.*;
import org.vaadin.viritin.*;
import org.vaadin.viritin.fields.*;

import javax.annotation.*;
import java.util.*;

@ViewConfig(viewName = CharacteristicsView.VIEW_NAME, messageKey = "view.characteristics")
public class CharacteristicsView extends VerticalLayout implements View {
    public static final String VIEW_NAME = "characteristicsView";

    List<Characteristic> characteristics = new ArrayList<>();
    private MTable<Characteristic> table;
    private ListContainer<Characteristic> characteristicContainer;
    private Map<Object, Field> fields = new HashMap<>();
    private UiScopedComponent uic;

    @Autowired
    public CharacteristicsView(UiScopedComponent uic) {
        this.uic = uic;
    }

    @PostConstruct
    private void init() {
        removeAllComponents();
        table = new MTable<>(Characteristic.class);
        table.setCaption(uic.getDate());
        addComponent(table);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
        if (characteristics.isEmpty()) {
            Characteristic c = new Characteristic();
            characteristics.add(c);
        }
        characteristicContainer = new ListContainer<>(Characteristic.class, characteristics);
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
        table.setContainerDataSource(characteristicContainer);
        table.setVisibleColumns("value");
        table.setEditable(true);
        table.setSelectable(false);
    }

    private void onFieldFocus(Object itemId) {
        if (characteristicContainer.isLastId(itemId))
            characteristicContainer.addItem(new Characteristic());
        fields.get(itemId).focus();
    }


}
