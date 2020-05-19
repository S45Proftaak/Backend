package com.foodplanner.rest_service;

import com.foodplanner.rest_service.configuration.PriceConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.PropertySource;


@SpringBootApplication
@EnableConfigurationProperties(value = PriceConfiguration.class)
@PropertySource("classpath:settings.properties")
public class RestServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(RestServiceApplication.class, args);
    }

}
