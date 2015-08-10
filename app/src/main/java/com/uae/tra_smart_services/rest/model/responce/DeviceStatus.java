package com.uae.tra_smart_services.rest.model.responce;

import com.google.gson.annotations.SerializedName;

/**
 * Created by mobimaks on 30.07.2015.
 */
public class DeviceStatus extends Status {

    @SerializedName("user_data")
    public Device device;

    public static class Device {

        @SerializedName("device_brand")
        public String brand;

        @SerializedName("device_name")
        public String name;

        @SerializedName("device_model")
        public String model;
    }
}
