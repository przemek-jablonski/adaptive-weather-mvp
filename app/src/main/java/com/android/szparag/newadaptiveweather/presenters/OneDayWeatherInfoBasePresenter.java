package com.android.szparag.newadaptiveweather.presenters;

import com.android.szparag.newadaptiveweather.views.contracts.OneDayWeatherInfoView;

/**
 * Created by ciemek on 15/10/2016.
 */

public interface OneDayWeatherInfoBasePresenter {

    void setView(OneDayWeatherInfoView view);

    void fetchWeather5Day();


}
