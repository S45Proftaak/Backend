package com.foodplanner.rest_service.endpoints;

public interface OrderEndpoint {
    String BASE = "/foodorder";
    String ALL_ORDERS = "/all-orders-by-id";
    String ORDERS_PER_WEEK = "/all-orders-per-week";
    String ADD_ORDER = "/add-order";
    String GET_CURRENT_PRICE = "/getCurrentPrice";
}
