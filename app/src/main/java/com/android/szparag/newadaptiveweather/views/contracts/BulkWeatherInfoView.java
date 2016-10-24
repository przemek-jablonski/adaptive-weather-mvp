package com.android.szparag.newadaptiveweather.views.contracts;

import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;

import com.android.szparag.newadaptiveweather.adapters.BaseRecyclerViewAdapter;
import com.android.szparag.newadaptiveweather.adapters.FrontWeatherAdapter;
import com.android.szparag.newadaptiveweather.backend.models.realm.Weather;

import io.realm.RealmResults;

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

    void buildFrontWeatherView();
    void hideFrontWeatherView();
    void showFrontWeatherView();
    void updateFrontWeatherView(Weather weather);

    void buildForecastCurrentView();
    void hideForecastCurrentView();
    void showForecastCurrentView();
    void updateForecastCurrentView(Weather weather);

    void buildForecast5DayView();
    void hideForecast5DayView();
    void showForecast5DayView();
    void updateForecast5DayView(RealmResults<Weather> weathers);

    void hideForecastLocationTimeLayout();
    void showForecastLocationTimeLayout();
    void updateForecastLocationTimeLayout(Weather weather);

//    void hideForecastChartLayout();
//    void showForecastChartLayout();
//    void updateForecastChartLayout(WeatherForecastResponse response);



    int[] getViewDimensions();

    RecyclerView getRecycler();
    BaseRecyclerViewAdapter getWeatherAdapter();
    public FrontWeatherAdapter getForecastFrontAdapter();

}
