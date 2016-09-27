package com.android.szparag.newadaptiveweather.dagger.modules;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.android.szparag.newadaptiveweather.AppController;
import com.android.szparag.newadaptiveweather.backend.services.WeatherService;
import com.android.szparag.newadaptiveweather.presenters.BasePresenter;
import com.android.szparag.newadaptiveweather.presenters.MainPresenter;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by ciemek on 21/09/2016.
 */

@Module
public class AdaptiveWeatherModule {

    private Application application;

    //The Application class is single dependency that needs to be satisfied 'manually'
    public AdaptiveWeatherModule(Application application) {
        this.application = application;
    }

    @Provides
    @Singleton
    public Context provideContext() {
        return application;
    }

    @Provides
    @Singleton
    public SharedPreferences provideSharedPreferences(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    @Provides
    @Singleton
    public AppController provideAppController() throws ClassCastException {
        return (AppController)application;
    }

    @Provides
    public BasePresenter provideMainPresenter(WeatherService service) {
        return new MainPresenter(service);
    }

}