package com.uae.tra_smart_services.rest.request;

import com.uae.tra_smart_services.rest.ServicesAPI;
import com.uae.tra_smart_services.rest.model.responce.ApprovedDevices;
import com.uae.tra_smart_services.rest.new_request.BaseRequest;

/**
 * Created by mobimaks on 31.07.2015.
 */
public class AllApprovedDeviceRequest extends BaseRequest<ApprovedDevices, ServicesAPI> {

    private String mToken;

    public AllApprovedDeviceRequest(String token) {
        super(ApprovedDevices.class, ServicesAPI.class);
        mToken = token;
    }

    @Override
    public ApprovedDevices loadDataFromNetwork() throws Exception {
        return getService().getAllApprovedDevices(mToken);
    }
}
