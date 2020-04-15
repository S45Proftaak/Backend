package com.foodplanner.rest_service.logic.foodorder;

import com.foodplanner.rest_service.databasemodel.FoodOrder;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class DateChecker {

    public List<Date> checkDates(List<FoodOrder> orders, List<Date> toCheckDates){
        List<Date> foundDates = new ArrayList<>();
        for (FoodOrder order : orders){
            for (Date date : toCheckDates){
                if(date.equals(order.getDate())){
                    foundDates.add(date);
                }
            }
        }
        return foundDates;
    }
}
