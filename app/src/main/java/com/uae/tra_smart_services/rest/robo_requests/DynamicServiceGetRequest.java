package com.uae.tra_smart_services.rest.robo_requests;

import android.content.Context;
import android.support.annotation.NonNull;

import java.io.IOException;
import java.util.Map;

import retrofit.client.Response;

/**
 * Created by mobimaks on 19.10.2015.
 */
public final class DynamicServiceGetRequest extends BaseDynamicServiceRequest {

    public DynamicServiceGetRequest(@NonNull Context _context,
                                    @NonNull final String _url,
                                    @NonNull final Map<String, String> _queryParams) {
        super(_context, _url, _queryParams);
    }

    @Override
    public Response loadDataFromNetwork() throws Exception {
        try {
            convertQueryAttachmentsToBase64();
            return getService().performGetRequest(getUrl(), getQueryParams());
        } catch (IOException e) {
            throw new Exception("Can't load image from device");
        }
    }
}
