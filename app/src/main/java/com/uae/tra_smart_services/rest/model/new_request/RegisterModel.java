package com.uae.tra_smart_services.rest.model.new_request;

import com.google.gson.annotations.Expose;

/**
 * Created by mobimaks on 15.08.2015.
 */
public class RegisterModel extends LoginModel {

    @Expose
    public String gender;

    @Expose
    public String phone;


}
