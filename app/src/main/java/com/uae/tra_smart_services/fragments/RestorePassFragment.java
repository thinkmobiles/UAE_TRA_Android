package com.uae.tra_smart_services.fragments;

import com.uae.tra_smart_services.R;
import com.uae.tra_smart_services.baseentities.BaseAuthorizationFragment;

/**
 * Created by ak-buffalo on 22.07.15.
 */
public class RestorePassFragment extends BaseAuthorizationFragment {

    public static RestorePassFragment newInstance() {
        return new RestorePassFragment();
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_restorepass;
    }
}
