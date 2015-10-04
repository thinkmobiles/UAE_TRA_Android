package com.uae.tra_smart_services.fragment.user_profile;

import com.uae.tra_smart_services.R;
import com.uae.tra_smart_services.fragment.base.BaseFragment;

/**
 * Created by mobimaks on 08.09.2015.
 */
@Deprecated
public class ResetPasswordFragment extends BaseFragment {

    public static ResetPasswordFragment newInstance() {
        return new ResetPasswordFragment();
    }

    @Override
    protected int getTitle() {
        return R.string.fragment_user_profile_title;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_reset_password;
    }

    @Override
    protected void initViews() {
        super.initViews();

    }
}
