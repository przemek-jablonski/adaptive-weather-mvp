package com.android.szparag.newadaptiveweather.backend.models.auxiliary;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Ciemek on 06/07/16.
 */
public class WeatherInfo {


    @SerializedName("id")
    @Expose
    public Integer id;

    @SerializedName("main")
    @Expose
    public String main;

    @SerializedName("description")
    @Expose
    public String description;

    @SerializedName("icon")
    @Expose
    public String iconId;

}
