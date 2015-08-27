package com.uae.tra_smart_services.fragment;

import android.os.Bundle;
import android.widget.TextView;

import com.octo.android.robospice.request.SpiceRequest;
import com.uae.tra_smart_services.R;
import com.uae.tra_smart_services.fragment.base.BaseFragment;
import com.uae.tra_smart_services.global.C;

/**
 * Created by ak-buffalo on 14.08.15.
 */
public class DomainInfoFragment extends BaseFragment {

    private TextView doaminInfo;

    public static DomainInfoFragment newInstance(String _domainInfo) {

        Bundle args = new Bundle();
        args.putString(C.DOMAIN_INFO, _domainInfo);
        DomainInfoFragment fragment = new DomainInfoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initViews() {
        super.initViews();
        doaminInfo = findView(R.id.tvDomainInfo_FDI);
        doaminInfo.setText(getArguments().getString(C.DOMAIN_INFO));
    }

    @Override
    protected int getTitle() {
        return R.string.str_domain_info;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_domain_info;
    }
}
