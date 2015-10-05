package com.uae.tra_smart_services.fragment.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.uae.tra_smart_services.R;
import com.uae.tra_smart_services.customviews.LoaderView;
import com.uae.tra_smart_services.customviews.ServiceRatingView;
import com.uae.tra_smart_services.global.Service;
import com.uae.tra_smart_services.interfaces.Loader.BackButton;
import com.uae.tra_smart_services.interfaces.Loader.Cancelled;
import com.uae.tra_smart_services.interfaces.Loader.Dismiss;
import com.uae.tra_smart_services.interfaces.OpenServiceInfo;
import com.uae.tra_smart_services.rest.model.request.RatingServiceRequestModel;
import com.uae.tra_smart_services.rest.model.response.RatingServiceResponseModel;
import com.uae.tra_smart_services.rest.model.response.ServiceInfoResponse;
import com.uae.tra_smart_services.rest.model.response.UserProfileResponseModel;
import com.uae.tra_smart_services.rest.robo_requests.RatingServiceRequest;
import com.uae.tra_smart_services.rest.robo_requests.ServiceInfoRequest;

/**
 * Created by ak-buffalo on 27.08.15.
 */
public abstract class BaseServiceFragment extends BaseFragment implements Cancelled, ServiceRatingView.CallBacks {

    private static final String KEY_SERVICE_INFO_REQUEST = "SERVICE_INFO_REQUEST" + BaseServiceFragment.class.getSimpleName() ;
    private static final String KEY_SERVICE_INFO_MODEL = "SERVICE_INFO_MODEL";

    private OpenServiceInfo mOpenServiceInfoListener;

    private ServiceInfoRequestListener mInfoRequestListener;
    private ServiceInfoRequest mServiceInfoRequest;
    private ServiceInfoResponse mServiceInfo;

    @CallSuper
    @Override
    public void onAttach(final Activity _activity) {
        super.onAttach(_activity);
        if (_activity instanceof OpenServiceInfo) {
            mOpenServiceInfoListener = (OpenServiceInfo) _activity;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if (savedInstanceState != null) {
            mServiceInfo = savedInstanceState.getParcelable(KEY_SERVICE_INFO_MODEL);
        }
    }

    @CallSuper
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        if (getServiceType() != null) {
            inflater.inflate(R.menu.menu_info, menu);
        }
        super.onCreateOptionsMenu(menu, inflater);
    }

    @CallSuper
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_show_info:
                hideKeyboard(getView());
                loadServiceInfo();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void loadServiceInfo() {
        final Service service;
        final String serviceName;
        if (mServiceInfo != null && mOpenServiceInfoListener != null) {
            mOpenServiceInfoListener.onOpenServiceInfo(mServiceInfo);
        } else if ((service = getServiceType()) != null && (serviceName = service.getServiceName()) != null) {

            loaderOverlayShow(getString(R.string.str_loading), mInfoRequestListener);
            loaderOverlayButtonBehavior(mInfoRequestListener);

            mServiceInfoRequest = new ServiceInfoRequest(serviceName, getResources().getConfiguration().locale.toString());

            getSpiceManager().execute(mServiceInfoRequest, KEY_SERVICE_INFO_REQUEST,
                    DurationInMillis.ALWAYS_EXPIRED, mInfoRequestListener);
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mInfoRequestListener = new ServiceInfoRequestListener();
    }

    @Override
    public void onStart() {
        super.onStart();
        getSpiceManager().getFromCache(ServiceInfoResponse.class, KEY_SERVICE_INFO_REQUEST,
                DurationInMillis.ALWAYS_RETURNED, mInfoRequestListener);
    }

    @Override
    public void onRate(int _rate) {
        final String[] rateNames = getResources().getStringArray(R.array.rate_names);
        getFragmentManager().popBackStackImmediate();
        sendRating(new RatingServiceRequestModel(getServiceName(), _rate, rateNames[_rate - 1]));
    }

    private void sendRating(RatingServiceRequestModel _model) {
        getSpiceManager().execute(
                new RatingServiceRequest(_model),
                new RequestListener<RatingServiceResponseModel>() {
                    @Override
                    public void onRequestFailure(SpiceException spiceException) {
                        // Unimplemented method
                    }

                    @Override
                    public void onRequestSuccess(RatingServiceResponseModel response) {
                        // Unimplemented method
                    }
                }
        );
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(KEY_SERVICE_INFO_MODEL, mServiceInfo);
        super.onSaveInstanceState(outState);
    }

    @CallSuper
    @Override
    public void onDetach() {
        mOpenServiceInfoListener = null;
        super.onDetach();
    }

    private class ServiceInfoRequestListener implements RequestListener<ServiceInfoResponse>, BackButton, Cancelled, Dismiss {

        @Override
        public void onRequestSuccess(ServiceInfoResponse result) {
            getSpiceManager().removeDataFromCache(ServiceInfoResponse.class, KEY_SERVICE_INFO_REQUEST);
            if (isAdded() && result != null) {
                mServiceInfo = result;
                loaderOverlayDismissWithAction(this);
            }
        }

        @Override
        public void onBackButtonPressed(LoaderView.State _currentState) {
            getFragmentManager().popBackStack();
        }

        @Override
        public void onLoadingCanceled() {
            if (getSpiceManager().isStarted()) {
                getSpiceManager().cancel(mServiceInfoRequest);
                getSpiceManager().removeDataFromCache(UserProfileResponseModel.class, KEY_SERVICE_INFO_REQUEST);
            }
        }

        @Override
        public void onLoadingDismissed() {
            getFragmentManager().popBackStack();
            if (mOpenServiceInfoListener != null) {
                mOpenServiceInfoListener.onOpenServiceInfo(mServiceInfo);
            }
        }

        @Override
        public void onRequestFailure(SpiceException spiceException) {
            getSpiceManager().removeDataFromCache(UserProfileResponseModel.class, KEY_SERVICE_INFO_REQUEST);
            processError(spiceException);
        }
    }

    @Nullable
    protected abstract Service getServiceType();

    protected abstract String getServiceName();
}
