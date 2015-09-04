package com.uae.tra_smart_services.fragment;

import android.app.LoaderManager;
import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.Loader;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.ArrayMap;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.uae.tra_smart_services.R;
import com.uae.tra_smart_services.dialog.AlertDialogFragment;
import com.uae.tra_smart_services.fragment.base.BaseFragment;
import com.uae.tra_smart_services.global.C;
import com.uae.tra_smart_services.rest.model.request.RatingServiceRequestModel;
import com.uae.tra_smart_services.rest.model.response.DomainInfoCheckResponseModel;
import com.uae.tra_smart_services.rest.model.response.RatingServiceResponseModel;
import com.uae.tra_smart_services.rest.robo_requests.RatingServiceRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ak-buffalo on 14.08.15.
 */
public class DomainInfoFragment extends BaseFragment implements RadioGroup.OnCheckedChangeListener, AlertDialogFragment.OnOkListener, LoaderManager.LoaderCallbacks<Map<String, String>> {

    private TextView tvDomainName_FDI;
    private TextView tvRegisterId_FDI;
    private TextView tvRegisterName_FDI;
    private TextView tvStatus_FDI;
    private TextView tvRegContactId_FDI;
    private TextView tvRegContactName_FDI;
    private RadioGroup ratingGroup;

    public static DomainInfoFragment newInstance(DomainInfoCheckResponseModel _domainInfo) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(C.DOMAIN_INFO, _domainInfo);
        DomainInfoFragment fragment = new DomainInfoFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void initData() {
        super.initData();
        getLoaderManager().initLoader(0, getArguments(), this).forceLoad();
    }

    @Override
    protected void initViews() {
        super.initViews();
        tvDomainName_FDI = findView(R.id.tvDomainName_FDI);
        tvRegisterId_FDI = findView(R.id.tvRegisterId_FDI);
        tvRegisterName_FDI = findView(R.id.tvRegisterName_FDI);
        tvStatus_FDI = findView(R.id.tvStatus_FDI);
        tvRegContactId_FDI = findView(R.id.tvRegContactId_FDI);
        tvRegContactName_FDI = findView(R.id.tvRegContactName_FDI);
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

    @Override
    public Loader onCreateLoader(int id, Bundle args) {
        return new DomainDataParser(getActivity(),  (DomainInfoCheckResponseModel) args.getParcelable(C.DOMAIN_INFO));
    }

    @Override
    public void onLoadFinished(Loader<Map<String, String>> loader, Map<String, String> data) {
        tvDomainName_FDI.setText(data.get("Domain Name"));
        tvRegisterId_FDI.setText(data.get("Registrar ID"));
        tvRegisterName_FDI.setText(data.get("Registrar Name"));
        tvStatus_FDI.setText(data.get("Status"));
        tvRegContactId_FDI.setText(data.get("Registrant Contact ID"));
        tvRegContactName_FDI.setText(data.get("Registrant Contact Name"));
    }

    @Override
    public void onLoaderReset(Loader loader) {
        loader.reset();
    }

    private static class DomainDataParser extends AsyncTaskLoader<Map<String, String>>{
        private DomainInfoCheckResponseModel mModel;

        public DomainDataParser(Context _context, DomainInfoCheckResponseModel _model) {
            super(_context);
            mModel = _model;
        }

        @Override
        public Map<String, String> loadInBackground() {
            return new HashMap<String, String>(){
                {
                    for (String line : mModel.urlData.split("\\r\\n")){
                        String[] keyvaluePair = line.split(":");
                        if(keyvaluePair.length == 2){
                            put(keyvaluePair[0].trim(), keyvaluePair[1].trim());
                        }
                    }
                }
            };
        }
    }
}
