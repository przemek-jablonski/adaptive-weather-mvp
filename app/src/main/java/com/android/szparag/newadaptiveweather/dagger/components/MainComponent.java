package com.android.szparag.newadaptiveweather.dagger.components;

import com.android.szparag.newadaptiveweather.AppController;
import com.android.szparag.newadaptiveweather.presenters.BulkWeatherInfoPresenter;
import com.android.szparag.newadaptiveweather.views.BulkWeatherInfoFragment;
import com.android.szparag.newadaptiveweather.activities.MainActivity;
import com.android.szparag.newadaptiveweather.dagger.modules.AdaptiveWeatherModule;
import com.android.szparag.newadaptiveweather.dagger.modules.NetworkingModule;
import com.android.szparag.newadaptiveweather.utils.Utils;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by ciemek on 21/09/2016.
 */

@Singleton
@Component(modules = { AdaptiveWeatherModule.class, NetworkingModule.class})
public interface MainComponent {

    void inject(BulkWeatherInfoFragment injectionTarget);
    void inject(BulkWeatherInfoPresenter injectionTarget);

    void inject(MainActivity injectionTarget);

    void inject(AppController injectionTarget);
    void inject(Utils injectionTarget);

}
