package com.uae.tra_smart_services.fragments;

import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.uae.tra_smart_services.R;
import com.uae.tra_smart_services.baseentities.BaseAuthorizationFragment;

/**
 * Created by ak-buffalo on 22.07.15.
 */
public class RegisterFragment extends BaseAuthorizationFragment implements View.OnClickListener {

    EditText etUserName, etGender, etPhone, etPassword;
    Button tvRegister;
    TextView btnLogInNow;

    public static RegisterFragment newInstance() {
        return new RegisterFragment();
    }

    @Override
    public void onStart() {
        super.onStart();
        toolbarTitleManager.setTitle(R.string.register_title);
    }

    @Override
    protected final void initViews() {
        // Input fields
        etUserName = findView(R.id.etEmail_FLI);
        etGender = findView(R.id.etGender_FLI);
        etPhone = findView(R.id.etPhone_FLI);
        etPassword = findView(R.id.etPassword_FLI);
        // Actions
        tvRegister = findView(R.id.tvRegister_FLI);
        btnLogInNow = findView(R.id.btnLogInNow_FLI);
    }

    @Override
    protected final void initListeners() {
        tvRegister.setOnClickListener(this);
        btnLogInNow.setOnClickListener(this);
    }


    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_register;
    }

    @Override
    public void onClick(View _v) {
        switch (_v.getId()) {
            case R.id.tvRegister_FLI:
                doRegistration();
                break;
            case R.id.btnLogInNow_FLI:
                actionsListener.onOpenLoginScreen();
                break;
        }
    }

    private void doRegistration(){
        // TODO Registration method should be implemented
        progressDialogManager.showProgressDialog("Registration..");
        postRegistrationDelay();
    }

    /**
     * Mock method should be refactored OR deleted
     * */
    private void postRegistrationDelay(){
        // Run post delayed activity start
        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             */
            @Override
            public void run() {
                progressDialogManager.hideProgressDialog();
                actionsListener.onOpenLoginScreen();
            }
        }, 2000);
    }
}
