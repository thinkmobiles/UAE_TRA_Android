package com.uae.tra_smart_services.rest.model.new_request;

/**
 * Created by ak-buffalo on 14.08.15.
 */
public class PoorCoverageRequestModel {

    private int signalLevel;
    private Location location;
    private String address;

    public PoorCoverageRequestModel(int signalLevel, String latitude, String longitude) {
        this.signalLevel = signalLevel;
        this.location = new Location(latitude, longitude);
    }

    public PoorCoverageRequestModel(int _signalLevel, String _address) {
        this.signalLevel = _signalLevel;
        this.address = _address;
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
        return "{"
                    +"signalLevel: " + signalLevel
                    +"location: {"
                            +"latitude: " + location.latitude + ","
                            +"longitude: " + location.longitude
                    +"},"
                +"}";
    }
}
