package com.uae.tra_smart_services_cutter.rest.robo_requests;

import com.uae.tra_smart_services_cutter.rest.TRAServicesAPI;
import com.uae.tra_smart_services_cutter.rest.model.request.PoorCoverageRequestModel;

import retrofit.client.Response;

/**
 * Created by ak-buffalo on 14.08.15.
 */
public class PoorCoverageRequest extends BaseRequest<Response, TRAServicesAPI> {

    private PoorCoverageRequestModel mRateServiceModel;

    public PoorCoverageRequest(final PoorCoverageRequestModel _ratingServiceModel) {
        super(Response.class, TRAServicesAPI.class);
        mRateServiceModel = _ratingServiceModel;
    }

    @Override
    public final Response loadDataFromNetwork() throws Exception {
        return getService().poorCoverage(mRateServiceModel);
    }
}