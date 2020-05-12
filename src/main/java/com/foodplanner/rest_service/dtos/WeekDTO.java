package com.foodplanner.rest_service.dtos;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class WeekDTO {
    private List<Date> dates;

    public WeekDTO(){
        dates = new ArrayList<>();
    }

    public List<Date> getDates() {
        return dates;
    }
}
