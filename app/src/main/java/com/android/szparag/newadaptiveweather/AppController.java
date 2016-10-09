package com.android.szparag.newadaptiveweather;

import android.app.Application;

import com.android.szparag.newadaptiveweather.dagger.components.DaggerMainComponent;
import com.android.szparag.newadaptiveweather.dagger.components.MainComponent;
import com.android.szparag.newadaptiveweather.dagger.modules.AdaptiveWeatherModule;
import com.android.szparag.newadaptiveweather.dagger.modules.NetworkingModule;

import io.realm.Realm;
import io.realm.RealmConfiguration;


/**
 * Created by ciemek on 21/09/2016.
 */
public class AppController extends Application {

    private MainComponent daggerComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        daggerComponent = DaggerMainComponent
                .builder()
                .adaptiveWeatherModule(new AdaptiveWeatherModule(this))
                .networkingModule(new NetworkingModule())
                .build();

        Realm.init(this);

        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder()
        .name("weatherdata.realm")
        .build();

        Realm.setDefaultConfiguration(realmConfiguration);

    }

    public MainComponent getComponent() {
        return daggerComponent;
    }

}