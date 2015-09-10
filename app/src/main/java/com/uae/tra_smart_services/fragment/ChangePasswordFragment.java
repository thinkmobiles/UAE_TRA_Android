package com.uae.tra_smart_services.fragment;

import android.text.method.PasswordTransformationMethod;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ToggleButton;

import com.uae.tra_smart_services.R;
import com.uae.tra_smart_services.customviews.HexagonView;
import com.uae.tra_smart_services.customviews.ProfileController;
import com.uae.tra_smart_services.customviews.ProfileController.ControllerButton;
import com.uae.tra_smart_services.customviews.ProfileController.OnControllerButtonClickListener;
import com.uae.tra_smart_services.fragment.base.BaseFragment;

/**
 * Created by mobimaks on 08.09.2015.
 */
public class ChangePasswordFragment extends BaseFragment implements OnCheckedChangeListener, OnControllerButtonClickListener {

    private HexagonView hvUserAvatar;
    private EditText etOldPassword, etNewPassword, etNewPasswordRetype;
    private ToggleButton tbOldPassword, tgNewPassword, tbNewPasswordRetype;
    private ProfileController pcProfileController;

    public static ChangePasswordFragment newInstance() {
        return new ChangePasswordFragment();
    }

    @Override
    protected void initViews() {
        super.initViews();
        hvUserAvatar = findView(R.id.hvUserAvatar_FCP);
        etOldPassword = findView(R.id.etOldPassword_FCP);
        etNewPassword = findView(R.id.etNewPassword_FCP);
        etNewPasswordRetype = findView(R.id.etNewPasswordRetype_FCP);
        tbOldPassword = findView(R.id.tbOldPassword_FCP);
        tgNewPassword = findView(R.id.tgNewPassword_FCP);
        tbNewPasswordRetype = findView(R.id.tbNewPasswordRetype_FCP);
        pcProfileController = findView(R.id.pcProfileController_FCP);
    }

    @Override
    protected void initListeners() {
        super.initListeners();
        tbOldPassword.setOnCheckedChangeListener(this);
        tgNewPassword.setOnCheckedChangeListener(this);
        tbNewPasswordRetype.setOnCheckedChangeListener(this);
        pcProfileController.setOnButtonClickListener(this);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.tbOldPassword_FCP:
                setPasswordVisibility(etOldPassword, isChecked);
                break;
            case R.id.tgNewPassword_FCP:
                setPasswordVisibility(etNewPassword, isChecked);
                break;
            case R.id.tbNewPasswordRetype_FCP:
                setPasswordVisibility(etNewPasswordRetype, isChecked);
                break;
        }
    }

    private void setPasswordVisibility(final EditText _passwordEditText, final boolean _isVisible) {
        _passwordEditText.setTransformationMethod(_isVisible ? null : PasswordTransformationMethod.getInstance());
    }

    @Override
    public void onControllerButtonClick(@ControllerButton int _buttonId) {

    }

    @Override
    protected int getTitle() {
        return R.string.fragment_user_profile_title;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_change_password;
    }
}
