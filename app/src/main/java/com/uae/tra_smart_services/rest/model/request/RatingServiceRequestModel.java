package com.uae.tra_smart_services.rest.model.request;

import com.google.gson.annotations.Expose;

/**
 * Created by PC on 9/3/2015.
 */
public class RatingServiceRequestModel{
    @Expose
    public String serviceName;

    @Expose
    public Integer rate;

    @Expose
    public String feedback = "";

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    @Override
    public String toString() {
        return "{"+
                "serviceName:" + serviceName + ","+
                "rate:" + rate + ", " +
                "feedback:" + feedback +
                "}";
    }
}
