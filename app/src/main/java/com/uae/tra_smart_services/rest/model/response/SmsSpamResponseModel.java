package com.uae.tra_smart_services.rest.model.response;

/**
 * Created by ak-buffalo on 14.08.15.
 */
public class SmsSpamResponseModel {
    public String status;

    @Override
    public String toString() {
        return "{\"status\":" + status + "}";
    }
}
