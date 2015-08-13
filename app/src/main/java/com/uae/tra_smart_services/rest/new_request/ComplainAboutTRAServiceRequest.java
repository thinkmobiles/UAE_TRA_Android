package com.uae.tra_smart_services.rest.new_request;

import com.octo.android.robospice.request.retrofit.RetrofitSpiceRequest;
import com.uae.tra_smart_services.rest.TRAServicesAPI;
import com.uae.tra_smart_services.rest.model.new_request.ComplainTRAServiceModel;

import retrofit.client.Response;

/**
 * Created by Vitaliy on 13/08/2015.
 */
public class ComplainAboutTRAServiceRequest extends RetrofitSpiceRequest<Response, TRAServicesAPI> {

    private ComplainTRAServiceModel mComplainTRAServiceModel;

    public ComplainAboutTRAServiceRequest(final ComplainTRAServiceModel _complainTRAServiceModel) {
        super(Response.class, TRAServicesAPI.class);
        mComplainTRAServiceModel = _complainTRAServiceModel;
    }

    @Override
    public Response loadDataFromNetwork() throws Exception {
        return getService().complainTraServiceProvider(mComplainTRAServiceModel);
    }
}
