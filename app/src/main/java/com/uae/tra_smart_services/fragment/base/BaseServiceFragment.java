package com.uae.tra_smart_services.fragment.base;

import android.support.annotation.CallSuper;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.uae.tra_smart_services.R;
import com.uae.tra_smart_services.customviews.ServiceRatingView;
import com.uae.tra_smart_services.customviews.LoaderView;
import com.uae.tra_smart_services.dialog.ServiceRatingDialog;
import com.uae.tra_smart_services.interfaces.Loader;
import com.uae.tra_smart_services.rest.model.request.RatingServiceRequestModel;
import com.uae.tra_smart_services.rest.model.response.RatingServiceResponseModel;
import com.uae.tra_smart_services.rest.robo_requests.RatingServiceRequest;

/**
 * Created by ak-buffalo on 27.08.15.
 */
public abstract class BaseServiceFragment extends BaseFragment implements Loader.Cancelled, ServiceRatingDialog.CallBacks {

    @CallSuper
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_rate, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @CallSuper
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_rate) {
            hideKeyboard(getView());

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCancelPressed(){
        // Unimplemented method
        // Used exceptionally to specify Cancel button in dialog
    }

    @Override
    public void onRate(int _rate){
        sendRating(new RatingServiceRequestModel(getServiceName(), _rate));
    }

    private void sendRating(RatingServiceRequestModel _model){
        loaderOverlayShow(getString(R.string.str_give_us_moment), BaseServiceFragment.this);
        loaderOverlayButtonBehavior(new Loader.BackButton() {
            @Override
            public void onBackButtonPressed(LoaderView.State _currentState) {
                getFragmentManager().popBackStack();
                if(_currentState == LoaderView.State.FAILURE || _currentState == LoaderView.State.SUCCESS){

                }
            }
        });

        getSpiceManager().execute(
                new RatingServiceRequest(_model),
                new RequestListener<RatingServiceResponseModel>() {
                    @Override
                    public void onRequestFailure(SpiceException spiceException) {
//                        mCallBacks.onRatedError(spiceException);
                    }

                    @Override
                    public void onRequestSuccess(RatingServiceResponseModel response) {
                        switch (response.getStatus()) {
                            case 201:
//                                mCallBacks.onRatedSuccessfully();

                                break;
                            case 400:
//                                mCallBacks.onRatedUnSuccessfully();
                                break;
                        }
                    }
                }
        );
    }

    private void showRatingDialog(){
        ServiceRatingDialog.newInstance(this)
                .setDialogTitle(getString(R.string.str_rating))
                .setDialogBody(new ServiceRatingView(getActivity()))
                .show(getFragmentManager());
    }

    protected abstract String getServiceName();
}
