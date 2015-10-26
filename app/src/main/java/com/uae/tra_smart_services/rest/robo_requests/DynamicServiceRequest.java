package com.uae.tra_smart_services.rest.robo_requests;

import android.support.annotation.NonNull;

import com.uae.tra_smart_services.entities.dynamic_service.DynamicService;
import com.uae.tra_smart_services.rest.TRAServicesAPI;

/**
 * Created by mobimaks on 20.10.2015.
 */
public final class DynamicServiceRequest extends BaseRequest<DynamicService, TRAServicesAPI> {

    private final String mServiceId;

    public DynamicServiceRequest(@NonNull final String _serviceId) {
        super(DynamicService.class, TRAServicesAPI.class);
        mServiceId = _serviceId;
    }

    @Override
    public final DynamicService loadDataFromNetwork() throws Exception {
        return getService().getDynamicServiceDetails(mServiceId);
    }

}
