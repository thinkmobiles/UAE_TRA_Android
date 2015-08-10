package com.uae.tra_smart_services.rest.model.request;

/**
 * Created by mobimaks on 30.07.2015.
 */
public class ImeiCode {

    private String IMEI;
    private String imei;

    private ImeiCode() {}

    public static ImeiCode createDeviceCheckImei(String imei) {
        ImeiCode imeiCode = new ImeiCode();
        imeiCode.IMEI = imei;
        return imeiCode;
    }

    public static ImeiCode createAuthenticationImei(String imei) {
        ImeiCode imeiCode = new ImeiCode();
        imeiCode.imei = imei;
        return imeiCode;
    }

    public static ImeiCode getDefault() {
        return createDeviceCheckImei("anonymous");
    }

}
