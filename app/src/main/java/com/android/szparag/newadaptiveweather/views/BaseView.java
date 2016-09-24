package com.android.szparag.newadaptiveweather.views;

import android.support.v4.app.Fragment;

import com.android.szparag.newadaptiveweather.dagger.components.MainComponent;

/**
 * Created by ciemek on 23/09/2016.
 */
public interface BaseView {

    void showNetworkConnectionError();

    void showWeatherFetchSuccess();
    void showWeatherFetchFailure();

    void setTextWeather1(String text);
    void setTextWeather2(String text);
    void setTextWeather3(String text);

    Fragment getAndroidView();

}
