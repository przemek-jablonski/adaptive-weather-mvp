package com.android.szparag.newadaptiveweather.backend.models.auxiliary;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Ciemek on 06/07/16.
 */
public class Sys {

    @SerializedName("type")
    @Expose
    public Integer type;

    @SerializedName("id")
    @Expose
    public Integer id;

    @SerializedName("message")
    @Expose
    public Float message;

    @SerializedName("country")
    @Expose
    public String countryCode;

    @SerializedName("sunrise")
    @Expose
    public Integer sunriseUnixTime;

    @SerializedName("sunset")
    @Expose
    public Integer sunsetUnixTime;

}
