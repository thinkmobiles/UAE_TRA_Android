package com.uae.tra_smart_services.rest.robo_requests;

import com.uae.tra_smart_services.rest.TRAServicesAPI;

import retrofit.client.Response;

/**
 * Created by mobimaks on 15.08.2015.
 */
public class LogoutRequest extends BaseRequest<Response, TRAServicesAPI> {

    public LogoutRequest() {
        super(Response.class, TRAServicesAPI.class);
    }

    @Override
    public Response loadDataFromNetwork() throws Exception {
        return getService().logout();
    }
}
