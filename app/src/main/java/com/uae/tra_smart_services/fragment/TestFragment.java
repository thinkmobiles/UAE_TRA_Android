package com.uae.tra_smart_services.fragment;

import android.os.Bundle;

import com.uae.tra_smart_services.R;
import com.uae.tra_smart_services.fragment.base.BaseFragment;

/**
 * Created by ak-buffalo on 19.08.15.
 */
public class TestFragment extends BaseFragment {
    public static TestFragment newInstance() {
        
        Bundle args = new Bundle();
        
        TestFragment fragment = new TestFragment();
        fragment.setArguments(args);
        return fragment;
    }
    
    @Override
    protected int getTitle() {
        return 0;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_info_hub;
    }
}
