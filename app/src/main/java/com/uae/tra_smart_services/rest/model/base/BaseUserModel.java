package com.uae.tra_smart_services.rest.model.base;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by mobimaks on 08.10.2015.
 */
public abstract class BaseUserModel implements Parcelable {

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

    public String getUsername() {
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
        dest.writeString(this.email);
        dest.writeString(this.mobile);
    }

    public BaseUserModel() {
    }

    protected BaseUserModel(Parcel in) {
        this.firstName = in.readString();
        this.lastName = in.readString();
        this.email = in.readString();
        this.mobile = in.readString();
    }

}
