package com.example.application.view.polling;

import java.util.*;
import java.util.concurrent.*;

public class DetectionRestTemplate {

    public Long start(String params) {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return new Random().nextLong();
    }

    public boolean isCompleted(Long id) {
        return Math.random() < 0.2;
    }

}
