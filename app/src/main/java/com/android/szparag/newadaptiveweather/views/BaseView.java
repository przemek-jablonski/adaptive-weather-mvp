package com.android.szparag.newadaptiveweather.views;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.android.szparag.newadaptiveweather.adapters.BaseAdapter;
import com.android.szparag.newadaptiveweather.backend.models.WeatherForecastResponse;
import com.android.szparag.newadaptiveweather.backend.models.auxiliary.City;
import com.android.szparag.newadaptiveweather.dagger.components.MainComponent;

/**
 * Created by ciemek on 23/09/2016.
 */
public interface BaseView {

    void showNetworkConnectionError();

    void showWeatherFetchSuccess();
    void showWeatherFetchFailure();

    void buildRecycler();
    void updateRecyclerItems(WeatherForecastResponse response);

    void hideWeatherLocationLayout();
    void showWeatherLocationLayout();
    void updateWeatherLocationLayout(City city);


    Fragment getAndroidView();
    Activity getActivity();

    RecyclerView getRecycler();
    BaseAdapter getAdapter();

}
