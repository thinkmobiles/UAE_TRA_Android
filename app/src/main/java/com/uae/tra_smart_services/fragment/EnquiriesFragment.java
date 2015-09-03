package com.uae.tra_smart_services.fragment;

import android.os.Bundle;

import com.octo.android.robospice.persistence.DurationInMillis;
import com.uae.tra_smart_services.R;
import com.uae.tra_smart_services.TRAApplication;
import com.uae.tra_smart_services.rest.model.request.ComplainTRAServiceModel;
import com.uae.tra_smart_services.rest.robo_requests.ComplainEnquiriesServiceRequest;

/**
 * Created by mobimaks on 14.08.2015.
 */
public class EnquiriesFragment extends ComplainAboutTraFragment {

    private static final String KEY_ENQUIRIES_REQUEST = "ENQUIRIES_REQUEST";

    private ComplainEnquiriesServiceRequest mRequest;

    public static EnquiriesFragment newInstance() {
        return new EnquiriesFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!TRAApplication.isLoggedIn()) {
            getFragmentManager().popBackStack();
        }
    }

    @Override
    protected void sendComplain() {
        ComplainTRAServiceModel traServiceModel = new ComplainTRAServiceModel();
        traServiceModel.title = getTitleText();
        traServiceModel.description = getDescriptionText();
        mRequest = new ComplainEnquiriesServiceRequest(traServiceModel, getActivity(), getImageUri());
        showProgressDialog(getString(R.string.str_sending), this);
        getSpiceManager().execute(mRequest, getRequestKey(), DurationInMillis.ALWAYS_EXPIRED, getRequestListener());
    }

    @Override
    public void onDialogCancel() {
        if (getSpiceManager().isStarted() && mRequest != null) {
            getSpiceManager().cancel(mRequest);
        }
    }

    @Override
    protected String getRequestKey() {
        return KEY_ENQUIRIES_REQUEST;
    }

    @Override
    protected int getTitle() {
        return R.string.service_enquiries;
    }
}
