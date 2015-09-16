package com.uae.tra_smart_services.fragment;

import android.widget.EditText;

import com.uae.tra_smart_services.R;
import com.uae.tra_smart_services.fragment.base.BaseAuthorizationFragment;
import com.uae.tra_smart_services.util.ImageUtils;

/**
 * Created by Andrey Korneychuk on 22.07.15.
 */
public class RestorePassFragment extends BaseAuthorizationFragment {

    private EditText etEmail;

    public static RestorePassFragment newInstance() {
        return new RestorePassFragment();
    }

    @Override
    protected int getTitle() {
        return R.string.forgot_password_title;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_restorepass;
    }

    @Override
    public void onDialogCancel() {
        // TODO Implement method when Restore password logic will be done.
    }

    @Override
    protected void initViews() {
        etEmail = findView(R.id.etEmail_FR);
        etEmail.setCompoundDrawablesRelativeWithIntrinsicBounds(ImageUtils.getFilteredDrawableByTheme(getActivity(), R.drawable.ic_mail, R.attr.authorizationDrawableColors), null, null, null);
    }
}
