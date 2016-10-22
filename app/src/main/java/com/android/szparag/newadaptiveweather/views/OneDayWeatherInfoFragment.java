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
import com.android.szparag.newadaptiveweather.utils.Constants;
import com.android.szparag.newadaptiveweather.utils.Utils;
import com.android.szparag.newadaptiveweather.views.contracts.OneDayWeatherInfoView;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.android.szparag.newadaptiveweather.utils.Constants.DAY_FORWARD_KEY;
import static com.android.szparag.newadaptiveweather.utils.Constants.DAY_FORWARD_PAGE_TITLE_KEY;

/**
 * Created by ciemek on 14/10/2016.
 */

public class OneDayWeatherInfoFragment extends Fragment implements OneDayWeatherInfoView {

    @Inject
    OneDayWeatherInfoBasePresenter presenter;

    @BindView(R.id.item_weather_chart_horizontal)
    LineChart forecastChartsView;

    public static OneDayWeatherInfoFragment newInstance(int dayForward, String pageTitle) {
        OneDayWeatherInfoFragment fragment = new OneDayWeatherInfoFragment();
        Bundle args = new Bundle();

        args.putInt(DAY_FORWARD_KEY, dayForward); //todo: key string values in Constants.(...) class
        args.putString(DAY_FORWARD_PAGE_TITLE_KEY, pageTitle);
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

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            presenter.fetchWeatherOneDay();
        }
    }

    @Override
    public int getDaysForward() {
        return getArguments().getInt(DAY_FORWARD_KEY, 1);
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
    public void updateForecastChartLayout(LineDataSet graphLineDataSet) {

        graphLineDataSet.setColor(getResources().getColor(R.color.tempGraph_high));
        graphLineDataSet.setCircleColor(getResources().getColor(R.color.tempGraph_dot));
        graphLineDataSet.setFillColor(getResources().getColor(R.color.tempGraph_low));
        LineData lineData = new LineData(graphLineDataSet);
        showForecastChartLayout();

        forecastChartsView.setData(lineData);
        forecastChartsView.getAxisLeft().setLabelCount(0);
        forecastChartsView.getAxisRight().setEnabled(false);
        forecastChartsView.getXAxis().setAvoidFirstLastClipping(true);
        forecastChartsView.getXAxis().setAxisMaxValue(forecastChartsView.getXAxis().getAxisMaximum() + 4);
        forecastChartsView.getXAxis().setAxisMinValue(forecastChartsView.getXAxis().getAxisMinimum() - 4);
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
