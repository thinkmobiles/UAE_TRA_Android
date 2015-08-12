package com.uae.tra_smart_services.fragment;

import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.uae.tra_smart_services.R;
import com.uae.tra_smart_services.fragment.base.BaseAuthorizationFragment;

/**
 * Created by Andrey Korneychuk on 22.07.15.
 */
public class LoginFragment extends BaseAuthorizationFragment
                        implements Button.OnClickListener{
    private EditText etUserName, etPassword;
    private Button btnLogIn, btnRegisterNow, btnSkip;
    private TextView tvRestorePassword;

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Override
    protected int getTitle() {
        return R.string.str_login;
    }

    @Override
    protected final void initViews() {
        // Input fields
        etUserName = findView(R.id.etEmail_FLI);
        etPassword = findView(R.id.etPassword_FLI);
        // Actions
        tvRestorePassword = findView(R.id.btnRestorePassword_FLI);
        btnLogIn = findView(R.id.btnLogIn_FLI);
        btnRegisterNow = findView(R.id.btnRegisterNow_FLI);
        btnSkip = findView(R.id.btnSkip_FLI);
    }

    @Override
    protected final void initListeners() {
        tvRestorePassword.setOnClickListener(this);
        btnLogIn.setOnClickListener(this);
        btnRegisterNow.setOnClickListener(this);
        btnSkip.setOnClickListener(this);
    }

    @Override
    protected final int getLayoutResource() {
        return R.layout.fragment_login;
    }

    @Override
    public final void onClick(final View _v) {
        switch (_v.getId()) {
            case R.id.btnRestorePassword_FLI:
                actionsListener.onOpenRestorePassScreen();
                break;
            case R.id.btnLogIn_FLI:
                doLogIn();
                break;
            case R.id.btnRegisterNow_FLI:
                actionsListener.onOpenRegisterScreen();
                break;
            case R.id.btnSkip_FLI:
                actionsListener.onHomeScreenOpenWithoutAuth();
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
