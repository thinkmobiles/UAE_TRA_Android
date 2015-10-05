package com.uae.tra_smart_services.fragment.user_profile;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.uae.tra_smart_services.R;
import com.uae.tra_smart_services.customviews.HexagonView;
import com.uae.tra_smart_services.customviews.LoaderView;
import com.uae.tra_smart_services.customviews.ProfileController;
import com.uae.tra_smart_services.customviews.ProfileController.ControllerButton;
import com.uae.tra_smart_services.customviews.ProfileController.OnControllerButtonClickListener;
import com.uae.tra_smart_services.fragment.base.BaseFragment;
import com.uae.tra_smart_services.interfaces.Loader;
import com.uae.tra_smart_services.interfaces.Loader.BackButton;
import com.uae.tra_smart_services.interfaces.Loader.Cancelled;
import com.uae.tra_smart_services.interfaces.LoaderMarker;
import com.uae.tra_smart_services.rest.model.request.UserNameModel;
import com.uae.tra_smart_services.rest.model.response.UserProfileResponseModel;
import com.uae.tra_smart_services.rest.robo_requests.ChangeUserNameRequest;

import retrofit.client.Response;

/**
 * Created by mobimaks on 08.09.2015.
 */
public final class EditUserProfileFragment extends BaseFragment
        implements OnClickListener, OnControllerButtonClickListener {

    private static final String KEY_USER_PROFILE_MODEL = "USER_PROFILE_MODEL";
    private static final String KEY_EDIT_PROFILE_REQUEST = "EDIT_PROFILE_REQUEST";

    private HexagonView hvUserAvatar;
    private TextView tvChangePhoto;
    private EditText etFirstName, etLastName, etAddress, etPhone;
    private ProfileController pcProfileController;

    private UserProfileResponseModel mUserProfile;
    private OnUserProfileDataChangeListener mProfileDataChangeListener;
    private EditUserProfileRequestListener mUserProfileRequestListener;
    private ChangeUserNameRequest mChangeUserNameRequest;

    public static EditUserProfileFragment newInstance(Fragment _targetFragment, @NonNull final UserProfileResponseModel _userProfile) {
        final EditUserProfileFragment fragment = new EditUserProfileFragment();
        final Bundle args = new Bundle();
        args.putParcelable(KEY_USER_PROFILE_MODEL, _userProfile);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Activity _activity) {
        super.onAttach(_activity);
        if (_activity instanceof OnUserProfileDataChangeListener) {
            mProfileDataChangeListener = (OnUserProfileDataChangeListener) _activity;
        }
    }

    @Override
    public void onCreate(final Bundle _savedInstanceState) {
        super.onCreate(_savedInstanceState);

        if (_savedInstanceState == null) {
            mUserProfile = getArguments().getParcelable(KEY_USER_PROFILE_MODEL);
        } else {
            mUserProfile = _savedInstanceState.getParcelable(KEY_USER_PROFILE_MODEL);
        }
    }

    @Override
    protected final void initViews() {
        super.initViews();
        hvUserAvatar = findView(R.id.hvUserAvatar_FEUP);
        tvChangePhoto = findView(R.id.tvChangePhoto_FEUP);
        etFirstName = findView(R.id.etFirstName_FEUP);
        etLastName = findView(R.id.etLastName_FEUP);
        etAddress = findView(R.id.etAddress_FEUP);
        etPhone = findView(R.id.etPhone_FEUP);
        pcProfileController = findView(R.id.pcProfileController_FEUP);
    }

    @Override
    protected final void initListeners() {
        super.initListeners();
        tvChangePhoto.setOnClickListener(this);
        pcProfileController.setOnButtonClickListener(this);
    }

    @Override
    public void onActivityCreated(final Bundle _savedInstanceState) {
        super.onActivityCreated(_savedInstanceState);
        mUserProfileRequestListener = new EditUserProfileRequestListener();
        if (_savedInstanceState == null) {
            setUserProfile(mUserProfile);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        getSpiceManager().getFromCache(UserProfileResponseModel.class, KEY_EDIT_PROFILE_REQUEST,
                DurationInMillis.ALWAYS_RETURNED, mUserProfileRequestListener);
    }

    @Override
    public final void onClick(final View _view) {

    }

    @Override
    public void onControllerButtonClick(final View _view, final @ControllerButton int _buttonId) {
        switch (_buttonId) {
            case ProfileController.BUTTON_CANCEL:
                onBackPressed();
                break;
            case ProfileController.BUTTON_CONFIRM:
                if (validateData()) {
                    saveUserProfile();
                }
                break;
            case ProfileController.BUTTON_RESET:
                restoreUserProfile();
                break;
        }
    }

    private boolean validateData() {
        if (etFirstName.getText().toString().trim().isEmpty() ||
                etLastName.getText().toString().trim().isEmpty()) {
            Toast.makeText(getActivity(), R.string.error_fill_all_fields, Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void saveUserProfile() {
        final UserNameModel profile = new UserNameModel();
        profile.firstName = etFirstName.getText().toString();
        profile.lastName = etLastName.getText().toString();
        mChangeUserNameRequest = new ChangeUserNameRequest(profile);

        hideKeyboard(getView());
        loaderOverlayShow(getString(R.string.fragment_edit_user_profile_saving), (LoaderMarker) mUserProfileRequestListener);
        loaderOverlayButtonBehavior(mUserProfileRequestListener);
        getSpiceManager().execute(mChangeUserNameRequest, KEY_EDIT_PROFILE_REQUEST,
                DurationInMillis.ALWAYS_EXPIRED, mUserProfileRequestListener);
    }

    private void restoreUserProfile() {
        etFirstName.setText(mUserProfile.firstName);
        etLastName.setText(mUserProfile.lastName);
        etPhone.setText(mUserProfile.mobile);
    }

    private void setUserProfile(final UserProfileResponseModel _userProfile) {
        etFirstName.setText(_userProfile.firstName);
        etLastName.setText(_userProfile.lastName);
        etPhone.setText(_userProfile.mobile);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(KEY_USER_PROFILE_MODEL, mUserProfile);
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onBackPressed() {
        final OnUserProfileDataChangeListener dataChangeListener = mProfileDataChangeListener;
        getFragmentManager().popBackStackImmediate();

        if (dataChangeListener != null) {
            dataChangeListener.onUserProfileDataChange(mUserProfile);
        }
        return true;
    }

    @Override
    public void onDetach() {
        mProfileDataChangeListener = null;
        super.onDetach();
    }

    private class EditUserProfileRequestListener implements RequestListener<UserProfileResponseModel>, BackButton, Cancelled {

        private boolean isRequestSuccess;

        @Override
        public void onRequestSuccess(UserProfileResponseModel result) {
            getSpiceManager().removeDataFromCache(UserProfileResponseModel.class, KEY_EDIT_PROFILE_REQUEST);
            if (isAdded() && result != null) {
                mUserProfile = result;
                isRequestSuccess = true;
                loaderOverlaySuccess(getString(R.string.fragment_edit_user_profile_success));
            }
        }

        @Override
        public void onBackButtonPressed(LoaderView.State _currentState) {
            getFragmentManager().popBackStackImmediate();
            if (isRequestSuccess) {
                onBackPressed();
            }
        }

        @Override
        public void onLoadingCanceled() {
            isRequestSuccess = false;
            if (getSpiceManager().isStarted()) {
                getSpiceManager().cancel(mChangeUserNameRequest);
                getSpiceManager().removeDataFromCache(UserProfileResponseModel.class, KEY_EDIT_PROFILE_REQUEST);
            }
        }

        @Override
        public void onRequestFailure(SpiceException spiceException) {
            getSpiceManager().removeDataFromCache(Response.class, KEY_EDIT_PROFILE_REQUEST);
            processError(spiceException);
        }

    }

    @Override
    protected final int getTitle() {
        return R.string.fragment_user_profile_title;
    }

    @Override
    protected final int getLayoutResource() {
        return R.layout.fragment_edit_user_profile;
    }

    public interface OnUserProfileDataChangeListener {
        void onUserProfileDataChange(final UserProfileResponseModel _userProfile);
    }
}
