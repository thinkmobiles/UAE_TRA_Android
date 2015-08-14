package com.uae.tra_smart_services.rest.model.new_response;

/**
 * Created by ak-buffalo on 14.08.15.
 */
public class DomainAvailabilityCheckResponse {
    public String availableStatus;

    @Override
    public String toString() {
        return "{\"availableStatus\":" + availableStatus + "}";
    }
}
