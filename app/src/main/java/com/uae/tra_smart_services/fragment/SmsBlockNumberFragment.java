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

import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.uae.tra_smart_services.R;
import com.uae.tra_smart_services.dialog.AlertDialogFragment;
import com.uae.tra_smart_services.dialog.ServicePickerDialog;
import com.uae.tra_smart_services.dialog.ServicePickerDialog.OnServiceProviderSelectListener;
import com.uae.tra_smart_services.entities.CustomFilterPool;
import com.uae.tra_smart_services.entities.Filter;
import com.uae.tra_smart_services.fragment.base.BaseFragment;
import com.uae.tra_smart_services.global.ServiceProvider;
import com.uae.tra_smart_services.rest.model.new_request.SmsSpamRequestModel;
import com.uae.tra_smart_services.rest.model.new_response.SmsSpamResponseModel;
import com.uae.tra_smart_services.rest.new_request.SmsSpamRequest;

/**
 * Created by mobimaks on 13.08.2015.
 */
public final class SmsBlockNumberFragment extends BaseFragment
    implements OnClickListener, OnServiceProviderSelectListener, AlertDialogFragment.OnOkListener {

    private EditText etOperatorNumber, etReferenceNumber, etDescription;
    private TextView tvServiceProvider;

    public static SmsBlockNumberFragment newInstance() {
        return new SmsBlockNumberFragment();
    }

    @Override
    public void onCreate(final Bundle _savedInstanceState) {
        super.onCreate(_savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    protected final void initViews() {
        super.initViews();
        etOperatorNumber = findView(R.id.etOperatorNumber_FBSN);
        etReferenceNumber = findView(R.id.etReferenceNumber_FBSN);
        etDescription = findView(R.id.etDescription_FBSN);
        tvServiceProvider = findView(R.id.tvServiceProvider_FBSN);
    }

    @Override
    protected final void initListeners() {
        super.initListeners();
        tvServiceProvider.setOnClickListener(this);
    }

    private CustomFilterPool filters;
    @Override
    protected final void initCustomEntities() {
        super.initCustomEntities();
        filters = new CustomFilterPool<String>(){
            {
                addFilter(new Filter<String>() {
                    @Override
                    public boolean check(String _data) {
                        // TODO Implement phone validation rule here, will return true by default
                        return true;
                    }
                });
            }
        };
    }

    @Override
    public final void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_send, menu);
    }

    @Override
    public final boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_send) {
            collectAndSendToServer();
            hideKeyboard(getView());
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private final void collectAndSendToServer(){
        if(filters.check(etOperatorNumber.getText().toString())){
            getSpiceManager().execute(
                    new SmsSpamRequest(
                            new SmsSpamRequestModel(
                                    etOperatorNumber.getText().toString(),
                                    etReferenceNumber.getText().toString(),
                                    etDescription.getText().toString(),
                                    tvServiceProvider.getText().toString()
                            )
                    ),
                    new SmsSpamBlockResponseListener()
            );
        } else {
            showMessage(R.string.str_error, R.string.str_invalid_number);
        }
    }

    @Override
    public final void onClick(final View _view) {
        hideKeyboard(_view);
        openServiceProviderPicker();
    }

    private final void openServiceProviderPicker() {
        ServicePickerDialog.newInstance(this).show(getFragmentManager());
    }

    @Override
    public final void onServiceProviderSelect(final ServiceProvider _provider) {
        tvServiceProvider.setText(_provider.toString());
    }

    @Override
    protected final int getTitle() {
        return R.string.str_sms_block_number;
    }

    @Override
    protected final int getLayoutResource() {
        return R.layout.fragment_block_sms_number;
    }

    @Override
    public void onOkPressed() {
        // Unimplemented method
    }

    private final class SmsSpamBlockResponseListener implements RequestListener<SmsSpamResponseModel> {

        @Override
        public void onRequestFailure(SpiceException spiceException) {
            Toast.makeText(getActivity(), spiceException.getMessage(), Toast.LENGTH_LONG).show();
        }

        @Override
        public void onRequestSuccess(SmsSpamResponseModel smsSpamReportResponse) {
            showMessage(R.string.str_success, R.string.str_report_has_been_sent);
        }
    }
}
