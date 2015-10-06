package com.uae.tra_smart_services.rest.robo_requests;

import com.uae.tra_smart_services.rest.TRAServicesAPI;
import com.uae.tra_smart_services.rest.model.response.ServiceInfoResponse;

/**
 * Created by mobimaks on 05.10.2015.
 */
public class ServiceInfoRequest extends BaseRequest<ServiceInfoResponse, TRAServicesAPI> {

    private final String mServiceName, mLanguage;

    public ServiceInfoRequest(final String _serviceName, final String _language) {
        super(ServiceInfoResponse.class, TRAServicesAPI.class);
        mServiceName = _serviceName;
        mLanguage = _language;
    }

    @Override
    public ServiceInfoResponse loadDataFromNetwork() throws Exception {
        return getService().getServiceInfo(mServiceName, mLanguage);
    }
}
