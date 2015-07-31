package com.uae.tra_smart_services.fragments;

import com.uae.tra_smart_services.R;
import com.uae.tra_smart_services.baseentities.BaseAuthorizationFragment;

/**
 * Created by Andrey Korneychuk on 22.07.15.
 */
public class RestorePassFragment extends BaseAuthorizationFragment {

    public static RestorePassFragment newInstance() {
        return new RestorePassFragment();
    }

    @Override
    public void onStart() {
        super.onStart();
        toolbarTitleManager.setTitle(R.string.forgot_password_title);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_restorepass;
    }
}
