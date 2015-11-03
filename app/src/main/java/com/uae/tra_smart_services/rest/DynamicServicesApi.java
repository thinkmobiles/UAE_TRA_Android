package com.uae.tra_smart_services.rest;

import android.support.annotation.NonNull;

import com.google.gson.JsonElement;

import retrofit.client.Response;
import retrofit.http.Body;
import retrofit.http.POST;
import retrofit.http.Path;

import static com.uae.tra_smart_services.global.ServerConstants.DYNAMIC_SERVICE_LIST;
import static com.uae.tra_smart_services.global.ServerConstants.PATH_HOLDER;

/**
 * Created by mobimaks on 19.10.2015.
 */
public interface DynamicServicesApi {

    @POST(DYNAMIC_SERVICE_LIST + "/{" + PATH_HOLDER + "}")
    Response performPostRequest(@Path(PATH_HOLDER) String _id,
                                @NonNull final @Body JsonElement _body);

}
