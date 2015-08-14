package com.uae.tra_smart_services.fragment;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.uae.tra_smart_services.R;
import com.uae.tra_smart_services.dialog.ServicePickerDialog;
import com.uae.tra_smart_services.dialog.ServicePickerDialog.OnServiceProviderSelectListener;
import com.uae.tra_smart_services.fragment.base.BaseFragment;
import com.uae.tra_smart_services.global.ServiceProvider;

/**
 * Created by mobimaks on 13.08.2015.
 */
public final class BlockSmsNumberFragment extends BaseFragment implements OnClickListener, OnServiceProviderSelectListener {

    private EditText etOperatorNumber, etReferenceNumber, etDescription;
    private TextView tvServiceProvider;

    public static BlockSmsNumberFragment newInstance() {
        return new BlockSmsNumberFragment();
    }

    @Override
    public void onCreate(final Bundle _savedInstanceState) {
        super.onCreate(_savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    protected void initViews() {
        super.initViews();
        etOperatorNumber = findView(R.id.etOperatorNumber_FBSN);
        etReferenceNumber = findView(R.id.etReferenceNumber_FBSN);
        etDescription = findView(R.id.etDescription_FBSN);
        tvServiceProvider = findView(R.id.tvServiceProvider_FBSN);
    }

    @Override
    protected void initListeners() {
        super.initListeners();
        tvServiceProvider.setOnClickListener(this);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_send, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_send) {
            Toast.makeText(getActivity(), "Send", Toast.LENGTH_SHORT).show();
            hideKeyboard(getView());
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(final View _view) {
        hideKeyboard(_view);
        openServiceProviderPicker();
    }

    private void openServiceProviderPicker() {
        ServicePickerDialog.newInstance(this).show(getFragmentManager());
    }

    @Override
    public final void onServiceProviderSelect(final ServiceProvider _provider) {
        tvServiceProvider.setText(_provider.toString());
    }

    @Override
    protected int getTitle() {
        return R.string.str_empty;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_block_sms_number;
    }
}
