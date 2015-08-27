package com.uae.tra_smart_services.rest;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import com.octo.android.robospice.retrofit.RetrofitGsonSpiceService;
import com.uae.tra_smart_services.global.C;
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
        if (TextUtils.isEmpty(ServerConstants.BASE_URL)) {
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
            ServerConstants.BASE_URL = prefs.getString(C.KEY_BASE_URL, ServerConstants.BASE_URL1);
        }
        return ServerConstants.BASE_URL;
    }
}
