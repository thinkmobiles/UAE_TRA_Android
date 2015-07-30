package com.uae.tra_smart_services.activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.uae.tra_smart_services.R;
import com.uae.tra_smart_services.baseentities.BaseActivity;
import com.uae.tra_smart_services.baseentities.BaseAuthorizationFragment;
import com.uae.tra_smart_services.baseentities.BaseCustomSwitcher;
import com.uae.tra_smart_services.baseentities.BaseFragmentActivity;
import com.uae.tra_smart_services.fragments.LoginFragment;
import com.uae.tra_smart_services.fragments.RegisterFragment;
import com.uae.tra_smart_services.fragments.RestorePassFragment;
import com.uae.tra_smart_services.fragments.SettingsFragment;
import com.uae.tra_smart_services.interfaces.I_SettingsChanged;
import com.uae.tra_smart_services.interfaces.OnReloadData;
import com.uae.tra_smart_services.interfaces.ToolbarTitleManager;

import java.util.Locale;

import retrofit.RetrofitError;

/**
 * Created by ak-buffalo on 23.07.15.
 */
public class HomeActivity extends BaseFragmentActivity
        implements ToolbarTitleManager {

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public final void onCreate(final Bundle _savedInstanceState) {
        super.onCreate(_savedInstanceState);
        setContentView(R.layout.activity_home);

        final Toolbar toolbar = findView(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.mipmap.ic_launcher);

        if (getFragmentManager().findFragmentById(getContainerId()) == null) {
            addFragment(SettingsFragment.newInstance());
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected int getContainerId() {
        return R.id.flContainer_HA;
    }

    @Override
    public void handleError(RetrofitError _error) {

    }

    @Override
    public void handleError(RetrofitError _error, OnReloadData _listener) {

    }
}
