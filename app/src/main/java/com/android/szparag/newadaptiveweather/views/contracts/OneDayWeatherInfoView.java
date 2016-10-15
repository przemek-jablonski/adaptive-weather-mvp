package com.android.szparag.newadaptiveweather.views.contracts;

import com.android.szparag.newadaptiveweather.backend.models.web.WeatherForecastResponse;

/**
 * Created by ciemek on 14/10/2016.
 */

public interface OneDayWeatherInfoView extends BaseAndroidView {

    void hideForecastChartLayout();
    void showForecastChartLayout();
    void updateForecastChartLayout(WeatherForecastResponse response);

}
