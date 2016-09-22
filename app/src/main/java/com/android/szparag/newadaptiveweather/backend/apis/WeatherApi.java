package com.android.szparag.newadaptiveweather.backend.apis;

import android.support.annotation.NonNull;
import android.util.Log;

import com.android.szparag.newadaptiveweather.backend.interceptors.PrintResponseInterceptor;
import com.android.szparag.newadaptiveweather.backend.models.WeatherCurrentResponse;
import com.android.szparag.newadaptiveweather.backend.models.WeatherForecastResponse;
import com.android.szparag.newadaptiveweather.backend.services.WeatherService;

import okhttp3.OkHttpClient;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by ciemek on 23/09/2016.
 */
public class WeatherApi {

    private WeatherService service;
    private Retrofit        retrofit;

    private final String OPENWEATHERMAP_BASEURL = "http://api.openweathermap.org/data/";
    private final String OPENWEATHERMAP_APIKEY = "6f21fa4c59c346931602e199b942473f";

    public WeatherApi() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(OPENWEATHERMAP_BASEURL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(new OkHttpClient.Builder().addInterceptor(new PrintResponseInterceptor()).build())
                .build();

        service = retrofit.create(WeatherService.class);
    }

    public void getCurrentWeather(@NonNull float latitude, @NonNull float longitude, Callback<WeatherCurrentResponse> callback) {
        service.getCurrentWeather(latitude, longitude, OPENWEATHERMAP_APIKEY).enqueue(callback);
    }

    public void getForecast5Day(@NonNull float latitude, @NonNull float longitude, Callback<WeatherForecastResponse> callback) {

        Log.e("kurwa mac", "getForecast5Day");
        service.getForecast5Day(latitude, longitude, OPENWEATHERMAP_APIKEY).enqueue(callback);
    }
}
