package com.android.szparag.newadaptiveweather.utils;

/**
 * Created by ciemek on 28/09/2016.
 */
public class Computation {

    public class UnixTimeInterval {
        public static final int NOW = 0; //SECONDS as units of measurement (as in epoch time)
        public static final int MINUTE_1 = 60;
        public static final int MINUTE_5 = 300;
        public static final int MINUTE_10 = 600;
        public static final int MINUTE_15 = 900;
        public static final int MINUTE_30 = 1800;
        public static final int HOUR_1 = 3600;
        public static final int HOUR_2 = 7200;
        public static final int HOUR_3 = 10800;
        public static final int HOUR_6 = 21600;
        public static final int HOUR_12 = 43200;
        public static final int DAY_1 = 86400;
        public static final int DAY_5 = 432000;

        public static final int OUTDATED_DATA_INTERVAL = MINUTE_15;
    }

    public static long calculateUnixTimeInterval(int unixTimeIntervalConstant) {
        return getCurrentUnixTime() - unixTimeIntervalConstant;
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
