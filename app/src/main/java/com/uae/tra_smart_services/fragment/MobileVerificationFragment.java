package com.uae.tra_smart_services.fragment;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
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
import com.uae.tra_smart_services.customviews.HexagonView;
import com.uae.tra_smart_services.customviews.LoaderView;
import com.uae.tra_smart_services.fragment.base.BaseServiceFragment;
import com.uae.tra_smart_services.global.C;
import com.uae.tra_smart_services.interfaces.Loader;
import com.uae.tra_smart_services.rest.model.response.SearchDeviceResponseModel;
import com.uae.tra_smart_services.rest.robo_requests.SearchByImeiRequest;

/**
 * Created by mobimaks on 13.08.2015.
 */
public class MobileVerificationFragment extends BaseServiceFragment implements OnClickListener {

    private static final String KEY_SEARCH_DEVICE_BY_IMEI_REQUEST = "SEARCH_DEVICE_BY_IMEI_REQUEST";
    private static final int CODE_SCANNER_REQUEST = 1;

    private ImageView ivCameraBtn;
    private EditText etImeiNumber;
    private RequestResponseListener mRequestListener;
    private ApprovedDevicesFragment.OnDeviceSelectListener mSelectListener;
    private HexagonView hvSendImeiCode;

    public static MobileVerificationFragment newInstance() {
        return new MobileVerificationFragment();
    }

    @Override
    public void onAttach(Activity _activity) {
        super.onAttach(_activity);
        mSelectListener = (ApprovedDevicesFragment.OnDeviceSelectListener) _activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(false);
    }

    @Override
    protected int getTitle() {
        return R.string.str_mobile_verification;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_mobile_verification;
    }

    @Override
    protected void initViews() {
        super.initViews();
        ivCameraBtn = findView(R.id.ivCameraBtn_FMV);
        etImeiNumber = findView(R.id.etImeiNumber_FMV);
        hvSendImeiCode = findView(R.id.hvSendImeiCode_FMV);
    }

    @Override
    protected void initListeners() {
        super.initListeners();
        mRequestListener = new RequestResponseListener();
        ivCameraBtn.setOnClickListener(this);
        hvSendImeiCode.setOnClickListener(this);
    }

    @Override
    public void onStart() {
        super.onStart();
//        getSpiceManager().getFromCache(SearchDeviceResponseModel.List.class, KEY_SEARCH_DEVICE_BY_IMEI_REQUEST, DurationInMillis.ALWAYS_RETURNED, mRequestListener);
        getSpiceManager().addListenerIfPending(SearchDeviceResponseModel.List.class, KEY_SEARCH_DEVICE_BY_IMEI_REQUEST, mRequestListener);
    }

    private SearchByImeiRequest mRequest;
    private void searchDeviceByImei() {
        mRequest = new SearchByImeiRequest(etImeiNumber.getText().toString());
        showLoaderOverlay(getString(R.string.str_sending), this);
        setLoaderOverlayBackButtonBehaviour(new Loader.BackButton() {
            @Override
            public void onBackButtonPressed(LoaderView.State _currentState) {
                getFragmentManager().popBackStack();
                getFragmentManager().popBackStack();
            }
        });
        getSpiceManager().execute(mRequest, KEY_SEARCH_DEVICE_BY_IMEI_REQUEST, DurationInMillis.ALWAYS_EXPIRED, mRequestListener);
    }

    @Override
    public void onLoadingCanceled() {
        if(getSpiceManager().isStarted() && mRequest!=null){
            getSpiceManager().cancel(mRequest);
        }
    }

    private boolean isImeiValid() {
        return !etImeiNumber.getText().toString().isEmpty();// TODO: Add IMEI check
    }

    @Override
    public void onClick(final View _view) {
        if (_view.getId() == R.id.hvSendImeiCode_FMV){
            hideKeyboard(etImeiNumber);
            if (isImeiValid()) {
                searchDeviceByImei();
            } else {
                Toast.makeText(getActivity(), "Please set IMEI code", Toast.LENGTH_SHORT).show();
            }
        } else {
            if (isCameraAvailable()) {
                startScanner();
            } else {
                Toast.makeText(getActivity(), getString(R.string.str_camera_is_not_available), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void startScanner() {
        startActivityForResult(ScannerActivity.getStartIntent(getActivity()), CODE_SCANNER_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CODE_SCANNER_REQUEST && resultCode == Activity.RESULT_OK) {
            String text = data.getStringExtra(C.KEY_SCANNER_RESULT_TEXT);
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
                dissmissLoaderDialog();
                dissmissLoaderOverlay(MobileVerificationFragment.this);
                if (result != null) {
                    mSelectListener.onDeviceSelect(result);
                }
            }
            getSpiceManager().removeDataFromCache(SearchDeviceResponseModel.List.class, KEY_SEARCH_DEVICE_BY_IMEI_REQUEST);
        }

        @Override
        public void onRequestFailure(SpiceException spiceException) {
            processError(spiceException);
            getSpiceManager().removeDataFromCache(SearchDeviceResponseModel.List.class, KEY_SEARCH_DEVICE_BY_IMEI_REQUEST);
        }
    }

    private boolean isCameraAvailable() {
        return getActivity().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA);
    }


}
