package com.uae.tra_smart_services.activities;

import android.os.Bundle;

import com.uae.tra_smart_services.R;
import com.uae.tra_smart_services.baseentities.BaseActivity;
import com.uae.tra_smart_services.interfaces.OnReloadData;

import retrofit.RetrofitError;

/**
 * Created by ak-buffalo on 23.07.15.
 */
public class HomeActivity extends BaseActivity {
    @Override
    public final void onCreate(final Bundle _savedInstanceState) {
        super.onCreate(_savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    @Override
    public void handleError(RetrofitError _error) {

    }

    @Override
    public void handleError(RetrofitError _error, OnReloadData _listener) {

    }
}
