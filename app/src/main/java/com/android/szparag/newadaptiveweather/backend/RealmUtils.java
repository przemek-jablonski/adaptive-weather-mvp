package com.android.szparag.newadaptiveweather.backend;

import android.support.annotation.Nullable;

import com.android.szparag.newadaptiveweather.backend.models.realm.Weather;
import com.android.szparag.newadaptiveweather.backend.models.web.WeatherCurrentResponse;
import com.android.szparag.newadaptiveweather.backend.models.web.WeatherForecastResponse;
import com.android.szparag.newadaptiveweather.backend.models.web.auxiliary.City;
import com.android.szparag.newadaptiveweather.backend.models.web.auxiliary.WeatherForecastItem;

import java.util.LinkedList;

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
        if (results.size() == 0) {
            return null;
        }

        if (results.size() == 1) {
            return results.first();
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

//    public RealmModel findClosestValueSorted(RealmResults<Weather> results, String key, float value) {
        //todo: use .average() here in up or down direction to cut iteration time by ~2
    //todo: if diff ==0, return this value
//    }

    public Weather mapJsonResponseToRealm(WeatherCurrentResponse responseBody, Weather mappedObject) {
        mappedObject.setCity(responseBody.cityName); //refactor to coord instead of city, with some error margin, like ~10km.
        mappedObject.setUnixTime(responseBody.calculationUnixTime);
        mappedObject.setHumanTime(new String("human time"));

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

    public Weather mapJsonRespnseToRealm(WeatherForecastItem responseItem, City city, Weather mappedObject) {
        mappedObject.setCity(city.name); //todo: refactor to coord instead of city, with some error margin, like ~10km.
        mappedObject.setUnixTime(responseItem.calculationUnixTime);
        mappedObject.setHumanTime(responseItem.calculationUTCTime);

        mappedObject.setTemperature(responseItem.mainWeatherData.temp);
        mappedObject.setTemperatureMin(responseItem.mainWeatherData.tempMin);
        mappedObject.setTemperatureMax(responseItem.mainWeatherData.tempMax);
        mappedObject.setHumidityPercent(responseItem.mainWeatherData.humidityPercent);
        mappedObject.setPressureAtmospheric(responseItem.mainWeatherData.pressureAtmospheric);
        mappedObject.setPressureSeaLevel(responseItem.mainWeatherData.pressureSeaLevel);
        mappedObject.setPressureGroundLevel(responseItem.mainWeatherData.pressureGroundLevel);

        mappedObject.setWeatherMain(responseItem.weather.get(0).main);
        mappedObject.setWeatherDescription(responseItem.weather.get(0).description);
        mappedObject.setWeatherIconId(responseItem.weather.get(0).iconId);

        return mappedObject;
    }

    public synchronized float getMinDiff() {
        return minDiff;
    }
}
