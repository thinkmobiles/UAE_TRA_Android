package com.uae.tra_smart_services.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.EditText;

import com.octo.android.robospice.persistence.DurationInMillis;
import com.uae.tra_smart_services.R;
import com.uae.tra_smart_services.TRAApplication;
import com.uae.tra_smart_services.customviews.LoaderView;
import com.uae.tra_smart_services.global.Service;
import com.uae.tra_smart_services.interfaces.Loader;
import com.uae.tra_smart_services.interfaces.LoaderMarker;
import com.uae.tra_smart_services.rest.model.request.ComplainTRAServiceModel;
import com.uae.tra_smart_services.rest.robo_requests.ComplainSuggestionServiceRequest;

/**
 * Created by mobimaks on 14.08.2015.
 */
public class SuggestionFragment extends ComplainAboutTraFragment {

    private static final String KEY_SUGGESTION_REQUEST = "SUGGESTION_REQUEST";

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
    protected void initViews() {
        super.initViews();
        ((EditText)findView(R.id.etComplainTitle_FCAT)).setHint(getString(R.string.str_suggestion_title));
    }

    @Override
    protected void sendComplain() {
        ComplainTRAServiceModel traServiceModel = new ComplainTRAServiceModel();
        traServiceModel.title = getTitleText();
        traServiceModel.description = getDescriptionText();
        mComplainSuggestionServiceRequest = new ComplainSuggestionServiceRequest(traServiceModel, getActivity(), getImageUri());
        loaderOverlayShow(getString(R.string.str_sending), (LoaderMarker) this);
        loaderOverlayButtonBehavior(new Loader.BackButton() {
            @Override
            public void onBackButtonPressed(LoaderView.State _currentState) {
                getFragmentManager().popBackStack();
                if (_currentState == LoaderView.State.FAILURE || _currentState == LoaderView.State.SUCCESS) {
                    getFragmentManager().popBackStack();
                }
            }
        });
        getSpiceManager().execute(mComplainSuggestionServiceRequest, getRequestKey(), DurationInMillis.ALWAYS_EXPIRED, getRequestListener());
    }

    @NonNull
    @Override
    protected Service getServiceType() {
        return Service.SUGGESTION;
    }

    @Override
    protected String getRequestKey() {
        return KEY_SUGGESTION_REQUEST;
    }

    @Override
    public void onLoadingCanceled() {
        if(getSpiceManager().isStarted() && mComplainSuggestionServiceRequest != null){
            getSpiceManager().cancel(mComplainSuggestionServiceRequest);
        }
    }

    @Override
    protected int getTitle() {
        return R.string.service_suggestion;
    }
}
