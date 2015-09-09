package com.uae.tra_smart_services.fragment;

import android.os.Bundle;

import com.uae.tra_smart_services.R;
import com.uae.tra_smart_services.fragment.base.BaseFragment;

/**
 * Created by PC on 9/9/2015.
 */
public class NotificationsFragment extends BaseFragment {

    public static NotificationsFragment newInstance() {
        return new NotificationsFragment();
    }
    @Override
    protected int getTitle() {
        return 0;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_notification;
    }
}
