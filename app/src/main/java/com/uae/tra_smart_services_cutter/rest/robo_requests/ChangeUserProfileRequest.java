package com.uae.tra_smart_services_cutter.rest.robo_requests;

import android.content.Context;
import android.support.annotation.NonNull;

import com.uae.tra_smart_services_cutter.rest.TRAServicesAPI;
import com.uae.tra_smart_services_cutter.rest.model.request.UserNameModel;
import com.uae.tra_smart_services_cutter.rest.model.response.UserProfileResponseModel;
import com.uae.tra_smart_services_cutter.util.ImageUtils;

import java.io.IOException;

/**
 * Created by mobimaks on 03.10.2015.
 */
public class ChangeUserProfileRequest extends BaseRequest<UserProfileResponseModel, TRAServicesAPI> {

    private final Context mContext;
    private final UserNameModel mNameModel;

    public ChangeUserProfileRequest(@NonNull final Context _context,
                                    @NonNull final UserNameModel _userNameModel) {
        super(UserProfileResponseModel.class, TRAServicesAPI.class);
        mContext = _context;
        mNameModel = _userNameModel;
    }

    @Override
    public UserProfileResponseModel loadDataFromNetwork() throws Exception {
        try {
            if (mNameModel.imageUri != null) {
                mNameModel.imageBase64 = ImageUtils.imageToBase64(mContext.getContentResolver(), mNameModel.imageUri);
            }
            return getService().editUserProfile(mNameModel);
        } catch (IOException e) {
            throw new Exception("Can't load image from device");
        }
    }
}
