package com.uae.tra_smart_services.fragment;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.PendingRequestListener;
import com.uae.tra_smart_services.R;
import com.uae.tra_smart_services.TRAApplication;
import com.uae.tra_smart_services.adapter.ServiceProviderAdapter;
import com.uae.tra_smart_services.customviews.LoaderView;
import com.uae.tra_smart_services.customviews.ThemedImageView;
import com.uae.tra_smart_services.fragment.base.BaseComplainFragment;
import com.uae.tra_smart_services.global.C;
import com.uae.tra_smart_services.global.Service;
import com.uae.tra_smart_services.interfaces.Loader;
import com.uae.tra_smart_services.interfaces.LoaderMarker;
import com.uae.tra_smart_services.rest.model.request.ComplainServiceProviderModel;
import com.uae.tra_smart_services.rest.robo_requests.ComplainAboutServiceRequest;
import com.uae.tra_smart_services.util.TRAPatterns;

import retrofit.client.Response;

/**
 * Created by mobimaks on 10.08.2015.
 */
public final class ComplainAboutServiceFragment extends BaseComplainFragment
        implements OnClickListener {

    protected static final String KEY_COMPLAIN_REQUEST = "COMPLAIN_ABOUT_SERVICE_REQUEST";

    private Spinner sProviderSpinner;
    private ThemedImageView tivAddAttachment;
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
        tivAddAttachment = findView(R.id.tivAddAttachment_FCAS);

        etComplainTitle = findView(R.id.etComplainTitle_FCAS);
        setCapitalizeTextWatcher(etComplainTitle);
        etReferenceNumber = findView(R.id.etReferenceNumber_FCAS);
        etDescription = findView(R.id.etDescription_FCAS);
        setCapitalizeTextWatcher(etDescription);
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
        tivAddAttachment.setOnClickListener(this);
        etComplainTitle.setOnFocusChangeListener(this);
        etDescription.setOnFocusChangeListener(this);
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

        getSpiceManager().execute(mComplainAboutServiceRequest, KEY_COMPLAIN_REQUEST, DurationInMillis.ALWAYS_EXPIRED, mRequestResponseListener);
    }

    @Override
    protected boolean validateData() {
        final String title = etComplainTitle.getText().toString();
        final String phone = etReferenceNumber.getText().toString();
        final String description = etDescription.getText().toString();

        if (title.isEmpty() || phone.isEmpty() || description.isEmpty()) {
            Toast.makeText(getActivity(), R.string.error_fill_all_fields, C.TOAST_LENGTH).show();
            return false;
        }

        if (phone.length() < TRAPatterns.MIN_PHONE_NUMBER_LENGTH) {
            Toast.makeText(getActivity(), R.string.phone_number_is_too_short, C.TOAST_LENGTH).show();
            return false;
        }

        boolean numberInvalid = !Patterns.PHONE.matcher(phone).matches();
        if (numberInvalid) {
            Toast.makeText(getActivity(), R.string.fragment_complain_no_reference_number, C.TOAST_LENGTH).show();
            return false;
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        hideKeyboard(v);
        switch (v.getId()) {
            case R.id.tivAddAttachment_FCAS:
                openImagePicker();
                break;
        }
    }

    @Override
    public void onAttachmentGet(@NonNull Uri _uri) {
        tivAddAttachment.setImageResource(R.drawable.ic_check);
    }

    @Override
    protected void onAttachmentDeleted() {
        tivAddAttachment.setImageResource(R.drawable.ic_action_attachment);
    }

    @Override
    public void onLoadingCanceled() {
        if (getSpiceManager().isStarted()) {
            getSpiceManager().cancel(mComplainAboutServiceRequest);
        }
    }

    @Override
    protected String getServiceName() {
        return "complain about Service Provider";
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
                loaderOverlaySuccess(getString(R.string.str_complain_has_been_sent));
                getSpiceManager().removeDataFromCache(Response.class, KEY_COMPLAIN_REQUEST);
//                if (result != null) {
//                    getFragmentManager().popBackStackImmediate();
//                }
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
    protected Service getServiceType() {
        return Service.COMPLAIN_ABOUT_PROVIDER;
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
