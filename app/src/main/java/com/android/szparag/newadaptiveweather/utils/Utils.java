package com.android.szparag.newadaptiveweather.utils;

import com.android.szparag.newadaptiveweather.AppController;
import com.android.szparag.newadaptiveweather.dagger.components.MainComponent;

/**
 * Created by ciemek on 23/09/2016.
 */
public class Utils {

    //todo: check if this isn't bad for performance (possible memory leak?)
    static AppController appController;

    public Utils(AppController appController) {
        Utils.appController = appController;
    }

    //todo: don't know if this is proper thing to do, check it
    public static MainComponent getDagger() {
        return appController.getComponent();
    }

}
