package com.uae.tra_smart_services_cutter.rest.model.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by mobimaks on 04.10.2015.
 */
public final class ChangePasswordModel {

    @Expose
    @SerializedName("oldPass")
    public String oldPassword;

    @Expose
    @SerializedName("newPass")
    public String newPassword;

}
