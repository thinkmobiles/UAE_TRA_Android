package com.uae.tra_smart_services.rest.new_request;

import com.octo.android.robospice.request.retrofit.RetrofitSpiceRequest;
import com.uae.tra_smart_services.rest.TRAServicesAPI;
import com.uae.tra_smart_services.rest.model.new_request.ComplainServiceProviderModel;

import retrofit.client.Response;

/**
 * Created by Vitaliy on 13/08/2015.
 */
public class ComplainAboutServiceRequest extends RetrofitSpiceRequest<Response, TRAServicesAPI> {

    private ComplainServiceProviderModel mComplainServiceModel;

    public ComplainAboutServiceRequest(final ComplainServiceProviderModel _complainServiceProviderModel) {
        super(Response.class, TRAServicesAPI.class);
        mComplainServiceModel = _complainServiceProviderModel;
    }

    @Override
    public final Response loadDataFromNetwork() throws Exception {
        return getService().complainServiceProvider(mComplainServiceModel);
    }
}
