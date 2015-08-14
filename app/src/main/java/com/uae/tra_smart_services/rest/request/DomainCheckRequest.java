package com.uae.tra_smart_services.rest.request;

import com.uae.tra_smart_services.rest.ServicesAPI;
import com.uae.tra_smart_services.rest.model.request.DnsRequest;
import com.uae.tra_smart_services.rest.model.responce.UserData;
import com.uae.tra_smart_services.rest.new_request.BaseRequest;

/**
 * Created by mobimaks on 31.07.2015.
 */
public class DomainCheckRequest extends BaseRequest<UserData, ServicesAPI> {

    private String mToken;
    private DnsRequest mDnsRequest;

    public DomainCheckRequest(String token, DnsRequest dnsRequest){
        super(UserData.class, ServicesAPI.class);
        mToken = token;
        mDnsRequest = dnsRequest;
    }

    @Override
    public UserData loadDataFromNetwork() throws Exception {
        return getService().getDnsUserData(mToken, mDnsRequest);
    }
}
