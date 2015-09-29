package com.uae.tra_smart_services.fragment;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;

import com.uae.tra_smart_services.R;
import com.uae.tra_smart_services.fragment.base.BaseServiceFragment;

/**
 * Created by and on 29.09.15.
 */

public class InnovationsFragment extends BaseServiceFragment {
    private EditText etTitle, etMessageDescription;
    private ImageView ivAddAttachment;

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
    protected void initViews() {
        super.initViews();
        ivAddAttachment = findView(R.id.tivAddAttachment_FIS);
        etTitle = findView(R.id.etTitle_FIS);
        etMessageDescription = findView(R.id.etMessageDescription_FIS);
    }
}
