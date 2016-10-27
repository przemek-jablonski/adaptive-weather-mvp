package com.android.szparag.newadaptiveweather.backend.models.realm;

import android.support.annotation.Nullable;

import com.android.szparag.newadaptiveweather.backend.interceptors.AvoidNullsInterceptor;

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
    private String  humanTime;
    private String  city;

    //WeatherForecastItem outer scope representation
    private int     cloudsPercent;
    private float   windSpeed;
    private float   windDirection;
    private float   windDirectionDegrees;
    private float   rainPast3h;
    private float   snowPast3h;

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
    public void setUnixTime(@Nullable long unixTime) {
        this.unixTime = getValue(unixTime);
    }

    public String getCity() {
        return city;
    }
    public void setCity(@Nullable String city) {
        this.city = getValue(city);
    }

    public int getCloudsPercent() {
        return cloudsPercent;
    }
    public void setCloudsPercent(@Nullable int cloudsPercent) {
        this.cloudsPercent = getValue(cloudsPercent);
    }

    public float getWindSpeed() {
        return windSpeed;
    }
    public void setWindSpeed(@Nullable float windSpeed) {
        this.windSpeed = getValue(windSpeed);
    }

    public float getWindDirection() {
        return windDirection;
    }
    public void setWindDirection(@Nullable float windDirection) {
        this.windDirection = getValue(windDirection);
    }
    public float getWindDirectionDegrees() {
        return windDirectionDegrees;
    }
    public void setWindDirectionDegrees(@Nullable float windDirectionDegrees) {
        this.windDirectionDegrees = getValue(windDirectionDegrees);
    }

    public float getRainPast3h() {
        return rainPast3h;
    }
    public void setRainPast3h(@Nullable float rainPast3h) {
        this.rainPast3h = getValue(rainPast3h);
    }

    public float getSnowPast3h() {
        return snowPast3h;
    }
    public void setSnowPast3h(@Nullable float snowPast3h) {
        this.snowPast3h = getValue(snowPast3h);
    }

    public float getTemperature() {
        return temperature;
    }
    public void setTemperature(@Nullable float temperature) {
        this.temperature = getValue(temperature);
    }

    public float getTemperatureMax() {
        return temperatureMax;
    }
    public void setTemperatureMax(@Nullable float temperatureMax) {
        this.temperatureMax = getValue(temperatureMax);
    }

    public float getTemperatureMin() {
        return temperatureMin;
    }
    public void setTemperatureMin(@Nullable float temperatureMin) {
        this.temperatureMin = getValue(temperatureMin);
    }

    public int getHumidityPercent() {
        return humidityPercent;
    }
    public void setHumidityPercent(@Nullable int humidityPercent) {
        this.humidityPercent = getValue(humidityPercent);
    }

    public float getPressureAtmospheric() {
        return pressureAtmospheric;
    }
    public void setPressureAtmospheric(@Nullable float pressureAtmospheric) {
        this.pressureAtmospheric = getValue(pressureAtmospheric);
    }

    public float getPressureSeaLevel() {
        return pressureSeaLevel;
    }
    public void setPressureSeaLevel(@Nullable float pressureSeaLevel) {
        this.pressureSeaLevel = getValue(pressureSeaLevel);
    }

    public float getPressureGroundLevel() {
        return pressureGroundLevel;
    }
    public void setPressureGroundLevel(@Nullable float pressureGroundLevel) {
        this.pressureGroundLevel = getValue(pressureGroundLevel);
    }

    public int getWeatherId() {
        return weatherId;
    }
    public void setWeatherId(@Nullable int weatherId) {
        this.weatherId = getValue(weatherId);
    }

    public String getWeatherMain() {
        return weatherMain;
    }
    public void setWeatherMain(@Nullable String weatherMain) {
        this.weatherMain = getValue(weatherMain);
    }

    public String getWeatherDescription() {
        return weatherDescription;
    }
    public void setWeatherDescription(@Nullable String weatherDescription) {
        this.weatherDescription = getValue(weatherDescription);
    }

    public String getWeatherIconId() {
        return weatherIconId;
    }
    public void setWeatherIconId(@Nullable String weatherIconId) {
        this.weatherIconId = getValue(weatherIconId);
    }

    public String getHumanTime() {
        return humanTime;
    }
    public void setHumanTime(@Nullable String humanTime) {
        this.humanTime = getValue(humanTime);
    }


    private int getValue(int val) {
        return Integer.valueOf(val) == null ? 0 : val;
    }

    private long getValue(long val) {
        return Long.valueOf(val) == null ? 0 : val;
    }

    private float getValue(float val) {
        return Float.valueOf(val) == null ? 0 : val;
    }

    private String getValue(String val) {
        return val == null ? "0" : val;
    }
}
