package com.android.szparag.newadaptiveweather.backend.models.auxiliary;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ciemek on 06/07/16.
 */
public class WeatherForecastItem {

    @SerializedName("dt")
    @Expose
    public Integer calculationUnixTime;

    @SerializedName("dt_txt")
    @Expose
    public String calculationUTCTime;

    @SerializedName("main")
    @Expose
    public Main main;

    @SerializedName("weather")
    @Expose
    public List<WeatherInfo> weather = new ArrayList<>();

    @SerializedName("clouds")
    @Expose
    public Clouds clouds;

    @SerializedName("wind")
    @Expose
    public Wind wind;

    @SerializedName("rain")
    @Expose
    public Rain rain;

    @SerializedName("snow")
    @Expose
    public Snow snow;

    @SerializedName("sys")
    @Expose
    public Sys sys;

}
