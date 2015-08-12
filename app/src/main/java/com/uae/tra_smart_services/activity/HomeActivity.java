package com.uae.tra_smart_services.activity;

import android.app.FragmentManager.OnBackStackChangedListener;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.uae.tra_smart_services.R;
import com.uae.tra_smart_services.activity.base.BaseFragmentActivity;
import com.uae.tra_smart_services.fragment.DomainCheckerFragment;
import com.uae.tra_smart_services.fragment.ApprovedDevicesFragment;
import com.uae.tra_smart_services.fragment.ApprovedDevicesFragment.OnDeviceSelectListener;
import com.uae.tra_smart_services.fragment.ComplainAboutServiceFragment;
import com.uae.tra_smart_services.fragment.HelpSalemFragment;
import com.uae.tra_smart_services.fragment.PoorCoverageFragment;
import com.uae.tra_smart_services.fragment.ComplainAboutTraFragment;
import com.uae.tra_smart_services.fragment.DeviceApprovalFragment;
import com.uae.tra_smart_services.fragment.ServiceListFragment;
import com.uae.tra_smart_services.fragment.ServiceListFragment.OnServiceSelectListener;
import com.uae.tra_smart_services.fragment.SmsReportFragment;
import com.uae.tra_smart_services.fragment.SmsSpamFragment;
import com.uae.tra_smart_services.fragment.SmsSpamFragment.OnSmsServiceSelectListener;
import com.uae.tra_smart_services.global.Service;
import com.uae.tra_smart_services.global.SmsService;
import com.uae.tra_smart_services.interfaces.OnReloadData;
import com.uae.tra_smart_services.interfaces.ToolbarTitleManager;

import retrofit.RetrofitError;

/**
 * Created by Andrey Korneychuk on 23.07.15.
 */
public class HomeActivity extends BaseFragmentActivity
        implements ToolbarTitleManager, OnServiceSelectListener, 
        OnDeviceSelectListener, OnBackStackChangedListener, OnSmsServiceSelectListener {

    private Toolbar mToolbar;

    @Override
    public final void onCreate(final Bundle _savedInstanceState) {
        getFragmentManager().addOnBackStackChangedListener(this);

        super.onCreate(_savedInstanceState);
        setContentView(R.layout.activity_home);

        mToolbar = findView(R.id.toolbar);
        setSupportActionBar(mToolbar);

        if (getFragmentManager().findFragmentById(getContainerId()) == null) {
            addFragment(ServiceListFragment.newInstance());
        }
        onBackStackChanged();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                final boolean isBackStackEmpty = getFragmentManager().getBackStackEntryCount() == 0;
                if (!isBackStackEmpty) {
                    onBackPressed();
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onServiceSelect(Service _service) {
        //TODO: check if need login
        Toast.makeText(this, _service.toString(), Toast.LENGTH_SHORT).show();
        switch (_service) {
            case DOMAIN_CHECK:
                replaceFragmentWithBackStack(DomainCheckerFragment.newInstance());
                break;
            case COMPLAIN_ABOUT_PROVIDER:
                replaceFragmentWithBackStack(ComplainAboutServiceFragment.newInstance());
                break;
            case COMPLAINT_ABOUT_TRA:
            case ENQUIRIES:
            case SUGGESTION:
                replaceFragmentWithBackStack(ComplainAboutTraFragment.newInstance());
                break;
            case SMS_SPAM:
                replaceFragmentWithBackStack(SmsSpamFragment.newInstance());
                break;
            case POOR_COVERAGE:
                replaceFragmentWithBackStack(PoorCoverageFragment.newInstance());
                break;
            case HELP_SALIM:
                replaceFragmentWithBackStack(HelpSalemFragment.newInstance());
                break;
            case MOBILE_VERIFICATION:
                break;
            case APPROVED_DEVICES:
                replaceFragmentWithBackStack(ApprovedDevicesFragment.newInstance());
                break;
        }
    }

    @Override
    public void onDeviceSelect(final String _device) {
        replaceFragmentWithBackStack(DeviceApprovalFragment.newInstance(_device));
    }

    @Override
    public void onBackStackChanged() {
        final boolean isBackStackEmpty = getFragmentManager().getBackStackEntryCount() == 0;
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(!isBackStackEmpty);
            actionBar.setHomeButtonEnabled(isBackStackEmpty);
            if (isBackStackEmpty) {
                mToolbar.setNavigationIcon(R.mipmap.ic_launcher);
            }
        }
    }

    @Override
    protected int getContainerId() {
        return R.id.flContainer_HA;
    }

    @Override
    public void handleError(RetrofitError _error) {
    }

    @Override
    public void handleError(RetrofitError _error, OnReloadData _listener) {
    }

    @Override
    public void onSmsServiceChildSelect(SmsService _service) {
        switch (_service) {
            case REPORT:
                replaceFragmentWithBackStack(SmsReportFragment.newInstance());
                break;
            case BLOCK:
                // TODO implement logic of Block Number Service fragment loading
                break;
        }
    }
}
