package com.android.szparag.newadaptiveweather.views;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.android.szparag.newadaptiveweather.adapters.BaseAdapter;
import com.android.szparag.newadaptiveweather.backend.models.WeatherCurrentResponse;
import com.android.szparag.newadaptiveweather.backend.models.WeatherForecastResponse;
import com.android.szparag.newadaptiveweather.backend.models.auxiliary.City;
import com.android.szparag.newadaptiveweather.backend.models.auxiliary.WeatherForecastItem;
import com.android.szparag.newadaptiveweather.dagger.components.MainComponent;

import java.util.List;

/**
 * Created by ciemek on 23/09/2016.
 */
public interface BaseView {

    void showNetworkConnectionError();

    void showWeatherFetchSuccess();
    void showWeatherFetchFailure();

    void buildForecastCurrentView();
    void updateForecastCurrentView(WeatherCurrentResponse forecast);

    void buildForecast5DayView();
    void updateForecast5DayView(WeatherForecastResponse forecast);

    void hideForecastLocationLayout();
    void showForecastLocationLayout();
    void updateForecastLocationTimeLayout(WeatherCurrentResponse response);
    void updateForecastLocationTimeLayout(WeatherForecastResponse response);




    Fragment getAndroidView();
    Activity getActivity();

    RecyclerView getRecycler();
    BaseAdapter getAdapter();

}
