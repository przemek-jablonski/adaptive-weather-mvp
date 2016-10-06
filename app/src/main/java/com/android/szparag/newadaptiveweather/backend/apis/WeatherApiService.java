package com.android.szparag.newadaptiveweather.backend.apis;

import android.support.annotation.NonNull;

import com.android.szparag.newadaptiveweather.backend.models.web.WeatherCurrentResponse;
import com.android.szparag.newadaptiveweather.backend.models.web.WeatherForecastResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by ciemek on 23/09/2016.
 */
public interface WeatherApiService {

    @GET("2.5/weather")
    Call<WeatherCurrentResponse> getCurrentWeather(
            @NonNull @Query("lat") Float latitude,
            @NonNull @Query("lon") Float longitude,
            @NonNull @Query("APPID") String apiKey //todo: put it in an interceptor
    );

    @GET("2.5/forecast")
    Call<WeatherForecastResponse> getForecast5Day(
            @NonNull @Query("lat") Float latitude,
            @NonNull @Query("lon") Float longitude,
            @NonNull @Query("APPID") String apiKey
    );

}
