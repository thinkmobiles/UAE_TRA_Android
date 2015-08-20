package com.uae.tra_smart_services.fragment;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.uae.tra_smart_services.R;
import com.uae.tra_smart_services.dialog.AlertDialogFragment;
import com.uae.tra_smart_services.entities.CustomFilterPool;
import com.uae.tra_smart_services.entities.Filter;
import com.uae.tra_smart_services.fragment.base.BaseFragment;
import com.uae.tra_smart_services.global.ServerConstants;
import com.uae.tra_smart_services.rest.model.new_response.DomainAvailabilityCheckResponseModel;
import com.uae.tra_smart_services.rest.model.new_response.DomainInfoCheckResponseModel;
import com.uae.tra_smart_services.rest.new_request.DomainAvailabilityCheckRequest;
import com.uae.tra_smart_services.rest.new_request.DomainInfoCheckRequest;

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
    protected final int getLayoutResource() {
        return R.layout.fragment_domain_checker;
    }

    @Override
    protected final int getTitle() {
        return R.string.str_domain_check;
    }

    private Button btnAvail, btnWhoIS;
    private EditText etDomainAvail;

    @Override
    protected final void initViews() {
        super.initViews();
        btnAvail = findView(R.id.btnAvail_FDCH);
        btnWhoIS = findView(R.id.btnWhoIs_FDCH);
        etDomainAvail = findView(R.id.etDomainAvail_FDCH);
    }

    @Override
    protected final void initListeners() {
        super.initListeners();
        btnAvail.setOnClickListener(this);
        btnWhoIS.setOnClickListener(this);
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
                        return !_data.isEmpty();
                    }
                });
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
    public final void onClick(View _view) {
        final String domain = etDomainAvail.getText().toString();
        if (filters.check(domain)) {
            progressDialogManager.showProgressDialog(getString(R.string.str_checking));
            switch (_view.getId()) {
                case R.id.btnAvail_FDCH:
                    checkAvailability(domain);
                    break;
                case R.id.btnWhoIs_FDCH:
                    checkWhoIs(domain);
                    break;
            }
        } else {
            showMessage(R.string.str_error, R.string.str_invalid_url);
        }
    }

    private final void checkAvailability(String _domain) {
        getSpiceManager()
                .execute(
                        new DomainAvailabilityCheckRequest(_domain),
                        new DomainAvailabilityCheckRequestListener(_domain)
                );
    }

    private final void checkWhoIs(String _domain) {
        getSpiceManager()
                .execute(
                        new DomainInfoCheckRequest(_domain),
                        new DomainInfoCheckRequestListener(_domain)
                );
    }

    @Override
    public void onOkPressed() {
        // Unimplemented method
    }

    private abstract class DomainCheckRequestListener<T> implements RequestListener<T> {
        protected String mDomain;

        DomainCheckRequestListener(String _domain) {
            mDomain = _domain;
        }

        public void onRequestFailure(SpiceException spiceException) {
            progressDialogManager.hideProgressDialog();
            Toast.makeText(getActivity(), spiceException.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private final class DomainAvailabilityCheckRequestListener
            extends DomainCheckRequestListener<DomainAvailabilityCheckResponseModel> {

        DomainAvailabilityCheckRequestListener(String _domain) {
            super(_domain);
        }

        @Override
        public void onRequestSuccess(DomainAvailabilityCheckResponseModel _str) {
            progressDialogManager.hideProgressDialog();
            if (_str.availableStatus.equals(ServerConstants.AVAILABLE)) {
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.flContainer_HA, DomainIsAvailableFragment.newInstance())
                        .addToBackStack(null)
                        .commit();
            } else if (_str.availableStatus.equals(ServerConstants.NOT_AVAILABLE)) {
                showFormattedMessage(R.string.str_error, R.string.str_url_not_avail, mDomain);
            }
        }
    }

    private final class DomainInfoCheckRequestListener
            extends DomainCheckRequestListener<DomainInfoCheckResponseModel> {

        DomainInfoCheckRequestListener(String _domain) {
            super(_domain);
        }

        @Override
        public void onRequestSuccess(DomainInfoCheckResponseModel domainInfoCheckResponse) {
            progressDialogManager.hideProgressDialog();
            if (!domainInfoCheckResponse.urlData.equals("No Data Found\r\n")) {
                getFragmentManager()
                        .beginTransaction()
                        .replace(
                                R.id.flContainer_HA,
                                DomainInfoFragment.newInstance(domainInfoCheckResponse.urlData))
                        .addToBackStack(null)
                        .commit();
            } else {
                showFormattedMessage(R.string.str_error, R.string.str_url_doesnot_exist, mDomain);
            }
        }
    }
}

