package com.example.application.view.unit;

import com.example.application.*;
import com.example.application.backend.*;
import com.example.application.view.*;
import com.vaadin.navigator.*;
import com.vaadin.server.*;
import com.vaadin.spring.annotation.*;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.*;
import org.vaadin.spring.i18n.*;
import org.vaadin.viritin.button.*;
import org.vaadin.viritin.fields.*;
import org.vaadin.viritin.layouts.*;

import javax.annotation.*;

@ViewConfig(viewName = UnitView.VIEW_NAME, messageKey = "view.unit")
@SpringView(name = UnitView.VIEW_NAME)
public class UnitView extends VerticalLayout implements View {
    public static final String VIEW_NAME = "unitView";

    private UnitDataRepository repository;
    private I18N i18n;
    private MTable<UnitData> unitDataTable;
    private Button addNew;
    private Button edit;
    private Button delete;

    @Autowired
    public UnitView(@SuppressWarnings("SpringJavaAutowiringInspection") UnitDataRepository repository, I18N i18n) {
        this.repository = repository;
        this.i18n = i18n;
    }

    @PostConstruct
    private void init() {
        createButtons();
        createTable();
        addComponent(createLayout());
        loadUnitData();
    }

    private Layout createLayout() {
        return new MVerticalLayout(
                new Label(i18n.get("view.unit")),
                new MHorizontalLayout(addNew, edit, delete),
                unitDataTable
        ).expand(unitDataTable);
    }

    private void createTable() {
        unitDataTable = new MTable<>(UnitData.class)
                .withProperties("unitCode", "unitName")
                .withColumnHeaders(i18n.get("view.unit.unitTable.column.code"), i18n.get("view.unit.unitTable.column.name"))
                .setSortableProperties("unitCode", "unitName")
                .withFullWidth();
        unitDataTable.addMValueChangeListener(e -> adjustActionButtonState());
    }

    private void createButtons() {
        addNew = new MButton(FontAwesome.PLUS, this::add);
        edit = new MButton(FontAwesome.PENCIL_SQUARE_O, this::edit);
        delete = new I18nConfirmButton(i18n,
                FontAwesome.TRASH_O,
                i18n.get("view.unit.deleteConfirmation"), this::remove);
    }

    private void add(Button.ClickEvent clickEvent) {
        edit(new UnitData());
    }

    private void edit(Button.ClickEvent clickEvent) {
        edit(unitDataTable.getValue());
    }

    private void remove(Button.ClickEvent clickEvent) {
        repository.delete(unitDataTable.getValue());
        unitDataTable.setValue(null);
        loadUnitData();
    }

    private void edit(UnitData unitData) {
        UnitDataForm unitDataForm = new UnitDataForm(unitData, i18n);
        unitDataForm.openInModalPopup();
        unitDataForm.setSavedHandler(this::saveEntry);
        unitDataForm.setResetHandler(this::resetEntry);
    }

    private void saveEntry(UnitData entry) {
        repository.save(entry);
        loadUnitData();
        closeWindow();
    }

    private void resetEntry(UnitData entry) {
        loadUnitData();
        closeWindow();
    }

    private void closeWindow() {
        ApplicationUi.getCurrent().getWindows().stream().forEach(w -> ApplicationUi.getCurrent().removeWindow(w));
    }


    private void loadUnitData() {
        unitDataTable.setBeans(repository.findAll());
        adjustActionButtonState();

    }

    private void adjustActionButtonState() {
        boolean hasSelection = unitDataTable.getValue() != null;
        edit.setEnabled(hasSelection);
        delete.setEnabled(hasSelection);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
    }
}
