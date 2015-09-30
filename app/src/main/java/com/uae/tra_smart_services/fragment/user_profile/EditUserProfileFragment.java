package com.uae.tra_smart_services.fragment.user_profile;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.uae.tra_smart_services.R;
import com.uae.tra_smart_services.customviews.HexagonView;
import com.uae.tra_smart_services.customviews.ProfileController;
import com.uae.tra_smart_services.customviews.ProfileController.ControllerButton;
import com.uae.tra_smart_services.customviews.ProfileController.OnControllerButtonClickListener;
import com.uae.tra_smart_services.fragment.base.BaseFragment;

/**
 * Created by mobimaks on 08.09.2015.
 */
public final class EditUserProfileFragment extends BaseFragment
        implements OnClickListener, OnItemSelectedListener, OnControllerButtonClickListener {

    private HexagonView hvUserAvatar;
    private TextView tvChangePhoto;
    private EditText etFirstName, etLastName, etAddress, etPhone;
    private ProfileController pcProfileController;

    private ArrayAdapter mStatesAdapter;

    public static EditUserProfileFragment newInstance() {
        return new EditUserProfileFragment();
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
        mStatesAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.states_array, R.layout.spinner_item_emirate);
        mStatesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    }

    @Override
    public final void onClick(final View _view) {

    }

    @Override
    public final void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public final void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onControllerButtonClick(@ControllerButton int _buttonId) {
        switch (_buttonId) {
            case ProfileController.BUTTON_CANCEL:
                getFragmentManager().popBackStack();
                break;
            case ProfileController.BUTTON_CONFIRM:
                break;
            case ProfileController.BUTTON_RESET:
                clearAllFields();
                break;
        }
    }

    private void clearAllFields() {
        etFirstName.getText().clear();
        etLastName.getText().clear();
        etAddress.getText().clear();
        etPhone.getText().clear();
    }

    @Override
    protected final int getTitle() {
        return R.string.fragment_user_profile_title;
    }

    @Override
    protected final int getLayoutResource() {
        return R.layout.fragment_edit_user_profile;
    }
}