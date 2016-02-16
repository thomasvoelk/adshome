package com.example.application.view.polling;

import com.example.application.*;
import com.example.application.EventBus;
import com.example.application.backend.*;
import com.example.application.styling.*;
import com.example.application.view.*;
import com.example.application.widgetset.*;
import com.google.common.eventbus.*;
import com.google.common.util.concurrent.*;
import com.vaadin.navigator.*;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.core.env.*;
import org.vaadin.viritin.grid.*;

import java.util.concurrent.*;

//@SpringView(name = ExecuteDetectionView.VIEW_NAME)
@ViewConfig(viewName = ExecuteDetectionView.VIEW_NAME, messageKey = "view.executeDetection")
public class ExecuteDetectionView extends CustomComponent implements View {

    public static final String VIEW_NAME = "pollingView";
    Label label = new Label();
    Button oldSchoolButton = new Button("Start (Old School)", clickEvent -> oldSchoolButtonClick());
    Button futureButton = new Button("Start (Future)", clickEvent -> futureButtonClick());
    MGrid<UnitData> unitGrid;
    private UnitDataRepository repository;
    private Environment environment;

    @Autowired
    public ExecuteDetectionView(UnitDataRepository repository, Environment environment) {
        this.repository = repository;
        VerticalSpacedLayout layout = new VerticalSpacedLayout();
        layout.addComponent(label);
        label.setValue("Idle");
        layout.addComponent(oldSchoolButton);
        layout.addComponent(futureButton);
        setCompositionRoot(layout);
        EventBus.register(this);
        addDetachListener(detachEvent -> EventBus.unregister(this));

        unitGrid = new MGrid<>(repository.findAll());
        unitGrid.getColumn("favorite").setRenderer(new HtmlButtonRenderer(), new BooleanToFontawesomeConverter());
        unitGrid.setEditorEnabled(true);
        layout.addComponent(unitGrid);
        String[] activeProfiles = environment.getActiveProfiles();
        if (activeProfiles.length > 0)
            layout.addComponent(new Label(activeProfiles[0]));
    }


    private void oldSchoolButtonClick() {
        label.setValue("Running...");
        ApplicationUi.getCurrent().setPollInterval(1000);
        new Thread(new Loader()).start();

    }

    @Subscribe
    public void onLongTaskFinished(LongTaskFinishedEvent event) {
        label.setValue("Task ID: " + event.getId());
        ApplicationUi.getCurrent().setPollInterval(-1);
    }

    private void futureButtonClick() {
        ApplicationUi.getCurrent().setPollInterval(1000);
        label.setValue("Running...");
        DetectionService service = new DetectionService();
        final ThreadFactory threadFactory = new ThreadFactoryBuilder()
                .setNameFormat("Detection-%d")
                .build();
        final ExecutorService pool = Executors.newFixedThreadPool(10, threadFactory);
        CompletableFuture<Long> startExecution =
                CompletableFuture.supplyAsync(() ->
                                service.startDetection(new DetectionRequest("Unit1", "Scenario2", new ExecutionPeriod(4711, 8877)))
                        , pool);
        startExecution.exceptionally(throwable -> {
            throwable.printStackTrace();
            ApplicationUi.getCurrent().setPollInterval(-1);
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
            ApplicationUi.getCurrent().access(() -> {
                label.setValue("Finished");
                ApplicationUi.getCurrent().setPollInterval(-1);
            });
        }
    }
}
