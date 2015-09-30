package com.uae.tra_smart_services.rest.model.request;

/**
 * Created by ak-buffalo on 18.09.15.
 */

public class RestorePasswordRequestModel {
    public String email;

    public RestorePasswordRequestModel(String _email){
        email = _email;
    }

    public String getEmail(){
       return email;
    }
}
