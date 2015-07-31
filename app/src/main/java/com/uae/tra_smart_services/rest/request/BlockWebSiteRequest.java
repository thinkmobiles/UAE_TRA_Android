package com.uae.tra_smart_services.rest.request;

import com.octo.android.robospice.request.retrofit.RetrofitSpiceRequest;
import com.uae.tra_smart_services.rest.ServicesAPI;
import com.uae.tra_smart_services.rest.model.request.WebUrl;
import com.uae.tra_smart_services.rest.model.responce.Status;

/**
 * Created by mobimaks on 31.07.2015.
 */
public class BlockWebSiteRequest extends RetrofitSpiceRequest<Status, ServicesAPI> {

    private String mToken;
    private WebUrl mWebUrl;

    public BlockWebSiteRequest(String token, WebUrl url) {
        super(Status.class, ServicesAPI.class);
        mToken = token;
        mWebUrl = url;
    }

    @Override
    public Status loadDataFromNetwork() throws Exception {
        return getService().blockWebSite(mToken, mWebUrl);
    }
}
