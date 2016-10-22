package com.android.szparag.newadaptiveweather.views.contracts;

import com.android.szparag.newadaptiveweather.backend.models.web.WeatherForecastResponse;
import com.android.szparag.newadaptiveweather.backend.models.web.auxiliary.WeatherForecastItem;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.List;

/**
 * Created by ciemek on 14/10/2016.
 */

public interface OneDayWeatherInfoView extends BaseAndroidView {

    void hideForecastChartLayout();
    void showForecastChartLayout();
    void updateForecastChartLayout(LineDataSet graphLineDataSet);

    int getDaysForward();

}
