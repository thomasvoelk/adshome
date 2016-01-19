package com.example.application.view.unit;

import com.example.application.*;
import com.example.application.backend.*;
import com.example.application.view.*;
import com.vaadin.navigator.*;
import com.vaadin.server.*;
import com.vaadin.spring.annotation.*;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.*;
import org.vaadin.viritin.button.*;
import org.vaadin.viritin.fields.*;
import org.vaadin.viritin.layouts.*;

import javax.annotation.*;

@ViewConfig(viewName = UnitView.VIEW_NAME, messageKey = "view.unit")
@SpringView(name = UnitView.VIEW_NAME)
public class UnitView extends VerticalLayout implements View {
    public static final String VIEW_NAME = "unitView";

    private UnitDataRepository repository;
    private MTable<UnitData> list = new MTable<>(UnitData.class)
            .withProperties("id", "name")
            .withColumnHeaders("id", "Name")
            .setSortableProperties("name")
            .withFullWidth();
    private Button addNew = new MButton(FontAwesome.PLUS, this::add);
    private Button edit = new MButton(FontAwesome.PENCIL_SQUARE_O, this::edit);
    private Button delete = new ConfirmButton(FontAwesome.TRASH_O,
            ApplicationUi.getI18N().get("view.unit.deleteConfirmation"), this::remove);

    @Autowired
    public UnitView(@SuppressWarnings("SpringJavaAutowiringInspection") UnitDataRepository repository) {
        this.repository = repository;
    }

    private void add(Button.ClickEvent clickEvent) {
        edit(new UnitData());
    }

    private void edit(Button.ClickEvent clickEvent) {
        edit(list.getValue());
    }

    private void remove(Button.ClickEvent clickEvent) {
        repository.delete(list.getValue());
        list.setValue(null);
        listEntities();
    }

    protected void edit(final UnitData unitData) {
        UnitDataForm unitDataForm = new UnitDataForm(
                unitData);
        unitDataForm.openInModalPopup();
        unitDataForm.setSavedHandler(this::saveEntry);
        unitDataForm.setResetHandler(this::resetEntry);
    }

    public void saveEntry(UnitData entry) {
        repository.save(entry);
        listEntities();
        closeWindow();
    }

    public void resetEntry(UnitData entry) {
        listEntities();
        closeWindow();
    }

    protected void closeWindow() {
        ApplicationUi.getCurrent().getWindows().stream().forEach(w -> ApplicationUi.getCurrent().removeWindow(w));
    }

    @PostConstruct
    void init() {

        addComponent(
                new MVerticalLayout(
                        new Label(ApplicationUi.getI18N().get("view.unit")),
                        new MHorizontalLayout(addNew, edit, delete),
                        list
                ).expand(list));
        listEntities();
        list.addMValueChangeListener(e -> adjustActionButtonState());
    }

    private void listEntities() {
        list.setBeans(repository.findAll());
        adjustActionButtonState();

    }

    protected void adjustActionButtonState() {
        boolean hasSelection = list.getValue() != null;
        edit.setEnabled(hasSelection);
        delete.setEnabled(hasSelection);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
    }
}
