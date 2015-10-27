package com.uae.tra_smart_services_cutter.rest.robo_requests;

import com.uae.tra_smart_services_cutter.rest.TRAServicesAPI;
import com.uae.tra_smart_services_cutter.rest.model.request.RegisterModel;

import retrofit.client.Response;

/**
 * Created by mobimaks on 15.08.2015.
*/
public class RegisterRequest extends BaseRequest<Response, TRAServicesAPI> {

    private final RegisterModel mRegisterModel;

    public RegisterRequest(final RegisterModel _registerModel) {
        super(Response.class, TRAServicesAPI.class);
        mRegisterModel = _registerModel;
    }

    @Override
    public Response loadDataFromNetwork() throws Exception {
        return getService().register(mRegisterModel);
    }
}
