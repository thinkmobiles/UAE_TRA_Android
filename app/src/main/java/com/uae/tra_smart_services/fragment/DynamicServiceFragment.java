package com.uae.tra_smart_services.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.uae.tra_smart_services.R;
import com.uae.tra_smart_services.dialog.AlertDialogFragment.OnOkListener;
import com.uae.tra_smart_services.dialog.AttachmentPickerDialog.OnImageSourceSelectListener;
import com.uae.tra_smart_services.entities.AttachmentManager;
import com.uae.tra_smart_services.entities.AttachmentManager.OnImageGetCallback;
import com.uae.tra_smart_services.entities.dynamic_service.BaseInputItem;
import com.uae.tra_smart_services.entities.dynamic_service.DynamicService;
import com.uae.tra_smart_services.entities.dynamic_service.InputItemsPage;
import com.uae.tra_smart_services.entities.dynamic_service.input_item.AttachmentInputItem;
import com.uae.tra_smart_services.fragment.base.BaseFragment;
import com.uae.tra_smart_services.global.AttachmentOption;
import com.uae.tra_smart_services.interfaces.OnOpenAttachmentPickerListener;
import com.uae.tra_smart_services.interfaces.OnOpenPermissionExplanationDialogListener;
import com.uae.tra_smart_services.rest.model.response.DynamicServiceInfoResponseModel;
import com.uae.tra_smart_services.rest.robo_requests.DynamicServiceDetailsRequest;
import com.uae.tra_smart_services.rest.robo_requests.DynamicServiceRequest;

import retrofit.client.Response;

/**
 * Created by mobimaks on 21.10.2015.
 */
public final class DynamicServiceFragment extends BaseFragment
        implements OnClickListener, OnOpenAttachmentPickerListener, OnImageGetCallback,
        OnOpenPermissionExplanationDialogListener, OnOkListener, OnImageSourceSelectListener {

    //region Const
    private static final String KEY_DYNAMIC_REQUEST = "DYNAMIC_REQUEST";
    private static final String KEY_SERVICE_INFO = "SERVICE_INFO";
    private static final String KEY_DYNAMIC_SERVICE_REQUEST = "DYNAMIC_SERVICE_REQUEST";
    private static final String KEY_DYNAMIC_SERVICE = "DYNAMIC_SERVICE";

    private static final String KEY_ATTACHMENT_CALLER_ID = "ATTACHMENT_CALLER_ID";
    //endregion

    //region Views
    private LinearLayout llContainer;
    //endregion

    //region Variables
    private DynamicServiceInfoResponseModel mServiceInfo;
    private DynamicServiceRequestListener mServiceRequestListener;
    private DynamicRequestListener mDynamicRequestListener;

    private DynamicService mDynamicService;
    private AttachmentManager mAttachmentManager;
    private String mAttachmentCallerId;
    private boolean mIsServiceLoaded;
    //endregion

    public static DynamicServiceFragment newInstance(final DynamicServiceInfoResponseModel _serviceInfo) {
        final DynamicServiceFragment fragment = new DynamicServiceFragment();
        final Bundle args = new Bundle();
        args.putParcelable(KEY_SERVICE_INFO, _serviceInfo);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(final Bundle _savedInstanceState) {
        super.onCreate(_savedInstanceState);
        mAttachmentManager = new AttachmentManager(getActivity(), this, this);

        final Bundle args = getArguments();
        mServiceInfo = args.getParcelable(KEY_SERVICE_INFO);
    }

    @Override
    protected final void initData() {
        super.initData();
        mServiceRequestListener = new DynamicServiceRequestListener();
        mDynamicRequestListener = new DynamicRequestListener();
    }

    @Override
    protected final void initViews() {
        super.initViews();
        llContainer = findView(R.id.llContainer_FDS);
    }

    @Override
    public final void onActivityCreated(final Bundle _savedInstanceState) {
        super.onActivityCreated(_savedInstanceState);

        if (_savedInstanceState != null) {
            mAttachmentManager.onRestoreInstanceState(_savedInstanceState);
            mAttachmentCallerId = _savedInstanceState.getString(KEY_ATTACHMENT_CALLER_ID, "");
            mIsServiceLoaded = _savedInstanceState.getBoolean(KEY_DYNAMIC_SERVICE);
        } else {
            mIsServiceLoaded = false;
        }

        if (mIsServiceLoaded) {
            mDynamicService = new DynamicService();
            mDynamicService.onRestoreInstanceState(_savedInstanceState);
            initDynamicViews();
        } else {
            loaderDialogShow();
            loadDynamicServiceInfo();
        }
    }

    private void loadDynamicServiceInfo() {
        final DynamicServiceDetailsRequest request = new DynamicServiceDetailsRequest(mServiceInfo.id);
        getSpiceManager().execute(request, KEY_DYNAMIC_SERVICE_REQUEST,
                DurationInMillis.ALWAYS_EXPIRED, mServiceRequestListener);
    }

    private void initDynamicViews() {
        toolbarTitleManager.setTitle(mDynamicService.serviceName);

        final LayoutInflater inflater = LayoutInflater.from(getActivity());
        for (final BaseInputItem inputItem : mDynamicService.pages.get(0).inputItems) {//TODO: add pagination
            llContainer.addView(inputItem.getView(inflater, llContainer));
            if (inputItem.isAttachmentItem()) {
                ((AttachmentInputItem) inputItem).setAttachmentCallback(this);
            }
        }

        final Button button = (Button) inflater.inflate(R.layout.input_item_button, llContainer, false);
        button.setText(mDynamicService.buttonText);
        button.setOnClickListener(this);
        llContainer.addView(button);
    }

    @Override
    public final void onOpenAttachmentPicker(final AttachmentInputItem _inputItem) {
        mAttachmentCallerId = _inputItem.getId();
        mAttachmentManager.openDefaultPicker(getActivity(), this, _inputItem.getAttachmentUri() != null);
    }

    @Override
    public final void onImageSourceSelect(AttachmentOption _option) {
        switch (_option) {
            case GALLERY:
                mAttachmentManager.openGallery(this);
                break;
            case CAMERA:
                mAttachmentManager.tryOpenCamera(this);
                break;
            case DELETE_ATTACHMENT:
                mAttachmentManager.clearAttachment();
                notifyInputItemDataChanged(null);
                break;
        }
    }

    @CallSuper
    @Override
    public void onRequestPermissionsResult(int _requestCode, @NonNull String[] _permissions, @NonNull int[] _grantResults) {
        if (!mAttachmentManager.onRequestPermissionsResult(this, _requestCode, _permissions, _grantResults)) {
            super.onRequestPermissionsResult(_requestCode, _permissions, _grantResults);
        }
    }

    @Override
    public void onOpenPermissionExplanationDialog(String _explanation) {
        showMessage(_explanation);
    }

    @Override
    public void onOkPressed(int _messageId) {
        mAttachmentManager.onConfirmPermissionExplanationDialog(this);
    }

    @Override
    public void onAttachmentGet(@NonNull Uri _imageUri) {
        mAttachmentManager.clearAttachment();
        notifyInputItemDataChanged(_imageUri);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mAttachmentManager.onActivityResult(requestCode, resultCode, data);
    }

    private void notifyInputItemDataChanged(@Nullable Uri _imageUri) {
        if (mDynamicService != null) {
            searchAttachmentCaller:
            for (InputItemsPage page : mDynamicService.pages) {
                for (BaseInputItem inputItem : page.inputItems) {
                    if (inputItem.getId().equals(mAttachmentCallerId)) {
                        ((AttachmentInputItem) inputItem).onAttachmentStateChanged(_imageUri);
                        break searchAttachmentCaller;
                    }
                }
            }
        }
        mAttachmentCallerId = null;
    }

    @Override
    public final void onStart() {
        super.onStart();
        getSpiceManager().getFromCache(DynamicService.class, KEY_DYNAMIC_SERVICE_REQUEST,
                DurationInMillis.ALWAYS_RETURNED, mServiceRequestListener);

        getDynamicSpiceManager().getFromCache(Response.class, KEY_DYNAMIC_REQUEST,
                DurationInMillis.ALWAYS_RETURNED, mDynamicRequestListener);
    }

    @Override
    public final void onClick(final View _view) {
        switch (_view.getId()) {
            case R.id.btnSubmit_IIB:
                validateAndSendData();
                break;
        }
    }

    private void validateAndSendData() {
        hideKeyboard(getView());
        if (mDynamicService.isDataValid()) {
            final DynamicServiceRequest request = new DynamicServiceRequest(mDynamicService);
            loaderDialogShow();
            getDynamicSpiceManager().execute(request, KEY_DYNAMIC_REQUEST, DurationInMillis.ALWAYS_EXPIRED, mDynamicRequestListener);
        } else {
            Toast.makeText(getActivity(), "Invalid data", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public final void onSaveInstanceState(final Bundle _outState) {
        if (mDynamicService != null) {
            mDynamicService.onSaveInstanceState(_outState);
        }
        if (mAttachmentCallerId != null) {
            _outState.putString(KEY_ATTACHMENT_CALLER_ID, mAttachmentCallerId);
        }
        mAttachmentManager.onSaveInstanceState(_outState);
        _outState.putBoolean(KEY_DYNAMIC_SERVICE, mIsServiceLoaded);
        super.onSaveInstanceState(_outState);
    }

    @Override
    public void onDestroy() {
        mAttachmentManager = null;
        super.onDestroy();
    }

    private final class DynamicRequestListener implements RequestListener<Response> {

        @Override
        public void onRequestSuccess(Response result) {
            getDynamicSpiceManager().removeDataFromCache(Response.class, KEY_DYNAMIC_SERVICE);
            if (isAdded() && result != null) {
                loaderDialogDismiss();
                Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onRequestFailure(SpiceException spiceException) {
            getDynamicSpiceManager().removeDataFromCache(Response.class, KEY_DYNAMIC_SERVICE);
            if (isAdded()) {
                loaderDialogDismiss();
                Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
            }

        }

    }

    private final class DynamicServiceRequestListener implements RequestListener<DynamicService> {

        @Override
        public final void onRequestSuccess(final DynamicService _service) {
            getSpiceManager().removeDataFromCache(DynamicService.class, KEY_DYNAMIC_SERVICE_REQUEST);
            if (isAdded() && _service != null) {
                mDynamicService = _service;
                mIsServiceLoaded = true;
                initDynamicViews();
                loaderDialogDismiss();
            }
        }

        @Override
        public final void onRequestFailure(final SpiceException _spiceException) {
            getSpiceManager().removeDataFromCache(DynamicService.class, KEY_DYNAMIC_SERVICE_REQUEST);
            if (isAdded()) {
                loaderDialogDismiss();
            }
        }

    }

    @Override
    protected final int getTitle() {
        return 0;
    }

    @Override
    protected final int getLayoutResource() {
        return R.layout.fragment_dynamic_service;
    }

}
