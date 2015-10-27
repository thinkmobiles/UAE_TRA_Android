package com.uae.tra_smart_services_cutter.interfaces;

import android.support.annotation.StringRes;

/**
 * Created by Mikazme on 23/07/2015.
 */
public interface ToolbarTitleManager {
    void setTitle(@StringRes int _resId);

    void setToolbarVisibility(final boolean _isVisible);
}
