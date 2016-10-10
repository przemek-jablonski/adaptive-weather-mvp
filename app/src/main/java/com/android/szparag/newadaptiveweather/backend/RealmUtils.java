package com.android.szparag.newadaptiveweather.backend;

import android.support.annotation.Nullable;

import com.android.szparag.newadaptiveweather.backend.models.realm.Weather;
import com.android.szparag.newadaptiveweather.backend.models.web.WeatherCurrentResponse;

import java.util.LinkedList;

import io.realm.RealmModel;
import io.realm.RealmObject;
import io.realm.RealmResults;

/**
 * Created by ciemek on 10/10/2016.
 */
public class RealmUtils {

    private float minDiff;

    public synchronized @Nullable Weather findClosestTimeValue(LinkedList<Weather> results, float value) {
        if (results.size() == 0) {
            return null;
        }

        if (results.size() == 1) {
            return results.getFirst();
        }

        float diff = 0;
        int minDiffId = -1;
        minDiff = Float.MAX_VALUE;

        for (int i = 0; i < results.size(); ++i) {
            diff = results.get(i).getUnixTime() - value;

            if (minDiff > Math.abs(diff)) {
                minDiff = Math.abs(diff);
                minDiffId = i;
            }
        }
        if (minDiffId == -1) {
            return null;
        }
        return results.get(minDiffId);
    }

    public synchronized @Nullable Weather findClosestTimeValue(RealmResults<Weather> results, float value) {
//        float diff = 0;
//        int minDiffId = -1;
//        minDiff = Float.MAX_VALUE;
//
//        for (int i = 0; i < results.size(); ++i) {
//            diff = value - results.get(i).getUnixTime();
//            if (diff == 0) {
//                return results.get(i);
//            }
//
//            if (minDiff > Math.abs(diff)) {
//                minDiff = diff;
//                minDiffId = i;
//            }
//        }
//        if (minDiffId == -1) {
//            return null;
//        }
//        return results.get(minDiffId);
        return null;
    }

//    public RealmModel findClosestValueSorted(RealmResults<Weather> results, String key, float value) {
        //todo: use .average() here in up or down direction to cut iteration time by ~2
    //todo: if diff ==0, return this value
//    }

    public Weather mapJsonResponseToRealm(WeatherCurrentResponse responseBody, Weather mappedObject) {
        mappedObject.setCity(responseBody.cityName); //refactor to coord instead of city, with some error margin, like ~10km.
        mappedObject.setUnixTime(responseBody.calculationUnixTime);

        mappedObject.setTemperature(responseBody.mainWeatherData.temp);
        mappedObject.setTemperatureMin(responseBody.mainWeatherData.tempMin);
        mappedObject.setTemperatureMax(responseBody.mainWeatherData.tempMax);
        mappedObject.setHumidityPercent(responseBody.mainWeatherData.humidityPercent);
        mappedObject.setPressureAtmospheric(responseBody.mainWeatherData.pressureAtmospheric);
        mappedObject.setPressureSeaLevel(responseBody.mainWeatherData.pressureSeaLevel);
        mappedObject.setPressureGroundLevel(responseBody.mainWeatherData.pressureGroundLevel);

        mappedObject.setWeatherMain(responseBody.weather.get(0).main);
        mappedObject.setWeatherDescription(responseBody.weather.get(0).description);
        mappedObject.setWeatherIconId(responseBody.weather.get(0).iconId);

        return mappedObject;
    }

    public synchronized float getMinDiff() {
        return minDiff;
    }
}
