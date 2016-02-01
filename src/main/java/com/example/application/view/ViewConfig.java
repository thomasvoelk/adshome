package com.example.application.view;

import com.vaadin.spring.annotation.*;
import org.springframework.core.annotation.*;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@SpringView
public @interface ViewConfig {
    String messageKey();

    @AliasFor(annotation = SpringView.class, attribute = "name")
    String viewName();
}