package com.uae.tra_smart_services.fragment.user_profile;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.squareup.picasso.Picasso;
import com.uae.tra_smart_services.R;
import com.uae.tra_smart_services.customviews.HexagonView;
import com.uae.tra_smart_services.customviews.LoaderView;
import com.uae.tra_smart_services.customviews.ProfileController;
import com.uae.tra_smart_services.customviews.ProfileController.ControllerButton;
import com.uae.tra_smart_services.customviews.ProfileController.OnControllerButtonClickListener;
import com.uae.tra_smart_services.dialog.AttachmentPickerDialog.OnImageSourceSelectListener;
import com.uae.tra_smart_services.entities.AttachmentManager;
import com.uae.tra_smart_services.entities.AttachmentManager.OnImageGetCallback;
import com.uae.tra_smart_services.fragment.base.BaseFragment;
import com.uae.tra_smart_services.global.AttachmentOption;
import com.uae.tra_smart_services.interfaces.Loader.BackButton;
import com.uae.tra_smart_services.interfaces.Loader.Cancelled;
import com.uae.tra_smart_services.rest.model.request.UserNameModel;
import com.uae.tra_smart_services.rest.model.response.UserProfileResponseModel;
import com.uae.tra_smart_services.rest.robo_requests.ChangeUserProfileRequest;
import com.uae.tra_smart_services.rest.robo_requests.ImageFromUriRequest;

import retrofit.client.Response;

/**
 * Created by mobimaks on 08.09.2015.
 */
public final class EditUserProfileFragment extends BaseFragment
        implements OnClickListener, OnControllerButtonClickListener, OnImageGetCallback, OnImageSourceSelectListener {

    private static final String KEY_USER_PROFILE_MODEL = "USER_PROFILE_MODEL";
    private static final String KEY_EDIT_PROFILE_REQUEST = "EDIT_PROFILE_REQUEST";

    private HexagonView hvUserAvatar;
    private TextView tvChangePhoto;
    private EditText etFirstName, etLastName, etAddress, etPhone;

    private AttachmentManager mAttachmentManager;
    private ProfileController pcProfileController;
    private Uri mImageUri;

    private UserProfileResponseModel mUserProfile;
    private OnUserProfileDataChangeListener mProfileDataChangeListener;
    private EditUserProfileRequestListener mUserProfileRequestListener;
    private ChangeUserProfileRequest mChangeUserNameRequest;

    public static EditUserProfileFragment newInstance(@NonNull final UserProfileResponseModel _userProfile) {
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
        mAttachmentManager = new AttachmentManager(getActivity(), this);
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
    public void onActivityCreated(final @Nullable Bundle _savedInstanceState) {
        super.onActivityCreated(_savedInstanceState);
        mUserProfileRequestListener = new EditUserProfileRequestListener();
        if (_savedInstanceState == null) {
            showUserProfile(mUserProfile);
        } else {
            mAttachmentManager.onRestoreInstanceState(_savedInstanceState);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        getSpiceManager().getFromCache(UserProfileResponseModel.class, KEY_EDIT_PROFILE_REQUEST,
                DurationInMillis.ALWAYS_RETURNED, mUserProfileRequestListener);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mAttachmentManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public final void onClick(final View _view) {
        switch (_view.getId()) {
            case R.id.tvChangePhoto_FEUP:
                openImagePicker();
                break;
        }
    }

    private void openImagePicker() {
        mAttachmentManager.openDefaultPicker(getActivity(), this);
    }

    @Override
    public final void onImageSourceSelect(AttachmentOption _option) {
        switch (_option) {
            case GALLERY:
                mAttachmentManager.openGallery(this);
                break;
            case CAMERA:
                mAttachmentManager.openCamera(this);
                break;
            case DELETE_ATTACHMENT:
                mAttachmentManager.clearAttachment();
                initUserAvatar(mUserProfile);
                break;
        }
    }

    private void initUserAvatar(final UserProfileResponseModel _userProfile) {
        if (_userProfile.getImageUrl().isEmpty()) {
            hvUserAvatar.postScaleType(HexagonView.INSIDE_CROP);
            hvUserAvatar.setHexagonSrcDrawable(R.drawable.ic_user_placeholder);
        } else {
            Picasso.with(getActivity()).load(_userProfile.getImageUrl()).into(hvUserAvatar);
        }
    }

    @Override
    public void onAttachmentGet(@NonNull final Uri _imageUri) {
        mImageUri = _imageUri;
        loaderDialogShow();
        ImageFromUriRequest imageFromUriRequest = new ImageFromUriRequest(getActivity(), _imageUri);
        getSpiceManager().execute(imageFromUriRequest, new ImageFromUriListener());
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
        profile.imageUri = mImageUri;
        mChangeUserNameRequest = new ChangeUserProfileRequest(getActivity(), profile);

        hideKeyboard(getView());
        loaderOverlayShow(getString(R.string.fragment_edit_user_profile_saving), mUserProfileRequestListener, false);
        loaderOverlayButtonBehavior(mUserProfileRequestListener);
        getSpiceManager().execute(mChangeUserNameRequest, KEY_EDIT_PROFILE_REQUEST,
                DurationInMillis.ALWAYS_EXPIRED, mUserProfileRequestListener);
    }

    private void restoreUserProfile() {
        etFirstName.setText(mUserProfile.firstName);
        etLastName.setText(mUserProfile.lastName);
        etPhone.setText(mUserProfile.mobile);
        mAttachmentManager.clearAttachment();
        initUserAvatar(mUserProfile);
    }

    private void showUserProfile(final UserProfileResponseModel _userProfile) {
        etFirstName.setText(_userProfile.firstName);
        etLastName.setText(_userProfile.lastName);
        etPhone.setText(_userProfile.mobile);
        initUserAvatar(_userProfile);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        mAttachmentManager.onSaveInstanceState(outState);
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
                Picasso.with(getActivity()).invalidate(mUserProfile.getImageUrl());
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

    private class ImageFromUriListener implements RequestListener<Drawable> {

        @Override
        public void onRequestSuccess(Drawable result) {
            if (isAdded()) {
                loaderDialogDismiss();
                hvUserAvatar.postScaleType(HexagonView.CENTER_CROP);
                hvUserAvatar.setHexagonSrcDrawable(result);
            }
        }

        @Override
        public void onRequestFailure(SpiceException spiceException) {
            mAttachmentManager.clearAttachment();
            loaderDialogDismiss(getString(R.string.fragment_edit_profile_image_error));
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
