package com.uae.tra_smart_services_cutter.rest.robo_requests;

import com.uae.tra_smart_services_cutter.rest.TRAServicesAPI;
import com.uae.tra_smart_services_cutter.rest.model.response.SearchDeviceResponseModel;

/**
 * Created by Mikazme on 13/08/2015.
 */
public class SearchByBrandRequest extends BaseRequest<SearchDeviceResponseModel.List, TRAServicesAPI> {

    private String mBrand;
    private Integer mStart;
    private Integer mEnd;

    public SearchByBrandRequest(final String _brand, final Integer _start, final Integer _end) {
        super(SearchDeviceResponseModel.List.class, TRAServicesAPI.class);
        mBrand = _brand;
        mStart = _start;
        mEnd = _end;
    }

    @Override
    public final SearchDeviceResponseModel.List loadDataFromNetwork() throws Exception {
        return getService().searchDeviceByBrandName(mBrand, mStart, mEnd);
    }
}
