package com.uae.tra_smart_services.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Toast;

import com.afollestad.materialdialogs.AlertDialogWrapper;
import com.afollestad.materialdialogs.MaterialDialog;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.uae.tra_smart_services.R;
import com.uae.tra_smart_services.dialog.AlertDialogFragment;
import com.uae.tra_smart_services.entities.C;
import com.uae.tra_smart_services.fragment.base.BaseFragment;
import com.uae.tra_smart_services.rest.new_request.CheckDomainAvailabilityRequest;

import java.util.ArrayList;
import java.util.regex.Pattern;

/**
 * Created by ak-buffalo on 10.08.15.
 */
public class DomainCheckerFragment extends BaseFragment
                                implements View.OnClickListener, AlertDialogFragment.OnOkListener {

    public static DomainCheckerFragment newInstance() {
        return new DomainCheckerFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_domain_checker;
    }

    private Button btnAvail, btnWhoIS;
    private EditText etDomainAvail;
    private DomainFilterPool filters;
    @Override
    protected void initViews() {
        super.initViews();
        btnAvail = findView(R.id.btnAvail_FDCH);
        btnWhoIS = findView(R.id.btnWhoIs_FDCH);
        etDomainAvail = findView(R.id.etDomainAvail_FDCH);
        filters = new DomainFilterPool(){
            {
                addFilter(new Filter(){
                    @Override
                    public boolean check(String _domain) {
                        return Patterns.DOMAIN_NAME.matcher(_domain).matches();
                    }
                });
            }
        };
    }

    @Override
    protected void initListeners() {
        super.initListeners();
        btnAvail.setOnClickListener(this);
        btnWhoIS.setOnClickListener(this);
    }

    @Override
    protected int getTitle() {
        return R.string.str_domain_check;
    }

    @Override
    public final void onClick(View _view) {
        switch(_view.getId()){
            case R.id.btnAvail_FDCH:
                checkAvailability();
                break;
            case R.id.btnWhoIs_FDCH:
                break;
        }
    }

    private void checkAvailability(){
        final String _domain = etDomainAvail.getText().toString();
        if(filters.check(_domain)){
            getSpiceManager().execute(new CheckDomainAvailabilityRequest(_domain), new RequestListener<String>() {
                @Override
                public void onRequestFailure(SpiceException spiceException) {
                    AlertDialogFragment.newInstance(DomainCheckerFragment.this)
                        .setDialogTitle(getString(R.string.str_error))
                        .setDialogBody(
                            String.format(getString(R.string.str_invalid_url), _domain)
                        )
                        .show(getFragmentManager());
                }

                @Override
                public void onRequestSuccess(String _str) {
                    Toast.makeText(getActivity(), _str, Toast.LENGTH_LONG).show();
                }
            });
        } else {
            AlertDialogFragment.newInstance(this)
                .setDialogTitle(getString(R.string.str_error))
                .setDialogBody(
                    getString(R.string.str_invalid_url)
                )
                .show(getFragmentManager());
        }
    }

    @Override
    public void onOkPressed() {
        // TODO Unimplemented method
    }

    class DomainFilterPool implements Filter{
        private ArrayList<Filter> filters = new ArrayList<>();

        public void addFilter(Filter _filter){
            filters.add(_filter);
        }

        @Override
        public boolean check(String _domain) {
            for (Filter filter : filters){
                return filter.check(_domain);
            }
            return true;
        }
    }

    interface Filter{
        boolean check(String _domain);
    }
}

