package com.android.szparag.newadaptiveweather;

import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.szparag.newadaptiveweather.adapters.BaseAdapter;
import com.android.szparag.newadaptiveweather.adapters.MainAdapter;
import com.android.szparag.newadaptiveweather.backend.models.WeatherForecastResponse;
import com.android.szparag.newadaptiveweather.backend.models.auxiliary.City;
import com.android.szparag.newadaptiveweather.presenters.BasePresenter;
import com.android.szparag.newadaptiveweather.utils.Utils;
import com.android.szparag.newadaptiveweather.views.BaseView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import decorators.HorizontalSeparator;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment implements BaseView {

    @Inject
    BasePresenter presenter;

    @BindView(R.id.button_retrofit_connect)
    Button buttonRetrofit;

    @BindView(R.id.main_fragment_recycler)
    RecyclerView recyclerView;

    @BindView(R.id.main_fragment_location)
    View locationLayout;

    private MainAdapter adapter;

    @OnClick(R.id.button_retrofit_connect)
    void onButtonRetrofitClick() {
        presenter.fetchForecast5Day();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ButterKnife.bind(this, getView());
        Utils.getDagger2(this).inject(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.setView(this);
        presenter.checkInternetConnectivity();
        buildRecycler();
        hideWeatherLocationLayout();
    }

    @Override
    public void showNetworkConnectionError() {
        Snackbar.make(getView(), getString(R.string.no_internet_connection_error), Snackbar.LENGTH_LONG);
    }

    @Override
    public void showWeatherFetchSuccess() {
        Snackbar.make(getView(), getString(R.string.weather_fetching_success), Snackbar.LENGTH_LONG);
    }

    @Override
    public void showWeatherFetchFailure() {
        Snackbar.make(getView(), getString(R.string.weather_fetching_failure), Snackbar.LENGTH_LONG);
    }

    @Override
    public void buildRecycler() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setHasFixedSize(false);
        recyclerView.addItemDecoration(new HorizontalSeparator(getActivity()));

        adapter = new MainAdapter(null);

        recyclerView.setAdapter(adapter);
    }

    @Override
    public RecyclerView getRecycler() {
        return recyclerView;
    }

    @Override
    public BaseAdapter getAdapter() {
        return adapter;
    }

    @Override
    public Fragment getAndroidView() {
        return this;
    }

    @Override
    public void updateRecyclerItems(WeatherForecastResponse response) {
        adapter.updateItems(response.list);
    }

    @Override
    public void hideWeatherLocationLayout() {
        locationLayout.setVisibility(LinearLayout.GONE);
    }

    @Override
    public void showWeatherLocationLayout() {
        locationLayout.setVisibility(LinearLayout.VISIBLE);
    }

    @Override
    public void updateWeatherLocationLayout(City city) {
        ((TextView) locationLayout.findViewById(R.id.item_weather_location_left)).setText(city.name);
        ((TextView) locationLayout.findViewById(R.id.item_weather_location_right)).setText(city.countryCode);
        ((TextView) locationLayout.findViewById(R.id.item_weather_location_gps)).setText(Utils.makeLocationGpsString(city));
    }
}
