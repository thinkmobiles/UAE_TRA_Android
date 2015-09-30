package com.uae.tra_smart_services.fragment;

import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.uae.tra_smart_services.R;
import com.uae.tra_smart_services.fragment.base.AttachmentFragment;

/**
 * Created by and on 29.09.15.
 */

public class InnovationsFragment extends AttachmentFragment implements View.OnClickListener, Switch.OnCheckedChangeListener{

    private static final int REQUEST_GALLERY_IMAGE_CODE = 1;
    private static final int REQUEST_CAMERA_PHOTO_CODE = 2;

    private EditText etTitle, etMessageDescription;
    private ImageView ivAddAttachment;
    private Button btnSubmit;
    private SwitchCompat swType;
    private TextView tvPublic, tvPrivate;

    @Override
    protected int getTitle() {
        return R.string.str_innovations;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_innovations_send;
    }

    @Override
    public void onLoadingCanceled() {
        // Not implemented yet
    }

    public static InnovationsFragment newInstance() {
        Bundle args = new Bundle();

        InnovationsFragment fragment = new InnovationsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(false);
    }

    private int color;
    @Override
    protected void initViews() {
        super.initViews();
        ivAddAttachment = findView(R.id.tivAddAttachment_FIS);
        etTitle = findView(R.id.etTitle_FIS);
        etMessageDescription = findView(R.id.etMessageDescription_FIS);
        btnSubmit = findView(R.id.btnSubmit_FRSS);
        tvPublic = findView(R.id.tvPublic);
        tvPrivate = findView(R.id.tvPrivate);
        swType = findView(R.id.swInnType);
        color = etTitle.getCurrentHintTextColor();

        if(swType.isChecked()){
            tvPrivate.setTextColor(color);
        } else {
            tvPublic.setTextColor(color);
        }
    }

    @Override
    protected void initListeners() {
        super.initListeners();
        ivAddAttachment.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);
        swType.setOnCheckedChangeListener(this);
    }

    @Override
    public void onClick(View v) {
        hideKeyboard(v);
        switch (v.getId()) {
            case R.id.tivAddAttachment_FIS:
                openImagePicker();
                break;
            case R.id.btnSubmit_FRSS:
                if(validateData()){
                    sendComplain();
                }
                break;
        }
    }

    @Override
    protected boolean validateData() {
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

    @Override
    protected void sendComplain() {
        showLoaderOverlay(getString(R.string.str_sending), this);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                changeLoaderOverlay_Success(getString(R.string.str_reuqest_has_been_sent));
            }
        }, 2500);
    }

    @Override
    protected void onImageGet(Uri _uri) {
        ivAddAttachment.setImageResource(R.drawable.ic_check);
    }

    boolean isPublic = true;
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if(isChecked){
            tvPublic.setTextColor(Color.LTGRAY);
            tvPrivate.setTextColor(color);
        } else {
            tvPrivate.setTextColor(Color.LTGRAY);
            tvPublic.setTextColor(color);
        }
    }
}
