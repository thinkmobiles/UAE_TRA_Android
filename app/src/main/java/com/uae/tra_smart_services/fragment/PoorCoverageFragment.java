package com.uae.tra_smart_services.fragment;

import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.squareup.picasso.Downloader;
import com.uae.tra_smart_services.R;
import com.uae.tra_smart_services.dialog.AlertDialogFragment;
import com.uae.tra_smart_services.dialog.CustomSingleChoiceDialog;
import com.uae.tra_smart_services.dialog.ProgressDialog;
import com.uae.tra_smart_services.entities.C;
import com.uae.tra_smart_services.fragment.base.BaseFragment;
import com.uae.tra_smart_services.global.LocationType;
import com.uae.tra_smart_services.rest.model.new_request.PoorCoverageRequestModel;
import com.uae.tra_smart_services.rest.model.new_request.SmsSpamRequestModel;
import com.uae.tra_smart_services.rest.new_request.PoorCoverageRequest;
import com.uae.tra_smart_services.rest.new_request.SmsSpamRequest;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import retrofit.client.Response;

/**
 * Created by ak-buffalo on 11.08.15.
 */
public class PoorCoverageFragment extends BaseFragment
        implements AlertDialogFragment.OnOkListener, CustomSingleChoiceDialog.OnItemPickListener,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
        SeekBar.OnSeekBarChangeListener, View.OnClickListener{

    private LocationType mLocationType;

    public static PoorCoverageFragment newInstance() {
        return new PoorCoverageFragment();
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_poor_coverage;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    private EditText etLocation;
    private SeekBar sbPoorCoverage;
    private ProgressBar sbProgressBar;
    @Override
    protected void initViews() {
        super.initViews();
        etLocation = findView(R.id.etLocation_FPC);
        etLocation.clearFocus();
        sbPoorCoverage = findView(R.id.sbPoorCoverage_FPC);
        sbProgressBar = findView(R.id.pbFindLoc_FPC);
        sbProgressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void initListeners() {
        super.initListeners();
        etLocation.setOnClickListener(this);
        sbPoorCoverage.setOnSeekBarChangeListener(this);
    }

    private GoogleApiClient mGoogleApiClient;
    private PoorCoverageRequestModel mLocationModel = new PoorCoverageRequestModel();
    CustomSingleChoiceDialog locationTypeChooser;
    @Override
    protected void initCustomEntities() {
        super.initCustomEntities();
        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
        etLocation.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    (locationTypeChooser = CustomSingleChoiceDialog
                            .newInstance(PoorCoverageFragment.this))
                            .setTitle("Please select location type")
                            .setBodyItems(LocationType.toStringArray())
                            .show(getFragmentManager());
                }
            }
        });
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
                collectDataAdnSendToServer();
            break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void collectDataAdnSendToServer(){
        mLocationModel.setAddress(etLocation.getText().toString());
        if (TextUtils.isEmpty(mLocationModel.getAddress()) &&
                mLocationModel.getLocation() == null) {
            showMessage(R.string.str_location_error, R.string.str_location_error_message);
            return;
        }
        if (mLocationModel.getSignalLevel() == 0) {
            showMessage(R.string.str_signal_level, R.string.signal_level_error);
            return;
        }

        progressDialogManager.showProgressDialog(getString(R.string.str_checking));
        getSpiceManager().execute(
                new PoorCoverageRequest(
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
                    showLocationSettings();
                }
                break;
            case MANUAL:
                etLocation.requestFocus();
                break;
        }
    }

    private void showLocationSettings() {
        sbProgressBar.setVisibility(View.VISIBLE);
        etLocation.setOnClickListener(null);
        final LocationRequest locationRequest = new LocationRequest();
        locationRequest.setNumUpdates(1);
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, locationRequest, new LocationListener() {
            @Override
            public void onLocationChanged(Location _location) {
                sbProgressBar.setVisibility(View.INVISIBLE);
                mLocationModel.setLocation(
                        String.valueOf(_location.getLatitude()),
                        String.valueOf(_location.getLongitude())
                );
                Address address = getAddress(_location);
                etLocation.setOnClickListener(PoorCoverageFragment.this);
                etLocation.setText(address.toString());
                LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
            }
        });
    }

    private Address getAddress(Location _location){
        List<Address> addresses = null;
        try {
            Geocoder geocoder = new Geocoder(getActivity(), Locale.getDefault());
            addresses = geocoder.getFromLocation(_location.getLatitude(), _location.getLongitude(), 1);
        } catch (IOException e) {
            // currently this exeption won't be handled
        } finally {
            Toast.makeText(getActivity(), getString(R.string.str_something_went_wrong), Toast.LENGTH_LONG);
        }
        return (addresses != null && addresses.size() != 0) ? addresses.get(0) : null;
    }

    @Override
    public void onConnected(Bundle bundle) {
        if (LocationType.AUTO.equals(mLocationType)) {
            showLocationSettings();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
    }

    @Override
    public void onClick(View _view) {
        switch (_view.getId()){
            case R.id.etLocation_FPC:
                locationTypeChooser
                        .setTitle("Please select location type")
                        .setBodyItems(LocationType.toStringArray())
                        .show(getFragmentManager());
                break;
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        mLocationModel.setSignalLevel(progress);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    private class PoorCoverageRequestListener implements RequestListener<Response> {

        @Override
        public void onRequestFailure(SpiceException spiceException) {
            progressDialogManager.hideProgressDialog();
            showMessage(R.string.str_error, R.string.str_request_failed);
        }

        @Override
        public void onRequestSuccess(Response poorCoverageRequestModel) {
            progressDialogManager.hideProgressDialog();
            switch (poorCoverageRequestModel.getStatus()){
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
}
