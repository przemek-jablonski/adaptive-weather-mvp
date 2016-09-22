package com.android.szparag.newadaptiveweather.backend.models.auxiliary;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Ciemek on 06/07/16.
 */
public class Wind {

    @SerializedName("speed")
    @Expose
    public Float speed;

    @SerializedName("deg")
    @Expose
    public Float directionDegrees; //meteorological degrees

}
