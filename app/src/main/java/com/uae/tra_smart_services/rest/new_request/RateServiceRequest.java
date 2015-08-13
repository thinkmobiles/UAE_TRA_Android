package com.uae.tra_smart_services.rest.new_request;

import com.octo.android.robospice.request.retrofit.RetrofitSpiceRequest;
import com.uae.tra_smart_services.rest.TRAServicesAPI;
import com.uae.tra_smart_services.rest.model.new_request.RatingServiceModel;

import retrofit.client.Response;

/**
 * Created by Vitaliy on 13/08/2015.
 */
public class RateServiceRequest extends RetrofitSpiceRequest<Response, TRAServicesAPI> {

    private RatingServiceModel mRateServiceModel;

    public RateServiceRequest(final RatingServiceModel _ratingServiceModel) {
        super(Response.class, TRAServicesAPI.class);
        mRateServiceModel = _ratingServiceModel;
    }

    @Override
    public final Response loadDataFromNetwork() throws Exception {
        return getService().ratingService(mRateServiceModel);
    }
}
