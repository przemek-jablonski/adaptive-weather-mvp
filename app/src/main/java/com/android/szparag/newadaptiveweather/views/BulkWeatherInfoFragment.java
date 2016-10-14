package com.android.szparag.newadaptiveweather.views;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.szparag.newadaptiveweather.R;
import com.android.szparag.newadaptiveweather.adapters.BaseAdapter;
import com.android.szparag.newadaptiveweather.adapters.MainAdapter;
import com.android.szparag.newadaptiveweather.backend.models.realm.Weather;
import com.android.szparag.newadaptiveweather.backend.models.web.WeatherForecastResponse;
import com.android.szparag.newadaptiveweather.presenters.BasePresenter;
import com.android.szparag.newadaptiveweather.utils.Utils;


import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.android.szparag.newadaptiveweather.decorators.HorizontalSeparator;
import com.android.szparag.newadaptiveweather.views.contracts.BulkWeatherInfoView;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class BulkWeatherInfoFragment extends Fragment implements BulkWeatherInfoView {

    @Inject
    BasePresenter presenter;

    @BindView(R.id.bulk_fragment_location)
    View        locationView;

    @BindView(R.id.bulk_fragment_current)
    View        forecastCurrentView;

    @BindView(R.id.bulk_fragment_5day_recycler)
    RecyclerView forecast5dayView;

    @BindView(R.id.item_weather_charts_horizontal)
    LineChart   forecastChartsView;


    //todo: make a viewholder pattern for every view inside forecastCurrentView
    //TODO: SOONER THAN LATER
    @BindView(R.id.item_weather_current_shortdesc)
    TextView forecastCurrentShortDesc;

    @BindView(R.id.item_weather_current_tempmaxmin)
    TextView forecastCurrentTemperatures;

    @BindView(R.id.item_weather_current_temperature)
    TextView forecastCurrentTemperature;

    @BindView(R.id.item_weather_current_desc)
    TextView forecastCurrentDesc;

    @BindView(R.id.item_weather_current_pressure_val)
    TextView forecastCurrentPressureVal;

    @BindView(R.id.item_weather_current_humidity_val)
    TextView forecastCurrentHumidityVal;

    @BindView(R.id.item_weather_current_windspeed_val)
    TextView forecastCurrentWindspeedVal;

    @BindView(R.id.item_weather_current_winddirection_val)
    TextView forecastCurrentWinddirectionVal;

    @BindView(R.id.item_weather_current_clouds_val)
    TextView forecastCurrentCloudsVal;

    @BindView(R.id.item_weather_current_rain_val)
    TextView forecastCurrentRainVal;

    @BindView(R.id.item_weather_current_snow_val)
    TextView forecastCurrentSnowVal;


    private MainAdapter adapter;
    private Unbinder    unbinder;


    // fragment lifecycle calls:
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_bulk_weather_info, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        unbinder = ButterKnife.bind(this, getView());
        Utils.getDagger2(this).inject(this);

        hideForecastCurrentView();
        hideForecastLocationLayout();
        hideForecast5DayView();
        hideForecastChartLayout();
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.setView(this);
        presenter.checkInternetConnectivity();
        buildForecastCurrentView();
        buildForecast5DayView();

        presenter.fetchWeatherCurrent();
        presenter.fetchWeather5Day();
        presenter.fetchBackgroundMap();
    }

    @Override
    public void onPause() {
        super.onPause();
        presenter.unregisterRealm();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.realmClose();
    }

    //methods for current forecast view:
    @Override
    public void buildForecastCurrentView() {
//        unbinderCurrentView = ButterKnife.bind(this, forecastCurrentView);
    }

    @Override
    public void hideForecastCurrentView() {
        forecastCurrentView.setVisibility(View.GONE);
    }

    @Override
    public void showForecastCurrentView() {
        forecastCurrentView.setVisibility(View.VISIBLE);
    }

    @Override
    public void updateForecastCurrentView(Weather weather) {
        forecastCurrentTemperature.setText(Utils.makeTemperatureString(weather.getTemperature()));
        forecastCurrentTemperatures.setText(Utils.makeTemperatureMinMaxString(weather.getTemperatureMax(), weather.getTemperatureMin()));

        forecastCurrentShortDesc.setText(weather.getWeatherMain());
        forecastCurrentDesc.setText(weather.getWeatherDescription());

        forecastCurrentPressureVal.setText(Utils.makeStringRoundedFloat(weather.getPressureAtmospheric()));
        forecastCurrentHumidityVal.setText(Utils.makeStringRoundedFloat(weather.getHumidityPercent()));

        forecastCurrentWindspeedVal.setText(Utils.makeStringRoundedFloat(weather.getWindSpeed()));
        forecastCurrentWinddirectionVal.setText(Utils.makeStringRoundedFloat(weather.getWindDirection()));

        forecastCurrentCloudsVal.setText(Utils.makeStringRoundedFloat(weather.getCloudsPercent()));

        forecastCurrentRainVal.setText(Utils.makeStringRoundedFloat(weather.getRainPast3h()));
        forecastCurrentSnowVal.setText(Utils.makeStringRoundedFloat(weather.getSnowPast3h()));
    }

    //methods for 5day forecast view:
    @Override
    public void buildForecast5DayView() {
        forecast5dayView.setLayoutManager(
                new LinearLayoutManager(
                    getActivity(),
                    LinearLayoutManager.VERTICAL,
                    false)
        );
        forecast5dayView.setHasFixedSize(false);
        forecast5dayView.addItemDecoration(new HorizontalSeparator(getActivity()));
        adapter = new MainAdapter(null);
        forecast5dayView.setAdapter(adapter);
        forecast5dayView.setNestedScrollingEnabled(false);

    }

    @Override
    public void hideForecast5DayView() {
        forecast5dayView.setVisibility(View.GONE);
    }

    @Override
    public void showForecast5DayView() {
        forecast5dayView.setVisibility(View.VISIBLE);
    }

    @Override
    public void updateForecast5DayView(WeatherForecastResponse forecast) {
        adapter.updateItems(forecast.list);
    }


    // location on/off
    @Override
    public void showForecastLocationLayout() {
        locationView.setVisibility(LinearLayout.VISIBLE);
    }

    @Override
    public void hideForecastLocationLayout() {
        locationView.setVisibility(LinearLayout.INVISIBLE);
    }

    @Override
    public void updateForecastLocationTimeLayout(WeatherForecastResponse response) {
        ((TextView) locationView.findViewById(R.id.item_weather_location_left)).setText(response.city.name);
        ((TextView) locationView.findViewById(R.id.item_weather_location_right)).setText(response.city.countryCode);
        ((TextView) locationView.findViewById(R.id.item_weather_location_gps)).setText(Utils.makeLocationGpsString(response.city));
        ((TextView) locationView.findViewById(R.id.item_weather_location_time)).setText(response.list.get(0).calculationUTCTime);
    }

    // location
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
        List<Entry> entries = new ArrayList<>(response.list.size());

        for (int i = 0; i < response.list.size(); ++i) {
            entries.add(new Entry(response.list.get(i).calculationUnixTime, response.list.get(i).mainWeatherData.temp));
        }
        LineDataSet lineDataSet = new LineDataSet(entries, "temperature");
        LineData lineData = new LineData(lineDataSet);
        forecastChartsView.setData(lineData);
        forecastChartsView.invalidate();

    }

    @Override
    public SharedPreferences getSharedPreferences() {
        return getActivity().getPreferences(Context.MODE_PRIVATE);
    }

    @Override
    public long writeToSharedPreferences(String key, long value) {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.remove(key);
        editor.putLong(key, value);
        editor.apply();
        return value;
    }

    public String getResourceString(int stringResId) {
        return getActivity().getString(stringResId);
    }

    @Override
    public int[] getViewDimensions() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return new int[] {
                displayMetrics.widthPixels,
                displayMetrics.heightPixels
        };
    }

    @Override
    public void setBackground(Bitmap bitmap) {
        getView().setBackground(new BitmapDrawable(getResources(), bitmap));
    }

    @Override
    public void setBackgroundPlaceholder() {
        //...
    }

    // snackbar responses:
    @Override
    public void showNetworkConnectionError() {
        Snackbar.make(getView(), getString(R.string.no_internet_connection_error), Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void showWeatherFetchSuccess() {
        Snackbar.make(getView(), getString(R.string.weather_fetching_success), Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void showWeatherFetchFailure() {
        Snackbar.make(getView(), getString(R.string.weather_fetching_failure), Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void showBackgroundFetchFailure() {
        Snackbar.make(getView(), getString(R.string.background_fetching_failure), Snackbar.LENGTH_LONG).show();
    }


    // overriden accessors:
    @Override
    public RecyclerView getRecycler() {
        return forecast5dayView;
    }

    @Override
    public BaseAdapter getAdapter() {
        return adapter;
    }

    @Override
    public Fragment getAndroidView() {
        return this;
    }

}
