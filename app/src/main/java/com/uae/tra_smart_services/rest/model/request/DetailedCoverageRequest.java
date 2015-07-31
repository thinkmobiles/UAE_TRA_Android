package com.uae.tra_smart_services.rest.model.request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by mobimaks on 30.07.2015.
 */
public class DetailedCoverageRequest extends CoverageRequest {

    @SerializedName("Email")
    public String email;

    @SerializedName("Mobile")
    public String mobile;

    @SerializedName("Name")
    public String name;

    @SerializedName("MobOp")
    public String mobileOperator;

    @SerializedName("SignalAtPos")
    public String signalAtPosition;

    public DetailedCoverageRequest(String latitude, String longitude) {
        super(latitude, longitude);
    }

}
