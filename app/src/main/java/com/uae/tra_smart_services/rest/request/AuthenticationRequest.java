package com.uae.tra_smart_services.rest.request;

import com.uae.tra_smart_services.rest.ServicesAPI;
import com.uae.tra_smart_services.rest.model.request.ImeiCode;
import com.uae.tra_smart_services.rest.model.responce.AccessToken;
import com.uae.tra_smart_services.rest.new_request.BaseRequest;

/**
 * Created by mobimaks on 31.07.2015.
 */
public class AuthenticationRequest extends BaseRequest<AccessToken, ServicesAPI> {

    private final ImeiCode mImeiCode;

    public AuthenticationRequest(final ImeiCode imeiCode) {
        super(AccessToken.class, ServicesAPI.class);
        mImeiCode = imeiCode;
    }

    @Override
    public AccessToken loadDataFromNetwork() throws Exception {
        return getService().authenticate(mImeiCode);
    }
}
