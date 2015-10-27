package com.uae.tra_smart_services_cutter.rest.model.request;

import com.google.gson.annotations.Expose;

/**
 * Created by mobimaks on 15.08.2015.
 */
public class RegisterModel extends LoginModel {

    @Expose
    public String first;

    @Expose
    public String last;

    @Expose
    public String emiratesId;

    @Expose
    public Integer state;

    @Expose
    public String mobile;

    @Expose
    public String email;
}
