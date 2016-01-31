package com.example.application;

public class LongTaskFinishedEvent {

    private String message;

    public LongTaskFinishedEvent(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
