package com.android.szparag.newadaptiveweather.backend.models.web;

import com.android.szparag.newadaptiveweather.backend.models.web.auxiliary.Clouds;
import com.android.szparag.newadaptiveweather.backend.models.web.auxiliary.Coordinates;
import com.android.szparag.newadaptiveweather.backend.models.web.auxiliary.MainWeatherData;
import com.android.szparag.newadaptiveweather.backend.models.web.auxiliary.Rain;
import com.android.szparag.newadaptiveweather.backend.models.web.auxiliary.Snow;
import com.android.szparag.newadaptiveweather.backend.models.web.auxiliary.Sys;
import com.android.szparag.newadaptiveweather.backend.models.web.auxiliary.WeatherInfo;
import com.android.szparag.newadaptiveweather.backend.models.web.auxiliary.Wind;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Ciemek on 06/07/16.
 */
public class WeatherCurrentResponse {

    @SerializedName("coord")
    @Expose
    public Coordinates coord;

    @SerializedName("weather")
    @Expose
    public List<WeatherInfo> weather = new ArrayList<>();

    @SerializedName("base")
    @Expose
    public String base;

    @SerializedName("main")
    @Expose
    public MainWeatherData mainWeatherData;

    @SerializedName("wind")
    @Expose
    public Wind wind;

    @SerializedName("clouds")
    @Expose
    public Clouds clouds;

    @SerializedName("rain")
    @Expose
    public Rain rain;

    @SerializedName("snow")
    @Expose
    public Snow snow;

    @SerializedName("dt")
    @Expose
    public Integer calculationUnixTime;

    @SerializedName("sys")
    @Expose
    public Sys sys;

    @SerializedName("id")
    @Expose
    public Integer cityId;

    @SerializedName("name")
    @Expose
    public String cityName;

    @SerializedName("cod")
    @Expose
    public Integer cod;

}
