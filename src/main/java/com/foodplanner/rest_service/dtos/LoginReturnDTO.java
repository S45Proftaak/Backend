package com.foodplanner.rest_service.dtos;

import org.springframework.hateoas.RepresentationModel;


public class LoginReturnDTO extends RepresentationModel<LoginReturnDTO> {

    String token;

    public LoginReturnDTO(){
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
