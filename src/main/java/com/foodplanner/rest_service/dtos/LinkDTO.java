package com.foodplanner.rest_service.dtos;

import com.foodplanner.rest_service.dtos.links.OrderLinks;
import com.foodplanner.rest_service.dtos.links.SecretaryLinks;

public class LinkDTO {

    OrderLinks orderLinks = new OrderLinks();
    SecretaryLinks secretaryLinks = new SecretaryLinks();

    public OrderLinks getOrderLinks() {
        return orderLinks;
    }

    public SecretaryLinks getSecretaryLinks() {
        return secretaryLinks;
    }
}
