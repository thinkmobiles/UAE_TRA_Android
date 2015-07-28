package com.uae.tra_smart_services.entities;

/**
 * Created by ak-buffalo on 28.07.15.
 *
 * Helper class collect all necessary methods
 */
public class H {
    /**
     * Returns the last non NULL variable
     * */
    public static <T> T coalesce(T ...items) {
        for(T i : items) if(i != null) return i;
        return null;
    }
}
