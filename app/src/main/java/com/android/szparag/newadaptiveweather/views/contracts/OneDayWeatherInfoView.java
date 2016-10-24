package com.android.szparag.newadaptiveweather.views.contracts;

import com.android.szparag.newadaptiveweather.backend.models.realm.Weather;
import com.github.mikephil.charting.data.LineDataSet;

import io.realm.RealmResults;

/**
 * Created by ciemek on 14/10/2016.
 */

public interface OneDayWeatherInfoView extends BaseAndroidView {

    void buildOneDayForecastView(); //todo: in builds there should be adding view to layout
    void hideOneDayForecastView();
    void showOneDayForecastView();
    void updateOneDayForecastView(RealmResults<Weather> weathers);

    void buildForecastChartLayout();
    void hideForecastChartLayout();
    void showForecastChartLayout();
    void updateForecastChartLayout(LineDataSet graphLineDataSet);

    int getDaysForward();

}
