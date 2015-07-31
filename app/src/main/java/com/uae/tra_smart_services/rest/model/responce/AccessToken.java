package com.uae.tra_smart_services.rest.model.responce;

import com.google.gson.annotations.SerializedName;
import com.uae.tra_smart_services.global.ServerConstants;

/**
 * Created by mobimaks on 30.07.2015.
 */
public class AccessToken {

    @SerializedName(ServerConstants.ACCESS_TOKEN)
    public String accessToken;

}
