package com.android.szparag.newadaptiveweather.utils;


import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;
import java.util.TimeZone;

import static junit.framework.Assert.*;

/**
 * Created by ciemek on 15/10/2016.
 */
public class ComputationTest {

    Calendar calendar;

    private final long DATE_MIDNIGHT = 1456358400;

    private final long DATE_1AM = 1456362000;
    private final long DATE_2AM = 1456365600;
    private final long DATE_3AM = 1456369200;
    private final long DATE_11AM = 1456398000;
    private final long DATE_2PM = 1456408800;
    private final long DATE_3PM = 1456412400;

    private final long DATE_3PM_1MIN = 1456412460;
    private final long DATE_3PM_15MIN = 1456413300;
    private final long DATE_3PM_29MIN = 1456414140;

    @Before
    public void setup() {
        calendar = Calendar.getInstance();

    }

    @Test
    public void getHour24FromUnixTimeSameDayDifferentHours() throws Exception {
        assertEquals(1, Computation.getHour24FromUnixTime(DATE_1AM));
        assertEquals(2, Computation.getHour24FromUnixTime(DATE_2AM));
        assertEquals(3, Computation.getHour24FromUnixTime(DATE_3AM));
        assertEquals(11, Computation.getHour24FromUnixTime(DATE_11AM));
        assertEquals(14, Computation.getHour24FromUnixTime(DATE_2PM));
        assertEquals(15, Computation.getHour24FromUnixTime(DATE_3PM));
        assertEquals(0, Computation.getHour24FromUnixTime(DATE_MIDNIGHT));
    }

    @Test
    public void getHour24FromUnixTimeSameHourDifferentMinutes() throws Exception {
        assertEquals(15, Computation.getHour24FromUnixTime(DATE_3PM_1MIN));
        assertEquals(15, Computation.getHour24FromUnixTime(DATE_3PM_15MIN));
        assertEquals(15, Computation.getHour24FromUnixTime(DATE_3PM_29MIN));
    }

//    @Test
//    public void getHour12FromUnixTime() throws Exception {
//
//    }

}