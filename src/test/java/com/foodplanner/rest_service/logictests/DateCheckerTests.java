package com.foodplanner.rest_service.logictests;

import com.foodplanner.rest_service.logic.foodorder.DateChecker;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.GregorianCalendar;

import static org.junit.jupiter.api.Assertions.*;

public class DateCheckerTests {

    private DateChecker dateChecker;

    @Test
    public void dateToLate()
    {
        dateChecker = new DateChecker();
        Date tolate = new GregorianCalendar(2018, 2, 11).getTime();
        assertTrue(dateChecker.areYouToLate(tolate));
    }
    @Test
    public void dateNotToLate()
    {
        dateChecker = new DateChecker();
        Date tolate = new GregorianCalendar(2022, 2, 11).getTime();
        assertFalse(dateChecker.areYouToLate(tolate));
    }

}
