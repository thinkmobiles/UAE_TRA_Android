package com.uae.tra_smart_services.activity;

import android.app.Fragment;
import android.app.FragmentManager.OnBackStackChangedListener;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

import com.uae.tra_smart_services.R;
import com.uae.tra_smart_services.TRAApplication;
import com.uae.tra_smart_services.activity.base.BaseFragmentActivity;
import com.uae.tra_smart_services.customviews.HexagonalButtonsLayout;
import com.uae.tra_smart_services.entities.FragmentType;
import com.uae.tra_smart_services.fragment.AboutTraFragment;
import com.uae.tra_smart_services.fragment.AddServiceFragment;
import com.uae.tra_smart_services.fragment.AddServiceFragment.OnFavoriteServicesSelectedListener;
import com.uae.tra_smart_services.fragment.ApprovedDevicesFragment;
import com.uae.tra_smart_services.fragment.ApprovedDevicesFragment.OnDeviceSelectListener;
import com.uae.tra_smart_services.fragment.ChangePasswordFragment;
import com.uae.tra_smart_services.fragment.ComplainAboutServiceFragment;
import com.uae.tra_smart_services.fragment.ComplainAboutTraFragment;
import com.uae.tra_smart_services.fragment.DeviceApprovalFragment;
import com.uae.tra_smart_services.fragment.DomainCheckerFragment;
import com.uae.tra_smart_services.fragment.DomainInfoFragment;
import com.uae.tra_smart_services.fragment.DomainIsAvailableFragment;
import com.uae.tra_smart_services.fragment.EditUserProfileFragment;
import com.uae.tra_smart_services.fragment.EnquiriesFragment;
import com.uae.tra_smart_services.fragment.FavoritesFragment;
import com.uae.tra_smart_services.fragment.FavoritesFragment.OnFavoritesEventListener;
import com.uae.tra_smart_services.fragment.HelpSalemFragment;
import com.uae.tra_smart_services.fragment.HexagonHomeFragment;
import com.uae.tra_smart_services.fragment.HexagonHomeFragment.OnOpenUserProfileClickListener;
import com.uae.tra_smart_services.fragment.HexagonHomeFragment.OnServiceSelectListener;
import com.uae.tra_smart_services.fragment.HexagonHomeFragment.OnStaticServiceSelectListener;
import com.uae.tra_smart_services.fragment.InfoHubFragment;
import com.uae.tra_smart_services.fragment.MobileVerificationFragment;
import com.uae.tra_smart_services.fragment.NotificationsFragment;
import com.uae.tra_smart_services.fragment.PoorCoverageFragment;
import com.uae.tra_smart_services.fragment.ResetPasswordFragment;
import com.uae.tra_smart_services.fragment.SearchFragment;
import com.uae.tra_smart_services.fragment.ServiceInfoFragment;
import com.uae.tra_smart_services.fragment.SettingsFragment;
import com.uae.tra_smart_services.fragment.SmsBlockNumberFragment;
import com.uae.tra_smart_services.fragment.SmsReportFragment;
import com.uae.tra_smart_services.fragment.SmsServiceListFragment;
import com.uae.tra_smart_services.fragment.SmsServiceListFragment.OnSmsServiceSelectListener;
import com.uae.tra_smart_services.fragment.SpeedTestFragment;
import com.uae.tra_smart_services.fragment.SuggestionFragment;
import com.uae.tra_smart_services.fragment.UserProfileFragment;
import com.uae.tra_smart_services.fragment.UserProfileFragment.OnUserProfileClickListener;
import com.uae.tra_smart_services.fragment.UserProfileFragment.UserProfileAction;
import com.uae.tra_smart_services.fragment.base.BaseFragment;
import com.uae.tra_smart_services.global.C;
import com.uae.tra_smart_services.global.HeaderStaticService;
import com.uae.tra_smart_services.global.Service;
import com.uae.tra_smart_services.global.SmsService;
import com.uae.tra_smart_services.interfaces.ToolbarTitleManager;
import com.uae.tra_smart_services.rest.model.response.DomainAvailabilityCheckResponseModel;
import com.uae.tra_smart_services.rest.model.response.DomainInfoCheckResponseModel;
import com.uae.tra_smart_services.rest.model.response.SearchDeviceResponseModel;

import java.util.List;

/**
 * Created by Andrey Korneychuk on 23.07.15.
 */
public class HomeActivity extends BaseFragmentActivity
        implements ToolbarTitleManager, OnServiceSelectListener, OnDeviceSelectListener,
        OnBackStackChangedListener, OnSmsServiceSelectListener, OnStaticServiceSelectListener,
        OnCheckedChangeListener, OnFavoritesEventListener, OnFavoriteServicesSelectedListener,
        OnOpenUserProfileClickListener, OnUserProfileClickListener, HexagonHomeFragment.OnHeaderStaticServiceSelectedListener,
        SettingsFragment.OnOpenAboutTraClickListener {

    private static final String TAG = "HomeActivity";
    protected static final int REQUEST_CHECK_SETTINGS = 1000;

    private Toolbar mToolbar;
    private RadioGroup bottomNavRadios;
    private BaseFragment mFragmentForReplacing;
    private ImageView ivBackground;


    @Override
    public final void onCreate(final Bundle _savedInstanceState) {
        getFragmentManager().addOnBackStackChangedListener(this);

        super.onCreate(_savedInstanceState);
        setContentView(R.layout.activity_home);

        mToolbar = findView(R.id.toolbar);
        setSupportActionBar(mToolbar);

        bottomNavRadios = findView(R.id.rgBottomNavRadio_AH);
        bottomNavRadios.setOnCheckedChangeListener(this);

        if (getIntent().getBooleanExtra(SettingsFragment.CHANGED, false)) {
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
    public <T> void onServiceSelect(Service _service, @Nullable T _data) {
        switch (_service) {
            case DOMAIN_CHECK:
                replaceFragmentWithBackStack(DomainCheckerFragment.newInstance());
                break;
            case DOMAIN_CHECK_INFO:
                replaceFragmentWithBackStack(DomainInfoFragment.newInstance((DomainInfoCheckResponseModel) _data));
                break;
            case DOMAIN_CHECK_AVAILABILITY:
                replaceFragmentWithBackStack(DomainIsAvailableFragment.newInstance((DomainAvailabilityCheckResponseModel) _data));
                break;
            case COMPLAIN_ABOUT_PROVIDER:
                openFragmentIfAuthorized(ComplainAboutServiceFragment.newInstance(), _service);
                break;
            case ENQUIRIES:
                openFragmentIfAuthorized(EnquiriesFragment.newInstance(), _service);
                break;
            case COMPLAINT_ABOUT_TRA:
                openFragmentIfAuthorized(ComplainAboutTraFragment.newInstance(), _service);
                break;
            case SUGGESTION:
                openFragmentIfAuthorized(SuggestionFragment.newInstance(), _service);
                break;
            case HELP_SALIM:
                replaceFragmentWithBackStack(HelpSalemFragment.newInstance());
                break;
            case APPROVED_DEVICES:
                replaceFragmentWithBackStack(ApprovedDevicesFragment.newInstance());
                break;
            case SMS_SPAM:
                replaceFragmentWithBackStack(SmsServiceListFragment.newInstance());
                break;
            case MOBILE_VERIFICATION:
                replaceFragmentWithBackStack(MobileVerificationFragment.newInstance());
                break;
            case POOR_COVERAGE:
                replaceFragmentWithBackStack(PoorCoverageFragment.newInstance());
                break;
        }
    }

    private void openFragmentIfAuthorized(final BaseFragment _fragment, final Service _service) {
        if (TRAApplication.isLoggedIn()) {
            replaceFragmentWithBackStack(_fragment);
        } else {
            Intent intent = AuthorizationActivity.getStartForResultIntent(this, _service);
            startActivityForResult(intent, C.REQUEST_CODE_LOGIN);
        }
    }

    @Override
    protected void onActivityResult(final int _requestCode, final int _resultCode, final Intent _data) {
        super.onActivityResult(_requestCode, _resultCode, _data);

        switch (_requestCode){
            case C.REQUEST_CODE_LOGIN:
                if (_resultCode == C.LOGIN_SUCCESS) {
                    openFragmentAfterLogin(_data);
                }
                break;
            case REQUEST_CHECK_SETTINGS:
                Log.i(TAG, "User agreed to make required location settings changes.");
                getFragmentManager()
                        .findFragmentById(R.id.flContainer_AH)
                        .onActivityResult(_requestCode, _resultCode, _data);
                break;
        }
    }

    private void openFragmentAfterLogin(final Intent _data) {
        final Enum fragmentType = (Enum) _data.getSerializableExtra(C.FRAGMENT_FOR_REPLACING);
        if (fragmentType instanceof Service) {
            onServiceSelect((Service) fragmentType, null);
        } else {
            switch (((FragmentType) fragmentType)) {
                case USER_PROFILE:
                    replaceFragmentWithBackStack(UserProfileFragment.newInstance());
                    break;
            }
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
                replaceFragmentWithBackStack(SpeedTestFragment.newInstance());
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
                clearBackStack();
                replaceFragmentWithOutBackStack(FavoritesFragment.newInstance());
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
    public final void onAddFavoritesClick() {
        replaceFragmentWithBackStack(AddServiceFragment.newInstance());
    }

    @Override
    public final void onOpenServiceClick(final Service _service) {
        onServiceSelect(_service, null);
    }

    @Override
    public final void onFavoriteServicesSelected(final List<Service> _items) {
        getFragmentManager().popBackStackImmediate();
        final FavoritesFragment favoritesFragment = (FavoritesFragment) getFragmentManager().findFragmentById(getContainerId());
        Log.d("Favorites", "Selected items count: " + _items.size());
        favoritesFragment.addServicesToFavorites(_items);
    }

    @Override
    public final void onOpenUserProfileClick() {
        if (TRAApplication.isLoggedIn()) {
            replaceFragmentWithBackStack(UserProfileFragment.newInstance());
        } else {
            Intent intent = AuthorizationActivity.getStartForResultIntent(this, FragmentType.USER_PROFILE);
            startActivityForResult(intent, C.REQUEST_CODE_LOGIN);
        }
    }

    @Override
    public final void onUserProfileItemClick(@UserProfileAction int _profileItem) {
        switch (_profileItem) {
            case UserProfileFragment.USER_PROFILE_EDIT_PROFILE:
                replaceFragmentWithBackStack(EditUserProfileFragment.newInstance());
                break;
            case UserProfileFragment.USER_PROFILE_CHANGE_PASSWORD:
                replaceFragmentWithBackStack(ChangePasswordFragment.newInstance());
                break;
            case UserProfileFragment.USER_PROFILE_RESET_PASSWORD:
                replaceFragmentWithBackStack(ResetPasswordFragment.newInstance());
                break;
        }
    }

    @Override
    public void onOpenServiceInfo(int _position, Service _item) {
        getFragmentManager().beginTransaction()
                .addToBackStack(null)
                .add(R.id.rlGlobalContainer_AH, ServiceInfoFragment.newInstance())
                .commit();
    }

    @Override
    public void onOpenAboutTraClick() {
        replaceFragmentWithBackStack(AboutTraFragment.newInstance());
    }

    @Override
    public void setToolbarVisibility(boolean _isVisible) {
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            if (_isVisible) {
                actionBar.show();
            } else {
                actionBar.hide();
            }
        }
    }

    @Override
    public void onHeaderStaticServiceSelected(HeaderStaticService _service) {
        Fragment fragment = null;
        switch(_service){
            case NOTIFICATION:
                fragment = NotificationsFragment.newInstance();
                break;
            case SEARCH:
                fragment = SearchFragment.newInstance();
                break;
        }
        if(fragment != null){
            getFragmentManager()
                .beginTransaction()
                .addToBackStack(null)
                .add(R.id.rlGlobalContainer_AH, fragment)
                .commit();
        }
    }
}
