package com.android.szparag.newadaptiveweather.backend.models.auxiliary;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Ciemek on 06/07/16.
 */
public class Coordinates {

    @SerializedName("lon")
    @Expose
    public Float lon;

    @SerializedName("lat")
    @Expose
    public Float lat;

}
