package com.android.szparag.newadaptiveweather.presenters;

import com.android.szparag.newadaptiveweather.views.BaseView;

/**
 * Created by ciemek on 24/09/2016.
 */
public interface BasePresenter {

    void setView(BaseView view);

    void checkInternetConnectivity();

    void fetchForecast5Day();

}
