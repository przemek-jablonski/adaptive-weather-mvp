package com.android.szparag.newadaptiveweather;

import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.szparag.newadaptiveweather.activities.MainActivity;
import com.android.szparag.newadaptiveweather.backend.models.WeatherForecastResponse;
import com.android.szparag.newadaptiveweather.backend.services.WeatherService;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    @BindView(R.id.weather_text1)
    TextView weatherText1;

    @BindView(R.id.weather_text2)
    TextView weatherText2;

    @BindView(R.id.weather_text3)
    TextView weatherText3;

    @Inject
    WeatherService service;

    private View fragmentView;

    private SharedPreferences sharedPreferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.fragment_main, container, false);

        return fragmentView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ButterKnife.bind(this, fragmentView);
//        Utils.getDagger().inject(this);
        ((AppController) getActivity().getApplication()).getComponent().inject(this);

        //dagger2 test: getting injected dependency from parent activity
        sharedPreferences = ((MainActivity) getActivity()).getSharedPreferences();

        //dagger2 test: printing result on screen
        ((TextView)getView().findViewById(R.id.fragment_text)).setText(
                Boolean.toString(
                        sharedPreferences.contains(((MainActivity) getActivity()).sharedPrefsKey)
                )
        );

        //retrofit2 test:
//        WeatherService service = new WeatherServiceImpl();
        float gpsWarsawLat = 52.196217f;
        float gpsWarsawLon = 21.178225f;

        service.getForecast5Day(gpsWarsawLat, gpsWarsawLon, new Callback<WeatherForecastResponse>() {
            @Override
            public void onResponse(Call<WeatherForecastResponse> call, Response<WeatherForecastResponse> response) {
                WeatherForecastResponse responseBody = response.body();
                weatherText1.setText(responseBody.city.name);
                weatherText2.setText(Float.toString(273.15f - responseBody.list.get(0).main.temp));
                weatherText3.setText(responseBody.list.get(0).weather.get(0).description);
            }

            @Override
            public void onFailure(Call<WeatherForecastResponse> call, Throwable t) {
                weatherText1.setText("failure");
            }
        });



    }
}
