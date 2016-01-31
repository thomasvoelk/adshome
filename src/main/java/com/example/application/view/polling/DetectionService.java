package com.example.application.view.polling;

import com.example.application.*;
import org.slf4j.*;

import java.util.concurrent.*;

public class DetectionService {

    private static final Logger log = LoggerFactory.getLogger(DetectionService.class);
    public Long startDetection(DetectionRequest request) {
        log.debug("Starting request '{}'", request);
        DetectionRestTemplate restTemplate = new DetectionRestTemplate();
        Long id = restTemplate.start(request.toString());
        while (!restTemplate.isCompleted(id)) {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        EventBus.post(new LongTaskFinishedEvent(id));
        log.debug("Finished");
        return id;
    }

}
