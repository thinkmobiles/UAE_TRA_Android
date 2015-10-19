package com.uae.tra_smart_services.rest.model.response;

import com.google.gson.annotations.Expose;

/**
 * Created by ak-buffalo on 14.08.15.
 */
public class DomainAvailabilityCheckResponseModel {

    @Expose
    public String availableStatus;

    @Expose
    public String domainStrValue;

    @Override
    public String toString() {
        return "{\"availableStatus\":" + availableStatus + ", \"domainStrValue\":" + domainStrValue + "}";
    }
}
