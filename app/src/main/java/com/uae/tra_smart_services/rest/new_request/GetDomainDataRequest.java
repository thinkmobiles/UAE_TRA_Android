package com.uae.tra_smart_services.rest.new_request;

import com.octo.android.robospice.request.retrofit.RetrofitSpiceRequest;
import com.uae.tra_smart_services.rest.TRAServicesAPI;

/**
 * Created by Vitaliy on 13/08/2015.
 */
public class GetDomainDataRequest extends RetrofitSpiceRequest<String, TRAServicesAPI> {

    private String mCheckUrl;

    public GetDomainDataRequest(final String _checkUrl) {
        super(String.class, TRAServicesAPI.class);
        mCheckUrl = _checkUrl;
    }

    @Override
    public final String loadDataFromNetwork() throws Exception {
        return getService().getDomainData(mCheckUrl);
    }
}
