package com.uae.tra_smart_services.rest;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.gson.JsonElement;

import java.util.Map;

import retrofit.client.Response;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;
import retrofit.http.QueryMap;

import static com.uae.tra_smart_services.global.ServerConstants.PATH_HOLDER;

/**
 * Created by mobimaks on 19.10.2015.
 */
public interface DynamicServicesApi {

    @GET("/{" + PATH_HOLDER + "}")
    Response performGetRequest(@NonNull final @Path(encode = false, value = PATH_HOLDER) String url,
                               @Nullable final @QueryMap Map<String, String> options);

    @POST("/{" + PATH_HOLDER + "}")
    Response performPostRequest(@NonNull final @Path(encode = false, value = PATH_HOLDER) String url,
                                @Nullable final @QueryMap Map<String, String> options);

    @POST("/{" + PATH_HOLDER + "}")
    Response performPostRequest(@NonNull final @Path(encode = false, value = PATH_HOLDER) String url,
                                @Nullable final @QueryMap Map<String, String> options,
                                @NonNull final @Body JsonElement _body);

}
