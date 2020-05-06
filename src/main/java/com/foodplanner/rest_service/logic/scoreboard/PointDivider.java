package com.foodplanner.rest_service.logic.scoreboard;

import org.springframework.beans.factory.annotation.Autowired;

public class PointDivider {
    private static final Long IN_TIME = 10L;
    private static final Long TOO_LATE = 4L;

    public void addPoints(boolean tooLate, Long currPoints){
        if(tooLate){
            currPoints += TOO_LATE;
        }else{
            currPoints += IN_TIME;
        }
    }
}
