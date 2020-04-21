package com.foodplanner.rest_service.logic.foodorder;

import com.foodplanner.rest_service.databasemodel.FoodOrder;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class DateChecker {

    public List<Date> checkDates(List<FoodOrder> orders, List<String> toCheckDates){
        List<Date> convertedDates = convertedDates(toCheckDates);
        List<Date> foundDates = new ArrayList<>();
        for (FoodOrder order : orders){
            for (Date date : convertedDates){
                if(date.equals(order.getDate())){
                    foundDates.add(date);
                }
            }
        }
        return foundDates;
    }

    public List<Date> convertedDates(List<String> dates){
        List<Date> convertedDates = new ArrayList<>();
        for(String string : dates){
                Date date = Date.valueOf(string);
                convertedDates.add(date);
        }
        return convertedDates;
    }
}
