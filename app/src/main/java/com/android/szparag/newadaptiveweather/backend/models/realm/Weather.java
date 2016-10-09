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
    private long    id;
    private long    unixTime;
    private String  city;

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


    public long getId() {
        return id;
    }

    public long getUnixTime() {
        return unixTime;
    }
    public void setUnixTime(long unixTime) {
        this.unixTime = unixTime;
    }

    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }

    public int getCloudsPercent() {
        return cloudsPercent;
    }
    public void setCloudsPercent(int cloudsPercent) {
        this.cloudsPercent = cloudsPercent;
    }

    public int getWindSpeed() {
        return windSpeed;
    }
    public void setWindSpeed(int windSpeed) {
        this.windSpeed = windSpeed;
    }

    public int getWindDirection() {
        return windDirection;
    }
    public void setWindDirection(int windDirection) {
        this.windDirection = windDirection;
    }

    public int getWindDirectionDegrees() {
        return windDirectionDegrees;
    }
    public void setWindDirectionDegrees(int windDirectionDegrees) {
        this.windDirectionDegrees = windDirectionDegrees;
    }

    public int getRainPast3h() {
        return rainPast3h;
    }
    public void setRainPast3h(int rainPast3h) {
        this.rainPast3h = rainPast3h;
    }

    public int getSnowPast3h() {
        return snowPast3h;
    }
    public void setSnowPast3h(int snowPast3h) {
        this.snowPast3h = snowPast3h;
    }

    public float getTemperature() {
        return temperature;
    }
    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }

    public float getTemperatureMax() {
        return temperatureMax;
    }
    public void setTemperatureMax(float temperatureMax) {
        this.temperatureMax = temperatureMax;
    }

    public float getTemperatureMin() {
        return temperatureMin;
    }
    public void setTemperatureMin(float temperatureMin) {
        this.temperatureMin = temperatureMin;
    }

    public int getHumidityPercent() {
        return humidityPercent;
    }
    public void setHumidityPercent(int humidityPercent) {
        this.humidityPercent = humidityPercent;
    }

    public float getPressureAtmospheric() {
        return pressureAtmospheric;
    }
    public void setPressureAtmospheric(float pressureAtmospheric) {
        this.pressureAtmospheric = pressureAtmospheric;
    }

    public float getPressureSeaLevel() {
        return pressureSeaLevel;
    }
    public void setPressureSeaLevel(float pressureSeaLevel) {
        this.pressureSeaLevel = pressureSeaLevel;
    }

    public float getPressureGroundLevel() {
        return pressureGroundLevel;
    }
    public void setPressureGroundLevel(float pressureGroundLevel) {
        this.pressureGroundLevel = pressureGroundLevel;
    }

    public int getWeatherId() {
        return weatherId;
    }
    public void setWeatherId(int weatherId) {
        this.weatherId = weatherId;
    }

    public String getWeatherMain() {
        return weatherMain;
    }
    public void setWeatherMain(String weatherMain) {
        this.weatherMain = weatherMain;
    }

    public String getWeatherDescription() {
        return weatherDescription;
    }
    public void setWeatherDescription(String weatherDescription) {
        this.weatherDescription = weatherDescription;
    }

    public String getWeatherIconId() {
        return weatherIconId;
    }
    public void setWeatherIconId(String weatherIconId) {
        this.weatherIconId = weatherIconId;
    }
}
