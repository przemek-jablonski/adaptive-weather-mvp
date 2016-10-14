package com.android.szparag.newadaptiveweather.presenters;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.Nullable;

import com.android.szparag.newadaptiveweather.backend.MethodCallback;
import com.android.szparag.newadaptiveweather.backend.RealmUtils;
import com.android.szparag.newadaptiveweather.backend.interceptors.AvoidNullsInterceptor;
import com.android.szparag.newadaptiveweather.backend.models.realm.Weather;
import com.android.szparag.newadaptiveweather.backend.models.web.WeatherCurrentResponse;
import com.android.szparag.newadaptiveweather.backend.models.web.WeatherForecastResponse;
import com.android.szparag.newadaptiveweather.backend.services.WeatherService;
import com.android.szparag.newadaptiveweather.utils.Computation;
import com.android.szparag.newadaptiveweather.utils.Constants;
import com.android.szparag.newadaptiveweather.utils.Utils;
import com.android.szparag.newadaptiveweather.views.contracts.BulkWeatherInfoView;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;



import javax.inject.Inject;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import io.realm.Sort;
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
    RealmUtils realmUtils;

    @Inject
    AvoidNullsInterceptor avoidNullsInterceptor;

    private BulkWeatherInfoView view;

    private float placeholderWarsawGpsLat = 52.233101f;
    private float placeholderWarsawGpsLon = 21.061399f;

    private String googleStaticMapsBaseUrl;
    private String googleStaticMapsApiKey;

    WeatherService service;

    private long lastApiUpdateTime = -2L;


    public BulkWeatherInfoPresenter(WeatherService service, String googleStaticMapsBaseUrl, String googleStaticMapsApiKey) {
        this.service = service;
        this.googleStaticMapsBaseUrl = googleStaticMapsBaseUrl;
        this.googleStaticMapsApiKey = googleStaticMapsApiKey;
    }

    @Override
    public void setView(BulkWeatherInfoView view) {
        this.view = view;
        lastApiUpdateTime = view.getSharedPreferences().getLong(Constants.LAST_API_UPDATE_TIME, -1L);
        Utils.getDagger2(view.getAndroidView()).inject(this);


        RealmChangeListener realmWeatherCurrentChangeListener = new RealmChangeListener() {
            @Override
            public void onChange(Object element) {
                Utils.logRealm("Realm WeatherCurrentChangeListener triggered." , "Calling fetchWeatherCurrent...");
                fetchWeatherCurrent();
            }
        };

        realm.addChangeListener(realmWeatherCurrentChangeListener);
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

        fetchWeatherCurrentLocal(new MethodCallback.OnSuccess() {
            @Override
            public void onSuccess(Weather result) {
                Utils.logMisc("Local - onSuccess()", "updating view...");
                view.showForecastCurrentView();
                view.updateForecastCurrentView(result);
                view.showWeatherFetchSuccess();

                //todo: make outdated_data_interval variable in settings and make boolean "always force refresh" and append (&& alwaysForce...) to IF statement
                if (lastApiUpdateTime < Computation.getCurrentUnixTime() - Computation.UnixTimeInterval.OUTDATED_DATA_INTERVAL) {
                    Utils.logMisc("LAST API UPDATE TIME WAS TOO LONG AGO (" + lastApiUpdateTime
                            + ", current: " + Computation.getCurrentUnixTime()
                            + ") , UPDATING DATA THROUGH INTERNET");
                    fetchWeatherCurrentInternet();
                } else {
                    Utils.logMisc("LAST API UPDATE TIME WAS (" + lastApiUpdateTime
                            + ", current: " + Computation.getCurrentUnixTime());
                }
            }
        }, new MethodCallback.OnFailure() {
            @Override
            public void onFailure() {
                Utils.logMisc("Local - ONFAILURE()", "fetching from internet");
                fetchWeatherCurrentInternet();
            }
        });

    }

    private void fetchWeatherCurrentLocal(
            @Nullable MethodCallback.OnSuccess onSuccess,
            @Nullable MethodCallback.OnFailure onFailure) {

        RealmQuery<Weather> weatherRealmQuery = realm.where(Weather.class);
        RealmResults<Weather> closestWeathers = weatherRealmQuery.findAllSorted("unixTime", Sort.ASCENDING);

        Weather currentWeather = realmUtils.findClosestTimeValue(closestWeathers, Computation.getCurrentUnixTime());

        if (currentWeather == null
//              || IF THERE IS INTERNET CONNECTIVITY AVAILABLE (if user said in settings that he always wants fresh weather info)
//              || IF LAST KNOWN LOCATION HAS CHANGED
                ) {
            Utils.logRealm("fetching CurrentWeather from Local FAILED!");
            if (currentWeather == null) {
                Utils.logRealm("(currentWeather null)");
            }
            Utils.logRealm("Calling onFailure...");
            onFailure.onFailure();
            return;
        }

        Utils.logRealm(
                "fetching CurrentWeather from Local succeeded!",
                "Calling onSuccess...",
                "currentWeather time delay: " + ((Computation.getCurrentUnixTime() - currentWeather.getUnixTime())/60f) + "minutes  (acceptable: up to " + (Computation.UnixTimeInterval.OUTDATED_DATA_INTERVAL/60f) + ")");
        onSuccess.onSuccess(currentWeather);
    }

    private void fetchWeatherCurrentInternet() {
        Utils.logRetrofit("fetchWeatherCurrentInternet() - firing up Retrofit...");
        lastApiUpdateTime = view.writeToSharedPreferences(Constants.LAST_API_UPDATE_TIME, Computation.getCurrentUnixTime());
        service.getCurrentWeather(placeholderWarsawGpsLat, placeholderWarsawGpsLon, new Callback<WeatherCurrentResponse>() {

            @Override
            public void onResponse(Call<WeatherCurrentResponse> call, Response<WeatherCurrentResponse> response) {
                Utils.logRetrofit("..Retrofit success");
                final WeatherCurrentResponse body = avoidNullsInterceptor.processResponseBody(response.body());

                realm.executeTransactionAsync(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        Weather currentWeatherFromAPI =
                                realm.createObject(Weather.class, realm.where(Weather.class).count());

                        currentWeatherFromAPI = realmUtils.mapJsonResponseToRealm(body, currentWeatherFromAPI);
                        Utils.logRealm("(fetchInternet) Weather object created: ID:" + currentWeatherFromAPI.getId() + ", Unix:" + currentWeatherFromAPI.getUnixTime());
                    }
                });
            }

            @Override
            public void onFailure(Call<WeatherCurrentResponse> call, Throwable t) {
                Utils.logRetrofit("..Retrofit failure");
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
                view.updateForecastChartLayout(response.body());
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
    public void unregisterRealm() {
        realm.removeAllChangeListeners();
    }

    @Override
    public void realmClose() {
        unregisterRealm();
        realm.close();
    }

}
