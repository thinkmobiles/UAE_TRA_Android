package com.uae.tra_smart_services.rest;

import com.octo.android.robospice.retrofit.RetrofitGsonSpiceService;
import com.uae.tra_smart_services.global.ServerConstants;

import retrofit.RestAdapter;

/**
 * Created by Vitaliy on 13/08/2015.
 */
public class TRARestService extends RetrofitGsonSpiceService {

    @Override
    protected RestAdapter.Builder createRestAdapterBuilder() {
        RestAdapter.Builder builder = super.createRestAdapterBuilder();
        builder.setLogLevel(RestAdapter.LogLevel.FULL);
        return builder;
    }

    @Override
    protected String getServerUrl() {
        return ServerConstants.BASE_URL;
    }
}
