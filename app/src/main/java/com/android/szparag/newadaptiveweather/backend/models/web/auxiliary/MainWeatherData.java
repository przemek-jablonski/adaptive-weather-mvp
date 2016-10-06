package com.android.szparag.newadaptiveweather.backend.models.web.auxiliary;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Ciemek on 06/07/16.
 */
public class MainWeatherData {

    @SerializedName("temp")
    @Expose
    public Float temp;

    @SerializedName("humidity")
    @Expose
    public Integer humidityPercent;

    @SerializedName("pressure")
    @Expose
    public Float pressureAtmospheric;

    @SerializedName("temp_min")
    @Expose
    public Float tempMin;

    @SerializedName("temp_max")
    @Expose
    public Float tempMax;

    @SerializedName("temp_kf")
    @Expose
    public Float tempKf; //internal parameter

    @SerializedName("sea_level")
    @Expose
    public Float pressureSeaLevel;

    @SerializedName("gnrd_level")
    @Expose
    public Float pressureGroundLevel;

}
