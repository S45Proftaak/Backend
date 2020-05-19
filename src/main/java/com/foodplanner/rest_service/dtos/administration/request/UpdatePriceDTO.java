package com.foodplanner.rest_service.dtos.administration.request;

public class UpdatePriceDTO {
    private double price;

    public UpdatePriceDTO(double price) {
        this.price = price;
    }

    public UpdatePriceDTO() {
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
