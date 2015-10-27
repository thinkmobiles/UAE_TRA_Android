package com.uae.tra_smart_services_cutter.rest.robo_requests;

import com.uae.tra_smart_services_cutter.rest.TRAServicesAPI;
import com.uae.tra_smart_services_cutter.rest.model.request.LoginModel;

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
