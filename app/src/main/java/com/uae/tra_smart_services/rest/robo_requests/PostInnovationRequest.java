package com.uae.tra_smart_services.rest.robo_requests;

import com.uae.tra_smart_services.rest.TRAServicesAPI;
import com.uae.tra_smart_services.rest.model.request.PostInnovationRequestModel;

import retrofit.client.Response;

/**
 * Created by ak-buffalo on 06.10.15.
 */
public class PostInnovationRequest extends BaseRequest<Response, TRAServicesAPI> {
    private PostInnovationRequestModel model;

    public PostInnovationRequest(PostInnovationRequestModel _model) {
        super(Response.class, TRAServicesAPI.class);
        model = _model;
    }

    @Override
    public Response loadDataFromNetwork() throws Exception {
        return getService().postInnovation(model);
    }
}