package com.uae.tra_smart_services.rest.model.request;

import com.google.gson.annotations.Expose;

/**
 * Created by Mikazme on 13/08/2015.
 */
public class ComplainTRAServiceModel {
    @Expose
    public String title;

    @Expose
    public String description;

    @Expose
    public String attachment;

}
