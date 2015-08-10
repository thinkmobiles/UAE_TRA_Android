package com.uae.tra_smart_services.rest.model.request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by mobimaks on 30.07.2015.
 */
public class SmsSpamReport {

    @SerializedName("NetName")
    public String networkName;

    @SerializedName("Sender")
    public String spammer;

    @SerializedName("DateSms")
    public String smsDate;

}
