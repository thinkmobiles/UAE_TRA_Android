package com.uae.tra_smart_services.rest;

import com.octo.android.robospice.retrofit.RetrofitGsonSpiceService;
import com.uae.tra_smart_services.global.ServerConstants;

import retrofit.RestAdapter;
import retrofit.RestAdapter.Builder;

/**
 * Created by mobimaks on 30.07.2015.
 */
public class RestService extends RetrofitGsonSpiceService {

    @Override
    protected Builder createRestAdapterBuilder() {
        Builder builder = super.createRestAdapterBuilder();
        builder.setLogLevel(RestAdapter.LogLevel.FULL);
        return builder;
    }

    @Override
    protected String getServerUrl() {
        return ServerConstants.BASE_URL1;
    }
}
