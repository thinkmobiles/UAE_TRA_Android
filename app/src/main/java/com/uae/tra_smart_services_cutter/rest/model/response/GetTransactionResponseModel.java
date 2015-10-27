package com.uae.tra_smart_services_cutter.rest.model.response;

import java.util.ArrayList;

/**
 * Created by Vitaliy on 05/10/2015.
 */
public class GetTransactionResponseModel {
    public String title;
    public String type;
    public String traSubmitDatetime;
    public String modifiedDatetime;
    public String stateCode;
    public String statusCode;
    public String traStatus;
    public String serviceStage;
    public String description;

    @SuppressWarnings("serial")
    public static class List extends ArrayList<GetTransactionResponseModel> {
    }

}
