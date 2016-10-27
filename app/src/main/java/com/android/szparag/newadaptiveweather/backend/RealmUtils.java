package com.android.szparag.newadaptiveweather.backend;

import android.support.annotation.Nullable;

import com.android.szparag.newadaptiveweather.backend.interceptors.AvoidNullsInterceptor;
import com.android.szparag.newadaptiveweather.backend.models.realm.Weather;
import com.android.szparag.newadaptiveweather.backend.models.web.WeatherCurrentResponse;
import com.android.szparag.newadaptiveweather.backend.models.web.WeatherForecastResponse;
import com.android.szparag.newadaptiveweather.backend.models.web.auxiliary.City;
import com.android.szparag.newadaptiveweather.backend.models.web.auxiliary.WeatherForecastItem;

import java.util.LinkedList;

import javax.inject.Inject;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

/**
 * Created by ciemek on 10/10/2016.
 */
public class RealmUtils {

    @Inject
    Realm realm;

    AvoidNullsInterceptor interceptor;


    public RealmUtils(Realm realm) {
        this.realm = realm;
        interceptor = new AvoidNullsInterceptor();
    }

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

    public Weather mapJsonResponseToRealm(WeatherCurrentResponse item, Weather mappedObject) {
        WeatherCurrentResponse fetch = interceptor.processResponseBody(item);

        mappedObject.setCity(fetch.cityName); //refactor to coord instead of city, with some error margin, like ~10km.
        mappedObject.setUnixTime(fetch.calculationUnixTime);
        mappedObject.setHumanTime(new String("human time"));

        mappedObject.setTemperature(fetch.mainWeatherData.temp);
        mappedObject.setTemperatureMin(fetch.mainWeatherData.tempMin);
        mappedObject.setTemperatureMax(fetch.mainWeatherData.tempMax);
        mappedObject.setHumidityPercent(fetch.mainWeatherData.humidityPercent);
        mappedObject.setPressureAtmospheric(fetch.mainWeatherData.pressureAtmospheric);
        mappedObject.setPressureSeaLevel(fetch.mainWeatherData.pressureSeaLevel);
        mappedObject.setPressureGroundLevel(fetch.mainWeatherData.pressureGroundLevel);

        mappedObject.setWindDirection(fetch.wind.directionDegrees);
        mappedObject.setWindSpeed(fetch.wind.speed);

        mappedObject.setCloudsPercent(fetch.clouds.cloudsPercent);

        mappedObject.setRainPast3h(fetch.rain.past3h);
        mappedObject.setSnowPast3h(fetch.snow.past3h);

        mappedObject.setWeatherMain(fetch.weather.get(0).main);
        mappedObject.setWeatherDescription(fetch.weather.get(0).description);
        mappedObject.setWeatherIconId(fetch.weather.get(0).iconId);
        mappedObject.setWeatherId(fetch.weather.get(0).id);

        return mappedObject;
    }

    public Weather mapJsonResponseToRealm(WeatherForecastItem item, City city, Weather mappedObject) {
        WeatherForecastItem fetch = interceptor.processResponseBody(item);

        mappedObject.setCity(city.name); //todo: refactor to coord instead of city, with some error margin, like ~10km.
        mappedObject.setUnixTime(fetch.calculationUnixTime);
        mappedObject.setHumanTime(fetch.calculationUTCTime);

        mappedObject.setTemperature(fetch.mainWeatherData.temp);
        mappedObject.setTemperatureMin(fetch.mainWeatherData.tempMin);
        mappedObject.setTemperatureMax(fetch.mainWeatherData.tempMax);
        mappedObject.setHumidityPercent(fetch.mainWeatherData.humidityPercent);
        mappedObject.setPressureAtmospheric(fetch.mainWeatherData.pressureAtmospheric);
        mappedObject.setPressureSeaLevel(fetch.mainWeatherData.pressureSeaLevel);
        mappedObject.setPressureGroundLevel(fetch.mainWeatherData.pressureGroundLevel);

        mappedObject.setWindDirection(fetch.wind.directionDegrees);
        mappedObject.setWindSpeed(fetch.wind.speed);

        mappedObject.setCloudsPercent(fetch.clouds.cloudsPercent);

        mappedObject.setRainPast3h(fetch.rain.past3h);
        mappedObject.setSnowPast3h(fetch.snow.past3h);

        mappedObject.setWeatherMain(fetch.weather.get(0).main);
        mappedObject.setWeatherDescription(fetch.weather.get(0).description);
        mappedObject.setWeatherIconId(fetch.weather.get(0).iconId);
        mappedObject.setWeatherId(fetch.weather.get(0).id);

        return mappedObject;
    }

    public synchronized float getMinDiff() {
        return minDiff;
    }

    //todo: make here methods for retrieving queries with parameters

    public RealmQuery queryWeathersBetweenTimes(long unixTimeFrom, long unixTimeTo, @Nullable boolean betweenOrEqualTo) {
        if (betweenOrEqualTo) {
            return queryWeatherBetweenTimesOrEqualTo(unixTimeFrom, unixTimeTo);
        }
        return realm.where(Weather.class).greaterThan("unixTime", unixTimeFrom).lessThan("unixTime", unixTimeTo);
    }

    private RealmQuery<Weather> queryWeatherBetweenTimesOrEqualTo(long unixTimeFrom, long unixTimeTo) {
        RealmQuery<Weather> results = realm.where(Weather.class).greaterThanOrEqualTo("unixTime", unixTimeFrom).lessThanOrEqualTo("unixTime", unixTimeTo);
        return  results;
    }


}
