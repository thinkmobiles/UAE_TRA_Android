package com.uae.tra_smart_services.rest.request;

import com.octo.android.robospice.request.retrofit.RetrofitSpiceRequest;
import com.uae.tra_smart_services.rest.ServicesAPI;
import com.uae.tra_smart_services.rest.model.request.ImeiCode;
import com.uae.tra_smart_services.rest.model.responce.DeviceStatus;

/**
 * Created by mobimaks on 31.07.2015.
 */
public class CheckMobileRequest extends RetrofitSpiceRequest<DeviceStatus, ServicesAPI> {

    private ImeiCode mImeiCode;

    public CheckMobileRequest(ImeiCode imeiCode) {
        super(DeviceStatus.class, ServicesAPI.class);
        mImeiCode = imeiCode;
    }

    @Override
    public DeviceStatus loadDataFromNetwork() throws Exception {
        return getService().getDeviceStatus(mImeiCode);
    }

}
