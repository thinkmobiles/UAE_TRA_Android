package com.uae.tra_smart_services.fragment;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.uae.tra_smart_services.R;
import com.uae.tra_smart_services.dialog.AlertDialogFragment;
import com.uae.tra_smart_services.entities.CustomFilterPool;
import com.uae.tra_smart_services.entities.Filter;
import com.uae.tra_smart_services.fragment.base.BaseServiceFragment;
import com.uae.tra_smart_services.rest.model.request.SmsReportRequestModel;
import com.uae.tra_smart_services.rest.model.response.SmsSpamResponseModel;
import com.uae.tra_smart_services.rest.robo_requests.SmsReportRequest;

/**
 * Created by ak-buffalo on 11.08.15.
 */
public class SmsReportFragment extends BaseServiceFragment implements AlertDialogFragment.OnOkListener {

    private EditText etNumberOfSpammer, etDescription;

    private SmsReportRequest mSmsReportRequest;
    private CustomFilterPool<String> filters;

    public static SmsReportFragment newInstance() {
        return new SmsReportFragment();
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_sms_report;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initViews() {
        super.initViews();
        etNumberOfSpammer = findView(R.id.etNumberOfSpammer_FSR);
        etDescription = findView(R.id.etDescription_FSR);
    }

    @Override
    protected void initData() {
        super.initData();
        filters = new CustomFilterPool<String>() {
            {
                addFilter(new Filter<String>() {
                    @Override
                    public boolean check(String _data) {
                        return !_data.isEmpty();
                    }
                });
            }
        };
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_send, menu);
    }

    @Override
    protected int getTitle() {
        return R.string.str_sms_report_number;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_send:
                hideKeyboard(getView());
                collectAndSendToServer();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void collectAndSendToServer() {
        if (validateData()) {

            showProgressDialog(getString(R.string.str_checking), this);
            getSpiceManager().execute(
                    mSmsReportRequest = new SmsReportRequest(
                            new SmsReportRequestModel(
                                    etNumberOfSpammer.getText().toString(),
                                    etDescription.getText().toString()
                            )
                    ),
                    new SmsSpamReportResponseListener()
            );
        }
    }

    private boolean validateData() {
        if (etNumberOfSpammer.getText().toString().isEmpty()) {
            Toast.makeText(getActivity(), R.string.str_invalid_number, Toast.LENGTH_SHORT).show();
            return false;
        }
        if (etDescription.getText().toString().isEmpty()) {
            Toast.makeText(getActivity(), R.string.fragment_complain_no_description, Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    @Override
    public void onOkPressed(final int _mMessageId) {
        // Unimplemented method
        // Used exceptionally to specify OK button in dialog
    }

    @Override
    public void onDialogCancel() {
        if (getSpiceManager().isStarted() && mSmsReportRequest != null) {
            getSpiceManager().cancel(mSmsReportRequest);
        }
    }

    private final class SmsSpamReportResponseListener implements RequestListener<SmsSpamResponseModel> {

        @Override
        public void onRequestFailure(SpiceException spiceException) {
            processError(spiceException);
        }

        @Override
        public void onRequestSuccess(SmsSpamResponseModel smsSpamReportResponse) {
            hideProgressDialog();
            showMessage(R.string.str_success, R.string.str_report_has_been_sent);
        }
    }
}
