package com.uae.tra_smart_services.rest.model.new_request;

import com.google.gson.annotations.Expose;

/**
 * Created by Vitaliy on 12/08/2015.
 */
public class RatingServiceModel {
    @Expose
    public String serviceName;
    @Expose
    public Integer rate;
    @Expose
    public String feedback;
}
