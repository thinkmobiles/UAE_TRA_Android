package com.uae.tra_smart_services.rest.new_request;

import com.octo.android.robospice.request.retrofit.RetrofitSpiceRequest;
import com.uae.tra_smart_services.rest.TRAServicesAPI;
import com.uae.tra_smart_services.rest.model.new_response.SearchDeviceResponse;

/**
 * Created by Vitaliy on 13/08/2015.
 */
public class SearchByBrandRequest extends RetrofitSpiceRequest<SearchDeviceResponse.List, TRAServicesAPI> {

    private String mBrand;
    private Integer mStart;
    private Integer mEnd;

    public SearchByBrandRequest(final String _brand, final Integer _start, final Integer _end) {
        super(SearchDeviceResponse.List.class, TRAServicesAPI.class);
        mBrand = _brand;
        mStart = _start;
        mEnd = _end;
    }

    @Override
    public final SearchDeviceResponse.List loadDataFromNetwork() throws Exception {
        return getService().searchDeviceByBrandName(mBrand, mStart, mEnd);
    }
}
