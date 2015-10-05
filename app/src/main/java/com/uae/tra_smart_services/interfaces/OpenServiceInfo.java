package com.uae.tra_smart_services.interfaces;

import com.uae.tra_smart_services.rest.model.response.ServiceInfoResponse;

/**
 * Created by mobimaks on 02.10.2015.
 */
public interface OpenServiceInfo {
    void onOpenServiceInfo(final ServiceInfoResponse _serviceInfo);
}
