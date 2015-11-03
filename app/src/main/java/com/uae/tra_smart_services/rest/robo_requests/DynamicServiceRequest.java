package com.uae.tra_smart_services.rest.robo_requests;

import android.support.annotation.NonNull;

import com.uae.tra_smart_services.entities.dynamic_service.DynamicService;
import com.uae.tra_smart_services.rest.DynamicServicesApi;

import retrofit.client.Response;

/**
 * Created by mobimaks on 19.10.2015.
 */
public final class DynamicServiceRequest extends BaseRequest<Response, DynamicServicesApi> {

    private final DynamicService mDynamicService;

    public DynamicServiceRequest(@NonNull final DynamicService _dynamicService) {

        super(Response.class, DynamicServicesApi.class);
        mDynamicService = _dynamicService;
    }

    @Override
    public Response loadDataFromNetwork() throws Exception {
        return getService().performPostRequest(mDynamicService.id, mDynamicService.getJsonData());
    }

}
