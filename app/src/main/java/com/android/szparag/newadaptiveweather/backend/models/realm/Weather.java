package com.android.szparag.newadaptiveweather.backend.models.realm;

import java.util.Date;

import io.realm.RealmModel;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;

/**
 * Created by ciemek on 06/10/2016.
 */

@RealmClass
public class Weather implements RealmModel {

    @PrimaryKey
    private Date    date;

    //WeatherForecastItem outer scope representation
    private int     cloudsPercent;
    private int     windSpeed;
    private int     windDirection;
    private int     windDirectionDegrees;
    private int     rainPast3h;
    private int     snowPast3h;

    //MainWeatherData representation
    private float   temperature;
    private float   temperatureMax;
    private float   temperatureMin;
    private int     humidityPercent;
    private float   pressureAtmospheric;
    private float   pressureSeaLevel;
    private float   pressureGroundLevel;

    //WeatherInfo representation
    private int     weatherId;
    private String  weatherMain;
    private String  weatherDescription;
    private String  weatherIconId;




}
