package com.foodplanner.rest_service.logictests;

import com.foodplanner.rest_service.logic.jwt.JwtTokenProvider;
import com.foodplanner.rest_service.logic.scoreboard.PointDivider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PointDividerTest {
    PointDivider pointDivider;
    Long currentPoints;

    @BeforeEach
    public void init(){
        pointDivider = new PointDivider();
        currentPoints = 10L;
    }

    @Test
    public void addPointsInTimeTest(){
        currentPoints = pointDivider.addPointsInTime(currentPoints);
        assertThat(currentPoints).isEqualTo(20);
    }

    @Test
    public void removePointsInTimeTest(){
        currentPoints = pointDivider.removePointsInTime(currentPoints);
        assertThat(currentPoints).isEqualTo(0);
    }

    @Test
    public void addPointsTooLateTest(){
        currentPoints = pointDivider.addPointsTooLate(currentPoints);
        assertThat(currentPoints).isEqualTo(14);
    }

    @Test
    public void removePointsTooLateTest(){
        currentPoints = pointDivider.removePointsTooLate(currentPoints);
        assertThat(currentPoints).isEqualTo(6);
    }
}
