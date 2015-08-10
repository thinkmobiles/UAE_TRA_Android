package com.uae.tra_smart_services.rest.model.responce;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by mobimaks on 30.07.2015.
 */
public class SearchResults {

    @SerializedName("user_data")
    public List<SearchResult> searchResultList;

    public static class SearchResult {
        public String functionName;
    }

}
