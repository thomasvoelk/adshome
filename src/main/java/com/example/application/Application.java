package com.example.application;

import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.boot.context.embedded.*;
import org.springframework.context.*;
import org.springframework.context.annotation.*;
import org.springframework.orm.jpa.support.*;
import org.vaadin.spring.i18n.*;

@SpringBootApplication
public class Application {

    @Autowired
    private ApplicationContext applicationContext;


    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
	}

    @Bean
    public FilterRegistrationBean openEntityManagerInViewFilter() {
        FilterRegistrationBean reg = new FilterRegistrationBean();
        reg.setName("OpenEntityManagerInViewFilter");
        reg.setFilter(new OpenEntityManagerInViewFilter());
        return reg;
    }


    @Bean
    public I18N i18n() {
        return new I18N(applicationContext);
    }

}
