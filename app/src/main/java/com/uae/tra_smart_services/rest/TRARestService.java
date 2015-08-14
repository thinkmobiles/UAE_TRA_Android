package com.uae.tra_smart_services.rest;

import com.octo.android.robospice.retrofit.RetrofitGsonSpiceService;

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
        return "http://134.249.164.53:7791";
    }
}
