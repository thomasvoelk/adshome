package com.example.application;

import com.example.application.view.*;
import com.vaadin.navigator.*;
import org.springframework.beans.*;
import org.springframework.beans.factory.config.*;
import org.springframework.beans.factory.support.*;
import org.springframework.context.*;
import org.springframework.context.annotation.*;

@Configuration
public class AppConfig implements ApplicationContextAware,
        BeanDefinitionRegistryPostProcessor {

    private ApplicationContext applicationContext;
    private BeanDefinitionRegistry beanDefinitionRegistry;

    @Bean
    @Primary
    ViewProvider myviewProvider() {
        return new AdsViewProvider(applicationContext,
                beanDefinitionRegistry);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void postProcessBeanDefinitionRegistry(
            BeanDefinitionRegistry registry) throws BeansException {
        beanDefinitionRegistry = registry;
    }

    @Override
    public void postProcessBeanFactory(
            ConfigurableListableBeanFactory beanFactory) throws BeansException {
        // NOP
    }

}
