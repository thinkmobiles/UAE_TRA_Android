package com.uae.tra_smart_services.rest.new_request;

import com.uae.tra_smart_services.rest.TRAServicesAPI;
import com.uae.tra_smart_services.rest.model.new_request.RegisterModel;

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
