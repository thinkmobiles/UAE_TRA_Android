package com.uae.tra_smart_services.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.uae.tra_smart_services.R;
import com.uae.tra_smart_services.adapter.InnovationSpinnerAdapter;
import com.uae.tra_smart_services.customviews.LoaderView;
import com.uae.tra_smart_services.dialog.ImageSourcePickerDialog;
import com.uae.tra_smart_services.dialog.ImageSourcePickerDialog.OnImageSourceSelectListener;
import com.uae.tra_smart_services.entities.AttachmentManager;
import com.uae.tra_smart_services.entities.AttachmentManager.OnImageGetCallback;
import com.uae.tra_smart_services.fragment.base.BaseServiceFragment;
import com.uae.tra_smart_services.global.ImageSource;
import com.uae.tra_smart_services.interfaces.Loader;
import com.uae.tra_smart_services.util.ImageUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by and on 29.09.15.
 */

public class InnovationsFragment extends BaseServiceFragment
        implements OnClickListener, OnCheckedChangeListener, OnImageGetCallback, OnImageSourceSelectListener {

    private EditText etTitle, etMessageDescription;
    private ImageView ivAddAttachment;
    private Button btnSubmit;
    private SwitchCompat swType;
    private TextView tvPublic, tvPrivate;
    private Spinner sInnovationSpinner;
    private Context mContext;

    private int color;

    private AttachmentManager mAttachmentManager;

    @Override
    public void onLoadingCanceled() {
        // Not implemented yet
        //HACK:
        loaderOverlayCancelled(mContext.getString(R.string.str_cancel_request));
    }

    public static InnovationsFragment newInstance() {
        return new InnovationsFragment();
    }

    @Override
    public void onAttach(Activity _activity) {
        super.onAttach(_activity);
        mContext = _activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAttachmentManager = new AttachmentManager(getActivity(), this);
    }

    @Override
    protected void initViews() {
        super.initViews();
        ivAddAttachment = findView(R.id.tivAddAttachment_FIS);
        etTitle = findView(R.id.etTitle_FIS);
        etMessageDescription = findView(R.id.etMessageDescription_FIS);
        btnSubmit = findView(R.id.btnSubmit_FIS);
        tvPublic = findView(R.id.tvPublic);
        tvPrivate = findView(R.id.tvPrivate);
        swType = findView(R.id.swInnType);
        sInnovationSpinner = findView(R.id.sInnovationSpinner_FIS);
        color = etTitle.getCurrentHintTextColor();

        togglePrivacy(swType.isChecked());
    }

    @Override
    protected void initListeners() {
        super.initListeners();
        ivAddAttachment.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);
        swType.setOnCheckedChangeListener(this);
    }

    @Override
    public void onActivityCreated(final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            mAttachmentManager.onRestoreInstanceState(savedInstanceState);
        }

        initInnovationSpinner();
    }

    private void initInnovationSpinner() {
        final List<String> stubData = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            stubData.add("Item " + (i + 1));
        }

        InnovationSpinnerAdapter stubAdapter = new InnovationSpinnerAdapter(getActivity(), stubData);
        sInnovationSpinner.setAdapter(stubAdapter);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mAttachmentManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onClick(View v) {
        hideKeyboard(v);
        switch (v.getId()) {
            case R.id.tivAddAttachment_FIS:
                openImagePicker();
                break;
            case R.id.btnSubmit_FIS:
                if (validateData()) {
                    sendComplain();
                }
                break;
        }
    }

    protected void openImagePicker() {
        final boolean isGalleryAvailable = mAttachmentManager.isGalleryPickAvailable();
        final boolean canGetPhoto = mAttachmentManager.canGetCameraPicture();
        if (isGalleryAvailable && canGetPhoto) {
            ImageSourcePickerDialog.newInstance(this).show(getFragmentManager());
        } else if (isGalleryAvailable) {
            mAttachmentManager.openGallery(this);
        } else if (canGetPhoto) {
            mAttachmentManager.openCamera(this);
        } else {
            Toast.makeText(getActivity(), R.string.fragment_complain_about_service_no_camera_and_app, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onImageSourceSelect(ImageSource _source) {
        switch (_source) {
            case GALLERY:
                mAttachmentManager.openGallery(this);
                break;
            case CAMERA:
                mAttachmentManager.openCamera(this);
                break;
        }
    }

    private boolean validateData() {
        boolean titleInvalid = etTitle.getText().toString().isEmpty();
        if (titleInvalid) {
            Toast.makeText(getActivity(), R.string.fragment_complain_no_title, Toast.LENGTH_SHORT).show();
            return false;
        }

        boolean messageInvalid = etMessageDescription.getText().toString().isEmpty();
        if (messageInvalid) {
            Toast.makeText(getActivity(), R.string.fragment_complain_no_description, Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private void sendComplain() {
        loaderOverlayShow(mContext.getString(R.string.str_sending), this);
        loaderOverlayButtonBehavior(new Loader.BackButton() {
            @Override
            public void onBackButtonPressed(LoaderView.State _currentState) {
                getFragmentManager().popBackStack();
                if (_currentState == LoaderView.State.FAILURE || _currentState == LoaderView.State.SUCCESS) {
                    getFragmentManager().popBackStack();
                }
            }
        });
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                loaderOverlaySuccess(mContext.getString(R.string.str_reuqest_has_been_sent));
            }
        }, 2500);
    }

    @Override
    public void onImageGet(Uri _uri) {
        ivAddAttachment.setImageDrawable(ImageUtils.getFilteredDrawableByTheme(getActivity(), R.drawable.ic_check, R.attr.authorizationDrawableColors));
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        mAttachmentManager.onSaveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        togglePrivacy(isChecked);
    }

    private void togglePrivacy(boolean isChecked) {
        if (isChecked) {
            tvPublic.setTextColor(Color.LTGRAY);
            tvPrivate.setTextColor(color);
        } else {
            tvPrivate.setTextColor(Color.LTGRAY);
            tvPublic.setTextColor(color);
        }
    }

    @Override
    public void onDestroy() {
        mAttachmentManager = null;
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        mContext = null;
        super.onDetach();
    }

    @Override
    protected int getTitle() {
        return R.string.str_innovations;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_innovations_send;
    }

}
