package com.android.szparag.newadaptiveweather.dagger.modules;

import android.content.Context;

import com.android.szparag.newadaptiveweather.R;
import com.android.szparag.newadaptiveweather.backend.apis.WeatherApiService;
import com.android.szparag.newadaptiveweather.backend.interceptors.PrintResponseInterceptor;
import com.android.szparag.newadaptiveweather.backend.services.WeatherService;
import com.android.szparag.newadaptiveweather.backend.services.WeatherServiceImpl;
import com.android.szparag.newadaptiveweather.utils.Constants;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by ciemek on 23/09/2016.
 */
@Module
public class NetworkingModule {

    @Provides
    @Named(Constants.OPENWEATHERMAP_BASEURL)
    public String provideOpenWeatherMapBaseUrl(Context context) {
        return context.getString(R.string.openweathermap_baseurl);
    }

    @Provides
    @Named(Constants.OPENWEATHERMAP_APIKEY)
    public String provideOpenWeatherMapApiKey(Context context) {
        return context.getString(R.string.openweathermap_apikey);
    }

    @Provides
    @Named(Constants.GOOGLEMAPSSTATIC_BASEURL)
    public String provideGoogleMapsStaticBaseUrl(Context context) {
        return context.getString(R.string.googlemapsstatic_baseurl);
    }

    @Provides
    @Named(Constants.GOOGLEMAPSSTATIC_APIKEY)
    public String provideGoogleMapsStaticApiKey(Context context) {
        return context.getString(R.string.googlemapsstatic_apikey);
    }

    @Provides
    @Singleton
    public Retrofit provideOpenWeatherMapRetrofitAdapter(@Named(Constants.OPENWEATHERMAP_BASEURL) String apiBaseUrl) {
        return new Retrofit.Builder()
                .baseUrl(apiBaseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(new OkHttpClient.Builder().addInterceptor(new PrintResponseInterceptor()).build())
                .build();
    }

    @Provides
    @Singleton
    public WeatherApiService provideWeatherApiService(Retrofit retrofit) {
        return retrofit.create(WeatherApiService.class);
    }


    @Provides
    @Singleton
    public WeatherService provideWeatherService(WeatherApiService weatherApiService, @Named(Constants.OPENWEATHERMAP_APIKEY) String apiKey) {
        return new WeatherServiceImpl(weatherApiService, apiKey);
    }

}
