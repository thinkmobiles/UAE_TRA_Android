package com.uae.tra_smart_services.rest.model.request;

import com.google.gson.annotations.Expose;

/**
 * Created by ak-buffalo on 06.10.15.
 */
public class PostInnovationRequestModel {
    @Expose
    public String title;
    @Expose
    public String message;
    @Expose
    public String type;
}
