package com.android.szparag.newadaptiveweather.presenters;

import com.android.szparag.newadaptiveweather.backend.models.realm.Weather;
import com.android.szparag.newadaptiveweather.views.contracts.OneDayWeatherInfoView;

import io.realm.RealmResults;

/**
 * Created by ciemek on 15/10/2016.
 */

public interface OneDayWeatherInfoBasePresenter {

    void setView(OneDayWeatherInfoView view);

    void fetchWeatherOneDay();

    void updateGraphData(RealmResults<Weather> weathersOneDay);
}
