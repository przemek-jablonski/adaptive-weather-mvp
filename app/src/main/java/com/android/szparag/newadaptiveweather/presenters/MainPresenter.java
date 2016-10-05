package com.android.szparag.newadaptiveweather.presenters;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.util.Log;

import com.android.szparag.newadaptiveweather.backend.models.WeatherCurrentResponse;
import com.android.szparag.newadaptiveweather.backend.models.WeatherForecastResponse;
import com.android.szparag.newadaptiveweather.backend.services.WeatherService;
import com.android.szparag.newadaptiveweather.utils.Utils;
import com.android.szparag.newadaptiveweather.views.BaseView;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.squareup.picasso.Transformation;


import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

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

//    @Inject
    WeatherService service;



    public MainPresenter(WeatherService service, String googleStaticMapsBaseUrl, String googleStaticMapsApiKey) {
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
        Picasso.with(view.getActivity()).load(createBackgroundMapUri())
                .into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                view.setBackground(bitmap);
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {
                view.showBackgroundFetchFailure();
            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {
                view.setBackgroundPlaceholder();
            }
        });
    }

    private String createBackgroundMapUri() {
        int[] viewDimensions = view.getViewDimensions();
        StringBuilder stringBuilder = new StringBuilder();


        stringBuilder.append(googleStaticMapsBaseUrl);
        stringBuilder.append('?');

        stringBuilder.append("center=");
        stringBuilder.append(Utils.makeLocationGpsString(
                placeholderWarsawGpsLat,
                placeholderWarsawGpsLon,
                true)
        );

        stringBuilder.append("&");
        stringBuilder.append("zoom=");
        stringBuilder.append(Integer.toString(6));

        stringBuilder.append("&");
        stringBuilder.append("size=");
        stringBuilder.append(Utils.makeStringGoogleMapsSize(
                viewDimensions[0],
                viewDimensions[1])
        );

        stringBuilder.append("&");
        stringBuilder.append("scale=");
        stringBuilder.append(Integer.toString(2));

        stringBuilder.append("&");
        stringBuilder.append("maptype=");
        stringBuilder.append("hybrid");

        stringBuilder.append("&");
        stringBuilder.append("format=");
        stringBuilder.append("jpg-baseline");

        stringBuilder.append("&");
        stringBuilder.append("key=");
        stringBuilder.append(googleStaticMapsApiKey);

        String s = stringBuilder.toString();

        Log.e("trollololol", s);

        return s;
    }

//
//    private String createBackgroundMapUri() {
//        Uri.Builder uriBuilder = new Uri.Builder();
//        int[] viewDimensions = view.getViewDimensions();
//
//        uriBuilder.encodedPath(googleStaticMapsBaseUrl);
//        uriBuilder.appendQueryParameter(
//                "center",       //make those load from strings.xml
//                Utils.makeLocationGpsString(
//                        placeholderWarsawGpsLat,
//                        placeholderWarsawGpsLon,
//                        true
//                ).toString()
//        );
//
//        uriBuilder.appendQueryParameter(
//                "zoom",
//                Integer.toString(6)
//        );
//
//        uriBuilder.appendQueryParameter(
//                "size",
//                Utils.makeStringGoogleMapsSize(viewDimensions[0], viewDimensions[1])
//        );
//
//        uriBuilder.appendQueryParameter(
//                "scale",
//                Integer.toString(2)
//        );
//
//        uriBuilder.appendQueryParameter(
//                "maptype",
//                "hybrid"        //make stuff in Constants class like Constants.Gmaps.Maptype.hybrid
//        );
//
//        uriBuilder.appendQueryParameter(
//                "format",
//                "jpg-baseline"
//        );
//
//        uriBuilder.appendQueryParameter(
//                "key",
//                googleStaticMapsApiKey
//        );
//
////        uriBuilder.appendQueryParameter(
////                "language",
////                "english"
////        );
//
//        Uri uri = uriBuilder.build();
//        String uriString = new String();
//        try {
//            uriString = URLDecoder.decode(uri.toString(), "UTF-8");
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//        Log.e("URI", uriString);
//        return uriString;
//    }

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

}
