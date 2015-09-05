package com.uae.tra_smart_services.fragment;

import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.IdRes;
import android.support.annotation.StringRes;
import android.widget.Button;
import android.widget.EditText;

import com.octo.android.robospice.request.SpiceRequest;
import com.uae.tra_smart_services.R;
import com.uae.tra_smart_services.fragment.base.BaseFragment;
import com.uae.tra_smart_services.global.C;
import com.uae.tra_smart_services.global.ServerConstants;
import com.uae.tra_smart_services.rest.model.response.DomainAvailabilityCheckResponseModel;

/**
 * Created by ak-buffalo on 14.08.15.
 */
public class DomainIsAvailableFragment extends BaseFragment {

    public static DomainIsAvailableFragment newInstance(DomainAvailabilityCheckResponseModel _model) {
        Bundle bundle = new Bundle();
        bundle.putString(C.DOMAIN_INFO, _model.domainStrValue);
        bundle.putString(C.DOMAIN_STATUS, _model.availableStatus);
        DomainIsAvailableFragment fragment = new DomainIsAvailableFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void initViews() {
        super.initViews();
        ((EditText)findView(R.id.etDomainStrValue_FDCH)).setText(getArguments().getString(C.DOMAIN_INFO));
        Button statustext = findView(R.id.tvDomainAvail_FDCH);
        String status = getArguments().getString(C.DOMAIN_STATUS);
        @StringRes int availabilityTextRes;
        @ColorRes int availabilityColorRes;
        if (status.equals(ServerConstants.AVAILABLE)){
            availabilityTextRes = R.string.str_domain_available;
            availabilityColorRes = R.color.hex_primary_green;
        } else if(status.equals(ServerConstants.NOT_AVAILABLE)){
            availabilityTextRes = R.string.str_domain_not_available;
            availabilityColorRes = R.color.hex_primary_red;
        } else {
            availabilityTextRes = R.string.str_domain_no_information;
            availabilityColorRes = R.color.hex_black_color;
        }
        statustext.setText(getString(availabilityTextRes));
        statustext.setTextColor(getResources().getColor(availabilityColorRes));
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