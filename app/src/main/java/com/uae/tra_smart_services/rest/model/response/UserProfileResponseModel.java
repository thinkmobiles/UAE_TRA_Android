package com.uae.tra_smart_services.rest.model.response;

import android.os.Parcel;

import com.google.gson.annotations.Expose;
import com.uae.tra_smart_services.rest.model.request.UserNameModel;

/**
 * Created by mobimaks on 03.10.2015.
 */
public class UserProfileResponseModel extends UserNameModel {

    @Expose
    public String avatar;
    @Expose
    public String error;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(this.email);
        dest.writeString(this.mobile);
        dest.writeString(this.error);
        dest.writeString(this.avatar);
    }

    public UserProfileResponseModel() {
    }

    protected UserProfileResponseModel(Parcel in) {
        super(in);
        this.email = in.readString();
        this.mobile = in.readString();
        this.error = in.readString();
        this.avatar = in.readString();
    }

    public static final Creator<UserProfileResponseModel> CREATOR = new Creator<UserProfileResponseModel>() {
        public UserProfileResponseModel createFromParcel(Parcel source) {
            return new UserProfileResponseModel(source);
        }

        public UserProfileResponseModel[] newArray(int size) {
            return new UserProfileResponseModel[size];
        }
    };
}
