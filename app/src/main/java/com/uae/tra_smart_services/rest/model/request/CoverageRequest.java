package com.uae.tra_smart_services.rest.model.request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by mobimaks on 30.07.2015.
 */
public class CoverageRequest {

    @SerializedName("LocLAT")
    public String latitude;

    @SerializedName("LocLON")
    public String longitude;

    public CoverageRequest(String latitude, String longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

}
