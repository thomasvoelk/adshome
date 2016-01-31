package com.example.application;

public class LongTaskFinishedEvent {

    private Long id;

    public LongTaskFinishedEvent(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
