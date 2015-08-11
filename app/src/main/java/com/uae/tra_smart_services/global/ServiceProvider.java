package com.uae.tra_smart_services.global;

/**
 * Created by mobimaks on 11.08.2015.
 */
public enum ServiceProvider {
    DU {
        @Override
        public String toString() {
            return "Du service provider";
        }
    },
    ETISALAT {
        @Override
        public String toString() {
            return "Etisalat service provider";
        }
    };

    public static String[] toStringArray() {
        final ServiceProvider[] providers = values();
        final String[] strProviders = new String[providers.length];
        for (int i = 0; i < providers.length; i++) {
            strProviders[i] = providers[i].toString();
        }
        return strProviders;
    }
}
