package com.example.application;

public class EventBus {

    public static void register(final Object listener) {
        ApplicationUi.getEventBus().register(listener);
    }

    public static void unregister(final Object listener) {
        ApplicationUi.getEventBus().unregister(listener);
    }

    public static void post(final Object event) {
        ApplicationUi.getEventBus().post(event);
    }
}