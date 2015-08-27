package com.uae.tra_smart_services.fragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.PendingRequestListener;
import com.uae.tra_smart_services.R;
import com.uae.tra_smart_services.fragment.base.BaseAuthorizationFragment;
import com.uae.tra_smart_services.rest.model.request.LoginModel;
import com.uae.tra_smart_services.rest.robo_requests.LoginRequest;

import retrofit.client.Response;

/**
 * Created by Andrey Korneychuk on 22.07.15.
 */
public class LoginFragment extends BaseAuthorizationFragment
        implements OnClickListener {

    private static final String KEY_LOGIN_REQUEST = "LOGIN_REQUEST";

    private EditText etUserName, etPassword;
    private Button btnLogIn, btnRegisterNow, btnSkip;
    private TextView tvRestorePassword;

    private RequestResponseListener mRequestLoginListener;

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
        etUserName = findView(R.id.etEmail_FR);
        etPassword = findView(R.id.etPassword_FR);
        // Actions
        tvRestorePassword = findView(R.id.btnRestorePassword_FLI);
        btnLogIn = findView(R.id.btnLogIn_FLI);
        btnRegisterNow = findView(R.id.btnRegisterNow_FLI);
        btnSkip = findView(R.id.btnSkip_FLI);

        etUserName.setText("vitaliy.shuba.trash@gmail.com");
        etPassword.setText("12345678");
    }

    @Override
    protected final void initListeners() {
        mRequestLoginListener = new RequestResponseListener();
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

    @Override
    public void onStart() {
        super.onStart();
        getSpiceManager().addListenerIfPending(Response.class, KEY_LOGIN_REQUEST, mRequestLoginListener);
    }

    private void doLogIn() {
        LoginModel model = new LoginModel();
        model.login = etUserName.getText().toString();
        model.pass = etPassword.getText().toString();

        if (TextUtils.isEmpty(model.login) || TextUtils.isEmpty(model.pass)) {
            Toast.makeText(getActivity(), R.string.error_fill_all_fields, Toast.LENGTH_SHORT).show();
            return;
        }

        showProgressDialog("Authenticating..", null);
        LoginRequest request = new LoginRequest(model);
        getSpiceManager().execute(request, KEY_LOGIN_REQUEST, DurationInMillis.ALWAYS_EXPIRED, mRequestLoginListener);
    }

    private class RequestResponseListener implements PendingRequestListener<Response> {

        @Override
        public void onRequestNotFound() {
            Log.d(getClass().getSimpleName(), "Request Not Found. isAdded: " + isAdded());
        }

        @Override
        public void onRequestSuccess(Response result) {
            Log.d(getClass().getSimpleName(), "Success. isAdded: " + isAdded());
            if (isAdded()) {
                hideProgressDialog();
                if (result != null && actionsListener != null) {
                    actionsListener.onLogInSuccess();
                }
            }
            getSpiceManager().removeDataFromCache(Response.class, KEY_LOGIN_REQUEST);
        }

        @Override
        public void onRequestFailure(SpiceException spiceException) {
            processError(spiceException);
            getSpiceManager().removeDataFromCache(Response.class, KEY_LOGIN_REQUEST);
        }
    }
}
