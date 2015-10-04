package com.uae.tra_smart_services.rest.model.request;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.uae.tra_smart_services.entities.UserProfile;

/**
 * Created by mobimaks on 03.10.2015.
 */
public class UserNameModel implements Parcelable {

    @Expose
    @SerializedName("first")
    public String firstName;

    @Expose
    @SerializedName("last")
    public String lastName;

    @Expose
    @SerializedName("email")
    public String email;

    @Expose
    @SerializedName("mobile")
    public String mobile;

    public UserNameModel() {
    }

    public UserNameModel(String _firstName, String _lastName) {
        firstName = _firstName;
        lastName = _lastName;
    }

    public UserNameModel(UserProfile _userProfile) {
        firstName = _userProfile.firstName;
        lastName = _userProfile.lastName;
    }

    public final String getUsername(){
        return firstName + " " + lastName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.firstName);
        dest.writeString(this.lastName);
    }

    protected UserNameModel(Parcel in) {
        this.firstName = in.readString();
        this.lastName = in.readString();
    }

    public static final Parcelable.Creator<UserNameModel> CREATOR = new Parcelable.Creator<UserNameModel>() {
        public UserNameModel createFromParcel(Parcel source) {
            return new UserNameModel(source);
        }

        public UserNameModel[] newArray(int size) {
            return new UserNameModel[size];
        }
    };
}
