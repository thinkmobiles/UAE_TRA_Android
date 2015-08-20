package com.uae.tra_smart_services.rest.new_request;

import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.widget.Toast;

import com.uae.tra_smart_services.R;
import com.uae.tra_smart_services.rest.TRAServicesAPI;
import com.uae.tra_smart_services.rest.model.new_response.SearchDeviceResponseModel;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * Created by ak-buffalo on 17.08.15.
 */
public class GeoLocationRequest extends BaseRequest<Address, TRAServicesAPI>  {
    private Geocoder mGeoCoder;
    private Location mLocation;

    public GeoLocationRequest(final Geocoder _geoCoder, final Location _location) {
        super(Address.class, TRAServicesAPI.class);
        mGeoCoder = _geoCoder;
        mLocation = _location;
    }

    @Override
    public final Address loadDataFromNetwork() throws Exception {
        List<Address> addresses  = mGeoCoder.getFromLocation(mLocation.getLatitude(), mLocation.getLongitude(), 1);
        /*Toast.makeText(getActivity(), getString(R.string.str_something_went_wrong), Toast.LENGTH_LONG);*/
        return (addresses != null && addresses.size() != 0) ? addresses.get(0) : null;
    }
}
