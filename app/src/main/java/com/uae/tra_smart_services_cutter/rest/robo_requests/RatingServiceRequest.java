package com.uae.tra_smart_services_cutter.rest.robo_requests;

import com.uae.tra_smart_services_cutter.rest.TRAServicesAPI;
import com.uae.tra_smart_services_cutter.rest.model.request.RatingServiceRequestModel;
import com.uae.tra_smart_services_cutter.rest.model.response.RatingServiceResponseModel;

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
