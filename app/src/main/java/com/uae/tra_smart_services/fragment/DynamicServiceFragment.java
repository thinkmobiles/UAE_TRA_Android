package com.uae.tra_smart_services.fragment;

import android.os.Bundle;
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
import com.uae.tra_smart_services.entities.dynamic_service.BaseInputItem;
import com.uae.tra_smart_services.entities.dynamic_service.DynamicService;
import com.uae.tra_smart_services.fragment.base.BaseFragment;
import com.uae.tra_smart_services.global.C.HttpMethod;
import com.uae.tra_smart_services.rest.DynamicServicesApi;
import com.uae.tra_smart_services.rest.model.response.DynamicServiceInfoResponseModel;
import com.uae.tra_smart_services.rest.robo_requests.BaseRequest;
import com.uae.tra_smart_services.rest.robo_requests.DynamicServiceGetRequest;
import com.uae.tra_smart_services.rest.robo_requests.DynamicServicePostRequest;
import com.uae.tra_smart_services.rest.robo_requests.DynamicServiceRequest;

import retrofit.client.Response;

/**
 * Created by mobimaks on 21.10.2015.
 */
public final class DynamicServiceFragment extends BaseFragment implements OnClickListener {

    //region Const
    private static final String KEY_DYNAMIC_REQUEST = "DYNAMIC_REQUEST";
    private static final String KEY_SERVICE_INFO = "SERVICE_INFO";
    private static final String KEY_DYNAMIC_SERVICE_REQUEST = "DYNAMIC_SERVICE_REQUEST";
    private static final String KEY_DYNAMIC_SERVICE = "DYNAMIC_SERVICE";
    //endregion

    //region Views
    private LinearLayout llContainer;
    //endregion

    //region Variables
    private DynamicServiceInfoResponseModel mServiceInfo;
    private DynamicServiceRequestListener mServiceRequestListener;
    private DynamicRequestListener mDynamicRequestListener;

    private DynamicService mDynamicService;
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
    public final void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            mDynamicService = savedInstanceState.getParcelable(KEY_DYNAMIC_SERVICE);
        }
        if (mDynamicService == null) {
            loaderDialogShow();
            loadDynamicServiceInfo();
        } else {
            initDynamicViews();
        }
    }

    private void loadDynamicServiceInfo() {
        final DynamicServiceRequest request = new DynamicServiceRequest(mServiceInfo.id);
        getSpiceManager().execute(request, KEY_DYNAMIC_SERVICE_REQUEST,
                DurationInMillis.ALWAYS_EXPIRED, mServiceRequestListener);
    }

    private void initDynamicViews() {
        toolbarTitleManager.setTitle(mDynamicService.serviceName);

        final LayoutInflater inflater = LayoutInflater.from(getActivity());
        for (final BaseInputItem inputItem : mDynamicService.inputItems) {
            llContainer.addView(inputItem.getView(inflater, llContainer));
        }

        final Button button = (Button) inflater.inflate(R.layout.input_item_button, llContainer, false);
        button.setText(mDynamicService.buttonText);
        button.setOnClickListener(this);
        llContainer.addView(button);
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
            final BaseRequest<Response, DynamicServicesApi> request;
            if (HttpMethod.GET.equals(mDynamicService.method)) {
                request = new DynamicServiceGetRequest(mDynamicService.url, null);
            } else {
                request = new DynamicServicePostRequest(mDynamicService.url);
            }
            loaderDialogShow();
            getDynamicSpiceManager().execute(request, KEY_DYNAMIC_REQUEST, DurationInMillis.ALWAYS_EXPIRED, mDynamicRequestListener);
        } else {
            Toast.makeText(getActivity(), "Invalid data", Toast.LENGTH_SHORT).show();
        }
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
