package com.foodplanner.rest_service.dtos;

import java.util.ArrayList;
import java.util.List;

public class LoginReturnDTO {

    String token;

    public List<LinkDTO> links;

    public LoginReturnDTO(){
        links = new ArrayList<>();
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void addLink(LinkDTO link) {
        links.add(link);
    }

    public String getToken() {
        return token;
    }

    public List<LinkDTO> getLinks() {
        return links;
    }
}
