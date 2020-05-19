package com.foodplanner.rest_service.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "configuration")
public class PriceConfiguration {

    private double price;

    public PriceConfiguration(double price) {
        this.price = price;
    }

    public PriceConfiguration() {
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
