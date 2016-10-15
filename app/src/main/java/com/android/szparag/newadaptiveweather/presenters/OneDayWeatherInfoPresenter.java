package com.android.szparag.newadaptiveweather.presenters;

import com.android.szparag.newadaptiveweather.backend.models.web.WeatherForecastResponse;
import com.android.szparag.newadaptiveweather.backend.services.WeatherService;
import com.android.szparag.newadaptiveweather.views.contracts.OneDayWeatherInfoView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ciemek on 15/10/2016.
 */

public class OneDayWeatherInfoPresenter implements OneDayWeatherInfoBasePresenter {


    //todo: map page fragment

    OneDayWeatherInfoView view;

    private float placeholderWarsawGpsLat = 52.233101f;
    private float placeholderWarsawGpsLon = 21.061399f;

    WeatherService service;

    public OneDayWeatherInfoPresenter(WeatherService service) {
        this.service = service;
    }

    @Override
    public void setView(OneDayWeatherInfoView view) {
        this.view = view;
    }

    @Override
    public void fetchWeather5Day() {
        service.getForecast5Day(placeholderWarsawGpsLat, placeholderWarsawGpsLon, new Callback<WeatherForecastResponse>() {
            @Override
            public void onResponse(Call<WeatherForecastResponse> call, Response<WeatherForecastResponse> response) {
                view.showForecastChartLayout();
                view.updateForecastChartLayout(response.body());
            }

            @Override
            public void onFailure(Call<WeatherForecastResponse> call, Throwable t) {

            }
        });
    }


}
