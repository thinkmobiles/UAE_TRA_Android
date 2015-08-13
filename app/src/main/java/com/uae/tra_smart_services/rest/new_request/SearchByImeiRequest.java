package com.uae.tra_smart_services.rest.new_request;

import com.octo.android.robospice.request.retrofit.RetrofitSpiceRequest;
import com.uae.tra_smart_services.rest.TRAServicesAPI;
import com.uae.tra_smart_services.rest.model.new_response.SearchDeviceResponse;

/**
 * Created by Vitaliy on 13/08/2015.
 */
public class SearchByImeiRequest extends RetrofitSpiceRequest<SearchDeviceResponse.List, TRAServicesAPI> {

    private String mIMEI;

    public SearchByImeiRequest(final String _imei) {
        super(SearchDeviceResponse.List.class, TRAServicesAPI.class);
        mIMEI = _imei;
    }

    @Override
    public final SearchDeviceResponse.List loadDataFromNetwork() throws Exception {
        return getService().searchDeviceByImei(mIMEI);
    }
}
