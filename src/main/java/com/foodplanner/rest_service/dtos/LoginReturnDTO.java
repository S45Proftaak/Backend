package com.foodplanner.rest_service.dtos;

public class LoginReturnDTO {

    String token;

    public LinkDTO links = new LinkDTO();

    public LoginReturnDTO(){
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public LinkDTO getLinks() {
        return links;
    }
}
