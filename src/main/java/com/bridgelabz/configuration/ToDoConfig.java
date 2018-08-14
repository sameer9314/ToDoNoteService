package com.bridgelabz.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
/**
 * Purpose : Configuration class for application.
 * @author Sameer Saurabh
 * @version  1.0
 * @Since  31/07/2018
 */
@Configuration
public class ToDoConfig {
     @Bean
      public static PropertySourcesPlaceholderConfigurer propertyConfigurer() {
        Resource resource;
        String activeProfile;
        
        PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer =  new PropertySourcesPlaceholderConfigurer();
        
        // get active profile
        activeProfile = System.getProperty("spring.profiles.active");
      
     
        // choose different property files for different active profile
        if ("development".equals(activeProfile)) {
          resource = new ClassPathResource("/META_INF/application_development.properties");
          System.out.println(activeProfile+" profile selected");
        } else if ("test".equals(activeProfile)) {
          resource = new ClassPathResource("/META_INF/application_test.properties");
          System.out.println(activeProfile+" profile selected");
        } else {
          resource = new ClassPathResource("/META_INF/application_production.properties");
          System.out.println(activeProfile+" profile selected");
        }
        
        // load the property file
        propertySourcesPlaceholderConfigurer.setLocation(resource);
        
        return propertySourcesPlaceholderConfigurer;
      }
    }
