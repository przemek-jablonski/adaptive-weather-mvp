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
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ciemek on 24/09/2016.
 */
public class BulkWeatherInfoPresenter implements BulkWeatherInfoBasePresenter {

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

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.where(Weather.class).findAll().deleteAllFromRealm();
            }
        });


//        RealmChangeListener realmWeatherCurrentChangeListener = new RealmChangeListener() {
//            @Override
//            public void onChange(Object element) {
//                Utils.logRealm("Realm WeatherCurrentChangeListener triggered." , "Calling fetchWeatherCurrent...");
////                view.
////                fetchWeather5Day();
//            }
//        };
//
//
//        realm.addChangeListener(realmWeatherCurrentChangeListener);
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

        //fixme: current weather may be incorectly queried (wrong data)
        fetchWeatherCurrentLocal(new MethodCallback.OnSuccess() {
            @Override
            public void onSuccess(Weather weather) {
//                view.updateForecastCurrentView(weather);
//                view.showWeatherFetchSuccess();
                //                view.updateForecastLocationTimeLayout(weather);
                //todo: make outdated_data_interval variable in settings and make boolean "always force refresh" and append (&& alwaysForce...) to IF statement
                //todo: get rid of this, this is mostly debug only
//                if (view.getSharedPreferences().getLong(Constants.LAST_API_UPDATE_TIME, -1L)
//                        < Computation.getCurrentUnixTime() - Computation.UnixTimeInterval.OUTDATED_DATA_INTERVAL) {
//                    Utils.logMisc("LAST API UPDATE TIME WAS TOO LONG AGO (" + lastApiUpdateTime
//                            + ", current: " + Computation.getCurrentUnixTime()
//                            + ") , UPDATING DATA THROUGH INTERNET");
//                    fetchWeatherCurrentInternet();
//                } else {
//                    Utils.logMisc("LAST API UPDATE TIME WAS (" + lastApiUpdateTime
//                            + ", current: " + Computation.getCurrentUnixTime());
//                }
            }
        }, new MethodCallback.OnFailure() {
            @Override
            public void onFailure() {
                fetchWeatherCurrentInternet();

            }
        });

    }

    //currently only internet based, not local
    private void fetchWeatherCurrentLocal(
            @Nullable MethodCallback.OnSuccess onSuccess,
            @Nullable MethodCallback.OnFailure onFailure) {

//        RealmQuery<Weather> weatherRealmQuery = realm.where(Weather.class);
//        RealmResults<Weather> closestWeathers = weatherRealmQuery.findAllSorted("unixTime", Sort.ASCENDING);
//
//        Weather currentWeather = realmUtils.findClosestTimeValue(closestWeathers, Computation.getCurrentUnixTime());
//
//        if (currentWeather == null
////              || IF THERE IS INTERNET CONNECTIVITY AVAILABLE (if user said in settings that he always wants fresh weather info)
////              || IF LAST KNOWN LOCATION HAS CHANGED
//                ) {
            onFailure.onFailure();
//            return;
//        }
//
//        onSuccess.onSuccess(currentWeather);
    }

    private void fetchWeatherCurrentInternet() {
//        lastApiUpdateTime = view.writeToSharedPreferences(Constants.LAST_API_UPDATE_TIME, Computation.getCurrentUnixTime());
        service.getCurrentWeather(placeholderWarsawGpsLat, placeholderWarsawGpsLon, new Callback<WeatherCurrentResponse>() {
            @Override
            public void onResponse(Call<WeatherCurrentResponse> call, Response<WeatherCurrentResponse> response) {
                final WeatherCurrentResponse body = avoidNullsInterceptor.processResponseBody(response.body());
                realm.executeTransactionAsync(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        Weather currentWeatherFromAPI =
                                realm.createObject(Weather.class, realm.where(Weather.class).count());

                        currentWeatherFromAPI = realmUtils.mapJsonResponseToRealm(body, currentWeatherFromAPI);
                    }
                }, new Realm.Transaction.OnSuccess() {
                    @Override
                    public void onSuccess() {
                        Weather currentWeather = realmUtils.findClosestTimeValue(realm.where(Weather.class).findAll(), Computation.getCurrentUnixTime());
                        view.updateForecastCurrentView(currentWeather);
                        view.updateFrontWeatherView(currentWeather);
                    }
                });


            }

            @Override
            public void onFailure(Call<WeatherCurrentResponse> call, Throwable t) {
                view.showWeatherFetchFailure();
            }
        });
    }

    @Override
    public void fetchWeather5Day() {

        fetchWeather5DayLocal(new MethodCallback.OnSuccesses() {
            @Override
            public void onSuccess(RealmResults<Weather> weathers) {
//                view.updateForecast5DayView(
//                        realm.where(Weather.class)
//                                .greaterThanOrEqualTo("unixTime", Computation.getCurrentUnixTime())
//                                .findAll()
//                );
//                view.showWeatherFetchSuccess();
//                if (view.getSharedPreferences().getLong(Constants.LAST_API_UPDATE_TIME, -1L)
//                        < Computation.getCurrentUnixTime() - Computation.UnixTimeInterval.OUTDATED_DATA_INTERVAL) {
//                    fetchWeather5DayInternet();
//                }
            }
        }, new MethodCallback.OnFailure() {
            @Override
            public void onFailure() {
                fetchWeather5DayInternet();
            }
        });
    }

    //(querying from local realm here)
    private void fetchWeather5DayLocal(
            @Nullable MethodCallback.OnSuccesses onSuccesses,
            @Nullable MethodCallback.OnFailure onFailure) {

//        RealmQuery<Weather> realmQuery = realm
//                .where(Weather.class)
//                .greaterThanOrEqualTo(
//                        "unixTime",
//                        Computation.getCurrentUnixTime()
//                );
//
//        RealmResults<Weather> forecasts5Day = realmQuery.findAllSorted("unixTime", Sort.ASCENDING);
//
//        if (forecasts5Day.size() == 0) {
            onFailure.onFailure();
//            return;
//        }
//
//        onSuccesses.onSuccess(forecasts5Day);
    }


    private void fetchWeather5DayInternet() {
//        lastApiUpdateTime = view.writeToSharedPreferences(Constants.LAST_API_UPDATE_TIME, Computation.getCurrentUnixTime());

//        realm.executeTransactionAsync(new Realm.Transaction() {
//            @Override
//            public void execute(Realm realm) {
//                realm
//                        .where(Weather.class)
//                        .greaterThan("unixTime", Computation.getCurrentUnixTime())
//                        .findAll()
//                        .deleteAllFromRealm();
//            }
//        });

        service.getForecast5Day(placeholderWarsawGpsLat, placeholderWarsawGpsLon, new Callback<WeatherForecastResponse>() {
            @Override
            public void onResponse(Call<WeatherForecastResponse> call, final Response<WeatherForecastResponse> response) {
                final WeatherForecastResponse body = avoidNullsInterceptor.processResponseBody(response.body());
                view.showForecastLocationTimeLayout();
                view.showForecast5DayView();

                realm.executeTransactionAsync(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        for (int i = 0; i < body.list.size(); ++i) {
                            Weather weather =
                                    realm.createObject(
                                            Weather.class,
                                            realm.where(Weather.class).count()
                                    );

                            weather = realmUtils.mapJsonRespnseToRealm(
                                    body.list.get(i),
                                    body.city,
                                    weather
                            );
                        }
                    }
                }, new Realm.Transaction.OnSuccess() {
                    @Override
                    public void onSuccess() {
                        view.updateForecast5DayView(realm.where(Weather.class).findAll());
                    }
                });
            }

            @Override
            public void onFailure(Call<WeatherForecastResponse> call, Throwable t) {
                view.showWeatherFetchFailure();
            }
        });
    }


    @Override
    public void fetchBackgroundMap() {
        Picasso
                .with(view.getAndroidView().getContext())
                .load(Utils.createBackgroundMapUri(
                googleStaticMapsBaseUrl,
                googleStaticMapsApiKey,
                placeholderWarsawGpsLat,
                placeholderWarsawGpsLon)
                )
                .into(view.getForecastFrontAdapter().getBackgroundImage());
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
