package com.uae.tra_smart_services.rest.new_request;

import com.octo.android.robospice.request.retrofit.RetrofitSpiceRequest;
import com.uae.tra_smart_services.rest.TRAServicesAPI;
import com.uae.tra_smart_services.rest.model.new_request.SmsSpamRequestModel;
import com.uae.tra_smart_services.rest.model.new_response.SmsSpamResponseModel;

/**
 * Created by ak-buffalo on 14.08.15.
 */
public class SmsSpamRequest extends RetrofitSpiceRequest<SmsSpamResponseModel, TRAServicesAPI> {

    private SmsSpamRequestModel mSmsSpamReportModel;

    public SmsSpamRequest(final SmsSpamRequestModel _smsSpamModel) {
        super(SmsSpamResponseModel.class, TRAServicesAPI.class);
        mSmsSpamReportModel = _smsSpamModel;
    }

    @Override
    public final SmsSpamResponseModel loadDataFromNetwork() throws Exception {
        return getService().reportSmsSpam(mSmsSpamReportModel);
    }
}

