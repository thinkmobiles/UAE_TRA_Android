package com.uae.tra_smart_services.fragment.tutorial;

import android.os.Bundle;

import com.uae.tra_smart_services.R;
import com.uae.tra_smart_services.fragment.base.BaseFragment;

/**
 * Created by Vitaliy on 21/09/2015.
 */
public class AvatarTutorialFragment extends BaseFragment {

    public static AvatarTutorialFragment newInstance() {
        AvatarTutorialFragment fragment = new AvatarTutorialFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getTitle() {
        return 0;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_avatar_tutorial;
    }


    @Override
    protected void setToolbarVisibility() {
        toolbarTitleManager.setToolbarVisibility(false);
    }
}
