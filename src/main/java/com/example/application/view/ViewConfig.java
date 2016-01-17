package com.example.application.view;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ViewConfig {
    String messageKey();

    String viewName();
}