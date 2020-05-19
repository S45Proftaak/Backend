package com.foodplanner.rest_service.dtos;

import com.foodplanner.rest_service.dtos.links.AdminLinks;
import com.foodplanner.rest_service.dtos.links.OrderLinks;
import com.foodplanner.rest_service.dtos.links.SecretaryLinks;

public class LinkDTO {

    OrderLinks orderLinks = new OrderLinks();
    SecretaryLinks secretaryLinks = new SecretaryLinks();
    AdminLinks adminLinks = new AdminLinks();

    public OrderLinks getOrderLinks() {
        return orderLinks;
    }

    public SecretaryLinks getSecretaryLinks() {
        return secretaryLinks;
    }

    public AdminLinks getAdminLinks() {
        return adminLinks;
    }
}
