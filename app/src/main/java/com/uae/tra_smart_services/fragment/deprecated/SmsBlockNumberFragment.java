package com.uae.tra_smart_services.fragment.deprecated;

import android.os.Bundle;
import android.support.annotation.Nullable;
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
import com.uae.tra_smart_services.dialog.SingleChoiceDialog;
import com.uae.tra_smart_services.dialog.SingleChoiceDialog.OnItemPickListener;
import com.uae.tra_smart_services.entities.CustomFilterPool;
import com.uae.tra_smart_services.entities.Filter;
import com.uae.tra_smart_services.fragment.base.BaseServiceFragment;
import com.uae.tra_smart_services.global.Service;
import com.uae.tra_smart_services.global.ServiceProvider;
import com.uae.tra_smart_services.rest.model.request.SmsBlockRequestModel;
import com.uae.tra_smart_services.rest.model.response.SmsSpamResponseModel;
import com.uae.tra_smart_services.rest.robo_requests.SmsBlockRequest;

/**
 * Created by mobimaks on 13.08.2015.
 */
@Deprecated
public final class SmsBlockNumberFragment extends BaseServiceFragment
        implements OnClickListener, OnItemPickListener, AlertDialogFragment.OnOkListener {

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
//        etOperatorNumber = findView(R.id.etOperatorNumber_FBSN);
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
    protected final void initData() {
        super.initData();
        filters = new CustomFilterPool<String>() {
            {
                addFilter(new Filter<String>() {
                    @Override
                    public boolean check(String _data) {
                        // TODO Implement mobile validation rule here, will return true by default
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

    @Override
    protected String getServiceName() {
        return "SMS Spam Block";
    }

    private SmsBlockRequest mSmsBlockRequest;

    private void collectAndSendToServer() {
        hideKeyboard(getView());
        if (validateData()) {
            loaderDialogShow(getString(R.string.str_checking), this);
            getSpiceManager().execute(
                    mSmsBlockRequest = new SmsBlockRequest(
                            new SmsBlockRequestModel(
                                    "7726",                                     // TODO: TEMPORARY HARDCODED
                                    etReferenceNumber.getText().toString(),
                                    etDescription.getText().toString(),
                                    tvServiceProvider.getText().toString()
                            )
                    ),
                    new SmsSpamBlockResponseListener()
            );
        }
    }

    private boolean validateData() {
//        if (etOperatorNumber.getText().toString().isEmpty()) {
//            Toast.makeText(getActivity(), R.string.str_invalid_number, Toast.LENGTH_SHORT).show();
//            return false;
//        }
        if (tvServiceProvider.getText().toString().isEmpty()) {
            Toast.makeText(getActivity(), R.string.fragment_complain_no_service_provider, Toast.LENGTH_SHORT).show();
            return false;
        }
        if (etReferenceNumber.getText().toString().isEmpty()) {
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
    public final void onClick(final View _view) {
        hideKeyboard(_view);
        openServiceProviderPicker();
    }

    private void openServiceProviderPicker() {
        SingleChoiceDialog
                .newInstance(this, R.string.str_select_service_provider, ServiceProvider.toStringResArray())
                .show(getFragmentManager());
    }

    @Override
    public void onItemPicked(int _dialogItem) {
        tvServiceProvider.setText(ServiceProvider.toStringResArray()[_dialogItem]);
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
    public void onOkPressed(final int _mMessageId) {
        // Unimplemented method
        // Used exceptionally to specify OK button in dialog
    }

    @Override
    public void onLoadingCanceled() {
        if (getSpiceManager().isStarted() && mSmsBlockRequest != null) {
            getSpiceManager().cancel(mSmsBlockRequest);
        }
    }

    private final class SmsSpamBlockResponseListener implements RequestListener<SmsSpamResponseModel> {

        @Override
        public void onRequestFailure(SpiceException spiceException) {
            processError(spiceException);
        }

        @Override
        public void onRequestSuccess(SmsSpamResponseModel smsSpamReportResponse) {
            loaderDialogDismiss();
            showMessage(R.string.str_success, R.string.str_report_has_been_sent);
            getFragmentManager().popBackStack();
        }
    }

    @Nullable
    @Override
    protected Service getServiceType() {
        return null;
    }
}
