package com.uae.tra_smart_services.fragment;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.PendingRequestListener;
import com.octo.android.robospice.request.listener.RequestListener;
import com.uae.tra_smart_services.R;
import com.uae.tra_smart_services.TRAApplication;
import com.uae.tra_smart_services.dialog.AlertDialogFragment;
import com.uae.tra_smart_services.fragment.base.AttachmentFragment;
import com.uae.tra_smart_services.rest.model.request.ComplainTRAServiceModel;
import com.uae.tra_smart_services.rest.robo_requests.ComplainAboutTRAServiceRequest;
import com.uae.tra_smart_services.util.ImageUtils;

import retrofit.client.Response;

/**
 * Created by mobimaks on 11.08.2015.
 */
public class ComplainAboutTraFragment extends AttachmentFragment
                                    implements OnClickListener, AlertDialogFragment.OnOkListener {

    protected static final String KEY_COMPLAIN_REQUEST = "COMPLAIN_ABOUT_TRA_REQUEST";

    private ImageView ivAddAttachment;
    private EditText etComplainTitle, etDescription;

    private ComplainAboutTRAServiceRequest request;
    private Uri mImageUri;
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
        ivAddAttachment = findView(R.id.ivAddAttachment_FCAT);

        etComplainTitle = findView(R.id.etComplainTitle_FCAT);
        etDescription = findView(R.id.etDescription_FCAT);
    }

    @Override
    protected void initListeners() {
        super.initListeners();
        mRequestListener = new RequestResponseListener();
        ivAddAttachment.setOnClickListener(this);
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
            case R.id.ivAddAttachment_FCAT:
                openImagePicker();
                break;
        }
    }

    @Override
    protected void onImageGet(Uri _uri) {
        ivAddAttachment.setImageDrawable(ImageUtils.getFilteredDrawableByTheme(getActivity(), R.drawable.ic_check, R.attr.authorizationDrawableColors));
        mImageUri = _uri;
    }

    @Override
    protected boolean validateData() {
        boolean titleInvalid = etComplainTitle.getText().toString().isEmpty();
        if (titleInvalid) {
            Toast.makeText(getActivity(), R.string.fragment_complain_no_title, Toast.LENGTH_SHORT).show();
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
    protected void sendComplain() {
        ComplainTRAServiceModel traServiceModel = new ComplainTRAServiceModel();
        traServiceModel.title = getTitleText();
        traServiceModel.description = getDescriptionText();
        request = new ComplainAboutTRAServiceRequest(traServiceModel, getActivity(), mImageUri);
        showLoaderOverlay(getString(R.string.str_sending), this);
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

    private class RequestResponseListener implements PendingRequestListener<Response> {

        @Override
        public void onRequestNotFound() {
            Log.d(getClass().getSimpleName(), "Request Not Found. isAdded: " + isAdded());
        }

        @Override
        public void onRequestSuccess(Response result) {
            Log.d(getClass().getSimpleName(), "Success. isAdded: " + isAdded());
            if (isAdded()) {
                boolean dismissed = dissmissLoaderDialog();
                dissmissLoaderOverlay(getString(R.string.str_reuqest_has_been_sent_and_you_will_receive_sms));
                getSpiceManager().removeDataFromCache(Response.class, getRequestKey());
                if (result != null) {
                    if(dismissed){
                        showMessage(R.string.str_success, R.string.str_complain_has_been_sent);
                        getFragmentManager().popBackStackImmediate();
                    } else {
                        changeLoaderOverlay_Success(getString(R.string.str_complain_has_been_sent));
                    }
                }
            }
        }

        @Override
        public void onRequestFailure(SpiceException spiceException) {
            getSpiceManager().removeDataFromCache(Response.class, getRequestKey());
            processError(spiceException);
        }
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
        return R.string.complain;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_complain_about_tra;
    }
}
