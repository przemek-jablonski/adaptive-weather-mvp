package com.android.szparag.newadaptiveweather.backend.interceptors;


import com.android.szparag.newadaptiveweather.backend.models.web.WeatherCurrentResponse;
import com.android.szparag.newadaptiveweather.backend.models.web.auxiliary.Clouds;
import com.android.szparag.newadaptiveweather.backend.models.web.auxiliary.MainWeatherData;
import com.android.szparag.newadaptiveweather.backend.models.web.auxiliary.Rain;
import com.android.szparag.newadaptiveweather.backend.models.web.auxiliary.Snow;

/**
 * Created by ciemek on 09/10/2016.
 */
public class AvoidNullsInterceptor {

    public AvoidNullsInterceptor() {

    }

    public WeatherCurrentResponse processResponseBody(WeatherCurrentResponse responseBody) {
        if (responseBody.snow == null) {
            responseBody.snow = fixNullsSnow();
        }

        if (responseBody.rain == null) {
            responseBody.rain = fixNullsRain();
        }

        if (responseBody.clouds == null) {
            responseBody.clouds = fixNullsClouds();
        }

        if (responseBody.mainWeatherData.pressureSeaLevel == null ||
                responseBody.mainWeatherData.pressureGroundLevel == null) {
            responseBody.mainWeatherData = fixNullsPressure(responseBody.mainWeatherData);
        }

        return responseBody;
    }

    private Snow fixNullsSnow() {
        Snow snow = new Snow();
        snow.past3h = 0;
        return snow;
    }

    private Rain fixNullsRain() {
        Rain rain = new Rain();
        rain.past3h = 0f;
        return rain;
    }

    private Clouds fixNullsClouds() {
        Clouds clouds = new Clouds();
        clouds.cloudsPercent = 0;
        return clouds;
    }

    private MainWeatherData fixNullsPressure(MainWeatherData mainWeatherData) {
        if (mainWeatherData.pressureGroundLevel == null) {
            mainWeatherData.pressureGroundLevel = 0f;
        }

        if (mainWeatherData.pressureSeaLevel == null) {
            mainWeatherData.pressureSeaLevel = 0f;
        }

        return mainWeatherData;
    }

}
