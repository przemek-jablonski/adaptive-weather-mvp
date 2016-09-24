package com.android.szparag.newadaptiveweather;

import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.szparag.newadaptiveweather.presenters.BasePresenter;
import com.android.szparag.newadaptiveweather.utils.Utils;
import com.android.szparag.newadaptiveweather.views.BaseView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment implements BaseView {

    @BindView(R.id.weather_text1)
    TextView weatherText1;

    @BindView(R.id.weather_text2)
    TextView weatherText2;

    @BindView(R.id.weather_text3)
    TextView weatherText3;

    @OnClick(R.id.button_retrofit_connect)
    void onButtonRetrofitClick() { presenter.fetchForecast5Day(); }

    private View fragmentLayout;

    @Inject
    BasePresenter presenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentLayout = inflater.inflate(R.layout.fragment_main, container, false);
        return fragmentLayout;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ButterKnife.bind(this, fragmentLayout);
        Utils.getDagger2(this).inject(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.setView(this);
        presenter.checkInternetConnectivity();
    }

    @Override
    public void showNetworkConnectionError() {
        Snackbar.make(fragmentLayout, getString(R.string.no_internet_connection_error), Snackbar.LENGTH_LONG);
    }

    @Override
    public void showWeatherFetchSuccess() {
        Snackbar.make(fragmentLayout, getString(R.string.weather_fetching_success), Snackbar.LENGTH_LONG);
    }

    @Override
    public void showWeatherFetchFailure() {
        Snackbar.make(fragmentLayout, getString(R.string.weather_fetching_failure), Snackbar.LENGTH_LONG);
    }

    @Override
    public void setTextWeather1(String text) {
        weatherText1.setText(text);
    }

    @Override
    public void setTextWeather2(String text) {
        weatherText2.setText(text);
    }

    @Override
    public void setTextWeather3(String text) {
        weatherText3.setText(text);
    }

    @Override
    public Fragment getAndroidView() {
        return this;
    }

}
