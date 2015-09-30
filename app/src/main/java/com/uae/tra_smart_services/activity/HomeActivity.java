package com.uae.tra_smart_services.activity;

import android.app.FragmentManager.OnBackStackChangedListener;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

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
import com.uae.tra_smart_services.fragment.ComplainAboutServiceFragment;
import com.uae.tra_smart_services.fragment.ComplainAboutTraFragment;
import com.uae.tra_smart_services.fragment.DeviceApprovalFragment;
import com.uae.tra_smart_services.fragment.DomainCheckerFragment;
import com.uae.tra_smart_services.fragment.DomainInfoFragment;
import com.uae.tra_smart_services.fragment.DomainIsAvailableFragment;
import com.uae.tra_smart_services.fragment.EnquiriesFragment;
import com.uae.tra_smart_services.fragment.FavoritesFragment;
import com.uae.tra_smart_services.fragment.FavoritesFragment.OnFavoritesEventListener;
import com.uae.tra_smart_services.fragment.HexagonHomeFragment;
import com.uae.tra_smart_services.fragment.HexagonHomeFragment.OnHeaderStaticServiceSelectedListener;
import com.uae.tra_smart_services.fragment.HexagonHomeFragment.OnOpenUserProfileClickListener;
import com.uae.tra_smart_services.fragment.HexagonHomeFragment.OnServiceSelectListener;
import com.uae.tra_smart_services.fragment.HexagonHomeFragment.OnStaticServiceSelectListener;
import com.uae.tra_smart_services.fragment.InfoHubFragment;
import com.uae.tra_smart_services.fragment.InnovationsFragment;
import com.uae.tra_smart_services.fragment.MobileVerificationFragment;
import com.uae.tra_smart_services.fragment.MobileVerificationFragment.OnDeviceVerifiedListener;
import com.uae.tra_smart_services.fragment.MobileVerifiedInfoFragment;
import com.uae.tra_smart_services.fragment.NotificationsFragment;
import com.uae.tra_smart_services.fragment.PoorCoverageFragment;
import com.uae.tra_smart_services.fragment.SearchFragment;
import com.uae.tra_smart_services.fragment.ServiceInfoFragment;
import com.uae.tra_smart_services.fragment.SettingsFragment;
import com.uae.tra_smart_services.fragment.SettingsFragment.OnOpenAboutTraClickListener;
import com.uae.tra_smart_services.fragment.SmsBlockNumberFragment;
import com.uae.tra_smart_services.fragment.SmsReportFragment;
import com.uae.tra_smart_services.fragment.SmsServiceListFragment.OnSmsServiceSelectListener;
import com.uae.tra_smart_services.fragment.SpeedTestFragment;
import com.uae.tra_smart_services.fragment.SuggestionFragment;
import com.uae.tra_smart_services.fragment.base.BaseFragment;
import com.uae.tra_smart_services.fragment.spam.ReportSmsSpamFragment;
import com.uae.tra_smart_services.fragment.spam.ReportSpamFragment;
import com.uae.tra_smart_services.fragment.spam.ReportSpamFragment.OnReportSpamServiceSelectListener;
import com.uae.tra_smart_services.fragment.spam.ReportSpamFragment.SpamOption;
import com.uae.tra_smart_services.fragment.spam.ReportWebSpamFragment;
import com.uae.tra_smart_services.fragment.spam.SpamHistoryFragment;
import com.uae.tra_smart_services.fragment.spam.SpamHistoryFragment.OnAddToSpamClickListener;
import com.uae.tra_smart_services.fragment.spam.SpamHistoryFragment.SpamType;
import com.uae.tra_smart_services.fragment.tutorial.TutorialContainerFragment;
import com.uae.tra_smart_services.fragment.user_profile.ChangePasswordFragment;
import com.uae.tra_smart_services.fragment.user_profile.EditUserProfileFragment;
import com.uae.tra_smart_services.fragment.user_profile.ResetPasswordFragment;
import com.uae.tra_smart_services.fragment.user_profile.UserProfileFragment;
import com.uae.tra_smart_services.fragment.user_profile.UserProfileFragment.OnUserProfileClickListener;
import com.uae.tra_smart_services.fragment.user_profile.UserProfileFragment.UserProfileAction;
import com.uae.tra_smart_services.global.C;
import com.uae.tra_smart_services.global.HeaderStaticService;
import com.uae.tra_smart_services.global.Service;
import com.uae.tra_smart_services.global.SmsService;
import com.uae.tra_smart_services.interfaces.OnActivateTutorialListener;
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
        OnOpenUserProfileClickListener, OnUserProfileClickListener, OnHeaderStaticServiceSelectedListener,
        OnOpenAboutTraClickListener, OnReportSpamServiceSelectListener, OnAddToSpamClickListener,
        OnActivateTutorialListener, OnDeviceVerifiedListener {

    private static final String KEY_CHECKED_TAB_ID = "CHECKED_TAB_ID";
    private static final String KEY_PREVIOUS_CHECKED_TAB_ID = "PREVIOUS_CHECKED_TAB_ID";
    private static final String KEY_SETTINGS_CHANGE_PROCESSED = "SETTINGS_CHANGE_PROCESSED";

    private static final String TAG = "HomeActivity";
    protected static final int REQUEST_CHECK_SETTINGS = 1000;

    private Toolbar mToolbar;
    private RadioGroup bottomNavRadios;

    @IdRes
    private int mCheckedTabId, mPreviousCheckedTabId;

    private boolean mSettingsChangeProcessed;

    @Override
    public final void onCreate(final Bundle _savedInstanceState) {
        getFragmentManager().addOnBackStackChangedListener(this);

        super.onCreate(_savedInstanceState);
        setContentView(R.layout.activity_home);

        if (_savedInstanceState != null) {
            mCheckedTabId = _savedInstanceState.getInt(KEY_CHECKED_TAB_ID);
            mPreviousCheckedTabId = _savedInstanceState.getInt(KEY_PREVIOUS_CHECKED_TAB_ID);
            mSettingsChangeProcessed = _savedInstanceState.getBoolean(KEY_SETTINGS_CHANGE_PROCESSED);
        }

        mToolbar = findView(R.id.toolbar);
        setSupportActionBar(mToolbar);

        bottomNavRadios = findView(R.id.rgBottomNavRadio_AH);
        bottomNavRadios.setOnCheckedChangeListener(this);

        if (!mSettingsChangeProcessed && getIntent().getBooleanExtra(SettingsFragment.CHANGED, false)) {
            mSettingsChangeProcessed = true;
            bottomNavRadios.check(R.id.rbSettings_BNRG);
        } else if (getFragmentManager().findFragmentById(getContainerId()) == null) {
            bottomNavRadios.check(R.id.rbHome_BNRG);
            showTutorialIfNeed();
        }

        onBackStackChanged();
    }

    private void showTutorialIfNeed() {
        if (!PreferenceManager.getDefaultSharedPreferences(this).contains(C.NOT_FIRST_START)) {
            PreferenceManager.getDefaultSharedPreferences(this).edit().
                    putBoolean(C.NOT_FIRST_START, true).apply();
            showTutorial();
        }
    }

    private void showTutorial() {
        getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack(null)
                .add(R.id.rlGlobalContainer_AH, TutorialContainerFragment.newInstance())
                .commit();
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
    public <T> void onServiceSelect(final Service _service, @Nullable T _data) {
        onServiceSelect(_service, _data, true);
    }

    public <T> void onServiceSelect(final Service _service, @Nullable T _data, final boolean _useBackStack) {
        switch (_service) {
            case DOMAIN_CHECK:
                replaceFragment(DomainCheckerFragment.newInstance(), _useBackStack);
                break;
            case DOMAIN_CHECK_INFO:
                replaceFragment(DomainInfoFragment.newInstance((DomainInfoCheckResponseModel) _data), _useBackStack);
                break;
            case DOMAIN_CHECK_AVAILABILITY:
                replaceFragment(DomainIsAvailableFragment.newInstance((DomainAvailabilityCheckResponseModel) _data), _useBackStack);
                break;
            case COMPLAIN_ABOUT_PROVIDER:
                openFragmentIfAuthorized(ComplainAboutServiceFragment.newInstance(), _service, _useBackStack);
                break;
            case ENQUIRIES:
                openFragmentIfAuthorized(EnquiriesFragment.newInstance(), _service, _useBackStack);
                break;
            case COMPLAINT_ABOUT_TRA:
                openFragmentIfAuthorized(ComplainAboutTraFragment.newInstance(), _service, _useBackStack);
                break;
            case SUGGESTION:
                openFragmentIfAuthorized(SuggestionFragment.newInstance(), _service, _useBackStack);
                break;
//            case HELP_SALIM:
//                replaceFragment(HelpSalemFragment.newInstance(), _useBackStack);
//                break;
            case APPROVED_DEVICES:
                replaceFragment(ApprovedDevicesFragment.newInstance(), _useBackStack);
                break;
            case SMS_SPAM:
                replaceFragment(ReportSpamFragment.newInstance(), _useBackStack);
                break;
            case MOBILE_VERIFICATION:
                replaceFragment(MobileVerificationFragment.newInstance(), _useBackStack);
                break;
            case POOR_COVERAGE:
                replaceFragment(PoorCoverageFragment.newInstance(), _useBackStack);
                break;
            case INTERNET_SPEEDTEST:
                replaceFragment(SpeedTestFragment.newInstance(), _useBackStack);
                break;
        }
    }

    private void openFragmentIfAuthorized(final BaseFragment _fragment, final Enum _service) {
        openFragmentIfAuthorized(_fragment, _service, true);
    }

    private void openFragmentIfAuthorized(final BaseFragment _fragment, final Enum _service, final boolean _useBackStack) {
        if (TRAApplication.isLoggedIn()) {
            replaceFragment(_fragment, _useBackStack);
        } else {
            Intent intent = AuthorizationActivity.getStartForResultIntent(this, _service);
            intent.putExtra(C.USE_BACK_STACK, _useBackStack);
            startActivityForResult(intent, C.REQUEST_CODE_LOGIN);
        }
    }

    @Override
    protected void onActivityResult(final int _requestCode, final int _resultCode, final Intent _data) {
        super.onActivityResult(_requestCode, _resultCode, _data);

        switch (_requestCode) {
            case C.REQUEST_CODE_LOGIN:
                handleLoginResult(_resultCode, _data);
                break;
            case REQUEST_CHECK_SETTINGS:
                Log.i(TAG, "User agreed to make required location settings changes.");
                getFragmentManager()
                        .findFragmentById(R.id.flContainer_AH)
                        .onActivityResult(_requestCode, _resultCode, _data);
                break;
        }
    }

    private void handleLoginResult(int _resultCode, Intent _data) {
        final boolean uncheckTabIfNotLogged = _data.getBooleanExtra(C.UNCHECK_TAB_IF_NOT_LOGGED_IN, false);
        if (_resultCode == C.LOGIN_SUCCESS) {
            final Enum fragmentType = (Enum) _data.getSerializableExtra(C.FRAGMENT_FOR_REPLACING);
            final boolean useBackStack = _data.getBooleanExtra(C.USE_BACK_STACK, true);
            if (uncheckTabIfNotLogged) {
                mPreviousCheckedTabId = 0;
                clearBackStack();
            }
            openFragmentAfterLogin(fragmentType, useBackStack);
        } else if (uncheckTabIfNotLogged) {
            bottomNavRadios.check(mPreviousCheckedTabId);
            mPreviousCheckedTabId = 0;
        }
    }

    private void openFragmentAfterLogin(final Enum _fragmentType, final boolean _useBackStack) {
        if (_fragmentType instanceof Service) {
            onServiceSelect((Service) _fragmentType, null, _useBackStack);
        } else if (_fragmentType instanceof FragmentType) {
            openFragmentAfterLogin((FragmentType) _fragmentType, _useBackStack);
        }
    }

    private void openFragmentAfterLogin(final FragmentType _fragmentType, final boolean _useBackStack) {
        switch (_fragmentType) {
            case USER_PROFILE:
                replaceFragment(UserProfileFragment.newInstance(), _useBackStack);
                break;
            case REPORT_SMS_SPAM:
                replaceFragment(ReportSmsSpamFragment.newInstance(), _useBackStack);
                break;
            case REPORT_WEB_SPAM:
                replaceFragment(ReportWebSpamFragment.newInstance(), _useBackStack);
                break;
            case SPAM_REPORT_HISTORY:
                replaceFragment(SpamHistoryFragment.newInstance(), _useBackStack);
                break;
            case ENQUIRIES:
                replaceFragment(EnquiriesFragment.newInstance(), _useBackStack);
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
                replaceFragmentWithBackStack(ReportSpamFragment.newInstance());
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
    public void onDeviceSelect(int _selectedBrandLogoRes, final SearchDeviceResponseModel.List _device) {
        replaceFragmentWithBackStack(DeviceApprovalFragment.newInstance(_selectedBrandLogoRes, _device));
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
    public void onSmsServiceChildSelect(final SmsService _service) {
        switch (_service) {
            case REPORT:
                // not implement
                replaceFragmentWithBackStack(SmsReportFragment.newInstance());
                break;
            case BLOCK:
                // not implement
                replaceFragmentWithBackStack(SmsBlockNumberFragment.newInstance());
                break;
        }
    }

    @Override
    public final void onReportSpamServiceSelect(@SpamOption int _service) {
        switch (_service) {
            case ReportSpamFragment.SPAM_OPTION_REPORT_SMS:
                openFragmentIfAuthorized(ReportSmsSpamFragment.newInstance(), FragmentType.REPORT_SMS_SPAM);
                break;
            case ReportSpamFragment.SPAM_OPTION_REPORT_WEB:
                openFragmentIfAuthorized(ReportWebSpamFragment.newInstance(), FragmentType.REPORT_WEB_SPAM);
                break;
            case ReportSpamFragment.SPAM_OPTION_REPORT_HISTORY:
                openFragmentIfAuthorized(SpamHistoryFragment.newInstance(), FragmentType.SPAM_REPORT_HISTORY);
                break;
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        Log.d(getClass().getSimpleName() + "log", "onCheckedChanged");

        if (mCheckedTabId == checkedId) {
            Log.d(getClass().getSimpleName() + "log", "onCheckedChanged mCheckedTabId");
            return;
        } else if (mPreviousCheckedTabId == checkedId) {
            Log.d(getClass().getSimpleName() + "log", "onCheckedChanged mPreviousCheckedTabId");
            mCheckedTabId = checkedId;
            return;
        }
        mPreviousCheckedTabId = 0;

        Log.d(getClass().getSimpleName() + "log", "onCheckedChanged: new check " + getResources().getResourceEntryName(checkedId));

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
                openEnquiriesIfAuthorized(false);
                break;
            case R.id.rbSettings_BNRG:
                clearBackStack();
                replaceFragmentWithOutBackStack(SettingsFragment.newInstance());
                break;
        }
        mCheckedTabId = checkedId;
    }

    private void openEnquiriesIfAuthorized(final boolean _useBackStack) {
        if (TRAApplication.isLoggedIn()) {
            clearBackStack();
            replaceFragment(EnquiriesFragment.newInstance(), _useBackStack);
        } else {
            mPreviousCheckedTabId = mCheckedTabId;
            Intent intent = AuthorizationActivity.getStartForResultIntent(this, FragmentType.ENQUIRIES);
            intent.putExtra(C.USE_BACK_STACK, _useBackStack);
            intent.putExtra(C.UNCHECK_TAB_IF_NOT_LOGGED_IN, true);
            startActivityForResult(intent, C.REQUEST_CODE_LOGIN);
        }
    }

    @Override
    public final void onAddFavoritesClick() {
        replaceFragmentWithBackStack(AddServiceFragment.newInstance());
    }

    @Override
    public final void onAddToSpamClick(@SpamType final int _spamType) {
        switch (_spamType) {
            case SpamHistoryFragment.SPAM_SMS:
                openFragmentIfAuthorized(ReportSmsSpamFragment.newInstance(), FragmentType.REPORT_SMS_SPAM);
                break;
            case SpamHistoryFragment.SPAM_WEB:
                openFragmentIfAuthorized(ReportWebSpamFragment.newInstance(), FragmentType.REPORT_WEB_SPAM);
                break;
        }
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
        addFragmentWithBackStackGlobally(ServiceInfoFragment.newInstance(_item));
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
        switch (_service) {
            case INNOVATIONS:
//                onServiceSelect(Service.SUGGESTION, null);
                replaceFragmentWithBackStack(InnovationsFragment.newInstance());
                break;
            case NOTIFICATION:
                addFragmentWithBackStackGlobally(NotificationsFragment.newInstance());
                break;
            case SEARCH:
                addFragmentWithBackStackGlobally(SearchFragment.newInstance());
                break;
        }
    }

    @Override
    public void onActivateTutorial() {
        clearBackStack();
        replaceFragmentWithOutBackStack(HexagonHomeFragment.newInstance());
        bottomNavRadios.check(R.id.rbHome_BNRG);
        showTutorial();
    }

    @Override
    public void onDeviceVerified(SearchDeviceResponseModel.List _device) {
        replaceFragmentWithBackStack(MobileVerifiedInfoFragment.newInstance(_device));
    }

    @Override
    protected void onSaveInstanceState(final Bundle _outState) {
        _outState.putBoolean(KEY_SETTINGS_CHANGE_PROCESSED, mSettingsChangeProcessed);
        _outState.putInt(KEY_CHECKED_TAB_ID, mCheckedTabId);
        _outState.putInt(KEY_PREVIOUS_CHECKED_TAB_ID, mPreviousCheckedTabId);
        super.onSaveInstanceState(_outState);
    }
}
