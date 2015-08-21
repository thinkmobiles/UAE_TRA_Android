package com.uae.tra_smart_services.rest.model.request;

import com.google.gson.annotations.Expose;

/**
 * Created by Vitaliy on 12/08/2015.
 */
public class HelpSalimModel {
    public HelpSalimModel(String url, String description) {
        this.url = url;
        this.description = description;
    }

    @Expose
    public String url;
    @Expose
    public String description;
}
