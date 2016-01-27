package com.example.application.view.ticket;

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
import java.util.*;

@ViewConfig(viewName = TicketView.VIEW_NAME, messageKey = "view.ticket")
@SpringView(name = TicketView.VIEW_NAME)
public class TicketView extends HorizontalLayout implements View {
    public static final String VIEW_NAME = "ticketView";


    private TicketRepository repository;
    private I18N i18n;
    private MTable<Ticket> ticketTable;
    private Button addNew;
    private Button edit;
    private Button delete;

    @Autowired
    public TicketView(@SuppressWarnings("SpringJavaAutowiringInspection") TicketRepository repository, I18N i18n) {
        this.repository = repository;
        this.i18n = i18n;
    }

    @PostConstruct
    private void init() {
        createButtons();
        createTable();
        addComponent(createLayout());
        loadTickets();
    }

    private Layout createLayout() {
        return new MVerticalLayout(
                new Label(i18n.get("view.ticket")),
                new MHorizontalLayout(addNew, edit, delete),
                ticketTable
        ).expand(ticketTable);
    }

    private void createTable() {
        ticketTable = new MTable<>(Ticket.class)
                .withProperties("unit.unitName", "details")
                .withColumnHeaders("Unit", "details")
                .setSortableProperties("unit.unitName", "details")
                .withFullWidth();
        ticketTable.addMValueChangeListener(e -> adjustActionButtonState());
    }

    private void createButtons() {
        addNew = new MButton(FontAwesome.PLUS, this::add);
        edit = new MButton(FontAwesome.PENCIL_SQUARE_O, this::edit);
        delete = new I18nConfirmButton(i18n,
                FontAwesome.TRASH_O,
                i18n.get("view.unit.deleteConfirmation"), this::remove);
    }

    private void add(Button.ClickEvent clickEvent) {
        edit(new Ticket());
    }

    private void edit(Button.ClickEvent clickEvent) {
        edit(ticketTable.getValue());
    }

    private void remove(Button.ClickEvent clickEvent) {
        repository.delete(ticketTable.getValue());
        ticketTable.setValue(null);
        loadTickets();
    }

    private void edit(Ticket ticket) {
//        UnitDataForm unitDataForm = new UnitDataForm(unitData, i18n);
//        unitDataForm.openInModalPopup();
//        unitDataForm.setSavedHandler(this::saveEntry);
//        unitDataForm.setResetHandler(this::resetEntry);
    }

    private void saveEntry(Ticket entry) {
        repository.save(entry);
        loadTickets();
        closeWindow();
    }

    private void resetEntry(Ticket entry) {
        loadTickets();
        closeWindow();
    }

    private void closeWindow() {
        ApplicationUi.getCurrent().getWindows().stream().forEach(w -> ApplicationUi.getCurrent().removeWindow(w));
    }


    private void loadTickets() {
        List<Ticket> tickets = repository.findAll();
        for (Ticket ticket : tickets) {
            String test = ticket.getUnit().getUnitName();
        }
        ticketTable.setBeans(tickets);
        adjustActionButtonState();

    }

    private void adjustActionButtonState() {
        boolean hasSelection = ticketTable.getValue() != null;
        edit.setEnabled(hasSelection);
        delete.setEnabled(hasSelection);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
    }
}
