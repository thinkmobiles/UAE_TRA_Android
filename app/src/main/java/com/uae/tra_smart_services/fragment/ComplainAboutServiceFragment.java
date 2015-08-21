package com.uae.tra_smart_services.fragment;

import android.net.Uri;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.PendingRequestListener;
import com.uae.tra_smart_services.R;
import com.uae.tra_smart_services.dialog.CustomSingleChoiceDialog;
import com.uae.tra_smart_services.dialog.CustomSingleChoiceDialog.OnItemPickListener;
import com.uae.tra_smart_services.fragment.base.BaseComplainFragment;
import com.uae.tra_smart_services.global.ServiceProvider;
import com.uae.tra_smart_services.rest.model.request.ComplainServiceProviderModel;
import com.uae.tra_smart_services.rest.robo_requests.ComplainAboutServiceRequest;

import retrofit.client.Response;

/**
 * Created by mobimaks on 10.08.2015.
 */
public final class ComplainAboutServiceFragment extends BaseComplainFragment
        implements OnClickListener, OnItemPickListener {

    protected static final String KEY_COMPLAIN_REQUEST = "COMPLAIN_REQUEST";

    private ImageView ivAddAttachment, ivNextItem;
    private TextView tvServiceProvider;
    private EditText etComplainTitle, etReferenceNumber, etDescription;

    private RequestResponseListener mRequestResponseListener;

    public static ComplainAboutServiceFragment newInstance() {
        return new ComplainAboutServiceFragment();
    }

    @Override
    protected void initViews() {
        super.initViews();
        ivAddAttachment = findView(R.id.ivAddAttachment_FCAS);
        ivNextItem = findView(R.id.ivNextItem_FCAS);

        tvServiceProvider = findView(R.id.tvServiceProvider_FCAS);

        etComplainTitle = findView(R.id.etComplainTitle_FCAS);
        etReferenceNumber = findView(R.id.etReferenceNumber_FCAS);
        etDescription = findView(R.id.etDescription_FCAS);
    }

    @Override
    protected void initListeners() {
        super.initListeners();
        mRequestResponseListener = new RequestResponseListener();
        ivAddAttachment.setOnClickListener(this);
        ivNextItem.setOnClickListener(this);
        tvServiceProvider.setOnClickListener(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        //getSpiceManager().getFromCache(Response.class, KEY_COMPLAIN_REQUEST, DurationInMillis.ALWAYS_RETURNED, mRequestResponseListener);
        getSpiceManager().addListenerIfPending(Response.class, KEY_COMPLAIN_REQUEST, mRequestResponseListener);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_send) {
            hideKeyboard(getView());
            sendComplain();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void sendComplain() {
        ComplainServiceProviderModel complainModel = new ComplainServiceProviderModel();
        complainModel.title = etComplainTitle.getText().toString();
        complainModel.serviceProvider = tvServiceProvider.getText().toString();
        complainModel.referenceNumber = etReferenceNumber.getText().toString();
        complainModel.description = etDescription.getText().toString();
        ComplainAboutServiceRequest request = new ComplainAboutServiceRequest(complainModel, getActivity(), getImageUri());

        showProgressDialog();
        getSpiceManager().execute(request, KEY_COMPLAIN_REQUEST, DurationInMillis.ALWAYS_EXPIRED, mRequestResponseListener);
    }

//    private boolean validateData() {
//        boolean titleInvalid = etComplainTitle.getText().toString().isEmpty();
//        if (titleInvalid) {
//            Toast.makeText(getActivity(), "Please provide complaint title", Toast.LENGTH_SHORT).show();
//            return false;
//        }
//        if (mImageUri == null) {
//            Toast.makeText(getActivity(), "Please select image", Toast.LENGTH_SHORT).show();
//            return false;
//        }
//        boolean serviceProviderSelected = !tvServiceProvider.getText().toString().isEmpty();
//        if (!serviceProviderSelected) {
//            Toast.makeText(getActivity(), "Please select service provider", Toast.LENGTH_SHORT).show();
//            return false;
//        }
//        boolean numberInvalid = !Patterns.PHONE.matcher(etReferenceNumber.getText().toString()).matches();
//        if (numberInvalid) {
//            Toast.makeText(getActivity(), "Please set reference number", Toast.LENGTH_SHORT).show();
//            return false;
//        }
//        boolean descriptionInvalid = etDescription.getText().toString().isEmpty();
//        if (descriptionInvalid) {
//            Toast.makeText(getActivity(), "Please provide description", Toast.LENGTH_SHORT).show();
//            return false;
//        }
//        return true;
//    }

    @Override
    public void onClick(View v) {
        hideKeyboard(v);
        switch (v.getId()) {
            case R.id.ivAddAttachment_FCAS:
                openImagePicker();
                break;
            case R.id.tvServiceProvider_FCAS:
            case R.id.ivNextItem_FCAS:
                openServiceProviderPicker();
                break;
        }
    }

    @Override
    protected void onImageGet(Uri _uri) {

    }

    private void openServiceProviderPicker() {
        CustomSingleChoiceDialog
                .newInstance(this)
                .setTitle("Select service provider")
                .setBodyItems(ServiceProvider.toStringArray())
                .show(getFragmentManager());
    }

    @Override
    public void onItemPicked(int _dialogItem) {
        tvServiceProvider.setText(ServiceProvider.values()[_dialogItem].toString());
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
                hideProgressDialog();
                if (result != null) {
                    showMessage(R.string.str_success, R.string.str_complain_has_been_sent);
                    getFragmentManager().popBackStackImmediate();
                }
            }
            getSpiceManager().removeDataFromCache(Response.class, KEY_COMPLAIN_REQUEST);
        }

        @Override
        public void onRequestFailure(SpiceException spiceException) {
            Log.d(getClass().getSimpleName(), "Failure. isAdded: " + isAdded());
            if (isAdded()) {
                hideProgressDialog();
                Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
            }
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
