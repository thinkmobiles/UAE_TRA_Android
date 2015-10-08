package com.uae.tra_smart_services.rest;

import com.octo.android.robospice.retrofit.RetrofitGsonSpiceService;
import com.squareup.okhttp.OkHttpClient;
import com.uae.tra_smart_services.BuildConfig;
import com.uae.tra_smart_services.global.ServerConstants;

import java.util.concurrent.TimeUnit;

import retrofit.RestAdapter;
import retrofit.client.OkClient;

import static com.uae.tra_smart_services.global.ServerConstants.TIMEOUT;

/**
 * Created by Mikazme on 13/08/2015.
 */
public class TRARestService extends RetrofitGsonSpiceService {

    @Override
    protected RestAdapter.Builder createRestAdapterBuilder() {
        RestAdapter.Builder builder = super.createRestAdapterBuilder();
        if (BuildConfig.DEBUG) {
            builder.setLogLevel(RestAdapter.LogLevel.FULL);
        }

        final OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.setReadTimeout(TIMEOUT, TimeUnit.SECONDS);
        okHttpClient.setConnectTimeout(TIMEOUT, TimeUnit.SECONDS);
        builder                .setClient(new OkClient(okHttpClient));
        return builder;
    }

    @Override
    protected String getServerUrl() {
        return ServerConstants.BASE_URL;
    }
}
