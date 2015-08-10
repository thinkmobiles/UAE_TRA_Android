package com.uae.tra_smart_services.rest.model.request;

import android.support.annotation.StringDef;

import com.google.gson.annotations.SerializedName;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by mobimaks on 30.07.2015.
 */
public class DnsRequest {

    //region Request type annotation
    @StringDef({AVAILABILITY_TYPE, WHOIS_TYPE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface RequestType {}

    public static final String AVAILABILITY_TYPE = "avaliablity";
    public static final String WHOIS_TYPE = "whois";
    //endregion

    @SerializedName("WebURL")
    public String webUrl;

    @RequestType
    @SerializedName("ReqType")
    public String requestType;

}
