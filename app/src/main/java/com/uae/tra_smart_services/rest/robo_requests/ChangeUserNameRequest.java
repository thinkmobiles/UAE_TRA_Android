package com.uae.tra_smart_services.rest.robo_requests;

import com.uae.tra_smart_services.rest.TRAServicesAPI;
import com.uae.tra_smart_services.rest.model.request.UserNameModel;
import com.uae.tra_smart_services.rest.model.response.UserProfileResponseModel;

/**
 * Created by mobimaks on 03.10.2015.
 */
public class ChangeUserNameRequest extends BaseRequest<UserProfileResponseModel, TRAServicesAPI> {

    private final UserNameModel mNameModel;

    public ChangeUserNameRequest(final UserNameModel _userNameModel) {
        super(UserProfileResponseModel.class, TRAServicesAPI.class);
        mNameModel = _userNameModel;
    }

    @Override
    public UserProfileResponseModel loadDataFromNetwork() throws Exception {
        return getService().editUserProfile(mNameModel);
    }
}
