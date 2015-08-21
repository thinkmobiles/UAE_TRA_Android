package com.uae.tra_smart_services.rest.robo_requests;

import com.uae.tra_smart_services.rest.TRAServicesAPI;
import com.uae.tra_smart_services.rest.model.request.HelpSalimModel;

import retrofit.client.Response;

/**
 * Created by Vitaliy on 13/08/2015.
 */
public class HelpSalimRequest extends BaseRequest<Response, TRAServicesAPI> {

    private HelpSalimModel mHelpSalimModel;

    public HelpSalimRequest(final HelpSalimModel _helpSalimModel) {
        super(Response.class, TRAServicesAPI.class);
        mHelpSalimModel = _helpSalimModel;
    }

    @Override
    public final Response loadDataFromNetwork() throws Exception {
        return getService().sendHelpSalim(mHelpSalimModel);
    }
}
