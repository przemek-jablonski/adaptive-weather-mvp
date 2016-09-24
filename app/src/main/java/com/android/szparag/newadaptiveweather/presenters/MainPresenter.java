package com.android.szparag.newadaptiveweather.presenters;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.android.szparag.newadaptiveweather.AppController;
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
public class MainPresenter implements BasePresenter, LifecyclePresenter {

    private BaseView view;

    @Inject
    WeatherService service;


    public MainPresenter(WeatherService service) {
        this.service = service;
    }


    @Override
    public void resume() {
        //...
    }

    @Override
    public void pause() {
        //...
    }


    @Override
    public void setView(BaseView view) {
        this.view = view;
    }

    @Override
    public void checkInternetConnectivity() {
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) view.getAndroidView().getActivity().getSystemService(
                view.getAndroidView().getContext().CONNECTIVITY_SERVICE)).getActiveNetworkInfo();

        if (!(activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting())) {
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
                WeatherForecastResponse responseBody = response.body();
                view.setTextWeather1(responseBody.city.name);
                view.setTextWeather2(Float.toString(273.15f - responseBody.list.get(0).main.temp));
                view.setTextWeather3(responseBody.list.get(0).weather.get(0).description);
                view.showWeatherFetchSuccess();
            }

            @Override
            public void onFailure(Call<WeatherForecastResponse> call, Throwable t) {
                view.setTextWeather1("failure");
                view.setTextWeather2("failure");
                view.setTextWeather3("failure");
                view.showWeatherFetchFailure();
            }
        });

    }

}
