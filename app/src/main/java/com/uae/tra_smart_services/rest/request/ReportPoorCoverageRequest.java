package com.uae.tra_smart_services.rest.request;

import com.octo.android.robospice.request.retrofit.RetrofitSpiceRequest;
import com.uae.tra_smart_services.rest.ServicesAPI;
import com.uae.tra_smart_services.rest.model.request.CoverageRequest;
import com.uae.tra_smart_services.rest.model.responce.Status;

/**
 * Created by mobimaks on 31.07.2015.
 */
public class ReportPoorCoverageRequest extends RetrofitSpiceRequest<Status, ServicesAPI> {

    private String mToken;
    private CoverageRequest mCoverageRequest;

    public ReportPoorCoverageRequest(String token, CoverageRequest coverageRequest) {
        super(Status.class, ServicesAPI.class);
        mToken = token;
        mCoverageRequest = coverageRequest;
    }

    @Override
    public Status loadDataFromNetwork() throws Exception {
        return getService().reportPoorCoverage(mToken, mCoverageRequest);
    }
}
