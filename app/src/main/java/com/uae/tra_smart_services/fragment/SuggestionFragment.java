package com.uae.tra_smart_services.fragment;

import android.os.Bundle;

import com.octo.android.robospice.persistence.DurationInMillis;
import com.uae.tra_smart_services.R;
import com.uae.tra_smart_services.TRAApplication;
import com.uae.tra_smart_services.rest.model.request.ComplainTRAServiceModel;
import com.uae.tra_smart_services.rest.robo_requests.ComplainSuggestionServiceRequest;

/**
 * Created by mobimaks on 14.08.2015.
 */
public class SuggestionFragment extends ComplainAboutTraFragment {

    private ComplainSuggestionServiceRequest mComplainSuggestionServiceRequest;

    public static SuggestionFragment newInstance() {
        return new SuggestionFragment();
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
        mComplainSuggestionServiceRequest = new ComplainSuggestionServiceRequest(traServiceModel, getActivity(), getImageUri());
        showProgressDialog(getString(R.string.str_sending), this);
        getSpiceManager().execute(mComplainSuggestionServiceRequest, KEY_COMPLAIN_REQUEST, DurationInMillis.ALWAYS_EXPIRED, getRequestListener());
    }

    @Override
    public void onDialogCancel() {
        if(getSpiceManager().isStarted() && mComplainSuggestionServiceRequest != null){
            getSpiceManager().cancel(mComplainSuggestionServiceRequest);
        }
    }

    @Override
    protected int getTitle() {
        return R.string.service_suggestion;
    }
}
