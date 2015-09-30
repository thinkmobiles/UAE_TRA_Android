package com.uae.tra_smart_services.fragment;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.PendingRequestListener;
import com.uae.tra_smart_services.R;
import com.uae.tra_smart_services.TRAApplication;
import com.uae.tra_smart_services.adapter.ServiceProviderAdapter;
import com.uae.tra_smart_services.fragment.base.AttachmentFragment;
import com.uae.tra_smart_services.rest.model.request.ComplainServiceProviderModel;
import com.uae.tra_smart_services.rest.robo_requests.ComplainAboutServiceRequest;
import com.uae.tra_smart_services.util.ImageUtils;

import retrofit.client.Response;

/**
 * Created by mobimaks on 10.08.2015.
 */
public final class ComplainAboutServiceFragment extends AttachmentFragment
        implements OnClickListener {

    protected static final String KEY_COMPLAIN_REQUEST = "COMPLAIN_ABOUT_SERVICE_REQUEST";

    private Spinner sProviderSpinner;
    private ImageView ivAddAttachment;
    private EditText etComplainTitle, etReferenceNumber, etDescription;

    private ComplainAboutServiceRequest mComplainAboutServiceRequest;

    private RequestResponseListener mRequestResponseListener;

    public static ComplainAboutServiceFragment newInstance() {
        return new ComplainAboutServiceFragment();
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
        initSpinner();
        ivAddAttachment = findView(R.id.ivAddAttachment_FCAS);

        etComplainTitle = findView(R.id.etComplainTitle_FCAS);
        etReferenceNumber = findView(R.id.etReferenceNumber_FCAS);
        etDescription = findView(R.id.etDescription_FCAS);
    }

    private void initSpinner() {
        sProviderSpinner = findView(R.id.sProviderSpinner_FCAS);
        ServiceProviderAdapter adapter = new ServiceProviderAdapter(getActivity());
        sProviderSpinner.setAdapter(adapter);
    }

    @Override
    protected void initListeners() {
        super.initListeners();
        mRequestResponseListener = new RequestResponseListener();
        ivAddAttachment.setOnClickListener(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        getSpiceManager().getFromCache(Response.class, KEY_COMPLAIN_REQUEST, DurationInMillis.ALWAYS_RETURNED, mRequestResponseListener);
    }

    @Override
    protected void sendComplain() {
        ComplainServiceProviderModel complainModel = new ComplainServiceProviderModel();
        complainModel.title = etComplainTitle.getText().toString();
        complainModel.serviceProvider = sProviderSpinner.getSelectedItem().toString();
        complainModel.referenceNumber = etReferenceNumber.getText().toString();
        complainModel.description = etDescription.getText().toString();
        mComplainAboutServiceRequest = new ComplainAboutServiceRequest(complainModel, getActivity(), getImageUri());

        showLoaderOverlay(getString(R.string.str_sending), this);

        getSpiceManager().execute(mComplainAboutServiceRequest, KEY_COMPLAIN_REQUEST, DurationInMillis.ALWAYS_EXPIRED, mRequestResponseListener);
    }

    @Override
    protected boolean validateData() {
        boolean titleInvalid = etComplainTitle.getText().toString().isEmpty();
        if (titleInvalid) {
            Toast.makeText(getActivity(), R.string.fragment_complain_no_title, Toast.LENGTH_SHORT).show();
            return false;
        }
//        boolean serviceProviderSelected = !tvServiceProvider.getSpannedText().toString().isEmpty();
//        if (!serviceProviderSelected) {
//            Toast.makeText(getActivity(), R.string.fragment_complain_no_service_provider, Toast.LENGTH_SHORT).show();
//            return false;
//        }
        boolean numberInvalid = !Patterns.PHONE.matcher(etReferenceNumber.getText().toString()).matches();
        if (numberInvalid) {
            Toast.makeText(getActivity(), R.string.fragment_complain_no_reference_number, Toast.LENGTH_SHORT).show();
            return false;
        }
        boolean descriptionInvalid = etDescription.getText().toString().isEmpty();
        if (descriptionInvalid) {
            Toast.makeText(getActivity(), R.string.fragment_complain_no_description, Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        hideKeyboard(v);
        switch (v.getId()) {
            case R.id.ivAddAttachment_FCAS:
                openImagePicker();
                break;
        }
    }

    @Override
    protected void onImageGet(Uri _uri) {
        ivAddAttachment.setImageDrawable(ImageUtils.getFilteredDrawableByTheme(getActivity(), R.drawable.ic_check, R.attr.authorizationDrawableColors));
    }

    @Override
    public void onLoadingCanceled() {
        if (getSpiceManager().isStarted()) {
            getSpiceManager().cancel(mComplainAboutServiceRequest);
        }
    }

    private class RequestResponseListener implements PendingRequestListener<Response> {

        @Override
        public void onRequestNotFound() {
            Log.d(getClass().getSimpleName(), "Request Not Found. isAdded: " + isAdded());
        }

        @Override
        public void onRequestSuccess(Response result) {
            Log.d(getClass().getSimpleName(), "Success. isAdded: " + isAdded());
            if (isAdded()) {
                dissmissLoaderDialog();
                dissmissLoaderOverlay(getString(R.string.str_reuqest_has_been_sent_and_you_will_receive_sms));
                getSpiceManager().removeDataFromCache(Response.class, KEY_COMPLAIN_REQUEST);
                if (result != null) {
                    showMessage(R.string.str_success, R.string.str_complain_has_been_sent);
                    getFragmentManager().popBackStackImmediate();
                }
            }
        }

        @Override
        public void onRequestFailure(SpiceException spiceException) {
            Log.d(getClass().getSimpleName(), "Failure. isAdded: " + isAdded());
            processError(spiceException);
            getSpiceManager().removeDataFromCache(Response.class, KEY_COMPLAIN_REQUEST);
        }
    }

    @Override
    protected int getTitle() {
        return R.string.complain;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_complain_about_service;
    }
}
