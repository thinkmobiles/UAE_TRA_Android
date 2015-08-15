package com.uae.tra_smart_services.rest.new_request;

import com.uae.tra_smart_services.rest.TRAServicesAPI;
import com.uae.tra_smart_services.rest.model.new_request.LoginModel;

import retrofit.client.Response;

/**
 * Created by mobimaks on 15.08.2015.
 */
public class LoginRequest extends BaseRequest<Response, TRAServicesAPI> {

    private final LoginModel mLoginModel;

    public LoginRequest(final LoginModel _loginModel) {
        super(Response.class, TRAServicesAPI.class);
        mLoginModel = _loginModel;
    }

    @Override
    public Response loadDataFromNetwork() throws Exception {
        return getService().login(mLoginModel);
    }
}
