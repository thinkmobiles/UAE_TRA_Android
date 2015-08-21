package com.uae.tra_smart_services.activity;

import android.app.FragmentManager.OnBackStackChangedListener;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.uae.tra_smart_services.R;
import com.uae.tra_smart_services.activity.base.BaseFragmentActivity;
import com.uae.tra_smart_services.customviews.HexagonalButtonsLayout;
import com.uae.tra_smart_services.fragment.ApprovedDevicesFragment;
import com.uae.tra_smart_services.fragment.ApprovedDevicesFragment.OnDeviceSelectListener;
import com.uae.tra_smart_services.fragment.ComplainAboutServiceFragment;
import com.uae.tra_smart_services.fragment.HelpSalemFragment;
import com.uae.tra_smart_services.fragment.HexagonHomeFragment;
import com.uae.tra_smart_services.fragment.InfoHubFragment;
import com.uae.tra_smart_services.fragment.PoorCoverageFragment;
import com.uae.tra_smart_services.fragment.ComplainAboutTraFragment;
import com.uae.tra_smart_services.fragment.DeviceApprovalFragment;
import com.uae.tra_smart_services.fragment.DomainCheckerFragment;
import com.uae.tra_smart_services.fragment.EnquiriesFragment;
import com.uae.tra_smart_services.fragment.MobileVerificationFragment;
import com.uae.tra_smart_services.fragment.SettingsFragment;
import com.uae.tra_smart_services.fragment.SmsBlockNumberFragment;
import com.uae.tra_smart_services.fragment.SmsReportFragment;
import com.uae.tra_smart_services.fragment.SmsServiceListFragment;
import com.uae.tra_smart_services.fragment.SmsServiceListFragment.OnSmsServiceSelectListener;
import com.uae.tra_smart_services.fragment.SuggestionFragment;
import com.uae.tra_smart_services.global.Service;
import com.uae.tra_smart_services.global.SmsService;
import com.uae.tra_smart_services.interfaces.OnReloadData;
import com.uae.tra_smart_services.interfaces.ToolbarTitleManager;
import com.uae.tra_smart_services.rest.model.response.SearchDeviceResponseModel;

import retrofit.RetrofitError;

/**
 * Created by Andrey Korneychuk on 23.07.15.
 */
public class HomeActivity extends BaseFragmentActivity
        implements ToolbarTitleManager, HexagonHomeFragment.OnServiceSelectListener,
        OnDeviceSelectListener, OnBackStackChangedListener,
        OnSmsServiceSelectListener, HexagonHomeFragment.OnStaticServiceSelectListener,
        RadioGroup.OnCheckedChangeListener {

    private Toolbar mToolbar;
    private RadioGroup bottomNavRadios;

    @Override
    public final void onCreate(final Bundle _savedInstanceState) {
        getFragmentManager().addOnBackStackChangedListener(this);

        super.onCreate(_savedInstanceState);
        setContentView(R.layout.activity_home);

        mToolbar = findView(R.id.toolbar);
        setSupportActionBar(mToolbar);

        bottomNavRadios = findView(R.id.rgBottomNavRadio_AH);
        bottomNavRadios.setOnCheckedChangeListener(this);

        if (getIntent().getBooleanExtra(SettingsFragment.CHANGED, false)){
            replaceFragmentWithOutBackStack(SettingsFragment.newInstance());
            bottomNavRadios.check(R.id.rbSettings_BNRG);
        } else if (getFragmentManager().findFragmentById(getContainerId()) == null) {
            addFragment(HexagonHomeFragment.newInstance());
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
        switch (_service) {
            case DOMAIN_CHECK:
                replaceFragmentWithBackStack(DomainCheckerFragment.newInstance());
                break;
            case COMPLAIN_ABOUT_PROVIDER:
                replaceFragmentWithBackStack(ComplainAboutServiceFragment.newInstance());
                break;
            case ENQUIRIES:
                replaceFragmentWithBackStack(EnquiriesFragment.newInstance());
                break;
            case COMPLAINT_ABOUT_TRA:
                replaceFragmentWithBackStack(ComplainAboutTraFragment.newInstance());
                break;
            case SUGGESTION:
                replaceFragmentWithBackStack(SuggestionFragment.newInstance());
                break;
            case HELP_SALIM:
                replaceFragmentWithBackStack(HelpSalemFragment.newInstance());
                break;
            case APPROVED_DEVICES:
                replaceFragmentWithBackStack(ApprovedDevicesFragment.newInstance());
                break;
        }
    }

    @Override
    public void onStaticServiceSelect(HexagonalButtonsLayout.StaticService _service) {
        switch (_service) {
            case VERIFICATION_SERVICE:
                replaceFragmentWithBackStack(MobileVerificationFragment.newInstance());
                break;
            case SMS_SPAM_SERVICE:
                replaceFragmentWithBackStack(SmsServiceListFragment.newInstance());
                break;
            case POOR_COVERAGE_SERVICE:
                replaceFragmentWithBackStack(PoorCoverageFragment.newInstance());
                break;
            case INTERNET_SPEED_TEST:
                Toast.makeText(getApplicationContext(), "Not implemented yet.",
                        Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void onDeviceSelect(final SearchDeviceResponseModel.List _device) {
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
        return R.id.flContainer_AH;
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
                replaceFragmentWithBackStack(SmsBlockNumberFragment.newInstance());
                break;
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.rbHome_BNRG:
                clearBackStack();
                replaceFragmentWithOutBackStack(HexagonHomeFragment.newInstance());
                break;
            case R.id.rbFavorites_BNRG:
                Toast.makeText(getApplicationContext(), "choice: Favorites",
                        Toast.LENGTH_SHORT).show();
                break;
            case R.id.rbInfoHub_BNRG:
                clearBackStack();
                replaceFragmentWithOutBackStack(InfoHubFragment.newInstance());
                break;
            case R.id.rbInquiries_BNRG:
                Toast.makeText(getApplicationContext(), "choice: Inquiries",
                        Toast.LENGTH_SHORT).show();
                break;
            case R.id.rbSettings_BNRG:
                clearBackStack();
                replaceFragmentWithOutBackStack(SettingsFragment.newInstance());
                break;
        }
    }

    @Override
    public void setToolbarVisibility(boolean _isVisible) {
        if (_isVisible) {
            getSupportActionBar().show();
        } else {
            getSupportActionBar().hide();
        }
    }
}
