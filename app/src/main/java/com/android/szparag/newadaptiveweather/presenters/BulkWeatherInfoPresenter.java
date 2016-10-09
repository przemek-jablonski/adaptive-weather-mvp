package com.android.szparag.newadaptiveweather.presenters;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;

import com.android.szparag.newadaptiveweather.backend.MethodCallback;
import com.android.szparag.newadaptiveweather.backend.interceptors.AvoidNullsInterceptor;
import com.android.szparag.newadaptiveweather.backend.models.realm.Weather;
import com.android.szparag.newadaptiveweather.backend.models.web.WeatherCurrentResponse;
import com.android.szparag.newadaptiveweather.backend.models.web.WeatherForecastResponse;
import com.android.szparag.newadaptiveweather.backend.services.WeatherService;
import com.android.szparag.newadaptiveweather.utils.Computation;
import com.android.szparag.newadaptiveweather.utils.Constants;
import com.android.szparag.newadaptiveweather.utils.Utils;
import com.android.szparag.newadaptiveweather.views.BaseView;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;



import javax.inject.Inject;

import io.realm.Realm;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ciemek on 24/09/2016.
 */
public class BulkWeatherInfoPresenter implements BasePresenter {

    @Inject
    Realm realm;

    @Inject
    AvoidNullsInterceptor avoidNullsInterceptor;

    private BaseView view;

    private float placeholderWarsawGpsLat = 52.233101f;
    private float placeholderWarsawGpsLon = 21.061399f;

    private String googleStaticMapsBaseUrl;
    private String googleStaticMapsApiKey;

    WeatherService service;



    public BulkWeatherInfoPresenter(WeatherService service, String googleStaticMapsBaseUrl, String googleStaticMapsApiKey) {
        this.service = service;
        this.googleStaticMapsBaseUrl = googleStaticMapsBaseUrl;
        this.googleStaticMapsApiKey = googleStaticMapsApiKey;
    }

    @Override
    public void setView(BaseView view) {
        this.view = view;
        Utils.getDagger2(view.getAndroidView()).inject(this);
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
        fetchWeatherCurrentLocal(
                new MethodCallback.OnSuccess() {
                    @Override
                    public void onSuccess(RealmResults results) {

                    }
                },
                new MethodCallback.OnFailure() {
                    @Override
                    public void onFailure() {
                        fetchWeatherCurrentInternet();
                    }
                }
        );
    }

    private void fetchWeatherCurrentLocal(
            @NonNull MethodCallback.OnSuccess onSuccess,
            @NonNull MethodCallback.OnFailure onFailure) {

        RealmResults<Weather> weathers = realm.where(Weather.class)
                .between(
                    "time",
                    Computation.getCurrentUnixTime(),
                    Computation.calculateUnixTimeInterval(Computation.UnixTimeInterval.MINUTE_15))
                .findAll();

        if (weathers.size() == 0) {
            Utils.logRealm("size of current weather is different than 1 (" + weathers.size() + ")!");
            Utils.logRealm("calling onFailure");
            onFailure.onFailure();
        }

        if (weathers.size() < 1) {
            Utils.logRealm("size of current weather is different than 1 (" + weathers.size() + ")!");
            Utils.logRealm("deleting all violating 'current weathers'...");
            if (weathers.deleteAllFromRealm()) {
                Utils.logRealm("...deleted succesfully, calling onFailure.");
            } else {
                Utils.logRealm("...DELETE UNSUCCESSFULL - RAISING EXCEPTION, CALLING ONFAILURE");
                Utils.logException(new UnsupportedOperationException());
            }

            onFailure.onFailure();
        }


        onSuccess.onSuccess(weathers);
    }

    private void fetchWeatherCurrentInternet() {
        service.getCurrentWeather(placeholderWarsawGpsLat, placeholderWarsawGpsLon, new Callback<WeatherCurrentResponse>() {
            @Override
            public void onResponse(Call<WeatherCurrentResponse> call, Response<WeatherCurrentResponse> response) {
                final WeatherCurrentResponse body = avoidNullsInterceptor.processResponseBody(response.body());

                realm.executeTransactionAsync(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        Weather w = realm.createObject(
                                Weather.class,
                                realm.where(Weather.class).count()
                        );

                        w.setCity(body.cityName); //refactor to coord instead of city, with some error margin, like ~10km.
                        w.setUnixTime(body.calculationUnixTime);

                        w.setTemperature(body.mainWeatherData.temp);
                        w.setTemperatureMin(body.mainWeatherData.tempMin);
                        w.setTemperatureMax(body.mainWeatherData.tempMax);
                        w.setHumidityPercent(body.mainWeatherData.humidityPercent);
                        w.setPressureAtmospheric(body.mainWeatherData.pressureAtmospheric);
                        w.setPressureSeaLevel(body.mainWeatherData.pressureSeaLevel);
                        w.setPressureGroundLevel(body.mainWeatherData.pressureGroundLevel);

                        w.setWeatherMain(body.weather.get(0).main);
                        w.setWeatherDescription(body.weather.get(0).description);
                        w.setWeatherIconId(body.weather.get(0).iconId);

                        Utils.logRealm("Weather object created: ID:" + w.getId() + ", Unix:" + w.getUnixTime());
                    }
                });

                view.showForecastLocationLayout();
                view.updateForecastCurrentView(body);
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
      realm.close();
    }

}
