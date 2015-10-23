package com.uae.tra_smart_services.entities.dynamic_service;

import com.google.gson.annotations.Expose;
import com.uae.tra_smart_services.global.C.HttpMethod;

import java.util.ArrayList;

/**
 * Created by mobimaks on 22.10.2015.
 */
public class DynamicService {

    @Expose
    public String id;

    @Expose
    public String url;

    @Expose
    public ArrayList<BaseInputItem> inputItems;

    @Expose
    public String buttonText;

    @Expose
    public String serviceName;

    @Expose
    @HttpMethod
    public String method;

}
