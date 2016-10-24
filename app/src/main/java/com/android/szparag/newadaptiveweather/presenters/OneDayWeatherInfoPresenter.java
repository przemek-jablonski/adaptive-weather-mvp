package com.android.szparag.newadaptiveweather.presenters;

import android.util.Log;

import com.android.szparag.newadaptiveweather.backend.RealmUtils;
import com.android.szparag.newadaptiveweather.backend.models.realm.Weather;
import com.android.szparag.newadaptiveweather.backend.services.WeatherService;
import com.android.szparag.newadaptiveweather.utils.Computation;
import com.android.szparag.newadaptiveweather.utils.Utils;
import com.android.szparag.newadaptiveweather.views.contracts.OneDayWeatherInfoView;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * Created by ciemek on 15/10/2016.
 */

public class OneDayWeatherInfoPresenter implements OneDayWeatherInfoBasePresenter {

    //todo: map page fragment

    @Inject
    Realm realm;

    @Inject
    RealmUtils realmUtils;

    OneDayWeatherInfoView view;

//    private RealmResults<Weather> weathersOneDay;

    private float   placeholderWarsawGpsLat = 52.233101f;
    private float   placeholderWarsawGpsLon = 21.061399f;

//    private int     weatherDay = 1; //0 - today, 1 - tomorrow, etc
//    private Date    date;

    WeatherService  service;

    public OneDayWeatherInfoPresenter(WeatherService service) {
        this.service = service;
    }

    @Override
    public void setView(OneDayWeatherInfoView view) {
        this.view = view;
        Utils.getDagger2(view.getAndroidView()).inject(this);
    }

    @Override
    public void fetchWeatherOneDay() {
        int dayForward = view.getDaysForward();
        long time1 = Computation.getNextDaysTime(dayForward);
        long time2 = Computation.getNextDaysTime(dayForward+1);
        RealmResults<Weather> weathersOneDay = realmUtils.queryWeathersBetweenTimes(
                time1,
                time2,
                true)
                .findAllSorted("unixTime", Sort.ASCENDING);

        updateGraphData(weathersOneDay);
        updateOneDayForecastView(weathersOneDay);
    }

    @Override
    public void updateGraphData(RealmResults<Weather> weathersOneDay) {
        List<Entry> chartEntries = new ArrayList<>(weathersOneDay.size());

        for (int entry = 0; entry < weathersOneDay.size(); ++entry) {
            chartEntries.add(new Entry(
                    Computation.getHour24FromUnixTime(weathersOneDay.get(entry).getUnixTime()),
                    (int)Computation.kelvinToCelsiusConversion(weathersOneDay.get(entry).getTemperature(), false)
            ));
        }
        LineDataSet lineDataSet = new LineDataSet(chartEntries, "temp");

        view.updateForecastChartLayout(lineDataSet);
    }

    @Override
    public void updateOneDayForecastView(RealmResults<Weather> weathersOneDay) {
        view.updateOneDayForecastView(weathersOneDay);
    }

    @Override
    public void closeRealm() {
        realm.close();
    }
}
