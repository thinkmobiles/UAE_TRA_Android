package com.uae.tra_smart_services.rest.model.request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by mobimaks on 30.07.2015.
 */
public class Rating {

    @SerializedName("serviceType")
    public String serviceName;

    public String description;

    public String rating;

}
