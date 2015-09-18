package com.uae.tra_smart_services.rest.robo_requests;

import com.uae.tra_smart_services.rest.TRAServicesAPI;
import com.uae.tra_smart_services.rest.model.request.RestorePasswordRequestModel;

import retrofit.client.Response;

/**
 * Created by Andrey Korneychuk on 18.09.15.
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