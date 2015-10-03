package com.uae.tra_smart_services.fragment;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
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
import com.uae.tra_smart_services.global.Service;
import com.uae.tra_smart_services.interfaces.Loader;
import com.uae.tra_smart_services.rest.model.response.SearchDeviceResponseModel;
import com.uae.tra_smart_services.rest.robo_requests.SearchByImeiRequest;
import com.uae.tra_smart_services.util.ImageUtils;

/**
 * Created by mobimaks on 13.08.2015.
 */
public class MobileVerificationFragment extends BaseServiceFragment implements OnClickListener {

    private static final String KEY_SEARCH_DEVICE_BY_IMEI_REQUEST = "SEARCH_DEVICE_BY_IMEI_REQUEST";
    private static final int CODE_SCANNER_REQUEST = 1;

    private ImageView ivCameraBtn;
    private EditText etImeiNumber;
    private RequestResponseListener mRequestListener;
    private OnDeviceVerifiedListener mSelectListener;
    private HexagonView hvSendImeiCode;

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
        hvSendImeiCode.setHexagonSrcDrawable(ImageUtils.getFilteredDrawable(getActivity(), ContextCompat.getDrawable(getActivity(), R.drawable.ic_check_ver_grn)));
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

    @Nullable
    @Override
    protected Service getServiceType() {
        return Service.MOBILE_VERIFICATION;
    }

    @Override
    protected String getServiceName() {
        return "Search Device By Imei";
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
}
