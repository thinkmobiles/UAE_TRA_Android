package com.uae.tra_smart_services.fragment;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

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

        tvServiceProvider = findView(R.id.tvServiceProvider_FBSN);

        etComplainTitle = findView(R.id.etComplainTitle_FCAS);
        etReferenceNumber = findView(R.id.etReferenceNumber_FBSN);
        etDescription = findView(R.id.etDescription_FBSN);
    }

    @Override
    protected void initListeners() {
        super.initListeners();
        ivAddAttachment.setOnClickListener(this);
        ivNextItem.setOnClickListener(this);
        tvServiceProvider.setOnClickListener(this);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    protected int getTitle() {
        return R.string.complain;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_send) {
            hideKeyboard(getView());
            sendComplain();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void sendComplain() {

    }

    @Override
    public void onClick(View v) {
        hideKeyboard(v);
        switch (v.getId()) {
            case R.id.ivAddAttachment_FCAS:
                openImagePicker();
                break;
            case R.id.tvServiceProvider_FBSN:
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
    protected int getLayoutResource() {
        return R.layout.fragment_complain_about_service;
    }
}
