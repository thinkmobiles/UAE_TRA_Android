package com.uae.tra_smart_services.rest.robo_requests;

import com.uae.tra_smart_services.rest.TRAServicesAPI;
import com.uae.tra_smart_services.rest.model.request.LogoutRequestModel;

import retrofit.client.Response;

/**
 * Created by mobimaks on 15.08.2015.
 */
public class LogoutRequest extends BaseRequest<Response, TRAServicesAPI> {

    private LogoutRequestModel mModel;

    public LogoutRequest(final LogoutRequestModel _model) {
        super(Response.class, TRAServicesAPI.class);
        mModel = _model;
    }

    @Override
    public Response loadDataFromNetwork() throws Exception {
        return getService().logout(mModel);
    }
}
