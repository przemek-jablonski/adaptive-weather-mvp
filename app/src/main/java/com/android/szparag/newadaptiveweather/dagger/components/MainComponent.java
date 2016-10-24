package com.android.szparag.newadaptiveweather.dagger.components;

import com.android.szparag.newadaptiveweather.AdaptiveWeatherApplication;
import com.android.szparag.newadaptiveweather.activities.MainActivity;
import com.android.szparag.newadaptiveweather.backend.RealmUtils;
import com.android.szparag.newadaptiveweather.dagger.modules.AdaptiveWeatherModule;
import com.android.szparag.newadaptiveweather.dagger.modules.NetworkingModule;
import com.android.szparag.newadaptiveweather.presenters.BulkWeatherInfoPresenter;
import com.android.szparag.newadaptiveweather.presenters.OneDayWeatherInfoPresenter;
import com.android.szparag.newadaptiveweather.utils.Utils;
import com.android.szparag.newadaptiveweather.views.BulkWeatherInfoFragment;
import com.android.szparag.newadaptiveweather.views.OneDayWeatherInfoFragment;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by ciemek on 21/09/2016.
 */

@Singleton
@Component(modules = { AdaptiveWeatherModule.class, NetworkingModule.class})
public interface MainComponent {

    //presenters:
    void inject(BulkWeatherInfoPresenter injectionTarget);
    void inject(OneDayWeatherInfoPresenter injectionTarget);

    //views:
    void inject(BulkWeatherInfoFragment injectionTarget);
    void inject(OneDayWeatherInfoFragment injectionTarget);
    void inject(MainActivity injectionTarget);

    //misc:
    void inject(AdaptiveWeatherApplication injectionTarget);
    void inject(Utils injectionTarget);
    void inject(RealmUtils injectionTarget);

}
