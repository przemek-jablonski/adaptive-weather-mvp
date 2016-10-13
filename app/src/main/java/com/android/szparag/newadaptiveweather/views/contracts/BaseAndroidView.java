package com.android.szparag.newadaptiveweather.views.contracts;

import android.app.Activity;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;

/**
 * Created by ciemek on 11/10/2016.
 */

/**
 * Base view contract for any view that needs to deliver Android framework stuff
 * into any class outside of Android SDK scope (Java presenters / controllers etc.)
 */
public interface BaseAndroidView {

    SharedPreferences getSharedPreferences();
    long writeToSharedPreferences(String key, long value);

    String getResourceString(int stringResId);


    Fragment getAndroidView();
    Activity getActivity();

}
