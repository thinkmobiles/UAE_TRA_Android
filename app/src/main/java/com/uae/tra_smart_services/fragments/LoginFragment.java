package com.uae.tra_smart_services.fragments;

import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.uae.tra_smart_services.R;
import com.uae.tra_smart_services.baseentities.BaseAuthorizationFragment;

/**
 * Created by Andrey Korneychuk on 22.07.15.
 */
public class LoginFragment extends BaseAuthorizationFragment
                        implements Button.OnClickListener{
    private EditText etUserName, etPassword;
    private Button btnLogIn;
    private TextView tvRestorePassword, tvRegisterNow;

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Override
    public void onStart() {
        super.onStart();
        toolbarTitleManager.setTitle(R.string.login_title);
    }

    @Override
    protected final void initViews() {
        // Input fields
        etUserName = findView(R.id.etEmail_FLI);
        etPassword = findView(R.id.etPassword_FLI);
        // Actions
        tvRestorePassword = findView(R.id.tvRestorePassword_FLI);
        btnLogIn = findView(R.id.btnLogIn_FLI);
        tvRegisterNow = findView(R.id.tvRegisterNow_FLI);
    }

    @Override
    protected final void initListeners() {
        tvRestorePassword.setOnClickListener(this);
        btnLogIn.setOnClickListener(this);
        tvRegisterNow.setOnClickListener(this);
    }

    @Override
    protected final int getLayoutResource() {
        return R.layout.fragment_login;
    }

    @Override
    public final void onClick(final View _v) {
        switch (_v.getId()) {
            case R.id.tvRestorePassword_FLI:
                actionsListener.onOpenRestorePassScreen();
                break;
            case R.id.btnLogIn_FLI:
                doLogIn();
                break;
            case R.id.tvRegisterNow_FLI:
                actionsListener.onOpenRegisterScreen();
                break;
        }
    }

    private void doLogIn(){
        // TODO Registration method should be implemented
        progressDialogManager.showProgressDialog("Authenticating..");
        postLoginDelay();
    }

    /**
     * Mock method should be refactored OR deleted
     * */
    private void postLoginDelay(){
        // Run post delayed activity start
        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             */
            @Override
            public void run() {
                progressDialogManager.hideProgressDialog();
                actionsListener.onLogInSuccess();
            }
        }, 2000);
    }
}
