package com.uae.tra_smart_services.rest.new_request;

import com.octo.android.robospice.request.retrofit.RetrofitSpiceRequest;
import com.uae.tra_smart_services.rest.TRAServicesAPI;
import com.uae.tra_smart_services.rest.model.new_response.DomainInfoCheckResponseModel;

/**
 * Created by Vitaliy on 13/08/2015.
 */
public class DomainInfoCheckRequest extends RetrofitSpiceRequest<DomainInfoCheckResponseModel, TRAServicesAPI> {

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
