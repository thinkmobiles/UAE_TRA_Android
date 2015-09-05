package com.uae.tra_smart_services.rest.model.response;

/**
 * Created by ak-buffalo on 14.08.15.
 */
public class DomainAvailabilityCheckResponseModel {
    public String availableStatus;
    public String domainStrValue;

    @Override
    public String toString() {
        return "{\"availableStatus\":" + availableStatus + ", \"domainStrValue\":" + domainStrValue + "}";
    }
}
