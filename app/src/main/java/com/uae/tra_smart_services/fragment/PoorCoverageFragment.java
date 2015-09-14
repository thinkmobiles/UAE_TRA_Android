package com.uae.tra_smart_services.fragment;

import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
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
import android.widget.Toast;

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
import com.uae.tra_smart_services.dialog.AlertDialogFragment.OnOkListener;
import com.uae.tra_smart_services.dialog.SingleChoiceDialog;
import com.uae.tra_smart_services.dialog.SingleChoiceDialog.OnItemPickListener;
import com.uae.tra_smart_services.fragment.base.BaseServiceFragment;
import com.uae.tra_smart_services.global.LocationType;
import com.uae.tra_smart_services.rest.model.request.PoorCoverageRequestModel;
import com.uae.tra_smart_services.rest.robo_requests.GeoLocationRequest;
import com.uae.tra_smart_services.rest.robo_requests.PoorCoverageRequest;

import java.util.Locale;

import retrofit.client.Response;

/**
 * Created by ak-buffalo on 11.08.15.
 */
public class PoorCoverageFragment extends BaseServiceFragment
        implements OnOkListener, OnItemPickListener,
        ConnectionCallbacks, OnConnectionFailedListener,
        OnSeekBarChangeListener, OnClickListener, ResultCallback<LocationSettingsResult> {

    private LocationType mLocationType;
    private TextView tvSignalLevel;

    private EditText etLocation;
    private SeekBar sbPoorCoverage;
    private ProgressBar sbProgressBar;

    private PoorCoverageRequest mPoorCoverageRequest;

    public static PoorCoverageFragment newInstance() {
        return new PoorCoverageFragment();
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_poor_coverage;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    protected void initViews() {
        super.initViews();
        etLocation = findView(R.id.etLocation_FPC);
        etLocation.clearFocus();
        sbPoorCoverage = findView(R.id.sbPoorCoverage_FPC);
        sbProgressBar = findView(R.id.pbFindLoc_FPC);
        sbProgressBar.setVisibility(View.INVISIBLE);
        tvSignalLevel = findView(R.id.tvSignalLevel_FPC);
        tvSignalLevel.setText(getResources().getStringArray(R.array.fragment_poor_coverage_signal_levels)[0]);
    }

    @Override
    protected void initListeners() {
        super.initListeners();
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

    private GoogleApiClient mGoogleApiClient;
    private PoorCoverageRequestModel mLocationModel = new PoorCoverageRequestModel();
    SingleChoiceDialog locationTypeChooser;
    protected LocationRequest mLocationRequest;
    protected LocationSettingsRequest mLocationSettingsRequest;
    protected boolean mRequestingLocationUpdates;
    @Override
    protected void initData() {
        super.initData();
        /*mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();*/

        buildGoogleApiClient();
        createLocationRequest();
        buildLocationSettingsRequest();
        startLocationUpdates();
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }
    public static final long UPDATE_INTERVAL_IN_MILLISECONDS = 10000;
    public static final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS =
            UPDATE_INTERVAL_IN_MILLISECONDS / 2;

    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();

        // Sets the desired interval for active location updates. This interval is
        // inexact. You may not receive updates at all if no location sources are available, or
        // you may receive them slower than requested. You may also receive updates faster than
        // requested if other applications are requesting location at a faster interval.
        mLocationRequest.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);

        // Sets the fastest rate for active location updates. This interval is exact, and your
        // application will never receive updates faster than this value.
        mLocationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS);

        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    protected void buildLocationSettingsRequest() {
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(mLocationRequest);
        mLocationSettingsRequest = builder.build();
    }

    protected void startLocationUpdates() {
        LocationServices.FusedLocationApi.requestLocationUpdates(
                mGoogleApiClient,
                mLocationRequest,
                new LocationListener() {
                    @Override
                    public void onLocationChanged(Location _location) {
                        sbProgressBar.setVisibility(View.INVISIBLE);
                        mLocationModel.setLocation(
                                String.valueOf(_location.getLatitude()),
                                String.valueOf(_location.getLongitude())
                        );
                        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
                        defineUserFriendlyAddress(_location);
                    }
                }
        ).setResultCallback(new ResultCallback<Status>() {
            public void onResult(Status status) {
                mRequestingLocationUpdates = true;
                //setButtonsEnabledState();
            }
        });

    }


    protected void checkLocationSettings() {
        PendingResult<LocationSettingsResult> result =
                LocationServices.SettingsApi.checkLocationSettings(
                        mGoogleApiClient,
                        mLocationSettingsRequest
                );
        result.setResultCallback(this);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_send, menu);
    }

    @Override
    protected int getTitle() {
        return R.string.str_signal_coverage;
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

    private void collectDataAdnSendToServer() {
        mLocationModel.setAddress(etLocation.getText().toString());
        mLocationModel.setSignalLevel(sbProgressBar.getProgress() + 1);
        if (TextUtils.isEmpty(mLocationModel.getAddress()) &&
                mLocationModel.getLocation() == null) {
            showMessage(R.string.str_location_error, R.string.str_location_error_message);
            return;
        }

        showProgressDialog(getString(R.string.str_sending), this);
        getSpiceManager().execute(
                mPoorCoverageRequest = new PoorCoverageRequest(
                        mLocationModel
                ),
                new PoorCoverageRequestListener()
        );
    }

    @Override
    public void onOkPressed() {
        // Unimplemented method
        // Used exceptionally to specify OK button in dialog
    }

    @Override
    public void onItemPicked(int _dialogItem) {
        Toast.makeText(getActivity(), LocationType.toStringArray()[_dialogItem].toString(), Toast.LENGTH_LONG).show();
        mLocationType = LocationType.values()[_dialogItem];
        switch (LocationType.values()[_dialogItem]) {
            case AUTO:
                if (mGoogleApiClient.isConnected()) {
                    checkLocationSettings();
//                    showLocationSettings();
                }
                break;
            case MANUAL:
                etLocation.requestFocus();
                etLocation.setText(getString(R.string.str_empty));
                break;
        }
    }

    private void showLocationSettings() {
        sbProgressBar.setVisibility(View.VISIBLE);
        etLocation.setOnClickListener(null);
        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setNumUpdates(1);
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, new LocationListener() {
            @Override
            public void onLocationChanged(Location _location) {
                /*sbProgressBar.setVisibility(View.INVISIBLE);
                mLocationModel.setLocation(
                        String.valueOf(_location.getLatitude()),
                        String.valueOf(_location.getLongitude())
                );
                LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
                defineUserFriendlyAddress(_location);*/
            }
        });
    }

    private void defineUserFriendlyAddress(Location _location) {
        final Geocoder geocoder = new Geocoder(getActivity().getBaseContext(), Locale.getDefault());
        getSpiceManager().execute(
                new GeoLocationRequest(geocoder, _location),
                new GeoLocationRequestListener()
        );
    }

    @Override
    public void onConnected(Bundle bundle) {
        if (LocationType.AUTO.equals(mLocationType)) {
            showLocationSettings();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        int k = 0;
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        int i = 0;
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
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        tvSignalLevel.setText(getResources().getStringArray(R.array.fragment_poor_coverage_signal_levels)[progress]);
        mLocationModel.setSignalLevel(progress + 1);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onDialogCancel() {
        if (getSpiceManager().isStarted() && mPoorCoverageRequest != null) {
            getSpiceManager().cancel(mPoorCoverageRequest);
        }
    }

    @Override
    public void onResult(LocationSettingsResult locationSettingsResult) {
        final Status status = locationSettingsResult.getStatus();
        switch (status.getStatusCode()) {
            case LocationSettingsStatusCodes.SUCCESS:
                Log.i("AAAAAAAAAAAAa", "All location settings are satisfied.");
                startLocationUpdates();
                break;
            case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                Log.i("AAAAAAAAAAAAa", "Location settings are not satisfied. Show the user a dialog to" +
                        "upgrade location settings ");

               /* try {
                    // Show the dialog by calling startResolutionForResult(), and check the result
                    // in onActivityResult().
     //               status.startResolutionForResult(MainActivity.this, REQUEST_CHECK_SETTINGS);
                } catch (IntentSender.SendIntentException e) {
                    Log.i(TAG, "PendingIntent unable to execute request.");
                }*/
                break;
            case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                Log.i("AAAAAAAAAAAAa", "Location settings are inadequate, and cannot be fixed here. Dialog " +
                        "not created.");
                break;
        }
    }

    private class PoorCoverageRequestListener implements RequestListener<Response> {

        @Override
        public void onRequestFailure(SpiceException spiceException) {
            processError(spiceException);
        }

        @Override
        public void onRequestSuccess(Response poorCoverageRequestModel) {
            hideProgressDialog();
            switch (poorCoverageRequestModel.getStatus()) {
                case 200:
                    showMessage(R.string.str_success, R.string.str_data_has_been_sent);
                    break;
                case 400:
                    showMessage(R.string.str_error, R.string.str_something_went_wrong);
                    break;
                case 500:
                    showMessage(R.string.str_error, R.string.str_request_failed);
                    break;
            }
        }
    }

    private class GeoLocationRequestListener implements RequestListener<Address> {

        @Override
        public void onRequestFailure(SpiceException spiceException) {
            processError(spiceException);
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
        }
    }
    protected void stopLocationUpdates() {
        // It is a good practice to remove location requests when the activity is in a paused or
        // stopped state. Doing so helps battery performance and is especially
        // recommended in applications that request frequent location updates.
        LocationServices.FusedLocationApi.removeLocationUpdates(
                mGoogleApiClient,
                new LocationListener() {
                    @Override
                    public void onLocationChanged(Location location) {

                    }
                }
        ).setResultCallback(new ResultCallback<Status>() {
            public void onResult(Status status) {
                mRequestingLocationUpdates = false;
                //setButtonsEnabledState();
            }
        });
    }



/*

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }
*/

    @Override
    public void onResume() {
        super.onResume();
        // Within {@code onPause()}, we pause location updates, but leave the
        // connection to GoogleApiClient intact.  Here, we resume receiving
        // location updates if the user has requested them.
        if (mGoogleApiClient.isConnected() && mRequestingLocationUpdates) {
            startLocationUpdates();
        }
    }
/*
    @Override
    protected void onPause() {
        super.onPause();
        // Stop location updates to save battery, but don't disconnect the GoogleApiClient object.
        if (mGoogleApiClient.isConnected()) {
            stopLocationUpdates();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        mGoogleApiClient.disconnect();
    }*/
}
