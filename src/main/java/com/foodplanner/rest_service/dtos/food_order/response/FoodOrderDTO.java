package com.foodplanner.rest_service.dtos.food_order.response;

import org.springframework.hateoas.RepresentationModel;

import java.util.List;

public class FoodOrderDTO extends RepresentationModel<FoodOrderDTO> {

    private List<String> dates;

    public FoodOrderDTO(List<String> dates) {
        this.dates = dates;
    }

    public FoodOrderDTO() {
    }

    public List<String> getDates() {
        return dates;
    }

    public void setDates(List<String> dates) {
        this.dates = dates;
    }
}
