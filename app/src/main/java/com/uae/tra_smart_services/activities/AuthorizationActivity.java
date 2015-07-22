package com.uae.tra_smart_services.activities;

import android.content.Intent;
import android.os.Bundle;

import com.uae.tra_smart_services.R;
import com.uae.tra_smart_services.baseentities.BaseAuthorizationFragment;
import com.uae.tra_smart_services.baseentities.BaseFragmentActivity;
import com.uae.tra_smart_services.fragments.LoginFragment;
import com.uae.tra_smart_services.fragments.RegisterFragment;
import com.uae.tra_smart_services.fragments.RestorePassFragment;
import com.uae.tra_smart_services.interfaces.OnReloadData;

import retrofit.RetrofitError;


public class AuthorizationActivity extends BaseFragmentActivity
                                implements BaseAuthorizationFragment.AuthorizationActionsListener{

//    private CallbackManager mCallbackManager;
//    private LoginManager mLoginManager;

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected final void onCreate(final Bundle _savedInstanceState) {
        super.onCreate(_savedInstanceState);
        setContentView(R.layout.activity_authorization);
        if (getFragmentManager().findFragmentById(getContainerId()) == null) {
            addFragment(LoginFragment.newInstance());
        }
    }

    @Override
    public void onOpenLoginScreen() {
        super.popBackStack();
    }

    @Override
    public void onLogInSuccess() {
        // Currently unimplemented method
        // Need clarification
        // TODO Implement method
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
    protected int getContainerId() {
        return R.id.flContainer_AA;
    }

    @Override
    public void handleError(RetrofitError _error) {

    }

    @Override
    public void handleError(RetrofitError _error, OnReloadData _listener) {

    }
}
