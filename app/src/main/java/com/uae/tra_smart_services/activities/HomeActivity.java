package com.uae.tra_smart_services.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.uae.tra_smart_services.R;
import com.uae.tra_smart_services.baseentities.BaseActivity;
import com.uae.tra_smart_services.interfaces.OnReloadData;

import retrofit.RetrofitError;

/**
 * Created by ak-buffalo on 23.07.15.
 */
public class HomeActivity extends BaseActivity implements View.OnClickListener{
    @Override
    public final void onCreate(final Bundle _savedInstanceState) {
        super.onCreate(_savedInstanceState);
        setContentView(R.layout.activity_home);
        RadioGroup bottomNav = (RadioGroup) findViewById(R.id.bottomNavRadio);
        bottomNav.setOnClickListener(this);
    }

    @Override
    public void handleError(RetrofitError _error) {}

    @Override
    public void handleError(RetrofitError _error, OnReloadData _listener) {}

    @Override
    public void onClick(View v) {
        Toast.makeText(this, ((TextView)v).getText(), Toast.LENGTH_LONG).show();
    }
}
