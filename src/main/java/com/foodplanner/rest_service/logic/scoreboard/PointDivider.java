package com.foodplanner.rest_service.logic.scoreboard;

public class PointDivider {
    private static final Long IN_TIME = 10L;
    private static final Long TOO_LATE = 4L;

    public Long addPointsTooLate(Long currPoints){
        return currPoints += TOO_LATE;
    }

    public Long addPointsInTime(Long currPoints){
        return currPoints += IN_TIME;
    }

    public Long removePointsTooLate(Long currPoints){
        return currPoints -= TOO_LATE;
    }

    public Long removePointsInTime(Long currPoints){
        return currPoints -= IN_TIME;
    }
}
