package com.uae.tra_smart_services.fragment;

import android.Manifest;
import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.content.IntentSender;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.uae.tra_smart_services.R;
import com.uae.tra_smart_services.customviews.LoaderView;
import com.uae.tra_smart_services.dialog.AlertDialogFragment.OnOkListener;
import com.uae.tra_smart_services.dialog.SingleChoiceDialog;
import com.uae.tra_smart_services.dialog.SingleChoiceDialog.OnItemPickListener;
import com.uae.tra_smart_services.entities.Permission;
import com.uae.tra_smart_services.entities.PermissionManager;
import com.uae.tra_smart_services.entities.PermissionManager.OnPermissionRequestSuccessListener;
import com.uae.tra_smart_services.fragment.base.BaseServiceFragment;
import com.uae.tra_smart_services.global.LocationType;
import com.uae.tra_smart_services.global.Service;
import com.uae.tra_smart_services.interfaces.Loader;
import com.uae.tra_smart_services.interfaces.OnOpenPermissionExplanationDialogListener;
import com.uae.tra_smart_services.rest.model.request.PoorCoverageRequestModel;
import com.uae.tra_smart_services.rest.robo_requests.GeoLocationRequest;
import com.uae.tra_smart_services.rest.robo_requests.PoorCoverageRequest;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit.client.Response;

/**
 * Created by ak-buffalo on 11.08.15.
 */
public class PoorCoverageFragment extends BaseServiceFragment implements //region INTERFACES
        OnOkListener, OnItemPickListener,
        ConnectionCallbacks, OnConnectionFailedListener,
        OnSeekBarChangeListener, OnClickListener, ResultCallback<LocationSettingsResult>,
        LocationListener, OnPermissionRequestSuccessListener, OnOpenPermissionExplanationDialogListener {
    //endregion

    //region CONSTANTS
    private static final String TAG = "PoorCoverageFragment";
    private static final int REQUEST_CHECK_SETTINGS = 1000;
    private static final long UPDATE_INTERVAL_IN_MILLISECONDS = 10000;
    private static final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS = UPDATE_INTERVAL_IN_MILLISECONDS / 2;
    private static final int LOCATION_PERMISSION_REQUEST = 0;
    private static final List<Permission> LOCATION_PERMISSION_LIST = new ArrayList<>();

    static {
        LOCATION_PERMISSION_LIST.add(
                new Permission(Manifest.permission.ACCESS_FINE_LOCATION, R.string.fragment_poor_coverage_location_permission_explanation));
    }
    //endregion

    //region MEMBERS
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private LocationSettingsRequest mLocationSettingsRequest;
    private Location mCurrentLocation;
    private Boolean mRequestingLocationUpdates = false;
    private String mLastUpdateTime = "";
    private PoorCoverageRequest mPoorCoverageRequest;
    private PoorCoverageRequestModel mLocationModel = new PoorCoverageRequestModel();

    private PermissionManager mLocationPermissionManager;
    //endregion

    //region VIEWS
    private SingleChoiceDialog locationTypeChooser;
    private TextView tvSignalLevel;
    private EditText etLocation;
    private SeekBar sbPoorCoverage;
    private ProgressBar sbProgressBar;
    //endregion

    public static PoorCoverageFragment newInstance() {
        return new PoorCoverageFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        mLocationPermissionManager = new PermissionManager(getActivity(), LOCATION_PERMISSION_LIST, this);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_send, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_send:
                hideKeyboard(tvSignalLevel);
                collectDataAdnSendToServer();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void initViews() {
        super.initViews();
        etLocation = findView(R.id.etLocation_FPC);
        etLocation.clearFocus();
        setCapitalizeTextWatcher(etLocation);
        sbPoorCoverage = findView(R.id.sbPoorCoverage_FPC);
        sbProgressBar = findView(R.id.pbFindLoc_FPC);
        tvSignalLevel = findView(R.id.tvSignalLevel_FPC);
        tvSignalLevel.setText(getResources().getStringArray(R.array.fragment_poor_coverage_signal_levels)[0]);
    }

    @Override
    protected void initListeners() {
        super.initListeners();
        mLocationPermissionManager.setRequestSuccessListener(this);
        etLocation.setOnClickListener(this);
        sbPoorCoverage.setOnSeekBarChangeListener(this);
        etLocation.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    locationTypeChooser = SingleChoiceDialog.newInstance(PoorCoverageFragment.this,
                            R.string.str_select_location_type, LocationType.toStringResArray());
                    locationTypeChooser.show(getFragmentManager());
                }
            }
        });
    }

    @Override
    protected void initData() {
        super.initData();
        buildGoogleApiClient();
        createLocationRequest();
        buildLocationSettingsRequest();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            mLocationPermissionManager.onRestoreInstanceState(savedInstanceState);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }
    }

    @Override
    public final void onOpenPermissionExplanationDialog(String _explanation) {
        showMessage(_explanation);
    }

    @Override
    public final void onOkPressed(final int _mMessageId) {
        mLocationPermissionManager.onConfirmPermissionExplanationDialog(this, LOCATION_PERMISSION_REQUEST);
    }

    @Override
    public void onRequestPermissionsResult(int _requestCode, @NonNull String[] _permissions, @NonNull int[] _grantResults) {
        if (_requestCode == LOCATION_PERMISSION_REQUEST) {
            if (!mLocationPermissionManager.onRequestPermissionsResult(this, _permissions, _grantResults)) {
                super.onRequestPermissionsResult(_requestCode, _permissions, _grantResults);
            }
        } else {
            super.onRequestPermissionsResult(_requestCode, _permissions, _grantResults);
        }
    }

    @Override
    public final void onPermissionRequestSuccess(Fragment _fragment) {
        checkLocationSettings();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mGoogleApiClient != null) {
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CHECK_SETTINGS:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        Log.i(TAG, "User agreed to make required location settings changes.");
                        startLocationUpdates();
                        break;
                    case Activity.RESULT_CANCELED:
                        Log.i(TAG, "User chose not to make required location settings changes.");
                        sbProgressBar.setVisibility(View.INVISIBLE);
                        break;
                }
                break;
        }
    }

    protected synchronized void buildGoogleApiClient() {
        Log.i(TAG, "Building GoogleApiClient");
        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    protected void createLocationRequest() {
        mLocationRequest = LocationRequest.create();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);
        mLocationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS);
    }

    protected void buildLocationSettingsRequest() {
        mLocationSettingsRequest =
                new LocationSettingsRequest.Builder()
                        .addLocationRequest(mLocationRequest)
                        .setAlwaysShow(true)
                        .build();
    }

    protected void checkLocationSettings() {
        sbProgressBar.setVisibility(View.VISIBLE);
        PendingResult<LocationSettingsResult> result =
                LocationServices.SettingsApi.checkLocationSettings(
                        mGoogleApiClient,
                        mLocationSettingsRequest
                );
        result.setResultCallback(this);
    }

    public void onResult(LocationSettingsResult locationSettingsResult) {
        final Status status = locationSettingsResult.getStatus();
        switch (status.getStatusCode()) {
            case LocationSettingsStatusCodes.SUCCESS:
                Log.i(TAG, "Starting location update.");
                startLocationUpdates();
                break;
            case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                Log.i(TAG, "Location settings are not satisfied. Show the location settings dialog");
                try {
                    status.startResolutionForResult(getActivity(), 1000);
                } catch (IntentSender.SendIntentException e) {
                    // Ignore the error.
                }
                break;
            case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                Log.i(TAG, "Location settings are inadequate, and cannot be fixed here");
                break;
        }
    }

    protected void startLocationUpdates() {
        LocationServices.FusedLocationApi.requestLocationUpdates(
                mGoogleApiClient,
                mLocationRequest,
                this
        ).setResultCallback(new ResultCallback<Status>() {
            public void onResult(Status status) {
                mRequestingLocationUpdates = true;
            }
        });
    }

    protected void stopLocationUpdates() {
        LocationServices.FusedLocationApi.removeLocationUpdates(
                mGoogleApiClient,
                this
        ).setResultCallback(new ResultCallback<Status>() {
            public void onResult(Status status) {
                mRequestingLocationUpdates = false;
            }
        });
    }

    private void collectDataAdnSendToServer() {
        mLocationModel.setAddress(etLocation.getText().toString());
        mLocationModel.setSignalLevel(sbProgressBar.getProgress() + 1);
        if (TextUtils.isEmpty(mLocationModel.getAddress()) && mLocationModel.getLocation() == null) {
            showMessage(R.string.str_location_error, R.string.str_location_error_message);
            return;
        }

        loaderOverlayShow(getString(R.string.str_sending), this);
        loaderOverlayButtonBehavior(new Loader.BackButton() {
            @Override
            public void onBackButtonPressed(LoaderView.State _currentState) {
                getFragmentManager().popBackStack();
                if (_currentState == LoaderView.State.FAILURE || _currentState == LoaderView.State.SUCCESS) {
                    getFragmentManager().popBackStack();
                }
            }
        });
        getSpiceManager().execute(
                mPoorCoverageRequest = new PoorCoverageRequest(
                        mLocationModel
                ),
                new PoorCoverageRequestListener()
        );
    }

    private void defineUserFriendlyAddress() {
        final Geocoder geocoder = new Geocoder(getActivity().getBaseContext(), Locale.getDefault());
        getSpiceManager().execute(
                new GeoLocationRequest(geocoder, mCurrentLocation),
                new GeoLocationRequestListener()
        );
    }

    @Override
    public void onLocationChanged(Location location) {
        mCurrentLocation = location;
        defineUserFriendlyAddress();
        mLastUpdateTime = DateFormat.getTimeInstance().format(new Date());
        stopLocationUpdates();
    }

    @Override
    public void onConnected(Bundle connectionHint) {
        Log.i(TAG, "Connection succeed");
    }

    @Override
    public void onConnectionSuspended(int cause) {
        Log.i(TAG, "Connection suspended");
    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        Log.i(TAG, "Connection failed: ErrorCode = " + result.getErrorCode());
    }

    @Override
    public void onClick(View _view) {
        switch (_view.getId()) {
            case R.id.etLocation_FPC:
                locationTypeChooser = SingleChoiceDialog.newInstance(this, R.string.str_select_location_type, LocationType.toStringResArray());
                locationTypeChooser.show(getFragmentManager());
                break;
        }
    }

    @Override
    public void onItemPicked(int _dialogItem) {
        switch (LocationType.values()[_dialogItem]) {
            case AUTO:
                hideKeyboard(tvSignalLevel);
                checkLocationSettingsIfPermissionGranted();
                break;
            case MANUAL:
                etLocation.requestFocus();
                etLocation.setText(getString(R.string.str_empty));
                break;
        }
    }

    private void checkLocationSettingsIfPermissionGranted() {
        if (mLocationPermissionManager.isAllPermissionsChecked()) {
            checkLocationSettings();
        } else {
            mLocationPermissionManager.requestUncheckedPermissions(this, LOCATION_PERMISSION_REQUEST);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        mLocationPermissionManager.onSaveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        tvSignalLevel.setText(getResources().getStringArray(R.array.fragment_poor_coverage_signal_levels)[progress]);
        mLocationModel.setSignalLevel(progress + 1);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        // Unhandled callback
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        // Unhandled callback
    }

    @Override
    public void onLoadingCanceled() {
        if (getSpiceManager().isStarted() && mPoorCoverageRequest != null) {
            getSpiceManager().cancel(mPoorCoverageRequest);
        }
    }

    @Override
    public void onDestroy() {
        mLocationPermissionManager = null;
        super.onDestroy();
    }

    private class PoorCoverageRequestListener implements RequestListener<Response> {

        @Override
        public void onRequestFailure(SpiceException spiceException) {
            processError(spiceException);
        }

        @Override
        public void onRequestSuccess(Response poorCoverageRequestModel) {
            boolean isDialog = loaderDialogDismiss();
            switch (poorCoverageRequestModel.getStatus()) {
                case 200:
                    if (isDialog) {
                        showMessage(R.string.str_success, R.string.str_data_has_been_sent);
                    } else {
                        loaderOverlaySuccess(getString(R.string.str_data_has_been_sent));
                    }
                    break;
                case 400:
                    if (isDialog) {
                        showMessage(R.string.str_error, R.string.str_something_went_wrong);
                    } else {
                        loaderOverlayCancelled(getString(R.string.str_something_went_wrong));
                    }
                    break;
                case 500:
                    if (isDialog) {
                        showMessage(R.string.str_error, R.string.str_request_failed);
                    } else {
                        loaderOverlayCancelled(getString(R.string.str_request_failed));
                    }
                    break;
            }
        }
    }

    private class GeoLocationRequestListener implements RequestListener<Address> {

        @Override
        public void onRequestFailure(SpiceException spiceException) {
            processError(spiceException);
            sbProgressBar.setVisibility(View.INVISIBLE);
        }

        @Override
        public void onRequestSuccess(Address address) {
            String userFriendlyAddress = new StringBuilder()
                    .append(address.getLocality()).append(", ")
                    .append(address.getThoroughfare()).append(", ")
                    .append(address.getSubThoroughfare()).append(", ")
                    .append(address.getAdminArea()).append(", ")
                    .append(address.getCountryName()).append(", ")
                    .append(address.getCountryCode())
                    .toString();
            etLocation.setOnClickListener(PoorCoverageFragment.this);
            etLocation.setText(userFriendlyAddress);
            sbProgressBar.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    protected String getServiceName() {
        return "complain Poor Coverage";
    }

    @Nullable
    @Override
    protected Service getServiceType() {
        return Service.POOR_COVERAGE;
    }

    @Override
    protected int getTitle() {
        return R.string.str_signal_coverage;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_poor_coverage;
    }
}
