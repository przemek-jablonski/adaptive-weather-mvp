package com.android.szparag.newadaptiveweather.backend.services;

import android.support.annotation.NonNull;

import com.android.szparag.newadaptiveweather.backend.apis.WeatherApiService;
import com.android.szparag.newadaptiveweather.backend.models.WeatherCurrentResponse;
import com.android.szparag.newadaptiveweather.backend.models.WeatherForecastResponse;

import retrofit2.Callback;


/**
 * Created by ciemek on 23/09/2016.
 */
public class WeatherServiceImpl implements WeatherService{

    private WeatherApiService service;
    private String OPENWEATHERMAP_APIKEY;

    public WeatherServiceImpl(WeatherApiService service, String OPENWEATHERMAP_APIKEY) {
        this.service = service;
        this.OPENWEATHERMAP_APIKEY = OPENWEATHERMAP_APIKEY;
    }


    @Override
    public void getCurrentWeather(@NonNull float latitude, @NonNull float longitude, Callback<WeatherCurrentResponse> callback) {
        service.getCurrentWeather(
                latitude,
                longitude,
                OPENWEATHERMAP_APIKEY
        ).enqueue(callback);
    }

    @Override
    public void getForecast5Day(@NonNull float latitude, @NonNull float longitude, Callback<WeatherForecastResponse> callback) {
        service.getForecast5Day(
                latitude,
                longitude,
                OPENWEATHERMAP_APIKEY
        ).enqueue(callback);
    }
}
