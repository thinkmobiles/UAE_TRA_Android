package com.uae.tra_smart_services.rest;

import com.uae.tra_smart_services.global.ServerConstants;

/**
 * Created by Mikazme on 13/08/2015.
 */
public final class TRARestService extends BaseRetrofitSpiceService {

    @Override
    protected final String getServerUrl() {
        return ServerConstants.BASE_URL;
    }

}
