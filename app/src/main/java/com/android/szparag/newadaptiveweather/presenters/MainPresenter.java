package com.android.szparag.newadaptiveweather.presenters;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.android.szparag.newadaptiveweather.backend.models.WeatherCurrentResponse;
import com.android.szparag.newadaptiveweather.backend.models.WeatherForecastResponse;
import com.android.szparag.newadaptiveweather.backend.services.WeatherService;
import com.android.szparag.newadaptiveweather.views.BaseView;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ciemek on 24/09/2016.
 */
public class MainPresenter implements BasePresenter {

    private BaseView view;

    @Inject
    WeatherService service;


    public MainPresenter(WeatherService service) {
        this.service = service;
    }

    @Override
    public void setView(BaseView view) {
        this.view = view;
    }

    @Override
    public void checkGrantedPermissions() {

    }

    @Override
    public void checkInternetConnectivity() {
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) view.getAndroidView().getActivity().getSystemService(
                view.getAndroidView().getContext().CONNECTIVITY_SERVICE)).getActiveNetworkInfo();

        if (activeNetworkInfo == null || !activeNetworkInfo.isConnected()) {
            view.showNetworkConnectionError();
        }
    }

    @Override
    public void fetchWeatherCurrent() {
        float gpsWarsawLat = 52.196217f;
        float gpsWarsawLon = 21.172225f;

        service.getCurrentWeather(gpsWarsawLat, gpsWarsawLon, new Callback<WeatherCurrentResponse>() {
            @Override
            public void onResponse(Call<WeatherCurrentResponse> call, Response<WeatherCurrentResponse> response) {
                view.showForecastLocationLayout();
                view.updateForecastCurrentView(response.body());
                view.showWeatherFetchSuccess();
            }

            @Override
            public void onFailure(Call<WeatherCurrentResponse> call, Throwable t) {
                view.showWeatherFetchFailure();
            }
        });
    }

    @Override
    public void fetchWeather5Day() {

        float gpsWarsawLat = 52.196217f;
        float gpsWarsawLon = 21.172225f;

        service.getForecast5Day(gpsWarsawLat, gpsWarsawLon, new Callback<WeatherForecastResponse>() {
            @Override
            public void onResponse(Call<WeatherForecastResponse> call, Response<WeatherForecastResponse> response) {
                view.showForecastLocationLayout();
                view.updateForecast5DayView(response.body());
                view.updateForecastLocationTimeLayout(response.body());
                view.showWeatherFetchSuccess();
            }

            @Override
            public void onFailure(Call<WeatherForecastResponse> call, Throwable t) {
                view.showWeatherFetchFailure();
            }
        });

    }

    @Override
    public void fetchWeatherPollutionCO() {
        //..
    }

    @Override
    public void fetchWeatherPollutionO3() {
        //..
    }

    @Override
    public void fetchWeatherMapTemperature() {
        //...
    }

    @Override
    public void fetchWeatherMapClouds() {
        //...
    }

    @Override
    public void fetchWeatherMapPressure() {
        //...
    }

    @Override
    public void fetchWeatherMapPrecipitation() {
        //...
    }

    @Override
    public void fetchWeatherStations() {
        //..
    }

}
