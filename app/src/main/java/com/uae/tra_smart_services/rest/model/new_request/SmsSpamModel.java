package com.uae.tra_smart_services.rest.model.new_request;

import com.google.gson.annotations.Expose;

/**
 * Created by Vitaliy on 12/08/2015.
 */
public class SmsSpamModel {
    @Expose
    public String phone;
    @Expose
    public String phoneProvider;
    @Expose
    public String providerType;
    @Expose
    public String description;
}
