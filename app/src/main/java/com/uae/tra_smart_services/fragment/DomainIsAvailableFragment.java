package com.uae.tra_smart_services.fragment;

import com.uae.tra_smart_services.R;
import com.uae.tra_smart_services.fragment.base.BaseFragment;

/**
 * Created by ak-buffalo on 14.08.15.
 */
public class DomainIsAvailableFragment extends BaseFragment {

    public static DomainIsAvailableFragment newInstance() {
        return new DomainIsAvailableFragment();
    }
    @Override
    protected int getTitle() {
        return R.string.str_domain_availability;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_domain_is_available;
    }
}
