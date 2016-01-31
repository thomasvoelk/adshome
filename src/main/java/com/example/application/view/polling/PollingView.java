package com.example.application.view.polling;

import com.example.application.*;
import com.example.application.EventBus;
import com.example.application.styling.*;
import com.example.application.view.*;
import com.google.common.eventbus.*;
import com.vaadin.navigator.*;
import com.vaadin.spring.annotation.*;
import com.vaadin.ui.*;

import java.util.concurrent.*;

@SpringView(name = PollingView.VIEW_NAME)
@ViewConfig(viewName = PollingView.VIEW_NAME, messageKey = "view.polling")
public class PollingView extends CustomComponent implements View {

    public static final String VIEW_NAME = "pollingView";
    Label label = new Label();
    Button oldSchoolButton = new Button("Start (Old School)", clickEvent -> oldSchoolButtonClick());
    Button futureButton = new Button("Start (Future)", clickEvent -> futureButtonClick());

    public PollingView() {
        VerticalSpacedLayout layout = new VerticalSpacedLayout();
        layout.addComponent(label);
        label.setValue("Idle");
        layout.addComponent(oldSchoolButton);
        layout.addComponent(futureButton);
        setCompositionRoot(layout);
        EventBus.register(this);
    }


    private void oldSchoolButtonClick() {
        label.setValue("Running...");
        getUI().setPollInterval(1000);
        new Thread(new Loader()).start();

    }

    @Subscribe
    public void onLongTaskFinished(LongTaskFinishedEvent event) {
        label.setValue("Task ID: " + event.getId());
        getUI().setPollInterval(-1);
    }

    private void futureButtonClick() {
        getUI().setPollInterval(1000);
        label.setValue("Running...");
        DetectionService service = new DetectionService();
        CompletableFuture<Long> startExecution = CompletableFuture.supplyAsync(() -> service.startDetection(new DetectionRequest("Unit1", "Scenario2", new ExecutionPeriod(4711, 8877))));
        startExecution.exceptionally(throwable -> {
            throwable.printStackTrace();
            getUI().setPollInterval(-1);
            return -1L;
        });
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {

    }

    class Loader implements Runnable {

        @Override
        public void run() {
            try {
                Thread.sleep(4500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            getUI().access(() -> {
                label.setValue("Finished");
                ApplicationUi.getCurrent().setPollInterval(-1);
            });
        }
    }
}
