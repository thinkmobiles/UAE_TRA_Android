package com.uae.tra_smart_services.rest.model.responce;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by mobimaks on 30.07.2015.
 */
public class ApprovedDevices {

    public List<ApprovedDevice> approvedDevices;

    public boolean isEmpty() {
        return approvedDevices == null || approvedDevices.isEmpty();
    }

    public static class ApprovedDevice {

        public String idApprovedDevices;

        @SerializedName("CompanyName")
        public String companyName;

        @SerializedName("EquipmentModel")
        public String equipmentModel;

        @SerializedName("EquipmentName")
        public String equipmentName;

        @SerializedName("RegistrationNumb")
        public String registrationNumb;

        @SerializedName("ExtId")
        public String extId;

    }

}
