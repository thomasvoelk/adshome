package com.example.application.view;

import com.example.application.*;
import com.vaadin.navigator.*;
import com.vaadin.spring.annotation.*;
import com.vaadin.ui.*;
import org.springframework.security.core.userdetails.*;

import javax.annotation.*;

@ViewConfig(viewName = HomeView.VIEW_NAME, messageKey = "view.home")
@ViewScope
public class HomeView extends VerticalLayout implements View {
    public static final String VIEW_NAME = "";

    private User user;

    public HomeView() {
//        this.user =
//                (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    @PostConstruct
    void init() {
        addComponents(new Label(ApplicationUi.getI18N().get("view.home")));
//        addComponent(new Label(user.getUsername()));
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
    }
}