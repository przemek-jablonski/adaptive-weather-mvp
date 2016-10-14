package com.android.szparag.newadaptiveweather.views.contracts;

import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;

import com.android.szparag.newadaptiveweather.adapters.BaseAdapter;
import com.android.szparag.newadaptiveweather.backend.models.realm.Weather;
import com.android.szparag.newadaptiveweather.backend.models.web.WeatherForecastResponse;

/**
 * Created by ciemek on 23/09/2016.
 */

/**
 * View contract for BulkWeatherInfo (Activity or Fragment)
 */
public interface BulkWeatherInfoView extends BaseAndroidView {

    void showNetworkConnectionError();

    void showWeatherFetchSuccess();
    void showWeatherFetchFailure();

    void setBackground(Bitmap bitmap);
    void setBackgroundPlaceholder();
    void showBackgroundFetchFailure();


    void buildForecastCurrentView();
    void hideForecastCurrentView();
    void showForecastCurrentView();
    void updateForecastCurrentView(Weather weather);

    void buildForecast5DayView();
    void hideForecast5DayView();
    void showForecast5DayView();
    void updateForecast5DayView(WeatherForecastResponse forecast);

    void hideForecastLocationLayout();
    void showForecastLocationLayout();
    void updateForecastLocationTimeLayout(WeatherForecastResponse response);


    void hideForecastChartLayout();
    void showForecastChartLayout();
    void updateForecastChartLayout(WeatherForecastResponse response);



    int[] getViewDimensions();

    RecyclerView getRecycler();
    BaseAdapter getAdapter();

}
