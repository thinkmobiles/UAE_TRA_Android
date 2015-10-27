package com.uae.tra_smart_services_cutter.rest.robo_requests;

import com.uae.tra_smart_services_cutter.rest.TRAServicesAPI;
import com.uae.tra_smart_services_cutter.rest.model.request.RestorePasswordRequestModel;

import retrofit.client.Response;

/**
 * Created by ak-buffalo on 18.09.15.
 */
public class RestorePasswordRequest extends BaseRequest<Response, TRAServicesAPI> {

    private final RestorePasswordRequestModel mRegisterModel;

    public RestorePasswordRequest(final RestorePasswordRequestModel _registerModel) {
        super(Response.class, TRAServicesAPI.class);
        mRegisterModel = _registerModel;
    }

    @Override
    public Response loadDataFromNetwork() throws Exception {
        return getService().restorePassword(mRegisterModel);
    }
}