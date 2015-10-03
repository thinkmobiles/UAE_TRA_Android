package com.uae.tra_smart_services.fragment.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.uae.tra_smart_services.R;
import com.uae.tra_smart_services.customviews.LoaderView;
import com.uae.tra_smart_services.customviews.ServiceRatingView;
import com.uae.tra_smart_services.dialog.ServiceRatingDialog;
import com.uae.tra_smart_services.dialog.ServiceRatingDialog.CallBacks;
import com.uae.tra_smart_services.global.Service;
import com.uae.tra_smart_services.interfaces.Loader;
import com.uae.tra_smart_services.interfaces.Loader.Cancelled;
import com.uae.tra_smart_services.interfaces.OpenServiceInfo;
import com.uae.tra_smart_services.rest.model.request.RatingServiceRequestModel;
import com.uae.tra_smart_services.rest.model.response.RatingServiceResponseModel;
import com.uae.tra_smart_services.rest.robo_requests.RatingServiceRequest;

/**
 * Created by ak-buffalo on 27.08.15.
 */
public abstract class BaseServiceFragment extends BaseFragment implements Cancelled, CallBacks {

    private OpenServiceInfo mOpenServiceInfoListener;

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
    }

    @CallSuper
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        if (getServiceType() != null) {
            inflater.inflate(R.menu.menu_info, menu);
        }
        inflater.inflate(R.menu.menu_rate, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @CallSuper
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_show_info:
                hideKeyboard(getView());
                if (mOpenServiceInfoListener != null) {
                    mOpenServiceInfoListener.onOpenServiceInfo(getServiceType());
                }
                return true;
            case R.id.action_rate:
                hideKeyboard(getView());
                showRatingDialog();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Nullable
    protected abstract Service getServiceType();

    @Override
    public void onCancelPressed() {
        // Unimplemented method
        // Used exceptionally to specify Cancel button in dialog
    }

    @Override
    public void onRate(int _rate) {
        sendRating(new RatingServiceRequestModel(getServiceName(), _rate, "Good service, I like it.     q"));
    }

    private void sendRating(RatingServiceRequestModel _model) {
        loaderOverlayShow(getString(R.string.str_give_us_moment), BaseServiceFragment.this);
        loaderOverlayButtonBehavior(new Loader.BackButton() {
            @Override
            public void onBackButtonPressed(LoaderView.State _currentState) {
                getFragmentManager().popBackStack();
            }
        });

        getSpiceManager().execute(
                new RatingServiceRequest(_model),
                new RequestListener<RatingServiceResponseModel>() {
                    @Override
                    public void onRequestFailure(SpiceException spiceException) {
                        processError(spiceException);
                    }

                    @Override
                    public void onRequestSuccess(RatingServiceResponseModel response) {
                        switch (response.getStatus()) {
                            case 201:
                                loaderOverlaySuccess(getString(R.string.str_rating_has_sent));
                                break;
                            case 400:
                                loaderOverlayFailed(getString(R.string.str_something_went_wrong));
                                break;
                        }
                    }
                }
        );
    }

    private void showRatingDialog() {
        ServiceRatingDialog.newInstance(this)
                .setDialogBody(new ServiceRatingView(getActivity()))
                .show(getFragmentManager());
    }

    protected abstract String getServiceName();

    @CallSuper
    @Override
    public void onDetach() {
        mOpenServiceInfoListener = null;
        super.onDetach();
    }

}
