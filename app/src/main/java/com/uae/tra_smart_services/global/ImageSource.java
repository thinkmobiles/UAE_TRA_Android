package com.uae.tra_smart_services.global;

import android.content.Context;
import android.support.annotation.StringRes;

import com.uae.tra_smart_services.R;

/**
 * Created by mobimaks on 11.08.2015.
 */
public enum ImageSource {
    CAMERA {
        @Override
        public int getTitleRes() {
            return R.string.fragment_complain_about_service_camera;
        }
    },
    GALLERY {
        @Override
        public int getTitleRes() {
            return R.string.fragment_complain_about_service_gallery;
        }
    };

    @StringRes
    public abstract int getTitleRes();

    public static String[] toStringArray(final Context _context) {
        final ImageSource[] imageSources = values();
        final String[] strProviders = new String[imageSources.length];
        for (int i = 0; i < imageSources.length; i++) {
            strProviders[i] = _context.getString(imageSources[i].getTitleRes());
        }
        return strProviders;
    }
}
