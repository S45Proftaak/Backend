package com.foodplanner.rest_service.dtos.links;

import com.foodplanner.rest_service.endpoints.OrderEndpoint;

public class OrderLinks {
    public String BASE = OrderEndpoint.BASE;
    public String ALL_ORDERS = OrderEndpoint.ALL_ORDERS;
    public String ORDERS_PER_WEEK = OrderEndpoint.ORDERS_PER_WEEK;
    public String ADD_ORDER = OrderEndpoint.ADD_ORDER;
    public String GET_CURRENT_PRICE = OrderEndpoint.GET_CURRENT_PRICE;
    public String GET_USERS_BY_DATE = OrderEndpoint.GET_USERS_BY_DATE;
}
