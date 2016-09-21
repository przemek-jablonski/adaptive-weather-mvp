package com.android.szparag.newadaptiveweather.dagger;

import com.android.szparag.newadaptiveweather.AppController;
import com.android.szparag.newadaptiveweather.activities.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by ciemek on 21/09/2016.
 */

@Singleton
@Component(modules = { AdaptiveWeatherModule.class })
public interface AdaptiveWeatherComponent {

    void inject(AppController injectionTarget);
    void inject(MainActivity injectionTarget);

}
