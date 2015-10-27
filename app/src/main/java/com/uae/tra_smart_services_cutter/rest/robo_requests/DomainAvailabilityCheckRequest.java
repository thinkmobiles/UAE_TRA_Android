package com.uae.tra_smart_services_cutter.rest.robo_requests;

import com.uae.tra_smart_services_cutter.rest.TRAServicesAPI;
import com.uae.tra_smart_services_cutter.rest.model.response.DomainAvailabilityCheckResponseModel;

/**
 * Created by Mikazme on 13/08/2015.
 */
public class DomainAvailabilityCheckRequest extends BaseRequest<DomainAvailabilityCheckResponseModel, TRAServicesAPI> {

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
