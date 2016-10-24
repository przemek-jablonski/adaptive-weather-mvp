package com.android.szparag.newadaptiveweather.views;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
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
import com.android.szparag.newadaptiveweather.adapters.CurrentWeatherAdapter;
import com.android.szparag.newadaptiveweather.adapters.WeatherAdapter;
import com.android.szparag.newadaptiveweather.backend.models.realm.Weather;
import com.android.szparag.newadaptiveweather.decorators.HorizontalSeparator;
import com.android.szparag.newadaptiveweather.presenters.BulkWeatherInfoBasePresenter;
import com.android.szparag.newadaptiveweather.utils.Computation;
import com.android.szparag.newadaptiveweather.utils.Utils;
import com.android.szparag.newadaptiveweather.views.contracts.BulkWeatherInfoView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.realm.RealmResults;

/**
 * A placeholder fragment containing a simple view.
 */
public class BulkWeatherInfoFragment extends Fragment implements BulkWeatherInfoView {

    @Inject
    BulkWeatherInfoBasePresenter presenter;

    @BindView(R.id.bulk_fragment_location)
    View        locationView;

    @BindView(R.id.bulk_fragment_current)
    View        forecastCurrentView;

    @BindView(R.id.bulk_fragment_5day_recycler)
    RecyclerView forecast5dayView;

    //todo: or maybe as a fragment? like WeatherCurrentFragment

    private CurrentWeatherAdapter currentWeatherAdapter;
    private WeatherAdapter weatherAdapter;
    private Unbinder    unbinder;


    public static BulkWeatherInfoFragment newInstance(int pagePos, String pageTitle) {
        BulkWeatherInfoFragment fragment = new BulkWeatherInfoFragment();
        Bundle args = new Bundle();

        args.putInt("pagePos", pagePos);
        args.putString("pageTitle", pageTitle);
        fragment.setArguments(args);

        return fragment;
    }

    // fragment lifecycle calls:
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_bulk_weather_info, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, getView());
        buildForecastCurrentView();
        buildForecast5DayView();
        hideForecastCurrentView();
        hideForecastLocationTimeLayout();
        hideForecast5DayView(); //todo: there should be hide method inside each build()
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Utils.getDagger2(this).inject(this);
        presenter.setView(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.fetchWeatherCurrent();
        presenter.fetchWeather5Day();
        presenter.fetchBackgroundMap();
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.checkInternetConnectivity();
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
        currentWeatherAdapter = new CurrentWeatherAdapter(forecastCurrentView);
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
        currentWeatherAdapter.setCurrentWeather(weather);
        currentWeatherAdapter.onBind();
        showForecastCurrentView();
    }

    @Override
    public void buildForecast5DayView() {
        forecast5dayView.setLayoutManager(
                new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        forecast5dayView.setHasFixedSize(false);
        forecast5dayView.addItemDecoration(new HorizontalSeparator(getActivity()));
        weatherAdapter = new WeatherAdapter(null);
        forecast5dayView.setAdapter(weatherAdapter);
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
    public void updateForecast5DayView(RealmResults<Weather> weathers) {
        showForecast5DayView();
        weatherAdapter.updateItems(weathers);
    }


    // location on/off
    @Override
    public void showForecastLocationTimeLayout() {
        locationView.setVisibility(LinearLayout.VISIBLE);
    }

    @Override
    public void hideForecastLocationTimeLayout() {
        locationView.setVisibility(LinearLayout.INVISIBLE);
    }

    @Override
    public void updateForecastLocationTimeLayout(Weather weather) {
        showForecastLocationTimeLayout();
        ((TextView) locationView.findViewById(R.id.item_weather_location_left)).setText(weather.getCity());
        ((TextView) locationView.findViewById(R.id.item_weather_location_right)).setText("PL"); //fixme
//        ((TextView) locationView.findViewById(R.id.item_weather_location_gps)).setText(Utils.makeLocationGpsString("asdadsasd")); //fixme
        ((TextView) locationView.findViewById(R.id.item_weather_location_gps)).setText("asdadsasd");
        ((TextView) locationView.findViewById(R.id.item_weather_location_time)).setText(Computation.getHumanDateFromUnixTime(weather.getUnixTime()));
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
//        getView().setBackground(new BitmapDrawable(getResources(), bitmap));
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

    public BaseAdapter getWeatherAdapter() {
        return weatherAdapter;
    }

    @Override
    public Fragment getAndroidView() {
        return this;
    }

}
