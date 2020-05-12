package com.foodplanner.rest_service.dtos;

import java.io.Serializable;
import java.sql.Date;

public class UserByDateDTO implements Serializable {

    private String name;
    private Date date;
    private boolean toLate;

    public UserByDateDTO(String name, Date date, boolean toLate) {
        this.name = name;
        this.date = date;
        this.toLate = toLate;
    }

    public UserByDateDTO() {
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setToLate(boolean toLate) {
        this.toLate = toLate;
    }

    public boolean isToLate() {
        return toLate;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
