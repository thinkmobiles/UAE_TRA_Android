package com.uae.tra_smart_services.entities.dynamic_service;

import com.google.gson.JsonObject;
import com.google.gson.annotations.Expose;
import com.uae.tra_smart_services.global.C.HttpMethod;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by mobimaks on 22.10.2015.
 */
public class DynamicService {

    @Expose
    public String id;

    @Expose
    public String url;

    @Expose
    public List<BaseInputItem> inputItems;

    @Expose
    public String buttonText;

    @Expose
    public String serviceName;

    @Expose
    @HttpMethod
    public String method;

    @Expose
    public Set<String> bodyArgs;

    @Expose
    public Set<String> queryArgs;

    public boolean isDataValid() {
        for (final BaseInputItem inputItem : inputItems) {
            if (!inputItem.isDataValid()) {
                return false;
            }
        }
        return true;
    }

    public Map<String, String> getQueryMap(){
        final Map<String,String> map = new HashMap<>();
        for (final BaseInputItem inputItem : inputItems) {
            if (queryArgs.contains(inputItem.getQueryName())) {
                map.put(inputItem.getQueryName(), inputItem.getArgsData());
            }
        }
        return map;
    }

    public JsonObject getData() {
        final JsonObject object = new JsonObject();
        for (final BaseInputItem inputItem : inputItems) {
            if (bodyArgs.contains(inputItem.getQueryName())){
                object.add(inputItem.getQueryName(), inputItem.getJsonData());
            }
        }
        return object;
    }


}
