package com.uae.tra_smart_services.fragment;

import android.preference.PreferenceManager;
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
import com.uae.tra_smart_services.global.C;
import com.uae.tra_smart_services.rest.model.request.LoginModel;
import com.uae.tra_smart_services.rest.robo_requests.LoginRequest;
import com.uae.tra_smart_services.util.LayoutDirectionUtils;

import retrofit.client.Response;

/**
 * Created by ak-buffalo on 22.07.15.
 */
public class LoginFragment extends BaseAuthorizationFragment
        implements OnClickListener {

    private static final String KEY_LOGIN_REQUEST = "LOGIN_REQUEST";

    private EditText etUserName, etPassword;
    private Button btnLogIn;
    private TextView tvRegisterNow, tvForgotPassword;

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
        etUserName = findView(R.id.etEmail_FRP);
        LayoutDirectionUtils.setDrawableStart(getActivity(), etUserName, R.drawable.ic_username);
        etPassword = findView(R.id.etPassword_FR);
        LayoutDirectionUtils.setDrawableStart(getActivity(), etPassword, R.drawable.ic_pass);

        // Actions
        btnLogIn = findView(R.id.btnDoRestorePass_FRP);
        tvRegisterNow = findView(R.id.tvRegisterNow_FLI);
        tvForgotPassword = findView(R.id.tvForgotPass_FLI);

//        if (BuildConfig.DEBUG) {
//            btnLogIn.setOnLongClickListener(new View.OnLongClickListener() {
//                @Override
//                public boolean onLongClick(View v) {
//                    etUserName.setText("vitaliy.shuba.trash@gmail.com");
//                    etPassword.setText("12345678");
//                    doLogIn();
//                    return true;
//                }
//            });
//        }
    }

    @Override
    protected final void initListeners() {
        mRequestLoginListener = new RequestResponseListener();
//        tvRestorePassword.setOnClickListener(this);
        btnLogIn.setOnClickListener(this);
        tvRegisterNow.setOnClickListener(this);
        tvForgotPassword.setOnClickListener(this);
    }

    @Override
    protected final int getLayoutResource() {
        return R.layout.fragment_login;
    }

    @Override
    public final void onClick(final View _v) {
        switch (_v.getId()) {
            case R.id.btnDoRestorePass_FRP:
                doLogIn();
                break;
            case R.id.tvRegisterNow_FLI:
                actionsListener.onOpenRegisterScreen();
                break;
            case R.id.tvForgotPass_FLI:
                    actionsListener.onOpenRestorePassScreen();
                break;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        getSpiceManager().addListenerIfPending(Response.class, KEY_LOGIN_REQUEST, mRequestLoginListener);
    }

    private LoginRequest mRequest;
    private void doLogIn() {
        LoginModel model = new LoginModel();
        model.login = etUserName.getText().toString();
        model.pass = etPassword.getText().toString();

        if (TextUtils.isEmpty(model.login) || TextUtils.isEmpty(model.pass)) {
            Toast.makeText(getActivity(), R.string.error_fill_all_fields, Toast.LENGTH_SHORT).show();
            return;
        }

        hideKeyboard(getView());
        showLoaderDialog(getString(R.string.str_authenticating), this);

        getSpiceManager().execute(mRequest = new LoginRequest(model), KEY_LOGIN_REQUEST, DurationInMillis.ALWAYS_EXPIRED, mRequestLoginListener);
    }

    @Override
    public void onLoadingCanceled() {
        if(getSpiceManager().isStarted() && mRequest!=null){
            getSpiceManager().cancel(mRequest);
        }
    }

    private class RequestResponseListener implements PendingRequestListener<Response> {

        @Override
        public void onRequestNotFound() {
            Log.d(getClass().getSimpleName(), "Request Not Found. isAdded: " + isAdded());
        }

        @Override
        public void onRequestSuccess(Response result) {
            Log.d(getClass().getSimpleName(), "Success. isAdded: " + isAdded());
            PreferenceManager.getDefaultSharedPreferences(getActivity()).edit().putBoolean(C.IS_LOGGED_IN, true).apply();

            if (isAdded()) {
                dissmissLoaderDialog();
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
