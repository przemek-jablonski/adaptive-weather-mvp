package com.android.szparag.newadaptiveweather.views;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;

import com.android.szparag.newadaptiveweather.adapters.BaseAdapter;
import com.android.szparag.newadaptiveweather.backend.models.realm.Weather;
import com.android.szparag.newadaptiveweather.backend.models.web.WeatherCurrentResponse;
import com.android.szparag.newadaptiveweather.backend.models.web.WeatherForecastResponse;

/**
 * Created by ciemek on 23/09/2016.
 */
public interface BaseView {

    void showNetworkConnectionError();

    void showWeatherFetchSuccess();
    void showWeatherFetchFailure();

    void setBackground(Bitmap bitmap);
    void setBackgroundPlaceholder();
    void showBackgroundFetchFailure();

    void buildForecastCurrentView();
    void updateForecastCurrentView(Weather weather);

    void buildForecast5DayView();
    void updateForecast5DayView(WeatherForecastResponse forecast);

    void hideForecastLocationLayout();
    void showForecastLocationLayout();
    void updateForecastLocationTimeLayout(WeatherForecastResponse response);

    SharedPreferences getSharedPreferences();
    void writeToSharedPreferences(String key, long value);

    Fragment getAndroidView();
    Activity getActivity();

    int[] getViewDimensions();

    RecyclerView getRecycler();
    BaseAdapter getAdapter();

}
