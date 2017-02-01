package ru.sergey.spring.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;

@Configuration
@ImportResource("classpath:app-context.xml")
@ComponentScan(basePackages= {"ru.sergey.spring"})
public class WebConfig  {
    private Environment environment;
    @Autowired
    public WebConfig(Environment environment) {
        this.environment = environment;
    }
}
