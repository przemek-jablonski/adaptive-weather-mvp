package com.android.szparag.newadaptiveweather.presenters;

import com.android.szparag.newadaptiveweather.views.contracts.BulkWeatherInfoView;

/**
 * Created by ciemek on 24/09/2016.
 */
public interface BasePresenter {

    void setView(BulkWeatherInfoView view);

    void checkInternetConnectivity();

    void checkGrantedPermissions();

    void fetchWeatherCurrent();

    void fetchWeather5Day();

    void fetchBackgroundMap();

    void fetchBackgroundImage();

    void fetchWeatherPollutionCO();

    void fetchWeatherPollutionO3();

    void fetchWeatherMapTemperature();
    void fetchWeatherMapClouds();
    void fetchWeatherMapPressure();
    void fetchWeatherMapPrecipitation();
         //...

    void fetchWeatherStations();

    void unregisterRealm();
    void realmClose();
}
