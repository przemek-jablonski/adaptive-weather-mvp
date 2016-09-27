package com.android.szparag.newadaptiveweather.presenters;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;

import com.android.szparag.newadaptiveweather.AppController;
import com.android.szparag.newadaptiveweather.adapters.MainAdapter;
import com.android.szparag.newadaptiveweather.backend.models.WeatherForecastResponse;
import com.android.szparag.newadaptiveweather.backend.services.WeatherService;
import com.android.szparag.newadaptiveweather.views.BaseView;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ciemek on 24/09/2016.
 */
public class MainPresenter implements BasePresenter {

    private BaseView view;

    @Inject
    WeatherService service;


    public MainPresenter(WeatherService service) {
        this.service = service;
    }

    @Override
    public void setView(BaseView view) {
        this.view = view;
    }

    @Override
    public void checkGrantedPermissions() {

    }

    @Override
    public void checkInternetConnectivity() {
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) view.getAndroidView().getActivity().getSystemService(
                view.getAndroidView().getContext().CONNECTIVITY_SERVICE)).getActiveNetworkInfo();

        if (activeNetworkInfo == null || !activeNetworkInfo.isConnected()) {
            view.showNetworkConnectionError();
        }
    }

    @Override
    public void fetchForecast5Day() {

        float gpsWarsawLat = 52.196217f;
        float gpsWarsawLon = 21.172225f;

        service.getForecast5Day(gpsWarsawLat, gpsWarsawLon, new Callback<WeatherForecastResponse>() {
            @Override
            public void onResponse(Call<WeatherForecastResponse> call, Response<WeatherForecastResponse> response) {
                view.showWeatherLocationLayout();
                view.updateRecyclerItems(response.body());
                view.updateWeatherLocationLayout(response.body().city);
                view.showWeatherFetchSuccess();
            }

            @Override
            public void onFailure(Call<WeatherForecastResponse> call, Throwable t) {
                view.showWeatherFetchFailure();
            }
        });

    }

}
