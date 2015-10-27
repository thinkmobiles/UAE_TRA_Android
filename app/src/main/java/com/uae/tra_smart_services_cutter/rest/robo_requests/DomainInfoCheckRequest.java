package com.uae.tra_smart_services_cutter.rest.robo_requests;

import com.uae.tra_smart_services_cutter.rest.TRAServicesAPI;
import com.uae.tra_smart_services_cutter.rest.model.response.DomainInfoCheckResponseModel;

/**
 * Created by Mikazme on 13/08/2015.
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
