package com.android.szparag.newadaptiveweather.utils;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.android.szparag.newadaptiveweather.AdaptiveWeatherApplication;
import com.android.szparag.newadaptiveweather.backend.models.realm.Weather;
import com.android.szparag.newadaptiveweather.backend.models.web.auxiliary.City;
import com.android.szparag.newadaptiveweather.backend.models.web.auxiliary.Coordinates;
import com.android.szparag.newadaptiveweather.dagger.components.MainComponent;

/**
 * Created by ciemek on 23/09/2016.
 */
public class Utils {

    public static MainComponent getDagger2(Activity activity) {
        return ((AdaptiveWeatherApplication) activity.getApplication()).getComponent();
    }

    public static MainComponent getDagger2(Fragment fragment) {
        return getDagger2(fragment.getActivity());
    }


    public static void logMisc(String... logMessages) {
        for (int i=0; i < logMessages.length; ++i) {
            logDebug(Constants.ADAPTIVE_WEATHER, logMessages[i]);
        }
    }

    public static void logRealm(String... logMessages) {
        for (int i=0; i < logMessages.length; ++i) {
            logDebug(Constants.ADAPTIVE_WEATHER_REALM, logMessages[i]);
        }
        //todo: add another logDebug line - statistics of given Realm (onchangelistener?)
    }

    public static void logRetrofit(String... logMessages) {
        for (int i=0; i < logMessages.length; ++i) {
            logError(Constants.ADAPTIVE_WEATHER_RETROFIT, logMessages[i]);
        }
    }

    public static void logException(Throwable exception) {
        logError(Constants.ADAPTIVE_WEATHER, exception.getMessage());
    }


    private static void logDebug(String tag, String message) {
        Log.println(Log.DEBUG, tag, message);
    }

    private static void logError(String tag, String message) {
        Log.println(Log.ERROR, tag, message);
    }


    public static CharSequence makeWeatherDetailString(Weather weather) {
        StringBuilder builder = new StringBuilder();

        builder.append(weather.getCloudsPercent());
        builder.append("% clouds");


        builder.append(" | ");
        builder.append(weather.getRainPast3h());
        builder.append(" rain");

        builder.append(" | ");
        builder.append(weather.getSnowPast3h());
        builder.append(" snow");


        builder.append(" | ");
        builder.append(weather.getWindSpeed());
        builder.append("kmph (?)");

        return builder.toString();
    }

    public static CharSequence makeLocationGpsString(float gpsLat, float gpsLon) {
        StringBuilder builder = new StringBuilder("(");
        builder.append(Float.toString(gpsLat));
        builder.append(", ");
        builder.append(Float.toString(gpsLon));
        builder.append(")");
        return builder.toString();
    }

    public static CharSequence makeLocationGpsString(City city) {
        return makeLocationGpsString(city.coordinates);
    }

    public static CharSequence makeLocationGpsString(Coordinates coordinates) {
        return makeLocationGpsString(coordinates.lat, coordinates.lon);
    }

    public static CharSequence makeLocationGpsString(float gpsLat, float gpsLon, boolean commaSeparatedOnly) {
        if (!commaSeparatedOnly) {
            return makeLocationGpsString(gpsLat, gpsLon);
        }

        StringBuilder builder = new StringBuilder();
        builder.append(Float.toString(gpsLat));
        builder.append(',');
        builder.append(Float.toString(gpsLon));
        return builder.toString();
    }

    public static String makeStringRoundedFloat(float val) {
        return Integer.toString(Math.round(val));
    }

    //todo: make a settings activity with units of measurement
    //and pass it into an api call
    public static CharSequence kelvinToCelsiusRoundDebug(float kelvinTemp) {
        return Integer.toString(Computation.kelvinToCelsiusConversionInteger(kelvinTemp));
    }

    public static CharSequence makeTemperatureString(float kelvinTemp) {
        return (kelvinToCelsiusRoundDebug(kelvinTemp) + "°");
    }

    public static CharSequence makeTemperatureMinMaxString(float kelvinTempMax, float kelvinTempMin) {
        return ("↑" + kelvinToCelsiusRoundDebug(kelvinTempMax) + "° "
                + "↓" + kelvinToCelsiusRoundDebug(kelvinTempMin) + "° ");
    }

    //maybe this is embedded in Date java class?
//    public static CharSequence dateStringFromUnix(String unixTime) {
//
//    }
//
//    public static CharSequence dateStringFromUTC(String utcTime) {
//
//    }
//
//    public static CharSequence utcTimeFromUnixTime(String unixTime) {
//
//    }

    public static String makeGoogleMapsStaticMapUri(
            String googleMapsStaticBaseUrl, String googleMapsStaticApiKey,
            float gpsLat, float gpsLon, int sizeHorizontal, int sizeVertical,
            int mapZoom, int mapScale, String mapType, String format) {

        GoogleMapsStaticUriBuilder uriBuilder = new GoogleMapsStaticUriBuilder(
            googleMapsStaticBaseUrl, googleMapsStaticApiKey
        );

        uriBuilder
                .appendLocationCenterQueryParameter(gpsLat, gpsLon)
                .appendZoomQueryParameter(mapZoom)
                .appendMapSizeParameter(sizeHorizontal, sizeVertical)
                .appendMapScaleParameter(mapScale)
                .appendMapTypeParameter(mapType)
                .appendFormatParameter(format);

        //debug:
        String uri = uriBuilder.getString();
        Log.d(Constants.ADAPTIVE_WEATHER, uri);

        return uri;
    }

    public static String makeStringGoogleMapsSize(int sizeHorizontal, int sizeVertical) {
        StringBuilder builder = new StringBuilder(Integer.toString(sizeHorizontal));
        builder.append("x");
        builder.append(Integer.toString(sizeVertical));
        return builder.toString();
    }

}
