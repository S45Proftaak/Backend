package com.foodplanner.rest_service.logic.foodorder;

import com.foodplanner.rest_service.databasemodel.FoodOrder;

import java.util.ArrayList;
import java.util.List;

public class DateChecker {

    public List<String> checkDates(List<FoodOrder> orders, List<String> toCheckDates){
        List<String> foundDates = new ArrayList<>();
        for (FoodOrder order : orders){
            for (String date : toCheckDates){
                if(date.equals(order.getDate().toString())){
                    foundDates.add(date);
                }
            }
        }
        return foundDates;
    }
}
