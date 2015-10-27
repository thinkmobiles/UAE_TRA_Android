package com.uae.tra_smart_services_cutter.rest.robo_requests;

import com.uae.tra_smart_services_cutter.rest.TRAServicesAPI;
import com.uae.tra_smart_services_cutter.rest.model.request.SmsReportRequestModel;
import com.uae.tra_smart_services_cutter.rest.model.response.SmsSpamResponseModel;

/**
 * Created by Mikazme on 26/08/2015.
 */
public class SmsReportRequest extends BaseRequest<SmsSpamResponseModel, TRAServicesAPI> {

    private SmsReportRequestModel mSmsSpamReportModel;

    public SmsReportRequest(final SmsReportRequestModel _smsSpamModel) {
        super(SmsSpamResponseModel.class, TRAServicesAPI.class);
        mSmsSpamReportModel = _smsSpamModel;
    }

    @Override
    public SmsSpamResponseModel loadDataFromNetwork() throws Exception {
        return getService().reportSmsSpam(mSmsSpamReportModel);
    }
}
