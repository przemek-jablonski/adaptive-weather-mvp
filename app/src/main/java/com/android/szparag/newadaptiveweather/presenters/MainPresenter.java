package com.android.szparag.newadaptiveweather.presenters;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.android.szparag.newadaptiveweather.backend.models.web.WeatherCurrentResponse;
import com.android.szparag.newadaptiveweather.backend.models.web.WeatherForecastResponse;
import com.android.szparag.newadaptiveweather.backend.services.WeatherService;
import com.android.szparag.newadaptiveweather.utils.Constants;
import com.android.szparag.newadaptiveweather.utils.Utils;
import com.android.szparag.newadaptiveweather.views.BaseView;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;


import java.util.Date;

import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ciemek on 24/09/2016.
 */
public class MainPresenter implements BasePresenter {

    private BaseView view;

    private float placeholderWarsawGpsLat = 52.233101f;
    private float placeholderWarsawGpsLon = 21.061399f;

    private String googleStaticMapsBaseUrl;
    private String googleStaticMapsApiKey;

    WeatherService service;
    Realm realm;



    public MainPresenter(/*Realm realm,*/ WeatherService service, String googleStaticMapsBaseUrl, String googleStaticMapsApiKey) {
//        this.realm = realm;
        this.service = service;
        this.googleStaticMapsBaseUrl = googleStaticMapsBaseUrl;
        this.googleStaticMapsApiKey = googleStaticMapsApiKey;
    }

    @Override
    public void setView(BaseView view) {
        this.view = view;
    }

    @Override
    public void checkGrantedPermissions() {

    }

    @Override
    public void checkInternetConnectivity() {
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) view.getAndroidView().getActivity().getSystemService(
                view.getAndroidView().getContext().CONNECTIVITY_SERVICE)).getActiveNetworkInfo();

        if (activeNetworkInfo == null || !activeNetworkInfo.isConnected()) {
            view.showNetworkConnectionError();
        }
    }

    @Override
    public void fetchWeatherCurrent() {

        service.getCurrentWeather(placeholderWarsawGpsLat, placeholderWarsawGpsLon, new Callback<WeatherCurrentResponse>() {
            @Override
            public void onResponse(Call<WeatherCurrentResponse> call, Response<WeatherCurrentResponse> response) {
                view.showForecastLocationLayout();
                view.updateForecastCurrentView(response.body());
                view.showWeatherFetchSuccess();
            }

            @Override
            public void onFailure(Call<WeatherCurrentResponse> call, Throwable t) {
                view.showWeatherFetchFailure();
            }
        });
    }

    @Override
    public void fetchWeather5Day() {

        service.getForecast5Day(placeholderWarsawGpsLat, placeholderWarsawGpsLon, new Callback<WeatherForecastResponse>() {
            @Override
            public void onResponse(Call<WeatherForecastResponse> call, Response<WeatherForecastResponse> response) {

//                realm.executeTransactionAsync(new Realm.Transaction() {
//                    @Override
//                    public void execute(Realm realm) {
//
//                    }
//                }, new Realm.Transaction.OnSuccess() {
//                    @Override
//                    public void onSuccess() {
//
//                    }
//                }, new Realm.Transaction.OnError() {
//                    @Override
//                    public void onError(Throwable error) {
//
//                    }
//                });

                view.showForecastLocationLayout();
                view.updateForecast5DayView(response.body());
                view.updateForecastLocationTimeLayout(response.body());
                view.showWeatherFetchSuccess();
            }

            @Override
            public void onFailure(Call<WeatherForecastResponse> call, Throwable t) {
                view.showWeatherFetchFailure();
            }
        });

    }

    @Override
    public void fetchBackgroundMap() {
        createBackgroundMapUri();
//        Picasso.with(view.getActivity()).load(createBackgroundMapUri())
//                .into(new Target() {
//            @Override
//            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
//                view.setBackground(bitmap);
//            }
//
//            @Override
//            public void onBitmapFailed(Drawable errorDrawable) {
//                view.showBackgroundFetchFailure();
//            }
//
//            @Override
//            public void onPrepareLoad(Drawable placeHolderDrawable) {
//                view.setBackgroundPlaceholder();
//            }
//        });
    }


    private String createBackgroundMapUri() {
        int[] viewDimensions = view.getViewDimensions();
        return Utils.makeGoogleMapsStaticMapUri(
                googleStaticMapsBaseUrl,
                googleStaticMapsApiKey,
                placeholderWarsawGpsLat,
                placeholderWarsawGpsLon,
                viewDimensions[0],
                viewDimensions[1],
                9,
                Constants.GoogleMapsStatic.Scale.SCALE_X2,
                Constants.GoogleMapsStatic.MapType.HYBRID,
                Constants.GoogleMapsStatic.Format.JPG
        );
    }

    @Override
    public void fetchBackgroundImage() {
        //...
    }

    @Override
    public void fetchWeatherPollutionCO() {
        //..
    }

    @Override
    public void fetchWeatherPollutionO3() {
        //..

    }

    @Override
    public void fetchWeatherMapTemperature() {
        //...
    }

    @Override
    public void fetchWeatherMapClouds() {
        //...
    }

    @Override
    public void fetchWeatherMapPressure() {
        //...
    }

    @Override
    public void fetchWeatherMapPrecipitation() {
        //...
    }

    @Override
    public void fetchWeatherStations() {
        //..
    }

    @Override
    public void realmClose() {
//      is it really nessesary, since i have only 1 presenter here?
//      realm.close();
    }

}
