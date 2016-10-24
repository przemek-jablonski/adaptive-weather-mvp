package com.android.szparag.newadaptiveweather.backend.interceptors;


import com.android.szparag.newadaptiveweather.backend.models.web.WeatherCurrentResponse;
import com.android.szparag.newadaptiveweather.backend.models.web.WeatherForecastResponse;
import com.android.szparag.newadaptiveweather.backend.models.web.auxiliary.Clouds;
import com.android.szparag.newadaptiveweather.backend.models.web.auxiliary.Rain;
import com.android.szparag.newadaptiveweather.backend.models.web.auxiliary.Snow;
import com.android.szparag.newadaptiveweather.backend.models.web.auxiliary.WeatherForecastItem;

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

        if (responseBody.mainWeatherData.pressureGroundLevel == null) {
            responseBody.mainWeatherData.pressureGroundLevel = 0f;
        }

        if (responseBody.mainWeatherData.pressureSeaLevel == null) {
            responseBody.mainWeatherData.pressureSeaLevel = 0f;
        }

        return responseBody;
    }

    public WeatherForecastItem processResponseBody(WeatherForecastItem responseBody) {
        if (responseBody.snow == null) {
            responseBody.snow = fixNullsSnow();
        }

        if (responseBody.rain == null) {
            responseBody.rain = fixNullsRain();
        }

        if (responseBody.clouds == null) {
            responseBody.clouds = fixNullsClouds();
        }

        if (responseBody.mainWeatherData.pressureGroundLevel == null) {
            responseBody.mainWeatherData.pressureGroundLevel = 0f;
        }

        if (responseBody.mainWeatherData.pressureSeaLevel == null) {
            responseBody.mainWeatherData.pressureSeaLevel = 0f;
        }

        return responseBody;
    }

    public WeatherForecastResponse processResponseBody(WeatherForecastResponse response) {
        for (int item = 0; item < response.list.size(); ++item) {
            response.list.set(item, processResponseBody(response.list.get(item)));
        }

        return response;
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

}
