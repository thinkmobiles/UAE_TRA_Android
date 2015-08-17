package com.uae.tra_smart_services.rest.model.new_request;

import com.google.gson.annotations.Expose;

/**
 * Created by Vitaliy on 12/08/2015.
 */
public class SmsSpamRequestModel {
    @Expose
    public String phone;
    @Expose
    public String phoneProvider;
    @Expose
    public String providerType;
    @Expose
    public String description;

    public SmsSpamRequestModel(String phone, String phoneProvider, String providerType, String description) {
        this.phone = phone;
        this.phoneProvider = phoneProvider;
        this.providerType = providerType;
        this.description = description;
    }
}