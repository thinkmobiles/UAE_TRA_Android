package com.uae.tra_smart_services.rest.robo_requests;

import com.uae.tra_smart_services.rest.TRAServicesAPI;
import com.uae.tra_smart_services.rest.model.response.DomainInfoCheckResponseModel;

/**
 * Created by Vitaliy on 13/08/2015.
 */
public class DomainInfoCheckRequest extends BaseRequest<DomainInfoCheckResponseModel, TRAServicesAPI> {

    private String mCheckUrl;

    public DomainInfoCheckRequest(final String _checkUrl) {
        super(DomainInfoCheckResponseModel.class, TRAServicesAPI.class);
        mCheckUrl = _checkUrl;
    }

    @Override
    public final DomainInfoCheckResponseModel loadDataFromNetwork() throws Exception {
        return getService().getDomainData(mCheckUrl);
    }
}
