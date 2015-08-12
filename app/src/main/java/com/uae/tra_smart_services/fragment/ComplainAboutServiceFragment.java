package com.uae.tra_smart_services.fragment;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.uae.tra_smart_services.R;
import com.uae.tra_smart_services.dialog.ServicePickerDialog;
import com.uae.tra_smart_services.dialog.ServicePickerDialog.OnServiceProviderSelectListener;
import com.uae.tra_smart_services.fragment.base.BaseComplainFragment;
import com.uae.tra_smart_services.global.ServiceProvider;

/**
 * Created by mobimaks on 10.08.2015.
 */
public final class ComplainAboutServiceFragment extends BaseComplainFragment
        implements OnClickListener, OnServiceProviderSelectListener {

    private ImageView ivAddAttachment, ivNextItem;
    private TextView tvServiceProvider;
    private EditText etComplainTitle, etReferenceNumber, etDescription;

    public static ComplainAboutServiceFragment newInstance() {
        return new ComplainAboutServiceFragment();
    }

    @Override
    protected void initViews() {
        super.initViews();
        ivAddAttachment = findView(R.id.ivAddAttachment_FCAS);
        ivNextItem = findView(R.id.ivNextItem_FCAS);

        tvServiceProvider = findView(R.id.tvServiceProvider_FCAS);

        etComplainTitle = findView(R.id.etComplainTitle_FCAS);
        etReferenceNumber = findView(R.id.etReferenceNumber_FCAS);
        etDescription = findView(R.id.etDescription_FCAS);
    }

    @Override
    protected void initListeners() {
        super.initListeners();
        ivAddAttachment.setOnClickListener(this);
        ivNextItem.setOnClickListener(this);
        tvServiceProvider.setOnClickListener(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        getToolbarTitleManager().setTitle(R.string.complain);
    }

    @Override
    public void onClick(View v) {
        hideKeyboard(v);
        switch (v.getId()) {
            case R.id.ivAddAttachment_FCAS:
                openImagePicker();
                break;
            case R.id.tvServiceProvider_FCAS:
            case R.id.ivNextItem_FCAS:
                openServiceProviderPicker();
                break;
        }
    }

    private void openServiceProviderPicker() {
        ServicePickerDialog.newInstance(this).show(getFragmentManager());
    }

    @Override
    public void onServiceProviderSelect(final ServiceProvider _provider) {
        tvServiceProvider.setText(_provider.toString());
    }

    @Override
    protected void sendComplain() {
        Toast.makeText(getActivity(), "Send", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_complain_about_service;
    }
}
