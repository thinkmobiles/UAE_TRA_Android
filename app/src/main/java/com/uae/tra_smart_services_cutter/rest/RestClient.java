package com.uae.tra_smart_services_cutter.rest;

import com.squareup.okhttp.OkHttpClient;
import com.uae.tra_smart_services_cutter.BuildConfig;
import com.uae.tra_smart_services_cutter.global.ServerConstants;

import java.util.concurrent.TimeUnit;

import retrofit.RestAdapter;
import retrofit.RestAdapter.Builder;
import retrofit.client.OkClient;

import static com.uae.tra_smart_services_cutter.global.ServerConstants.TIMEOUT;

/**
 * Created by mobimaks on 07.10.2015.
 */
public final class RestClient {

    private static RestClient mRestClient;
    private TRAServicesAPI mTRAServicesAPI;

    private RestClient() {
        final OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.setReadTimeout(TIMEOUT, TimeUnit.SECONDS);
        okHttpClient.setConnectTimeout(TIMEOUT, TimeUnit.SECONDS);

        final Builder builder = new Builder()
                .setEndpoint(ServerConstants.BASE_URL)
                .setClient(new OkClient(okHttpClient));
        if (BuildConfig.DEBUG) {
            builder.setLogLevel(RestAdapter.LogLevel.FULL);
        }
        final RestAdapter adapter = builder.build();
        mTRAServicesAPI = adapter.create(TRAServicesAPI.class);
    }

    public static RestClient getInstance() {
        if (mRestClient == null) {
            mRestClient = new RestClient();
        }
        return mRestClient;
    }

    public final TRAServicesAPI getTRAServicesAPI() {
        return mTRAServicesAPI;
    }
}
