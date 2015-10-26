package com.uae.tra_smart_services.rest.robo_requests;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.gson.JsonElement;
import com.uae.tra_smart_services.rest.DynamicServicesApi;

import java.util.Map;

import retrofit.client.Response;

/**
 * Created by mobimaks on 19.10.2015.
 */
public final class DynamicServicePostRequest extends BaseRequest<Response, DynamicServicesApi> {

    private final String mUrl;
    private final Map<String, String> mQueryParams;
    private final JsonElement mBody;

    public DynamicServicePostRequest(@NonNull final String _url) {
        this(_url, null, null);
    }

    public DynamicServicePostRequest(@NonNull final String _url, @Nullable final Map<String, String> _queryParams) {
        this(_url, _queryParams, null);
    }

    public DynamicServicePostRequest(@NonNull final String _url, @Nullable final JsonElement _body) {
        this(_url, null, _body);
    }

    public DynamicServicePostRequest(@NonNull final String _url,
                                     @Nullable final Map<String, String> _queryParams,
                                     @Nullable final JsonElement _body) {

        super(Response.class, DynamicServicesApi.class);
        mUrl = _url;
        mQueryParams = _queryParams;
        mBody = _body;
    }

    @Override
    public Response loadDataFromNetwork() throws Exception {
        if (mBody == null) {
            return getService().performPostRequest(mUrl, mQueryParams);
        } else {
            return getService().performPostRequest(mUrl, mQueryParams, mBody);
        }
    }
}
