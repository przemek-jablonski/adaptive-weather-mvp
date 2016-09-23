package com.android.szparag.newadaptiveweather;

import android.app.Application;

import com.android.szparag.newadaptiveweather.dagger.components.DaggerMainComponent;
import com.android.szparag.newadaptiveweather.dagger.components.MainComponent;
import com.android.szparag.newadaptiveweather.dagger.modules.AdaptiveWeatherModule;

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
                .build();

    }

    public MainComponent getComponent() {
        return daggerComponent;
    }

}
