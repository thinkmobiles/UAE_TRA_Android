package com.uae.tra_smart_services.fragment;

import android.os.Bundle;
import android.widget.EditText;

import com.octo.android.robospice.request.SpiceRequest;
import com.uae.tra_smart_services.R;
import com.uae.tra_smart_services.fragment.base.BaseFragment;
import com.uae.tra_smart_services.global.C;

/**
 * Created by ak-buffalo on 14.08.15.
 */
public class DomainIsAvailableFragment extends BaseFragment {

    public static DomainIsAvailableFragment newInstance(Bundle _bundle) {
        DomainIsAvailableFragment fragment = new DomainIsAvailableFragment();
        fragment.setArguments(_bundle);
        return fragment;
    }

    @Override
    protected void initViews() {
        super.initViews();
        ((EditText)findView(R.id.etDomainStrValue_FDCH)).setText(getArguments().getString(C.DOMAIN_INFO));
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
