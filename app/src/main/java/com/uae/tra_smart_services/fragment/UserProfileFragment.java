package com.uae.tra_smart_services.fragment;

import android.app.Activity;
import android.support.annotation.IntDef;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.uae.tra_smart_services.R;
import com.uae.tra_smart_services.TRAApplication;
import com.uae.tra_smart_services.customviews.HexagonView;
import com.uae.tra_smart_services.fragment.base.BaseFragment;
import com.uae.tra_smart_services.rest.robo_requests.LogoutRequest;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import retrofit.client.Response;

/**
 * Created by mobimaks on 08.09.2015.
 */
public final class UserProfileFragment extends BaseFragment implements OnClickListener {

    private static final String KEY_LOGOUT_REQUEST = "LOGOUT_REQUEST";

    public static final int USER_PROFILE_EDIT_PROFILE = 0;
    public static final int USER_PROFILE_CHANGE_PASSWORD = 1;
    public static final int USER_PROFILE_RESET_PASSWORD = 2;
    public static final int USER_PROFILE_LOGOUT = 3;

    @IntDef({USER_PROFILE_EDIT_PROFILE, USER_PROFILE_CHANGE_PASSWORD, USER_PROFILE_RESET_PASSWORD, USER_PROFILE_LOGOUT})
    @Retention(RetentionPolicy.SOURCE)
    public @interface UserProfileAction {
    }

    private HexagonView hvUserAvatar;
    private TextView tvUsername;
    private LinearLayout llEditProfile, llChangePassword,/* llResetPassword, */llLogout;

    private OnUserProfileClickListener mProfileClickListener;
    private LogoutRequest mLogoutRequest;

    public static UserProfileFragment newInstance() {
        return new UserProfileFragment();
    }

    @Override
    public void onAttach(Activity _activity) {
        super.onAttach(_activity);
        if (_activity instanceof OnUserProfileClickListener) {
            mProfileClickListener = (OnUserProfileClickListener) _activity;
        }
    }

    @Override
    protected void initViews() {
        super.initViews();
        hvUserAvatar = findView(R.id.hvUserAvatar_FUP);
        tvUsername = findView(R.id.tvUsername_FUP);

        llEditProfile = findView(R.id.llEditProfile_FUP);
        llChangePassword = findView(R.id.llChangePassword_FUP);
//        llResetPassword = findView(R.id.llResetPassword_FUP);
        llLogout = findView(R.id.llLogout_FUP);
    }

    @Override
    protected void initListeners() {
        super.initListeners();
        llEditProfile.setOnClickListener(this);
        llChangePassword.setOnClickListener(this);
//        llResetPassword.setOnClickListener(this);
        llLogout.setOnClickListener(this);
    }

    @Override
    public final void onClick(final View _view) {
        if (mProfileClickListener != null) {
            switch (_view.getId()) {
                case R.id.llEditProfile_FUP:
                    mProfileClickListener.onUserProfileItemClick(USER_PROFILE_EDIT_PROFILE);
                    break;
                case R.id.llChangePassword_FUP:
                    mProfileClickListener.onUserProfileItemClick(USER_PROFILE_CHANGE_PASSWORD);
                    break;
//                case R.id.llResetPassword_FUP:
//                    mProfileClickListener.onUserProfileItemClick(USER_PROFILE_RESET_PASSWORD);
//                    break;
                case R.id.llLogout_FUP:
                    logout();
                    break;
            }
        }
    }

    private void logout() {
        mLogoutRequest = new LogoutRequest();
        showProgressDialog();
        getSpiceManager().execute(mLogoutRequest, KEY_LOGOUT_REQUEST, DurationInMillis.ALWAYS_EXPIRED, mLogoutRequestListener);
    }

    private RequestListener<Response> mLogoutRequestListener = new RequestListener<Response>() {

        @Override
        public void onRequestSuccess(final Response _result) {
            TRAApplication.setIsLoggedIn(false);
            if (isAdded()) {
                hideProgressDialog();
                getFragmentManager().popBackStackImmediate();
            }
        }

        @Override
        public void onRequestFailure(final SpiceException _spiceException) {
            processError(_spiceException);
        }

    };


    @Override
    public void onDetach() {
        mProfileClickListener = null;
        super.onDetach();
    }

    @Override
    protected int getTitle() {
        return R.string.fragment_user_profile_title;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_user_profile;
    }

    public interface OnUserProfileClickListener {
        void onUserProfileItemClick(@UserProfileAction int _profileItem);
    }

}
