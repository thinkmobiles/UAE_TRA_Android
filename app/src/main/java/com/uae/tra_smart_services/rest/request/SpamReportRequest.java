package com.uae.tra_smart_services.rest.request;

import com.octo.android.robospice.request.retrofit.RetrofitSpiceRequest;
import com.uae.tra_smart_services.rest.ServicesAPI;
import com.uae.tra_smart_services.rest.model.request.SmsSpamReport;
import com.uae.tra_smart_services.rest.model.responce.Status;

/**
 * Created by mobimaks on 31.07.2015.
 */
public class SpamReportRequest extends RetrofitSpiceRequest<Status, ServicesAPI> {

    private String mToken;
    private SmsSpamReport mSpamReport;

    public SpamReportRequest(String token, SmsSpamReport spamReport) {
        super(Status.class, ServicesAPI.class);
        mToken = token;
        mSpamReport = spamReport;
    }

    @Override
    public Status loadDataFromNetwork() throws Exception {
        return getService().reportSmsSpam(mToken, mSpamReport);
    }
}