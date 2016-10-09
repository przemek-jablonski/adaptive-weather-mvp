package com.android.szparag.newadaptiveweather.utils;


import android.icu.util.DateInterval;

import java.text.DateFormat;

/**
 * Created by ciemek on 28/09/2016.
 */
public class Computation {

    public class UnixTimeInterval {
        public static final int NOW = 0; //minutes as units of measurement
        public static final int MINUTE_1 = 1;
        public static final int MINUTE_5 = 5;
        public static final int MINUTE_10 = 10;
        public static final int MINUTE_15 = 15;
        public static final int MINUTE_30 = 30;
        public static final int HOUR_1 = 60;
        public static final int HOUR_2 = 120;
        public static final int HOUR_3 = 180;
        public static final int HOUR_6 = 360;
        public static final int HOUR_12 = 720;
        public static final int DAY_1 = 1440;
        public static final int DAY_5 = 7200;

        static final int SECONDS_IN_MINUTE = 60;
    }

    public static long calculateUnixTimeInterval(int unixTimeIntervalConstant) {
        return getCurrentUnixTime() - (unixTimeIntervalConstant * UnixTimeInterval.SECONDS_IN_MINUTE);
    }


    public static float kelvinToCelsiusConversion(float kelvinTemp) {
        return kelvinTemp - Constants.KELVIN_TO_CELSIUS_SUBTRAHEND;
    }

    public static int kelvinToCelsiusConversionInteger(float kelvinTemp) {
        return Math.round(kelvinToCelsiusConversion(kelvinTemp));
    }

    public static long getCurrentUnixTime() {
        return System.currentTimeMillis() / 1000L;
    }

}
