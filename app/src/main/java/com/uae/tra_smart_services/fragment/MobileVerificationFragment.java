package com.uae.tra_smart_services.fragment;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.PendingRequestListener;
import com.uae.tra_smart_services.R;
import com.uae.tra_smart_services.activity.ScannerActivity;
import com.uae.tra_smart_services.fragment.base.BaseFragment;
import com.uae.tra_smart_services.global.Constants;
import com.uae.tra_smart_services.rest.model.new_response.SearchDeviceResponseModel;
import com.uae.tra_smart_services.rest.new_request.SearchByImeiRequest;

/**
 * Created by mobimaks on 13.08.2015.
 */
public class MobileVerificationFragment extends BaseFragment implements OnClickListener {

    private static final String KEY_SEARCH_DEVICE_BY_IMEI_REQUEST = "SEARCH_DEVICE_BY_IMEI_REQUEST";
    private static final int CODE_SCANNER_REQUEST = 1;

    private ImageView ivCameraBtn;
    private EditText etImeiNumber;
    private RequestResponseListener mRequestListener;

    public static MobileVerificationFragment newInstance() {
        return new MobileVerificationFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    protected void initViews() {
        super.initViews();
        ivCameraBtn = findView(R.id.ivCameraBtn_FMV);
        etImeiNumber = findView(R.id.etImeiNumber_FMV);
    }

    @Override
    protected void initListeners() {
        super.initListeners();
        mRequestListener = new RequestResponseListener();
        ivCameraBtn.setOnClickListener(this);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_send, menu);
    }

    @Override
    public void onStart() {
        super.onStart();
//        getSpiceManager().getFromCache(SearchDeviceResponseModel.List.class, KEY_SEARCH_DEVICE_BY_IMEI_REQUEST, DurationInMillis.ALWAYS_RETURNED, mRequestListener);
        getSpiceManager().addListenerIfPending(SearchDeviceResponseModel.List.class, KEY_SEARCH_DEVICE_BY_IMEI_REQUEST, mRequestListener);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_send) {
            hideKeyboard(etImeiNumber);
            if (isImeiValid()) {
                searchDeviceByImei();
            } else {
                Toast.makeText(getActivity(), "Please set IMEI code", Toast.LENGTH_SHORT).show();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void searchDeviceByImei() {
        SearchByImeiRequest request = new SearchByImeiRequest(etImeiNumber.getText().toString());
        showProgressDialog();
        getSpiceManager().execute(request, KEY_SEARCH_DEVICE_BY_IMEI_REQUEST, DurationInMillis.ALWAYS_EXPIRED, mRequestListener);
    }

    private boolean isImeiValid() {
        return !etImeiNumber.getText().toString().isEmpty();// TODO: Add IMEI check
    }

    @Override
    public void onClick(final View _view) {
        if (isCameraAvailable()) {
            startScanner();
        } else {
            Toast.makeText(getActivity(), "Camera isn't available", Toast.LENGTH_SHORT).show();
        }
    }

    private void startScanner() {
        startActivityForResult(ScannerActivity.getStartIntent(getActivity()), CODE_SCANNER_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CODE_SCANNER_REQUEST && resultCode == Activity.RESULT_OK) {
            String text = data.getStringExtra(Constants.KEY_SCANNER_RESULT_TEXT);
            etImeiNumber.setText(text);
        }
    }

    private class RequestResponseListener implements PendingRequestListener<SearchDeviceResponseModel.List> {

        @Override
        public void onRequestNotFound() {
            Log.d(getClass().getSimpleName(), "Request Not Found. isAdded: " + isAdded());
        }

        @Override
        public void onRequestSuccess(SearchDeviceResponseModel.List result) {
            Log.d(getClass().getSimpleName(), "Success. isAdded: " + isAdded());
            if (isAdded()) {
                hideProgressDialog();
                if (result != null) {
                    showMessage(R.string.str_success, R.string.str_request_failed);
                }
            }
            getSpiceManager().removeDataFromCache(SearchDeviceResponseModel.List.class, KEY_SEARCH_DEVICE_BY_IMEI_REQUEST);
        }

        @Override
        public void onRequestFailure(SpiceException spiceException) {
            Log.d(getClass().getSimpleName(), "Failure. isAdded: " + isAdded());
            if (isAdded()) {
                hideProgressDialog();
                Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
            }
            getSpiceManager().removeDataFromCache(SearchDeviceResponseModel.List.class, KEY_SEARCH_DEVICE_BY_IMEI_REQUEST);
        }
    }

    private boolean isCameraAvailable() {
        return getActivity().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA);
    }

    @Override
    protected int getTitle() {
        return R.string.str_empty;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_mobile_verification;
    }
}
