package com.example.techmasterpi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ui.freemarker.FreeMarkerConfigurationFactoryBean;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

@Configuration
public class freemarkerConfiguration {

    @Bean
    public FreeMarkerConfigurationFactoryBean freemarkerConfig() {
        FreeMarkerConfigurationFactoryBean freemarkerConfiguration = new FreeMarkerConfigurationFactoryBean();
        freemarkerConfiguration.setTemplateLoaderPath("classpath:/templates/");
        freemarkerConfiguration.setDefaultEncoding("UTF-8");
        return freemarkerConfiguration;
    }

    @Bean
    public FreeMarkerConfigurer freeMarkerConfi() {
        FreeMarkerConfigurer freeMarkerConfigurer = new FreeMarkerConfigurer();
        freeMarkerConfigurer.setConfiguration(freemarkerConfig().getObject());
        return freeMarkerConfigurer;
    }

}

