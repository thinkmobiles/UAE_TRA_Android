package com.uae.tra_smart_services.rest.model.request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by mobimaks on 30.07.2015.
 */
public class WebUrl {

    @SerializedName("WebURL")
    private final String webUrl;

    public WebUrl(final String webUrl) {
        this.webUrl = webUrl;
    }
}
