package com.uae.tra_smart_services_cutter.fragment;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;

import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.PendingRequestListener;
import com.octo.android.robospice.request.listener.RequestListener;
import com.uae.tra_smart_services_cutter.R;
import com.uae.tra_smart_services_cutter.TRAApplication;
import com.uae.tra_smart_services_cutter.customviews.LoaderView;
import com.uae.tra_smart_services_cutter.customviews.ThemedImageView;
import com.uae.tra_smart_services_cutter.dialog.AlertDialogFragment;
import com.uae.tra_smart_services_cutter.fragment.base.BaseComplainFragment;
import com.uae.tra_smart_services_cutter.global.Service;
import com.uae.tra_smart_services_cutter.interfaces.Loader;
import com.uae.tra_smart_services_cutter.interfaces.LoaderMarker;
import com.uae.tra_smart_services_cutter.rest.model.request.ComplainTRAServiceModel;
import com.uae.tra_smart_services_cutter.rest.robo_requests.ComplainAboutTRAServiceRequest;

import retrofit.client.Response;

/**
 * Created by mobimaks on 11.08.2015.
 */
public class ComplainAboutTraFragment extends BaseComplainFragment
                                    implements OnClickListener, AlertDialogFragment.OnOkListener {

    protected static final String KEY_COMPLAIN_REQUEST = "COMPLAIN_ABOUT_TRA_REQUEST";

    private ThemedImageView tivAddAttachment;
    private EditText etComplainTitle, etDescription;

    private ComplainAboutTRAServiceRequest request;
    private RequestResponseListener mRequestListener;

    public static ComplainAboutTraFragment newInstance() {
        return new ComplainAboutTraFragment();
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
        tivAddAttachment = findView(R.id.tivAddAttachment_FCAT);

        etComplainTitle = findView(R.id.etComplainTitle_FCAT);
        setCapitalizeTextWatcher(etComplainTitle);
        etDescription = findView(R.id.etDescription_FCAT);
        setCapitalizeTextWatcher(etDescription);
    }

    @Override
    protected void initListeners() {
        super.initListeners();
        mRequestListener = new RequestResponseListener();
        tivAddAttachment.setOnClickListener(this);
        etComplainTitle.setOnFocusChangeListener(this);
        etDescription.setOnFocusChangeListener(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        getSpiceManager().getFromCache(Response.class, getRequestKey(), DurationInMillis.ALWAYS_RETURNED, mRequestListener);
//        getSpiceManager().addListenerIfPending(Response.class, getRequestKey(), mRequestListener);
    }

    protected String getRequestKey(){
        return KEY_COMPLAIN_REQUEST;
    }

    @Override
    public void onClick(View v) {
        hideKeyboard(v);
        switch (v.getId()) {
            case R.id.tivAddAttachment_FCAT:
                openImagePicker();
                break;
        }
    }

    @Override
    public void onAttachmentGet(Uri _uri) {
        tivAddAttachment.setImageResource(R.drawable.ic_check);
    }

    @Override
    protected void onAttachmentDeleted() {
        tivAddAttachment.setImageResource(R.drawable.ic_action_attachment);
    }

    @Override
    protected boolean validateData() {
        final String title = etComplainTitle.getText().toString().trim();
        final String description = etDescription.getText().toString().trim();

        if (title.isEmpty() || description.isEmpty()) {
            Toast.makeText(getActivity(), R.string.error_fill_all_fields, Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    @Override
    protected void sendComplain() {
        ComplainTRAServiceModel traServiceModel = new ComplainTRAServiceModel();
        traServiceModel.title = getTitleText();
        traServiceModel.description = getDescriptionText();
        request = new ComplainAboutTRAServiceRequest(traServiceModel, getActivity(), getImageUri());
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
        getSpiceManager().execute(request, getRequestKey(), DurationInMillis.ALWAYS_EXPIRED, mRequestListener);
    }

    @Override
    public void onLoadingCanceled() {
        if(getSpiceManager().isStarted()){
            getSpiceManager().removeDataFromCache(Response.class, getRequestKey());
            getSpiceManager().cancel(request);
        }
    }

    @Override
    public void onOkPressed(final int _mMessageId) {
        // Unimplemented method
        // Used exceptionally to specify OK button in dialog
    }

    @Override
    protected String getServiceName() {
        return "complain about TRA Service";
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
                getSpiceManager().removeDataFromCache(Response.class, getRequestKey());
            }
        }

        @Override
        public void onRequestFailure(SpiceException spiceException) {
            getSpiceManager().removeDataFromCache(Response.class, getRequestKey());
            processError(spiceException);
        }
    }

    @Override
    protected Service getServiceType() {
        return Service.COMPLAINT_ABOUT_TRA;
    }

    protected RequestListener<Response> getRequestListener() {
        return mRequestListener;
    }

    @NonNull
    protected final String getDescriptionText() {
        return etDescription.getText().toString();
    }

    @NonNull
    protected final String getTitleText() {
        return etComplainTitle.getText().toString();
    }

    @Override
    protected int getTitle() {
        return R.string.service_complain_about_tra;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_complain_about_tra;
    }
}
