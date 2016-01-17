package com.example;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ViewConfig {
    String messageKey();

    String uri();

    String displayName();

    CreateMode createMode() default CreateMode.ALWAYS_NEW;

    enum CreateMode {ALWAYS_NEW, LAZY_INIT, EAGER_INIT}
}