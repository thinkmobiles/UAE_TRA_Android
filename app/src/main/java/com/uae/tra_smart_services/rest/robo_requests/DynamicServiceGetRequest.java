package com.uae.tra_smart_services.rest.robo_requests;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.uae.tra_smart_services.rest.DynamicServicesApi;

import java.util.Map;

import retrofit.client.Response;

/**
 * Created by mobimaks on 19.10.2015.
 */
public final class DynamicServiceGetRequest extends BaseRequest<Response, DynamicServicesApi> {

    private final String mUrl;
    private final Map<String, String> mQueryParams;

    public DynamicServiceGetRequest(@NonNull final String _url,
                                    @Nullable final Map<String, String> _queryParams) {

        super(Response.class, DynamicServicesApi.class);
        mUrl = _url;
        mQueryParams = _queryParams;
    }

    @Override
    public Response loadDataFromNetwork() throws Exception {
        return getService().performGetRequest(mUrl, mQueryParams);
    }
}
