package com.uae.tra_smart_services.rest.model.response;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;

/**
 * Created by Vitaliy on 05/10/2015.
 */
public class GetTransactionResponseModel {
    @Expose
    public String title;

    @Expose
    public String type;

    @Expose
    public String traSubmitDatetime;

    @Expose
    public String modifiedDatetime;

    @Expose
    public String stateCode;

    @Expose
    public String statusCode;

    @Expose
    public String traStatus;

    @Expose
    public String serviceStage;

    @Expose
    public String description;

    public static class List extends ArrayList<GetTransactionResponseModel> {
    }
}
