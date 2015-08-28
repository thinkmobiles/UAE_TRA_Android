package com.uae.tra_smart_services.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.uae.tra_smart_services.R;
import com.uae.tra_smart_services.TRAApplication;
import com.uae.tra_smart_services.activity.base.BaseFragmentActivity;
import com.uae.tra_smart_services.fragment.LoginFragment;
import com.uae.tra_smart_services.fragment.RegisterFragment;
import com.uae.tra_smart_services.fragment.RestorePassFragment;
import com.uae.tra_smart_services.fragment.base.BaseAuthorizationFragment;
import com.uae.tra_smart_services.global.C;
import com.uae.tra_smart_services.interfaces.OnReloadData;
import com.uae.tra_smart_services.interfaces.ToolbarTitleManager;

import java.net.CookieHandler;
import java.net.CookieManager;

import retrofit.RetrofitError;

/**
 * Created by Andrey Korneychuk on 22.07.15.
 */
public class AuthorizationActivity extends BaseFragmentActivity
            implements BaseAuthorizationFragment.AuthorizationActionsListener, ToolbarTitleManager {

    private String mAction;

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public final void onCreate(final Bundle _savedInstanceState) {
        super.onCreate(_savedInstanceState);
        setContentView(R.layout.activity_authorization);
        CookieHandler.setDefault(new CookieManager());
        mAction = getIntent().getAction();

        final Toolbar toolbar = findView(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.mipmap.ic_launcher);

        if (getFragmentManager().findFragmentById(getContainerId()) == null) {
            addFragment(LoginFragment.newInstance());
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
    public void onOpenLoginScreen() {
        super.popBackStack();
    }

    @Override
    public void onLogInSuccess() {
        TRAApplication.setIsLoggedIn(true);

        if (mAction != null && mAction.equals(C.ACTION_LOGIN)) {
            setResult(C.LOGIN_SUCCESS);
            finish();
        } else {
            startActivity(new Intent(this, HomeActivity.class));
            finish();
        }
    }

    @Override
    public void onOpenRegisterScreen() {
        super.replaceFragmentWithBackStack(RegisterFragment.newInstance());
    }

    @Override
    public void onRegisterSuccess() {
        super.popBackStack();
    }

    @Override
    public void onOpenRestorePassScreen() {
        super.replaceFragmentWithBackStack(RestorePassFragment.newInstance());

    }

    @Override
    public void onRestorePassSuccess() {
        super.replaceFragmentWithBackStack(LoginFragment.newInstance());
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onHomeScreenOpenWithoutAuth() {
        if (mAction != null && mAction.equals(C.ACTION_LOGIN)) {
            setResult(C.LOGIN_SKIP);
            finish();
        } else {
            startActivity(new Intent(this, HomeActivity.class));
            finish();
        }
    }

    @Override
    protected int getContainerId() {
        return R.id.flContainer_AA;
    }

    @Override
    public void setToolbarVisibility(boolean _isVisible) {
        if (_isVisible)
            getSupportActionBar().show();
        else
            getSupportActionBar().hide();
    }
}
