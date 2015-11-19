package com.uae.tra_smart_services.fragment;

import android.Manifest;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.PendingRequestListener;
import com.uae.tra_smart_services.R;
import com.uae.tra_smart_services.activity.ScannerActivity;
import com.uae.tra_smart_services.customviews.HexagonView;
import com.uae.tra_smart_services.customviews.LoaderView;
import com.uae.tra_smart_services.dialog.AlertDialogFragment.OnOkListener;
import com.uae.tra_smart_services.entities.Permission;
import com.uae.tra_smart_services.fragment.base.BaseServiceFragment;
import com.uae.tra_smart_services.global.C;
import com.uae.tra_smart_services.global.Service;
import com.uae.tra_smart_services.interfaces.Loader;
import com.uae.tra_smart_services.interfaces.OnOpenPermissionExplanationDialogListener;
import com.uae.tra_smart_services.manager.PermissionManager;
import com.uae.tra_smart_services.manager.PermissionManager.OnPermissionRequestSuccessListener;
import com.uae.tra_smart_services.rest.model.response.SearchDeviceResponseModel;
import com.uae.tra_smart_services.rest.robo_requests.SearchByImeiRequest;
import com.uae.tra_smart_services.util.ImageUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mobimaks on 13.08.2015.
 */
public class MobileVerificationFragment extends BaseServiceFragment
        implements OnClickListener, OnOpenPermissionExplanationDialogListener, OnOkListener, OnPermissionRequestSuccessListener {

    private static final String KEY_SEARCH_DEVICE_BY_IMEI_REQUEST = "SEARCH_DEVICE_BY_IMEI_REQUEST";
    private static final int CODE_SCANNER_REQUEST = 1;
    private static final int CAMERA_PERMISSION_REQUEST_CODE = 0;
    private static final List<Permission> SCAN_IMEI_PERMISSIONS_LIST = new ArrayList<>(1);

    static {
        SCAN_IMEI_PERMISSIONS_LIST.add(
                new Permission(Manifest.permission.CAMERA, R.string.fragment_mobile_verification_camera_permission_explanation));
    }

    private HexagonView hvSendImeiCode;
    private TextView tvSendImeiCode;
    private Button hvObtainOwnIMEI;
    private ImageView ivCameraBtn;
    private EditText etImeiNumber;

    private OnDeviceVerifiedListener mSelectListener;
    private RequestResponseListener mRequestListener;
    private SearchByImeiRequest mRequest;

    private PermissionManager mCameraPermissionManager;

    public static MobileVerificationFragment newInstance() {
        return new MobileVerificationFragment();
    }

    @Override
    public void onAttach(Activity _activity) {
        super.onAttach(_activity);
        mSelectListener = (OnDeviceVerifiedListener) _activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        mCameraPermissionManager = new PermissionManager(getActivity(), SCAN_IMEI_PERMISSIONS_LIST, this);
    }

    @Override
    protected void initViews() {
        super.initViews();
        ivCameraBtn = findView(R.id.ivCameraBtn_FMV);
        etImeiNumber = findView(R.id.etImeiNumber_FMV);
        hvObtainOwnIMEI = findView(R.id.btnMyImei_FMV);
        hvSendImeiCode = findView(R.id.hvSendImeiCode_FMV);
        tvSendImeiCode = findView(R.id.tvSendImeiCode_FMV);
        hvSendImeiCode.setHexagonSrcDrawable(ImageUtils.getFilteredDrawable(getActivity(), ContextCompat.getDrawable(getActivity(), R.drawable.ic_check_ver_grn)));
    }

    @Override
    protected void initListeners() {
        super.initListeners();
        mRequestListener = new RequestResponseListener();
        ivCameraBtn.setOnClickListener(this);
        hvSendImeiCode.setOnClickListener(this);
        tvSendImeiCode.setOnClickListener(this);
        hvObtainOwnIMEI.setOnClickListener(this);
        mCameraPermissionManager.setRequestSuccessListener(this);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            mCameraPermissionManager.onRestoreInstanceState(savedInstanceState);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
//        getSpiceManager().getFromCache(SearchDeviceResponseModel.List.class, KEY_SEARCH_DEVICE_BY_IMEI_REQUEST, DurationInMillis.ALWAYS_RETURNED, mRequestListener);
        getSpiceManager().addListenerIfPending(SearchDeviceResponseModel.List.class, KEY_SEARCH_DEVICE_BY_IMEI_REQUEST, mRequestListener);
    }

    private void searchDeviceByImei() {
        mRequest = new SearchByImeiRequest(etImeiNumber.getText().toString());
        loaderOverlayShow(getString(R.string.str_sending), this);
        loaderOverlayButtonBehavior(new Loader.BackButton() {
            @Override
            public void onBackButtonPressed(LoaderView.State _currentState) {
                getFragmentManager().popBackStack();
                if (_currentState == LoaderView.State.FAILURE || _currentState == LoaderView.State.SUCCESS) {
                    getFragmentManager().popBackStack();
                }
            }
        });
        getSpiceManager().execute(mRequest, KEY_SEARCH_DEVICE_BY_IMEI_REQUEST, DurationInMillis.ALWAYS_EXPIRED, mRequestListener);
    }

    @Override
    public void onLoadingCanceled() {
        if (getSpiceManager().isStarted() && mRequest != null) {
            getSpiceManager().cancel(mRequest);
        }
    }

    private boolean isImeiValid() {
        final String imeiText = etImeiNumber.getText().toString();
        return imeiText.length() == 15 && TextUtils.isDigitsOnly(imeiText);// TODO: Add IMEI check
    }

    @Override
    public void onClick(final View _view) {
        switch(_view.getId()){
            case R.id.tvSendImeiCode_FMV:
            case R.id.hvSendImeiCode_FMV:{
                hideKeyboard(etImeiNumber);
                if (isImeiValid()) {
                    searchDeviceByImei();
                } else {
                    Toast.makeText(getActivity(), R.string.enter_valid_imei_code, C.TOAST_LENGTH).show();
                }
            } break;
            case R.id.ivCameraBtn_FMV:{
                openImeiScannerIfCan();
            } break;
            case R.id.btnMyImei_FMV:{
                String deviceID = ((TelephonyManager) getActivity().getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
                if(deviceID != null){
                    etImeiNumber.setText(deviceID);
                } else {
                    Toast.makeText(getActivity(), "There is no registered radio module", Toast.LENGTH_SHORT).show();
                }
            } break;
        }
    }

    private void openImeiScannerIfCan() {
        if (isCameraAvailable()) {
            if (mCameraPermissionManager.isAllPermissionsChecked()) {
                startScanner();
            } else {
                mCameraPermissionManager.requestUncheckedPermissions(this, CAMERA_PERMISSION_REQUEST_CODE);
            }
        } else {
            Toast.makeText(getActivity(), getString(R.string.str_camera_is_not_available), C.TOAST_LENGTH).show();
        }
    }

    @Override
    public final void onOpenPermissionExplanationDialog(String _explanation) {
        showMessage(_explanation);
    }

    @Override
    public final void onOkPressed(int _messageId) {
        mCameraPermissionManager.onConfirmPermissionExplanationDialog(this, CAMERA_PERMISSION_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] _permissions, @NonNull int[] _grantResults) {
        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE) {
            if (!mCameraPermissionManager.onRequestPermissionsResult(this, _permissions, _grantResults)) {
                super.onRequestPermissionsResult(requestCode, _permissions, _grantResults);
            }
        } else {
            super.onRequestPermissionsResult(requestCode, _permissions, _grantResults);
        }
    }

    @Override
    public final void onPermissionRequestSuccess(Fragment _fragment) {
        startScanner();
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

    @Override
    public void onSaveInstanceState(Bundle outState) {
        mCameraPermissionManager.onSaveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onDestroy() {
        mCameraPermissionManager = null;
        super.onDestroy();
    }

    private class RequestResponseListener implements PendingRequestListener<SearchDeviceResponseModel.List> {

        @Override
        public void onRequestNotFound() {
            loaderOverlayCancelled(getString(R.string.str_something_went_wrong));
        }

        @Override
        public void onRequestSuccess(final SearchDeviceResponseModel.List result) {
            if (isAdded()) {
                if (result != null && result.size() != 0) {
                    loaderOverlayDismissWithAction(new Loader.Dismiss() {
                        @Override
                        public void onLoadingDismissed() {
                            getFragmentManager().popBackStack();
                            mSelectListener.onDeviceVerified(result);
                            getSpiceManager().removeDataFromCache(SearchDeviceResponseModel.List.class, KEY_SEARCH_DEVICE_BY_IMEI_REQUEST);
                        }
                    });
                } else {
                    loaderOverlayCancelled(getString(R.string.str_no_data_found));
                }
            }
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

    public interface OnDeviceVerifiedListener {
        void onDeviceVerified(final SearchDeviceResponseModel.List _device);
    }

    @Nullable
    @Override
    protected Service getServiceType() {
        return Service.MOBILE_VERIFICATION;
    }

    @Override
    protected String getServiceName() {
        return "Search Device By Imei";
    }

    @Override
    protected int getTitle() {
        return R.string.str_mobile_verification;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_mobile_verification;
    }
}
