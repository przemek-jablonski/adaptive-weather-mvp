package com.android.szparag.newadaptiveweather.backend.models.auxiliary;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Ciemek on 06/07/16.
 */
public class City {

    @SerializedName("id")
    @Expose
    public Integer id;

    @SerializedName("name")
    @Expose
    public String name;

    @SerializedName("coord")
    @Expose
    public Coordinates coordinates;

    @SerializedName("country")
    @Expose
    public String countryCode;

    @SerializedName("population")
    @Expose
    public Integer population;

    @SerializedName("sys")
    @Expose
    public Sys sys;

}
