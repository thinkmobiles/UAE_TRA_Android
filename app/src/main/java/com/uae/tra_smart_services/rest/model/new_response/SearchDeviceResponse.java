package com.uae.tra_smart_services.rest.model.new_response;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;

/**
 * Created by Vitaliy on 13/08/2015.
 */
public class SearchDeviceResponse {
    @Expose
    public String tac;
    @Expose
    public String marketingName;
    @Expose
    public String designationType;
    @Expose
    public String manufacturer;
    @Expose
    public String bands;
    @Expose
    public String allocationDate;
    @Expose
    public String countryCode;
    @Expose
    public String fixedCode;
    @Expose
    public String radioInterface;
    @Expose
    public String manufacturerCode;
    @Expose
    public Long startIndex;
    @Expose
    public Long endIndex;
    @Expose
    public Long count;
    @Expose
    public Long totalNumberofRecords;

    @SuppressWarnings("serial")
    public static class List extends ArrayList<SearchDeviceResponse> {
    }
}
