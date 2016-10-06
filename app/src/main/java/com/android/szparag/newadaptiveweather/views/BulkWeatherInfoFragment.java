package com.android.szparag.newadaptiveweather.views;

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
import com.android.szparag.newadaptiveweather.backend.models.web.WeatherCurrentResponse;
import com.android.szparag.newadaptiveweather.backend.models.web.WeatherForecastResponse;
import com.android.szparag.newadaptiveweather.presenters.BasePresenter;
import com.android.szparag.newadaptiveweather.utils.Utils;


import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.android.szparag.newadaptiveweather.decorators.HorizontalSeparator;

/**
 * A placeholder fragment containing a simple view.
 */
public class BulkWeatherInfoFragment extends Fragment implements BaseView {

    @Inject
    BasePresenter presenter;

    @BindView(R.id.bulk_fragment_location)
    View    locationView;

    @BindView(R.id.bulk_fragment_current)
    View    forecastCurrentView;

    @BindView(R.id.bulk_fragment_5day_recycler)
    RecyclerView forecast5dayView;


    //todo: make a viewholder pattern for every view inside forecastCurrentView
    @BindView(R.id.item_weather_current_shortdesc)
    TextView forecastCurrentShortDesc;

    @BindView(R.id.item_weather_current_tempmaxmin)
    TextView forecastCurrentTemperatures;

    @BindView(R.id.item_weather_current_temperature)
    TextView forecastCurrentTemperature;

    @BindView(R.id.item_weather_current_desc)
    TextView forecastCurrentDesc;

    @BindView(R.id.item_weather_current_pressure_text)
    TextView forecastCurrentPressureText;

    @BindView(R.id.item_weather_current_pressure_val)
    TextView forecastCurrentPressureVal;

    @BindView(R.id.item_weather_current_humidity_text)
    TextView forecastCurrentHumidityText;

    @BindView(R.id.item_weather_current_humidity_val)
    TextView forecastCurrentHumidityVal;

    @BindView(R.id.item_weather_current_windspeed_text)
    TextView forecastCurrentWindspeedText;

    @BindView(R.id.item_weather_current_windspeed_val)
    TextView forecastCurrentWindspeedVal;

    @BindView(R.id.item_weather_current_winddirection_text)
    TextView forecastCurrentWinddirectionText;

    @BindView(R.id.item_weather_current_winddirection_val)
    TextView forecastCurrentWinddirectionVal;

    @BindView(R.id.item_weather_current_clouds_text)
    TextView forecastCurrentCloudsText;

    @BindView(R.id.item_weather_current_clouds_val)
    TextView forecastCurrentCloudsVal;

    @BindView(R.id.item_weather_current_rain_text)
    TextView forecastCurrentRainText;

    @BindView(R.id.item_weather_current_rain_val)
    TextView forecastCurrentRainVal;

    @BindView(R.id.item_weather_current_snow_text)
    TextView forecastCurrentSnowText;

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
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.setView(this);
        presenter.checkInternetConnectivity();
        hideForecastLocationLayout();
        buildForecastCurrentView();
        buildForecast5DayView();


        presenter.fetchWeatherCurrent();
        presenter.fetchWeather5Day();

//        presenter.fetchBackgroundMap();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void buildForecastCurrentView() {
//        unbinderCurrentView = ButterKnife.bind(this, forecastCurrentView);
    }

    @Override
    public void updateForecastCurrentView(WeatherCurrentResponse forecast) {
        forecastCurrentTemperature.setText(Utils.makeTemperatureString(forecast.mainWeatherData.temp));
        forecastCurrentTemperatures.setText(Utils.makeTemperatureMinMaxString(forecast.mainWeatherData.tempMax, forecast.mainWeatherData.tempMin));
        forecastCurrentShortDesc.setText(forecast.weather.get(0).main);
        forecastCurrentDesc.setText(forecast.weather.get(0).description);

        forecastCurrentPressureText.setText(getString(R.string.pressure_text));
        forecastCurrentPressureVal.setText(Utils.makeStringRoundedFloat(forecast.mainWeatherData.pressureAtmospheric));

        forecastCurrentHumidityText.setText(getString(R.string.humidity_text));
        forecastCurrentHumidityVal.setText(Utils.makeStringRoundedFloat(forecast.mainWeatherData.humidityPercent));

        forecastCurrentWindspeedText.setText(getString(R.string.windspeed_text));
        forecastCurrentWindspeedVal.setText(Utils.makeStringRoundedFloat(forecast.wind.speed));

        forecastCurrentWinddirectionText.setText(getString(R.string.winddirection_text));
        forecastCurrentWinddirectionVal.setText(Utils.makeStringRoundedFloat(forecast.wind.directionDegrees));

        forecastCurrentCloudsText.setText(getString(R.string.clouds_text));
        forecastCurrentCloudsVal.setText(Utils.makeStringRoundedFloat(forecast.clouds.cloudsPercent));

        forecastCurrentRainText.setText(getString(R.string.rain_text));
        forecastCurrentSnowText.setText(getString(R.string.snow_text));


        //todo: for the love of god, this nulls in models need to be got rid of
        if (forecast.rain != null) {
            if (forecast.rain.past3h != null) {
                forecastCurrentRainVal.setText(Utils.makeStringRoundedFloat(forecast.rain.past3h));
            } else {
                forecastCurrentRainVal.setText(" 0 ");
            }
        } else {
            forecastCurrentRainVal.setText(" 0 ");
        }

        //todo: who doesn't love spaghetti, right?
        if (forecast.snow != null ) {
            if (forecast.snow.past3h != null) {
                forecastCurrentSnowVal.setText(Utils.makeStringRoundedFloat(forecast.snow.past3h));
            } else {
                forecastCurrentSnowVal.setText(" - ");
            }
        } else {
            forecastCurrentSnowVal.setText(" - ");
        }
    }

    @Override
    public void buildForecast5DayView() {
        forecast5dayView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        forecast5dayView.setHasFixedSize(false);
        forecast5dayView.addItemDecoration(new HorizontalSeparator(getActivity()));
        adapter = new MainAdapter(null);
        forecast5dayView.setAdapter(adapter);
        forecast5dayView.setNestedScrollingEnabled(false);

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
