package com.uae.tra_smart_services.rest.request;

import com.octo.android.robospice.request.retrofit.RetrofitSpiceRequest;
import com.uae.tra_smart_services.rest.ServicesAPI;
import com.uae.tra_smart_services.rest.model.request.Rating;
import com.uae.tra_smart_services.rest.model.responce.Status;

/**
 * Created by mobimaks on 31.07.2015.
 */
public class RateRequest extends RetrofitSpiceRequest<Status, ServicesAPI> {

    private String mToken;
    private Rating mRating;

    public RateRequest(String token, Rating rating) {
        super(Status.class, ServicesAPI.class);
        mToken = token;
        mRating = rating;
    }

    @Override
    public Status loadDataFromNetwork() throws Exception {
        return getService().rateService(mToken, mRating);
    }
}
