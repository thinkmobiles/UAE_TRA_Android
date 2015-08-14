package com.uae.tra_smart_services.rest.request;

import com.uae.tra_smart_services.rest.ServicesAPI;
import com.uae.tra_smart_services.rest.model.responce.ApprovedDevices;
import com.uae.tra_smart_services.rest.new_request.BaseRequest;

/**
 * Created by mobimaks on 31.07.2015.
 */
public class ApprovedDeviceRequest extends BaseRequest<ApprovedDevices, ServicesAPI> {

    private String mToken, mBrend, mModel;

    public ApprovedDeviceRequest(String token, String brend, String model) {
        super(ApprovedDevices.class, ServicesAPI.class);
        mToken = token;
        mBrend = brend;
        mModel = model;
    }

    @Override
    public ApprovedDevices loadDataFromNetwork() throws Exception {
        return getService().getApprovedDevices(mToken, mBrend, mModel);
    }
}
