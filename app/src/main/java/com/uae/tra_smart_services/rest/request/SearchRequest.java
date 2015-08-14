package com.uae.tra_smart_services.rest.request;

import com.uae.tra_smart_services.rest.ServicesAPI;
import com.uae.tra_smart_services.rest.model.responce.SearchResults;
import com.uae.tra_smart_services.rest.new_request.BaseRequest;

/**
 * Created by mobimaks on 31.07.2015.
 */
public class SearchRequest extends BaseRequest<SearchResults, ServicesAPI> {

    private String mToken, mSearchQuery;

    public SearchRequest(String token, String searchQuery) {
        super(SearchResults.class, ServicesAPI.class);
        mToken = token;
        mSearchQuery = searchQuery;
    }

    @Override
    public SearchResults loadDataFromNetwork() throws Exception {
        return getService().search(mToken, mSearchQuery);
    }
}
