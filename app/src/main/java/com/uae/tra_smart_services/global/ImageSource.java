package com.uae.tra_smart_services.global;

/**
 * Created by mobimaks on 11.08.2015.
 */
public enum ImageSource {
    CAMERA {
        @Override
        public String toString() {
            return "Camera";
        }
    },
    GALLERY {
        @Override
        public String toString() {
            return "Gallery";
        }
    };

    public static String[] toStringArray() {
        final ImageSource[] imageSources = values();
        final String[] strProviders = new String[imageSources.length];
        for (int i = 0; i < imageSources.length; i++) {
            strProviders[i] = imageSources[i].toString();
        }
        return strProviders;
    }
}
