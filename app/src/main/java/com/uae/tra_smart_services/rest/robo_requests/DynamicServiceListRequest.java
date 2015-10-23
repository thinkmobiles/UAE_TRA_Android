package com.uae.tra_smart_services.rest.robo_requests;

import com.uae.tra_smart_services.rest.TRAServicesAPI;
import com.uae.tra_smart_services.rest.model.response.DynamicServiceInfoResponseModel;
import com.uae.tra_smart_services.rest.model.response.DynamicServiceInfoResponseModel.List;

/**
 * Created by mobimaks on 20.10.2015.
 */
public class DynamicServiceListRequest extends BaseRequest<DynamicServiceInfoResponseModel.List, TRAServicesAPI> {

    public DynamicServiceListRequest() {
        super(List.class, TRAServicesAPI.class);
    }

    @Override
    public List loadDataFromNetwork() throws Exception {
        return getService().getDynamicServiceList();
    }
}
