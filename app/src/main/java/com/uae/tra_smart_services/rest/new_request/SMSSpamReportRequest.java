package com.uae.tra_smart_services.rest.new_request;

import com.octo.android.robospice.request.retrofit.RetrofitSpiceRequest;
import com.uae.tra_smart_services.rest.TRAServicesAPI;
import com.uae.tra_smart_services.rest.model.new_request.SmsSpamModel;

import retrofit.client.Response;

/**
 * Created by Vitaliy on 13/08/2015.
 */
public class SMSSpamReportRequest extends RetrofitSpiceRequest<Response, TRAServicesAPI> {

    private SmsSpamModel mSmsSpamReportModel;

    public SMSSpamReportRequest(final SmsSpamModel _smsSpamModel) {
        super(Response.class, TRAServicesAPI.class);
        mSmsSpamReportModel = _smsSpamModel;
    }

    @Override
    public final Response loadDataFromNetwork() throws Exception {
        return getService().reportSmsSpam(mSmsSpamReportModel);
    }
}
