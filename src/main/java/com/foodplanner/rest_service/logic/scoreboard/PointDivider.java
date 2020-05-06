package com.foodplanner.rest_service.logic.scoreboard;

public class PointDivider {
    private static final Long IN_TIME = 10L;
    private static final Long TOO_LATE = 4L;

    public Long addPointsAndReturn(boolean tooLate, Long currPoints){
        if(tooLate){
            currPoints += TOO_LATE;
        }else{
            currPoints += IN_TIME;
        }
        return currPoints;
    }
}
