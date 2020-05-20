package com.foodplanner.rest_service.dtos;

import java.sql.Date;

public class NewOrderDTO {

    public Date date;

    public NewOrderDTO(Date date){
        this.date = date;
    }

    public NewOrderDTO(){}

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
