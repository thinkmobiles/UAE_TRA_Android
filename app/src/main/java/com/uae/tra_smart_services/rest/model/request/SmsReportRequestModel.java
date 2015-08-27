package com.uae.tra_smart_services.rest.model.request;

import com.google.gson.annotations.Expose;

/**
 * Created by Vitaliy on 26/08/2015.
 */
public class SmsReportRequestModel {
    @Expose
    public String phone;
    @Expose
    public String description;

    public SmsReportRequestModel(final String _phone, final String _description) {
        phone = _phone;
        description = _description;
    }
}
