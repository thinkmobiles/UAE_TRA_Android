package com.uae.tra_smart_services.rest.model.request;

/**
 * Created by ak-buffalo on 14.08.15.
 */
public class PoorCoverageRequestModel {

    private int signalLevel;
    private Location location;
    private String address;

    public String getAddress() {
        return address;
    }

    public int getSignalLevel() {
        return signalLevel;
    }

    public void setSignalLevel(int signalLevel) {
        this.signalLevel = signalLevel;
    }

    public void setLocation(String latitude, String longitude){
        this.location = new Location(latitude, longitude);
    }

    public Location getLocation() {
        return location;
    }

    public void setAddress(String address){
        this.address = address;
    }

    private class Location{
        private String latitude;
        private String longitude;

        public Location(String latitude, String longitude) {
            this.latitude = latitude;
            this.longitude = longitude;
        }
    }

    @Override
    public String toString() {
        return "{"+
                    "signalLevel:" + signalLevel + ","+
                    "location:{"+
                            "latitude:" + location.latitude + ","+
                            "longitude:" + location.longitude +
                    "},"+
                    "address:" + address+
                "}";
    }
}
