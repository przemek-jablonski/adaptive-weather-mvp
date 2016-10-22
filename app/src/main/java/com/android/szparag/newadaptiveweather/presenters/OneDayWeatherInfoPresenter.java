package com.android.szparag.newadaptiveweather.presenters;

import com.android.szparag.newadaptiveweather.backend.models.web.WeatherForecastResponse;
import com.android.szparag.newadaptiveweather.backend.models.web.auxiliary.WeatherForecastItem;
import com.android.szparag.newadaptiveweather.backend.services.WeatherService;
import com.android.szparag.newadaptiveweather.utils.Computation;
import com.android.szparag.newadaptiveweather.views.contracts.OneDayWeatherInfoView;

import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ciemek on 15/10/2016.
 */

public class OneDayWeatherInfoPresenter implements OneDayWeatherInfoBasePresenter {

    //todo: map page fragment

    OneDayWeatherInfoView view;

    private float   placeholderWarsawGpsLat = 52.233101f;
    private float   placeholderWarsawGpsLon = 21.061399f;

    private int     weatherDay = 1; //0 - today, 1 - tomorrow, etc
    private Date    date;

    WeatherService  service;

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
                //fixme: this really should be rewritten to Realm - put data into realm and query for given day ;/
                //fixme: asap
                view.showForecastChartLayout();
//
//
//                Date todayDate = new Date(Computation.getCurrentUnixTime());
//
//                List<WeatherForecastItem> weatherForecastItems = response.body().list;
//                List<WeatherForecastItem> weatherForecastDayItem = new LinkedList<WeatherForecastItem>();
//                Calendar cal = Calendar.getInstance();
//
//                cal.setTimeInMillis(weatherForecastItems.get(0).calculationUnixTime * 1000L);
//                cal.get()
//
//                for (int i=0; i < weatherForecastItems.size(); ++i) {
//                    if (cal.get weatherForecastItems.get(i).calculationUnixTime)
//                }

                view.updateForecastChartLayout(response.body());
            }

            @Override
            public void onFailure(Call<WeatherForecastResponse> call, Throwable t) {

            }
        });
    }


}
