package com.uae.tra_smart_services.fragment;

import android.os.Bundle;
import android.util.Patterns;
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
import com.uae.tra_smart_services.rest.model.request.HelpSalimModel;
import com.uae.tra_smart_services.rest.robo_requests.HelpSalimRequest;

import retrofit.client.Response;

/**
 * Created by ak-buffalo on 11.08.15.
 */
public class HelpSalemFragment extends BaseServiceFragment implements AlertDialogFragment.OnOkListener {

    private EditText etUrl, etDescription;

    private HelpSalimRequest mHelpSalimRequest;
    private CustomFilterPool<String> filters;

    public static HelpSalemFragment newInstance() {
        return new HelpSalemFragment();
    }

    @Override
    protected int getLayoutResource() {return R.layout.fragment_help_salem;}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }
    @Override
    protected void initViews() {
        super.initViews();
        etUrl = findView(R.id.etUrl_FHS);
        etDescription = findView(R.id.etDescription_FHS);
    }

    @Override
    protected final void initData() {
        super.initData();
        filters = new CustomFilterPool<String>(){
            {
                addFilter(new Filter<String>() {
                    @Override
                    public boolean check(String _data) {
                        return Patterns.DOMAIN_NAME.matcher(_data).matches();
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
        return R.string.str_report_url;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_send:
                hideKeyboard(getView());
                collectAndSendToServer();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    private void collectAndSendToServer(){
        if(validateData()){
            showProgressDialog(getString(R.string.str_checking), this);
            getSpiceManager().execute(
                    mHelpSalimRequest = new HelpSalimRequest(
                            new HelpSalimModel(
                                    etUrl.getText().toString(),
                                    etDescription.getText().toString()
                            )
                    ),
                    new HelpSalimRequestListener()
            );
        }
    }

    private boolean validateData() {
        if (!filters.check(etUrl.getText().toString())) {
            Toast.makeText(getActivity(), R.string.str_invalid_url, Toast.LENGTH_SHORT).show();
            return false;
        }
        if (etDescription.getText().toString().isEmpty()) {
            Toast.makeText(getActivity(), R.string.fragment_complain_no_description, Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    @Override
    public void onOkPressed() {
        // Unimplemented method
        // Used exceptionally to specify OK button in dialog
    }

    private final class HelpSalimRequestListener implements RequestListener<Response> {

        @Override
        public void onRequestFailure(SpiceException spiceException) {
            processError(spiceException);
        }

        @Override
        public void onRequestSuccess(Response smsSpamReportResponse) {
            hideProgressDialog();
            showMessage(R.string.str_success, R.string.str_report_has_been_sent);
            getFragmentManager().popBackStackImmediate();
        }
    }

    @Override
    public void onDialogCancel() {
        if (getSpiceManager().isStarted() && mHelpSalimRequest != null) {
            getSpiceManager().cancel(mHelpSalimRequest);
        }
    }
}