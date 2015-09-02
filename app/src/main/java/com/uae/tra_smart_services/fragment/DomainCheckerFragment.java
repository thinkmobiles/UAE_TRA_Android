package com.uae.tra_smart_services.fragment;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.uae.tra_smart_services.R;
import com.uae.tra_smart_services.dialog.AlertDialogFragment;
import com.uae.tra_smart_services.entities.CustomFilterPool;
import com.uae.tra_smart_services.entities.Filter;
import com.uae.tra_smart_services.fragment.base.BaseServiceFragment;
import com.uae.tra_smart_services.global.ServerConstants;
import com.uae.tra_smart_services.rest.model.response.DomainAvailabilityCheckResponseModel;
import com.uae.tra_smart_services.rest.model.response.DomainInfoCheckResponseModel;
import com.uae.tra_smart_services.rest.robo_requests.DomainAvailabilityCheckRequest;
import com.uae.tra_smart_services.rest.robo_requests.DomainInfoCheckRequest;
import com.uae.tra_smart_services.util.ImageUtils;

/**
 * Created by ak-buffalo on 10.08.15.
 */
public class DomainCheckerFragment extends BaseServiceFragment
        implements View.OnClickListener, AlertDialogFragment.OnOkListener {

//    private ImageView ivLogo;
    private Button btnAvail, btnWhoIS;
    private EditText etDomainAvail;

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

    @Override
    protected final void initViews() {
        super.initViews();
//        ivLogo = findView(R.id.ivLogo_FDC);
//        ivLogo.setImageDrawable(ImageUtils.getFilteredDrawable(ivLogo.getContext(), R.drawable.tra_logo));
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
            showProgressDialog(getString(R.string.str_checking), this);
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
    private DomainAvailabilityCheckRequest mDomainAvailabilityCheckRequest;
    private final void checkAvailability(String _domain) {
        mDomainAvailabilityCheckRequest = new DomainAvailabilityCheckRequest(_domain);
        getSpiceManager()
                .execute(
                        mDomainAvailabilityCheckRequest,
                        new DomainAvailabilityCheckRequestListener(_domain)
                );
    }

    private DomainInfoCheckRequest mDomainInfoCheckRequest;
    private final void checkWhoIs(String _domain) {
        mDomainInfoCheckRequest = new DomainInfoCheckRequest(_domain);
        getSpiceManager()
                .execute(
                        mDomainInfoCheckRequest,
                        new DomainInfoCheckRequestListener(_domain)
                );
    }

    @Override
    public void onOkPressed() {
        // Unimplemented method
        // Used exceptionally to specify OK button in dialog
    }

    @Override
    public void onDialogCancel() {
        if(getSpiceManager().isStarted()){
            if(mDomainAvailabilityCheckRequest != null){
                getSpiceManager().cancel(mDomainAvailabilityCheckRequest);
            }
            if(mDomainInfoCheckRequest != null){
                getSpiceManager().cancel(mDomainInfoCheckRequest);
            }
        }
    }

    private abstract class DomainCheckRequestListener<T> implements RequestListener<T> {
        protected String mDomain;

        DomainCheckRequestListener(String _domain) {
            mDomain = _domain;
        }

        public void onRequestFailure(SpiceException spiceException) {
            processError(spiceException);
        }
    }

    private final class DomainAvailabilityCheckRequestListener
            extends DomainCheckRequestListener<DomainAvailabilityCheckResponseModel> {

        DomainAvailabilityCheckRequestListener(String _domain) {
            super(_domain);
        }

        @Override
        public void onRequestSuccess(DomainAvailabilityCheckResponseModel _str) {
            hideProgressDialog();
            if (_str.availableStatus.equals(ServerConstants.AVAILABLE)) {
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.flContainer_AH, DomainIsAvailableFragment.newInstance())
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
            hideProgressDialog();
            if (!domainInfoCheckResponse.urlData.equals("No Data Found\r\n")) {
                getFragmentManager()
                        .beginTransaction()
                        .replace(
                                R.id.flContainer_AH,
                                DomainInfoFragment.newInstance(domainInfoCheckResponse.urlData))
                        .addToBackStack(null)
                        .commit();
            } else {
                showFormattedMessage(R.string.str_error, R.string.str_url_doesnot_exist, mDomain);
            }
        }
    }
}

