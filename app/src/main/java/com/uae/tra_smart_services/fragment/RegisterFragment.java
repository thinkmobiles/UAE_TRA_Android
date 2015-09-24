package com.uae.tra_smart_services.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.PendingRequestListener;
import com.uae.tra_smart_services.R;
import com.uae.tra_smart_services.adapter.StateRegisterAdapter;
import com.uae.tra_smart_services.entities.CustomFilterPool;
import com.uae.tra_smart_services.entities.Filter;
import com.uae.tra_smart_services.fragment.base.BaseAuthorizationFragment;
import com.uae.tra_smart_services.rest.model.request.RegisterModel;
import com.uae.tra_smart_services.rest.robo_requests.RegisterRequest;
import com.uae.tra_smart_services.util.ImageUtils;

import retrofit.client.Response;

/**
 * Created by Andrey Korneychuk on 22.07.15.
 */
public class RegisterFragment extends BaseAuthorizationFragment implements View.OnClickListener {

    private static final String KEY_REGISTER_REQUEST = "REGISTER_REQUEST";

    private EditText etUserName, etPhone, etPassword, etConfirmPassword, etFirstName,
            etLastName, etEmiratesId, etEmail;
    private Button tvRegister;
    private TextView btnLogInNow;
    private Spinner acsState, acsCountry;

    private RegisterRequest mRegisterRequest;

    private StateRegisterAdapter mStatesAdapter, mCountriesAdapter;
    private CustomFilterPool<RegisterModel> mFilterPool;

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
        etUserName = findView(R.id.etUsername_FR);
        etUserName.setCompoundDrawablesRelativeWithIntrinsicBounds(ImageUtils.getFilteredDrawableByTheme(getActivity(), R.drawable.ic_username, R.attr.authorizationDrawableColors), null, null, null);

        etPhone = findView(R.id.etPhone_FR);
        etPhone.setCompoundDrawablesRelativeWithIntrinsicBounds(ImageUtils.getFilteredDrawableByTheme(getActivity(), R.drawable.ic_phone, R.attr.authorizationDrawableColors), null, null, null);

        etPassword = findView(R.id.etPassword_FR);
        etPassword.setCompoundDrawablesRelativeWithIntrinsicBounds(ImageUtils.getFilteredDrawableByTheme(getActivity(), R.drawable.ic_pass, R.attr.authorizationDrawableColors), null, null, null);

        etConfirmPassword = findView(R.id.etConfirmPassword_FR);
        etConfirmPassword.setCompoundDrawablesRelativeWithIntrinsicBounds(ImageUtils.getFilteredDrawableByTheme(getActivity(), R.drawable.ic_pass, R.attr.authorizationDrawableColors), null, null, null);

        etFirstName = findView(R.id.etFirstName_FR);
        etFirstName.setCompoundDrawablesRelativeWithIntrinsicBounds(ImageUtils.getFilteredDrawableByTheme(getActivity(), R.drawable.ic_username, R.attr.authorizationDrawableColors), null, null, null);

        etLastName = findView(R.id.etLastName_FR);
        etLastName.setCompoundDrawablesRelativeWithIntrinsicBounds(ImageUtils.getFilteredDrawableByTheme(getActivity(), R.drawable.ic_username, R.attr.authorizationDrawableColors), null, null, null);

        etEmiratesId = findView(R.id.etEmiratesID_FR);
        etEmiratesId.setCompoundDrawablesRelativeWithIntrinsicBounds(ImageUtils.getFilteredDrawableByTheme(getActivity(), R.drawable.ic_id, R.attr.authorizationDrawableColors), null, null, null);

        etEmail = findView(R.id.etEmail_FRP);
        etEmail.setCompoundDrawablesRelativeWithIntrinsicBounds(ImageUtils.getFilteredDrawableByTheme(getActivity(), R.drawable.ic_mail, R.attr.authorizationDrawableColors), null, null, null);


//        acsState = findView(R.id.spState_FR);
    //        acsCountry = findView(R.id.spCountry_FR);
        // Actions
        tvRegister = findView(R.id.tvRegister_FLI);
    }

    @Override
    protected final void initListeners() {
        mRequestListener = new RequestListener();
        tvRegister.setOnClickListener(this);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        mStatesAdapter = new StateRegisterAdapter(getActivity(), Arrays.asList(getResources().getStringArray(R.array.states_array)));
//        acsState.setAdapter(mStatesAdapter);

        initFilters();
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_register;
    }

    @Override
    public void onStart() {
        super.onStart();
//        getSpiceManager().getFromCache(Response.class, KEY_REGISTER_REQUEST, DurationInMillis.ALWAYS_RETURNED, mRequestListener);
        getSpiceManager().addListenerIfPending(Response.class, KEY_REGISTER_REQUEST, mRequestListener);
    }

    @Override
    public void onClick(View _v) {
        switch (_v.getId()) {
            case R.id.tvRegister_FLI:
                doRegistration();
                break;
        }
    }

    private void doRegistration() {
        final RegisterModel registerModel = new RegisterModel();
        registerModel.login = etUserName.getText().toString();
        registerModel.pass = etPassword.getText().toString();
        registerModel.mobile = etPhone.getText().toString();
        registerModel.first = etFirstName.getText().toString();
        registerModel.last = etLastName.getText().toString();
        registerModel.state = 3; // HARDCODED DUBAI
        registerModel.email = etEmail.getText().toString();
        registerModel.emiratesId = etEmiratesId.getText().toString();

        if (mFilterPool.check(registerModel)) {
            showLoaderDialog(getString(R.string.str_registering), this);
            getSpiceManager().execute(mRegisterRequest = new RegisterRequest(registerModel),
                    KEY_REGISTER_REQUEST, DurationInMillis.ALWAYS_EXPIRED, mRequestListener);
        }
    }

    @Override
    public void onDialogCancel() {
        if(getSpiceManager().isStarted() && mRegisterRequest!=null){
            getSpiceManager().cancel(mRegisterRequest);
        }
    }

    private class RequestListener implements PendingRequestListener<Response> {

        @Override
        public void onRequestNotFound() {

        }

        @Override
        public void onRequestSuccess(Response result) {
            Log.d(getClass().getSimpleName(), "Success. isAdded: " + isAdded());
            if (isAdded()) {
                dissmissLoaderDialog();
                if (result != null && actionsListener != null) {
                    actionsListener.onRegisterSuccess();
                }
            }
            getSpiceManager().removeDataFromCache(Response.class, KEY_REGISTER_REQUEST);
        }

        @Override
        public void onRequestFailure(SpiceException spiceException) {
            processError(spiceException);
            getSpiceManager().removeDataFromCache(Response.class, KEY_REGISTER_REQUEST);
        }
    }

    private void initFilters() {
        mFilterPool = new CustomFilterPool<RegisterModel>() {
            {
                addFilter(new Filter<RegisterModel>() {
                    @Override
                    public boolean check(final RegisterModel _data) {
                        if (TextUtils.isEmpty(_data.email) || TextUtils.isEmpty(_data.login) ||
                                TextUtils.isEmpty(_data.pass) || TextUtils.isEmpty(_data.first) ||
                                TextUtils.isEmpty(_data.last) || TextUtils.isEmpty(_data.emiratesId) ||
                                TextUtils.isEmpty(_data.mobile)) {
                            showMessage(R.string.str_error, R.string.error_fill_all_fields);
                            return false;
                        }
                        return true;
                    }
                });

                addFilter(new Filter<RegisterModel>() {
                    @Override
                    public boolean check(final RegisterModel _data) {
                        if (_data.state == null || _data.state == 0) {
                            showMessage(R.string.str_error, R.string.error_select_state);
                            acsState.requestFocus();
                            return false;
                        }
                        return true;
                    }
                });

                addFilter(new Filter<RegisterModel>() {
                    @Override
                    public boolean check(final RegisterModel _data) {
                        if (!Patterns.EMAIL_ADDRESS.matcher(_data.email).matches()) {
                            showMessage(0, R.string.str_error, R.string.error_valid_email);
                            etEmail.setError(getString(R.string.error_valid_email));
                            etEmail.requestFocus();
                            return false;
                        }
                        return true;
                    }
                });

                addFilter(new Filter<RegisterModel>() {
                    @Override
                    public boolean check(final RegisterModel _data) {
                        if (!Patterns.PHONE.matcher(_data.mobile).matches()) {
                            showMessage(0, R.string.str_error, R.string.error_valid_phone);
                            etPhone.setError(getString(R.string.error_valid_phone));
                            etPhone.requestFocus();
                            return false;
                        }
                        return true;
                    }
                });

                addFilter(new Filter<RegisterModel>() {
                    @Override
                    public boolean check(RegisterModel _data) {
                        if (!_data.pass.equals(etConfirmPassword.getText().toString())) {
                            showMessage(0, R.string.str_error, R.string.error_password_confirm);
                            etPassword.setError(getString(R.string.error_password_confirm));
                            etConfirmPassword.setError(getString(R.string.error_password_confirm));
                            etConfirmPassword.requestFocus();
                            return false;
                        }
                        return true;
                    }
                });
            }
        };
    }
}
