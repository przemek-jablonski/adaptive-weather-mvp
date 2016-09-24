package com.android.szparag.newadaptiveweather.utils;

import android.app.Activity;
import android.support.v4.app.Fragment;

import com.android.szparag.newadaptiveweather.AppController;
import com.android.szparag.newadaptiveweather.dagger.components.MainComponent;

/**
 * Created by ciemek on 23/09/2016.
 */
public class Utils {

    public static MainComponent getDagger2(Activity activity) {
        return ((AppController) activity.getApplication()).getComponent();
    }

    public static MainComponent getDagger2(Fragment fragment) {
        return getDagger2(fragment.getActivity());
    }


}
