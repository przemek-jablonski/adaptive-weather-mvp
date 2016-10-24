package com.android.szparag.newadaptiveweather.backend.models.web;

import com.android.szparag.newadaptiveweather.backend.models.web.auxiliary.City;
import com.android.szparag.newadaptiveweather.backend.models.web.auxiliary.WeatherForecastItem;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ciemek on 06/07/16.
 */
public class WeatherForecastResponse {

    @SerializedName("city")
    @Expose
    public City city;

    @SerializedName("cod")
    @Expose
    public String cod;

    @SerializedName("message")
    @Expose
    public Float message;

    @SerializedName("cnt")
    @Expose
    public Integer linesReturned;

    @SerializedName("list")
    @Expose
    public List<WeatherForecastItem> list = new ArrayList<>();

}
