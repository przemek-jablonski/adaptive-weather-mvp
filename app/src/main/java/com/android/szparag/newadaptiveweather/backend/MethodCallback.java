package com.android.szparag.newadaptiveweather.backend;

import io.realm.RealmResults;

/**
 * Created by ciemek on 09/10/2016.
 */
public interface MethodCallback {

    interface OnSuccess {
        void onSuccess(RealmResults results);
    }



    interface OnFailure {
        void onFailure();
    }

}
