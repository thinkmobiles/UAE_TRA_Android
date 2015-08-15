package com.uae.tra_smart_services.fragment;

import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.PendingRequestListener;
import com.uae.tra_smart_services.R;
import com.uae.tra_smart_services.fragment.base.BaseAuthorizationFragment;
import com.uae.tra_smart_services.rest.model.new_request.RegisterModel;
import com.uae.tra_smart_services.rest.model.new_response.ErrorResponseModel;
import com.uae.tra_smart_services.rest.new_request.RegisterRequest;

import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Andrey Korneychuk on 22.07.15.
 */
public class RegisterFragment extends BaseAuthorizationFragment implements View.OnClickListener {

    private static final String KEY_REGISTER_REQUEST = "REGISTER_REQUEST";

    EditText etUserName, etGender, etPhone, etPassword;
    Button tvRegister;
    TextView btnLogInNow;

    private RequestListener mRequestListener;

    public static RegisterFragment newInstance() {
        return new RegisterFragment();
    }

    @Override
    protected int getTitle() {
        return R.string.register_title;
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
        mRequestListener = new RequestListener();
        tvRegister.setOnClickListener(this);
        btnLogInNow.setOnClickListener(this);
    }


    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_register;
    }

    @Override
    public void onStart() {
        super.onStart();
        getSpiceManager().getFromCache(Response.class, KEY_REGISTER_REQUEST, DurationInMillis.ALWAYS_RETURNED, mRequestListener);
        getSpiceManager().addListenerIfPending(Response.class, KEY_REGISTER_REQUEST, mRequestListener);
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

    private void doRegistration() {
        progressDialogManager.showProgressDialog("Registration..");
        RegisterModel registerModel = new RegisterModel();
        registerModel.login = etUserName.getText().toString();
        registerModel.pass = etPassword.getText().toString();
        registerModel.phone = etPhone.getText().toString();
        registerModel.gender = etGender.getText().toString();

        RegisterRequest registerRequest = new RegisterRequest(registerModel);
        getSpiceManager().execute(registerRequest, KEY_REGISTER_REQUEST, DurationInMillis.ALWAYS_EXPIRED, mRequestListener);
    }

    /**
     * Mock method should be refactored OR deleted
     */
    private void postRegistrationDelay() {
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

    private class RequestListener implements PendingRequestListener<Response> {

        @Override
        public void onRequestNotFound() {

        }

        @Override
        public void onRequestSuccess(Response result) {
            Log.d(getClass().getSimpleName(), "Success. isAdded: " + isAdded());
            if (isAdded()) {
                progressDialogManager.hideProgressDialog();
                if (result != null && actionsListener != null) {
                    actionsListener.onRegisterSuccess();
                }
            }
            getSpiceManager().removeDataFromCache(Response.class, KEY_REGISTER_REQUEST);
        }

        @Override
        public void onRequestFailure(SpiceException spiceException) {
            Log.d(getClass().getSimpleName(), "Failure. isAdded: " + isAdded());
            if (isAdded()) {
                progressDialogManager.hideProgressDialog();
                String errorMessage;
                Throwable cause = spiceException.getCause();
                if (cause != null && cause instanceof RetrofitError) {
                    RetrofitError error = (RetrofitError) cause;
                    ErrorResponseModel errorResponse = (ErrorResponseModel) error.getBodyAs(ErrorResponseModel.class);
                    errorMessage = errorResponse.error;
                } else {
                    errorMessage = "Error";
                }

                Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_SHORT).show();
            }
            getSpiceManager().removeDataFromCache(Response.class, KEY_REGISTER_REQUEST);
        }
    }
}
