package com.uae.tra_smart_services.global;

/**
 * Created by ak-buffalo on 14.08.15.
 */
public enum LocationType {
    AUTO {
        @Override
        public String toString() {
            return "Automatically";
        }
    },
    MANUAL {
        @Override
        public String toString() {
            return "Enter your location";
        }
    };

    public static String[] toStringArray() {
        final LocationType[] providers = values();
        final String[] strTypes = new String[providers.length];
        for (int i = 0; i < providers.length; i++) {
            strTypes[i] = providers[i].toString();
        }
        return strTypes;
    }
}
