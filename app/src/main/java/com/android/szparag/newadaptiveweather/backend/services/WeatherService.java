package com.android.szparag.newadaptiveweather.backend.services;

import android.support.annotation.NonNull;

import com.android.szparag.newadaptiveweather.backend.models.web.WeatherCurrentResponse;
import com.android.szparag.newadaptiveweather.backend.models.web.WeatherForecastResponse;

import retrofit2.Callback;

/**
 * Created by ciemek on 23/09/2016.
 */
public interface WeatherService {

    void getCurrentWeather(@NonNull float latitude, @NonNull float longitude, Callback<WeatherCurrentResponse> callback);
    void getForecast5Day(@NonNull float latitude, @NonNull float longitude, Callback<WeatherForecastResponse> callback);

}
