package com.android.szparag.newadaptiveweather;

import android.app.Application;

import com.android.szparag.newadaptiveweather.dagger.AdaptiveWeatherComponent;
import com.android.szparag.newadaptiveweather.dagger.AdaptiveWeatherModule;
import com.android.szparag.newadaptiveweather.dagger.DaggerAdaptiveWeatherComponent;

/**
 * Created by ciemek on 21/09/2016.
 */
public class AppController extends Application {

    AdaptiveWeatherComponent daggerComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        daggerComponent = DaggerAdaptiveWeatherComponent
                .builder()
                .adaptiveWeatherModule(new AdaptiveWeatherModule(this))
                .build();
    }

    public AdaptiveWeatherComponent getComponent() {
        return daggerComponent;
    }

}
