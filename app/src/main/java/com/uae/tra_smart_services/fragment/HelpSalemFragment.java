package com.uae.tra_smart_services.fragment;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.squareup.picasso.Downloader;
import com.uae.tra_smart_services.R;
import com.uae.tra_smart_services.dialog.AlertDialogFragment;
import com.uae.tra_smart_services.entities.CustomFilterPool;
import com.uae.tra_smart_services.entities.Filter;
import com.uae.tra_smart_services.fragment.base.BaseFragment;
import com.uae.tra_smart_services.rest.model.new_request.HelpSalimModel;
import com.uae.tra_smart_services.rest.model.new_request.SmsSpamRequestModel;
import com.uae.tra_smart_services.rest.model.new_response.DomainInfoCheckResponseModel;
import com.uae.tra_smart_services.rest.model.new_response.SmsSpamResponseModel;
import com.uae.tra_smart_services.rest.model.responce.Status;
import com.uae.tra_smart_services.rest.new_request.HelpSalimRequest;
import com.uae.tra_smart_services.rest.new_request.SmsSpamRequest;

import retrofit.client.Response;

/**
 * Created by ak-buffalo on 11.08.15.
 */
public class HelpSalemFragment extends BaseFragment implements AlertDialogFragment.OnOkListener {
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

    private EditText eturl, etDescription;
    @Override
    protected void initViews() {
        super.initViews();
        eturl = findView(R.id.eturl_FHS);
        etDescription = findView(R.id.etDescription_FHS);
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
                collectAndSendToServer();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private final void collectAndSendToServer(){
        if(filters.check(eturl.getText().toString())){
            progressDialogManager.showProgressDialog(getString(R.string.str_checking));
            getSpiceManager().execute(
                    new HelpSalimRequest(
                            new HelpSalimModel(
                                    eturl.getText().toString(),
                                    etDescription.getText().toString()
                            )
                    ),
                    new HelpSalimRequestListener()
            );
        } else {
            showMessage(R.string.str_error, R.string.str_invalid_number);
        }
    }

    @Override
    public void onOkPressed() {
        // Unimplemented method
    }

    private final class HelpSalimRequestListener implements RequestListener<Response> {

        @Override
        public void onRequestFailure(SpiceException spiceException) {
            progressDialogManager.hideProgressDialog();
            Toast.makeText(getActivity(), spiceException.getMessage(), Toast.LENGTH_LONG).show();
        }

        @Override
        public void onRequestSuccess(Response smsSpamReportResponse) {
            progressDialogManager.hideProgressDialog();
            showMessage(R.string.str_success, R.string.str_report_has_been_sent);
        }
    }
}