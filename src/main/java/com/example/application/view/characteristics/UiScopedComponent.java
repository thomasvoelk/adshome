package com.example.application.view.characteristics;

import com.vaadin.spring.annotation.*;

import javax.annotation.*;
import java.util.*;

@SpringComponent
@UIScope
public class UiScopedComponent {

    private Date date;

    @PostConstruct
    private void init() {
        date = new Date();
    }

    public String getDate() {
        return date.toString();
    }
}
