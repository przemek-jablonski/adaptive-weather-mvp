package com.android.szparag.newadaptiveweather.utils;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.android.szparag.newadaptiveweather.AppController;
import com.android.szparag.newadaptiveweather.backend.models.WeatherForecastResponse;
import com.android.szparag.newadaptiveweather.backend.models.auxiliary.City;
import com.android.szparag.newadaptiveweather.backend.models.auxiliary.WeatherForecastItem;
import com.android.szparag.newadaptiveweather.dagger.components.MainComponent;

/**
 * Created by ciemek on 23/09/2016.
 */
public class Utils {

    public static float KELVIN_TO_CELSIUS_SUBTRAHEND = 273.15f;

    public static MainComponent getDagger2(Activity activity) {
        return ((AppController) activity.getApplication()).getComponent();
    }

    public static MainComponent getDagger2(Fragment fragment) {
        return getDagger2(fragment.getActivity());
    }

    public static CharSequence makeWeatherDetailString(WeatherForecastItem weatherForecastItem) {
        StringBuilder builder = new StringBuilder();

        if (weatherForecastItem.clouds.cloudsPercent < 5) {
            builder.append("Clear sky");
        } else {
            builder.append(weatherForecastItem.clouds.cloudsPercent);
            builder.append("% clouds");
        }

        //TODO: write custom json deserializer, that will map (null)s to (0)s
        if (weatherForecastItem.rain != null) {
            if (weatherForecastItem.rain.past3h != null) {
                if (weatherForecastItem.rain.past3h > 0) {
                    builder.append(" | ");
                    builder.append(weatherForecastItem.rain.past3h);
                    builder.append(" rain");
                }
            }
        }

        if (weatherForecastItem.snow != null) {
            if(weatherForecastItem.rain.past3h != null) {
                if (weatherForecastItem.snow.past3h > 0) {
                    builder.append(" | ");
                    builder.append(weatherForecastItem.snow.past3h);
                    builder.append(" snow");
                }
            }
        }

        builder.append(" | ");
        if (weatherForecastItem.wind.speed < 1) {
            builder.append("no wind");
        } else {
            builder.append(weatherForecastItem.wind.speed);
            builder.append("kmph (?)");
        }

        return builder.toString();
    }

    public static CharSequence makeLocationGpsString(City city) {
        StringBuilder builder = new StringBuilder("(");

        builder.append(Float.toString(city.coordinates.lat));
        builder.append(", ");
        builder.append(Float.toString(city.coordinates.lon));
        builder.append(")");

        return builder.toString();
    }

    public static CharSequence kelvinToCelsiusRoundDebug(float kelvinTemp) {
        return Integer.toString(Math.round(kelvinTemp - KELVIN_TO_CELSIUS_SUBTRAHEND));
    }


}
