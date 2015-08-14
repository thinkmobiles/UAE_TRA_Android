package com.uae.tra_smart_services.rest.new_request;

import com.octo.android.robospice.request.retrofit.RetrofitSpiceRequest;
import com.uae.tra_smart_services.rest.TRAServicesAPI;
import com.uae.tra_smart_services.rest.model.new_response.DomainAvailabilityCheckResponseModel;

/**
 * Created by Vitaliy on 13/08/2015.
 */
public class DomainAvailabilityCheckRequest extends RetrofitSpiceRequest<DomainAvailabilityCheckResponseModel, TRAServicesAPI> {

    private String mCheckUrl;

    public DomainAvailabilityCheckRequest(final String _checkUrl) {
        super(DomainAvailabilityCheckResponseModel.class, TRAServicesAPI.class);
        mCheckUrl = _checkUrl;
    }

    @Override
    public final DomainAvailabilityCheckResponseModel loadDataFromNetwork() throws Exception {
        return getService().checkDomainAvailability(mCheckUrl);
    }
}
