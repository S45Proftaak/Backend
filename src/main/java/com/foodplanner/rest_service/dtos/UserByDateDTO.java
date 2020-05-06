package com.foodplanner.rest_service.dtos;

import java.io.Serializable;

public class UserByDateDTO implements Serializable {

    private String name;
    private boolean toLate;

    public UserByDateDTO(String name, boolean toLate) {
        this.name = name;
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
}
