package com.uae.tra_smart_services.rest.robo_requests;

import com.uae.tra_smart_services.rest.TRAServicesAPI;
import com.uae.tra_smart_services.rest.model.response.UserProfileResponseModel;

/**
 * Created by mobimaks on 03.10.2015.
 */
public class UserProfileRequest extends BaseRequest<UserProfileResponseModel, TRAServicesAPI> {

    public UserProfileRequest() {
        super(UserProfileResponseModel.class, TRAServicesAPI.class);
    }

    @Override
    public UserProfileResponseModel loadDataFromNetwork() throws Exception {
        return getService().getUserProfile();
    }
}
