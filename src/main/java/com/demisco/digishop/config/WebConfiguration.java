package com.demisco.digishop.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfiguration implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/home").setViewName("/home");
        registry.addViewController("/login").setViewName("/person/login");
        registry.addViewController("/person/user_management").setViewName("/person/user_management");

    }
}
