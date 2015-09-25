package com.uae.tra_smart_services.customviews;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.uae.tra_smart_services.R;
import com.uae.tra_smart_services.global.Service;
import com.uae.tra_smart_services.rest.model.request.RatingServiceRequestModel;
import com.uae.tra_smart_services.rest.model.response.RatingServiceResponseModel;
import com.uae.tra_smart_services.rest.robo_requests.RatingServiceRequest;

/**
 * Created by Andrey Korneychuk on 14.09.15.
 */
public class DomainServiceRatingView extends RelativeLayout implements RadioGroup.OnCheckedChangeListener{

    private RadioGroup ratingGroup;
    private CallBacks mCallBacks;
    public DomainServiceRatingView(Context context) {
        super(context);
    }

    public DomainServiceRatingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        inflate(getContext(), R.layout.layout_domain_service_rating, this);
    }

    public void init(CallBacks _callBacks){
        mCallBacks = _callBacks;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        inflate(getContext(), R.layout.layout_domain_service_rating, this);
        initViews();
    }

    private void initViews(){
        ratingGroup = (RadioGroup) findViewById(R.id.rgDomainCheckServiceRating_FDCH);
        ratingGroup.setOnCheckedChangeListener(this);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        RadioButton radioButton = (RadioButton) group.findViewById(checkedId);
        if(radioButton.isChecked()){
            int rating = Integer.valueOf(radioButton.getTag().toString());
            RatingServiceRequestModel model = new RatingServiceRequestModel();
            model.setServiceName(mCallBacks.getServiceType().toString());
            model.setRate(rating);
            executeCall(model, radioButton);
        }
    }

    private void executeCall(final RatingServiceRequestModel _model, final RadioButton _radio){
        mCallBacks.preExecuteCall();
        mCallBacks.getPublicSpiceManager().execute(
                new RatingServiceRequest(_model),
                new RequestListener<RatingServiceResponseModel>() {
                    @Override
                    public void onRequestFailure(SpiceException spiceException) {
                        _radio.setChecked(false);
                        mCallBacks.onRatedError(spiceException);
                    }

                    @Override
                    public void onRequestSuccess(RatingServiceResponseModel response) {
                        mCallBacks.postExecuteCall();
                        switch (response.getStatus()) {
                            case 201:
                                mCallBacks.onRatedSuccessfully();
                                break;
                            case 400:
                                mCallBacks.onRatedUnSuccessfully();
                                _radio.setChecked(false);
                                break;
                        }
                    }
                }
        );
    }

    public interface CallBacks{
        Service getServiceType();
        SpiceManager getPublicSpiceManager();
        void preExecuteCall();
        void postExecuteCall();
        void onRatedSuccessfully();
        void onRatedUnSuccessfully();
        void onRatedError(SpiceException _spiceException);
    }
}
