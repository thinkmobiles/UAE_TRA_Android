package com.uae.tra_smart_services.rest.robo_requests;

import com.uae.tra_smart_services.rest.TRAServicesAPI;
import com.uae.tra_smart_services.rest.model.request.RatingServiceRequestModel;
import com.uae.tra_smart_services.rest.model.response.CustomResponse;
import com.uae.tra_smart_services.rest.model.response.RatingServiceResponseModel;

import retrofit.client.Response;

/**
 * Created by PC on 9/3/2015.
 */
public class RatingServiceRequest extends BaseRequest<RatingServiceResponseModel, TRAServicesAPI> {

    private final RatingServiceRequestModel mRegisterModel;

    public RatingServiceRequest(final RatingServiceRequestModel _registerModel) {
        super(RatingServiceResponseModel.class, TRAServicesAPI.class);
        mRegisterModel = _registerModel;
    }

    @Override
    public RatingServiceResponseModel loadDataFromNetwork() throws Exception {
        return getService().ratingService(mRegisterModel);
    }
}
