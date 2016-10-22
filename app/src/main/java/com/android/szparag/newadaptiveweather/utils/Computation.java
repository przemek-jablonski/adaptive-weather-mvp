package com.android.szparag.newadaptiveweather.utils;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.concurrent.TimeUnit;

/**
 * Created by ciemek on 28/09/2016.
 */
public class Computation {

    public class UnixTimeInterval {
        public static final int MINUTE_1 = 60; //SECONDS as units of measurement (as in epoch time)
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
        public static final int DAY_2 = 172800;
        public static final int DAY_5 = 432000;

        public static final int OUTDATED_DATA_INTERVAL = HOUR_2;
    }

    public static long getNextDaysTime(int daysForward) {
        Calendar date = new GregorianCalendar();
        date.set(Calendar.HOUR_OF_DAY, 0);
        date.set(Calendar.MINUTE, 0);
        date.set(Calendar.SECOND, 0);
        date.set(Calendar.MILLISECOND, 0);
        date.add(Calendar.DAY_OF_MONTH, daysForward);
        return date.getTime().getTime()/1000L;
    }

    public static int getHour24FromUnixTime(long unixTime) {
//        //todo: move it to dagger module with @Singleton annot
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(unixTime * 1000L);
        calendar.add(Calendar.HOUR_OF_DAY, -1);
        return calendar.get(Calendar.HOUR_OF_DAY);
    }

    public static int getHour12FromUnixTime(long unixTime) {
        //todo: move it to dagger module with @Singleton annot
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(unixTime * 1000L);
        return calendar.get(Calendar.HOUR);
    }

    //todo: move to utils
    public static CharSequence getHumanDateFromUnixTime(long unixTime) {
        SimpleDateFormat date = new SimpleDateFormat("MMM d h:mm a");
        return date.format(unixTime * 1000L);
    }

    public static long calculateUnixTimeInterval(int unixTimeIntervalConstant) {
        return getCurrentUnixTime() - unixTimeIntervalConstant;
    }

    public static float kelvinToCelsiusConversion(float kelvinTemp) {
        return kelvinTemp - Constants.KELVIN_TO_CELSIUS_SUBTRAHEND;
    }

    public static float kelvinToCelsiusConversion(float kelvinTemp, boolean roundCelsius) {
        if (!roundCelsius) {
            return kelvinToCelsiusConversion(kelvinTemp);
        }
        return Math.abs(kelvinTemp - Constants.KELVIN_TO_CELSIUS_SUBTRAHEND);
    }

    public static int kelvinToCelsiusConversionInteger(float kelvinTemp) {
        return Math.round(kelvinToCelsiusConversion(kelvinTemp));
    }

    public static long getCurrentUnixTime() {
        return System.currentTimeMillis() / 1000L;
    }

}
