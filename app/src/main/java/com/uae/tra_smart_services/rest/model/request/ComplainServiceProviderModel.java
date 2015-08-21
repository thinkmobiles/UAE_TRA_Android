package com.uae.tra_smart_services.rest.model.request;

import com.google.gson.annotations.Expose;

public class ComplainServiceProviderModel {

    @Expose
    public String title;
    @Expose
    public String serviceProvider;
    @Expose
    public String description;
    @Expose
    public String referenceNumber;
    @Expose
    public String attachment;

}
