package com.uae.tra_smart_services.fragment;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.uae.tra_smart_services.R;
import com.uae.tra_smart_services.dialog.AlertDialogFragment;
import com.uae.tra_smart_services.fragment.base.BaseFragment;
import com.uae.tra_smart_services.global.C;
import com.uae.tra_smart_services.rest.model.request.RatingServiceRequestModel;
import com.uae.tra_smart_services.rest.model.response.CustomResponse;
import com.uae.tra_smart_services.rest.model.response.RatingServiceResponseModel;
import com.uae.tra_smart_services.rest.robo_requests.RatingServiceRequest;

import retrofit.client.Response;

/**
 * Created by ak-buffalo on 14.08.15.
 */
public class DomainInfoFragment extends BaseFragment implements RadioGroup.OnCheckedChangeListener, AlertDialogFragment.OnOkListener {

    private TextView doaminInfo;
    private RadioGroup ratingGroup;

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
        ratingGroup = findView(R.id.rgDomainCheckServiceRating_FDCH);
        int initRating = PreferenceManager.getDefaultSharedPreferences(getActivity()).getInt(C.DOMAIN_INFO_RATING, 0);
        RadioButton checkedButton;
        if((checkedButton = ((RadioButton) ratingGroup.findViewWithTag(initRating))) != null){
            checkedButton.setChecked(true);
        }
        ratingGroup.setOnCheckedChangeListener(this);
    }

    @Override
    protected int getTitle() {
        return R.string.str_domain_info;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_domain_info;
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        RadioButton radioButton = (RadioButton) group.findViewById(checkedId);
        if(radioButton.isChecked()){
            int rating = Integer.valueOf(radioButton.getTag().toString());
            RatingServiceRequestModel model = new RatingServiceRequestModel();
            model.setServiceName("domain_info");
            model.setRate(rating);
            executeCall(model, radioButton);
        }
    }

    private void executeCall(final RatingServiceRequestModel _model, final RadioButton _radio){
        showProgressDialog(getString(R.string.str_sending), null);
        getSpiceManager().execute(
                new RatingServiceRequest(_model),
                new RequestListener<RatingServiceResponseModel>() {
                    @Override
                    public void onRequestFailure(SpiceException spiceException) {
                        _radio.setChecked(false);
                        processError(spiceException);
                    }

                    @Override
                    public void onRequestSuccess(RatingServiceResponseModel response) {
                        hideProgressDialog();
                        switch (response.getStatus()) {
                            case 201:
                                showMessage(R.string.str_success, R.string.str_rating_has_been_sent);
                                PreferenceManager.getDefaultSharedPreferences(getContext()).edit().putInt(C.DOMAIN_INFO_RATING, _model.getRate());
                                break;
                            case 400:
                                showMessage(R.string.str_error, R.string.str_something_went_wrong);
                                _radio.setChecked(false);
                                break;
                            default:
                                PreferenceManager.getDefaultSharedPreferences(getContext()).edit().putInt(C.DOMAIN_INFO_RATING, _model.getRate());
                                break;
                        }
                    }
                }
        );
    }

    @Override
    public void onOkPressed() {
        // Unimplemented method
        // Used exceptionally to specify OK button in dialog
    }
}
