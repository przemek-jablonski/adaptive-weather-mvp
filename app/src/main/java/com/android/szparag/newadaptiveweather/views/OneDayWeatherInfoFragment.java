package com.android.szparag.newadaptiveweather.views;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.szparag.newadaptiveweather.R;
import com.android.szparag.newadaptiveweather.backend.models.web.WeatherForecastResponse;
import com.android.szparag.newadaptiveweather.backend.models.web.auxiliary.WeatherForecastItem;
import com.android.szparag.newadaptiveweather.presenters.OneDayWeatherInfoBasePresenter;
import com.android.szparag.newadaptiveweather.utils.Computation;
import com.android.szparag.newadaptiveweather.utils.Utils;
import com.android.szparag.newadaptiveweather.views.contracts.OneDayWeatherInfoView;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ciemek on 14/10/2016.
 */

public class OneDayWeatherInfoFragment extends Fragment implements OneDayWeatherInfoView {

    @Inject
    OneDayWeatherInfoBasePresenter presenter;

    @BindView(R.id.item_weather_chart_horizontal)
    LineChart forecastChartsView;

    public static OneDayWeatherInfoFragment newInstance(int pagePos, String pageTitle) {
        OneDayWeatherInfoFragment fragment = new OneDayWeatherInfoFragment();
        Bundle args = new Bundle();

        args.putInt("pagePos", pagePos);
        args.putString("pageTitle", pageTitle);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_one_day_weather_info, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ButterKnife.bind(this, getView());
        Utils.getDagger2(this).inject(this);

        hideForecastChartLayout();
    }

    @Override
    public void onResume() {
        super.onResume();

        presenter.setView(this);

        presenter.fetchWeather5Day();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void hideForecastChartLayout() {
        forecastChartsView.setVisibility(View.GONE);
    }

    @Override
    public void showForecastChartLayout() {
        forecastChartsView.setVisibility(View.VISIBLE);
    }

    @Override
    public void updateForecastChartLayout(WeatherForecastResponse response) {
        updateForecastChartLayout(response.list);
    }

    @Override
    public void updateForecastChartLayout(List<WeatherForecastItem> oneDayWeatherList) {
        List<Entry> entries = new ArrayList<>(oneDayWeatherList.size());

        for (int i = 0; i < oneDayWeatherList.size(); ++i) {
            entries.add(
                    new Entry(
                            Computation.getHour24FromUnixTime(oneDayWeatherList.get(i).calculationUnixTime),
                            Computation.kelvinToCelsiusConversion(oneDayWeatherList.get(i).mainWeatherData.temp, true))
            );
        }
        LineDataSet lineDataSet = new LineDataSet(entries, "temperature");

        LineData lineData = new LineData(lineDataSet);
        forecastChartsView.setData(lineData);
        forecastChartsView.invalidate();
    }

    @Override
    public SharedPreferences getSharedPreferences() {
        return null;
    }

    @Override
    public long writeToSharedPreferences(String key, long value) {
        return 0;
    }

    @Override
    public String getResourceString(int stringResId) {
        return getActivity().getString(stringResId);
    }

    @Override
    public Fragment getAndroidView() {
        return this;
    }

}
