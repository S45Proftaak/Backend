package com.foodplanner.rest_service.dtos.food_order.response;

import org.springframework.hateoas.RepresentationModel;

public class GetPriceDTO extends RepresentationModel<GetPriceDTO> {

    private double price;

    public GetPriceDTO() {
    }

    public GetPriceDTO(double price) {
        this.price = price;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
