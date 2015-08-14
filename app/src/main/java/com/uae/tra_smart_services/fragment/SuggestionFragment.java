package com.uae.tra_smart_services.fragment;

import com.octo.android.robospice.persistence.DurationInMillis;
import com.uae.tra_smart_services.rest.model.new_request.ComplainTRAServiceModel;
import com.uae.tra_smart_services.rest.new_request.ComplainSuggestionServiceRequest;

/**
 * Created by mobimaks on 14.08.2015.
 */
public class SuggestionFragment extends ComplainAboutTraFragment {

    public static SuggestionFragment newInstance() {
        return new SuggestionFragment();
    }

    @Override
    protected void sendComplain() {
        ComplainTRAServiceModel traServiceModel = new ComplainTRAServiceModel();
        traServiceModel.title = getTitleText();
        traServiceModel.description = getDescriptionText();
        ComplainSuggestionServiceRequest request = new ComplainSuggestionServiceRequest(traServiceModel, getActivity(), getImageUri());
        showProgressDialog();
        getSpiceManager().execute(request, KEY_COMPLAIN_REQUEST, DurationInMillis.ALWAYS_EXPIRED, getRequestListener());
    }

}
