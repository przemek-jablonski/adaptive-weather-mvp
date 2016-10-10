package com.android.szparag.newadaptiveweather.backend;

import com.android.szparag.newadaptiveweather.backend.models.realm.Weather;

import io.realm.RealmResults;

/**
 * Created by ciemek on 09/10/2016.
 */
public interface MethodCallback {

    interface OnSuccess {
        void onSuccess(Weather result);
    }

    interface OnFailure {
        void onFailure();
    }

}