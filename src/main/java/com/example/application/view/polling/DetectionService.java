package com.example.application.view.polling;

import com.example.application.*;

import java.util.concurrent.*;

public class DetectionService {

    public Long startDetection(DetectionRequest request) {
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
        return id;
    }

}
