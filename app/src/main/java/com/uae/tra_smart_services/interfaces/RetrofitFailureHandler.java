package com.uae.tra_smart_services.interfaces;

import retrofit.RetrofitError;

/**
 * Created by Vitaliy on 22/05/2015.
 */
public interface RetrofitFailureHandler {

    void failure(final RetrofitError _error);
}
