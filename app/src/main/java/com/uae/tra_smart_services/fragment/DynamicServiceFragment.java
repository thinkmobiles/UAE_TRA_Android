package com.uae.tra_smart_services.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.uae.tra_smart_services.R;
import com.uae.tra_smart_services.entities.dynamic_service.BaseInputItem;
import com.uae.tra_smart_services.entities.dynamic_service.DynamicService;
import com.uae.tra_smart_services.fragment.base.BaseFragment;
import com.uae.tra_smart_services.rest.model.response.DynamicServiceInfoResponseModel;
import com.uae.tra_smart_services.rest.robo_requests.DynamicServiceRequest;

import java.util.ArrayList;

/**
 * Created by mobimaks on 21.10.2015.
 */
public final class DynamicServiceFragment extends BaseFragment {

    //region Const
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
        final ArrayList<BaseInputItem> inputItems = mDynamicService.inputItems;
        final int count = inputItems.size();
        for (int i = 0; i < count; i++) {
            final BaseInputItem inputItem = inputItems.get(i);
            llContainer.addView(inputItem.getView(inflater, llContainer));
        }
    }

    @Override
    public final void onStart() {
        super.onStart();
        getSpiceManager().getFromCache(DynamicService.class, KEY_DYNAMIC_SERVICE_REQUEST,
                DurationInMillis.ALWAYS_RETURNED, mServiceRequestListener);
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
