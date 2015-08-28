package com.uae.tra_smart_services.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.SearchView.OnQueryTextListener;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.SpiceRequest;
import com.octo.android.robospice.request.listener.PendingRequestListener;
import com.uae.tra_smart_services.R;
import com.uae.tra_smart_services.adapter.DevicesListAdapter;
import com.uae.tra_smart_services.fragment.base.BaseFragment;
import com.uae.tra_smart_services.fragment.base.BaseServiceFragment;
import com.uae.tra_smart_services.rest.model.response.SearchDeviceResponseModel;
import com.uae.tra_smart_services.rest.robo_requests.SearchByBrandRequest;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mobimaks on 11.08.2015.
 */
public final class ApprovedDevicesFragment extends BaseServiceFragment implements OnItemClickListener, OnQueryTextListener {

    private static final String KEY_SEARCH_DEVICE_BY_BRAND_REQUEST = "SEARCH_DEVICE_BY_BRAND_REQUEST";

    private ListView lvDevices;
    private SearchView svSearchView;

    private DevicesListAdapter mAdapter;
    private OnDeviceSelectListener mSelectListener;
    private RequestResponseListener mRequestListener;

    public static ApprovedDevicesFragment newInstance() {
        return new ApprovedDevicesFragment();
    }

    @Override
    public void onAttach(Activity _activity) {
        super.onAttach(_activity);
        if (_activity instanceof OnDeviceSelectListener) {
            mSelectListener = (OnDeviceSelectListener) _activity;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    protected void initViews() {
        super.initViews();
        lvDevices = findView(R.id.lvDevices_FAD);
    }

    @Override
    protected void initListeners() {
        super.initListeners();
        mRequestListener = new RequestResponseListener();
        lvDevices.setOnItemClickListener(this);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initDevicesList();
    }

    private void initDevicesList() {
        List<String> stubData = new ArrayList<>();
        stubData.add("Apple");
        stubData.add("Samsung");
        stubData.add("BlackBerry");
        stubData.add("Nokia");
        stubData.add("HTC");
        stubData.add("All devices");

        mAdapter = new DevicesListAdapter(getActivity(), stubData);
        lvDevices.setAdapter(mAdapter);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_search, menu);
        svSearchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.action_search));
        svSearchView.setOnQueryTextListener(this);
    }

    @Override
    protected int getTitle() {
        return R.string.approved_devices;
    }

    @Override
    public void onStart() {
        super.onStart();
//        getSpiceManager().getFromCache(SearchDeviceResponseModel.List.class, KEY_SEARCH_DEVICE_BY_BRAND_REQUEST, DurationInMillis.ALWAYS_RETURNED, mRequestListener);
        getSpiceManager().addListenerIfPending(SearchDeviceResponseModel.List.class, KEY_SEARCH_DEVICE_BY_BRAND_REQUEST, mRequestListener);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        mAdapter.getFilter().filter(query);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        mAdapter.getFilter().filter(newText);
        return true;
    }
    SearchByBrandRequest request;
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (mSelectListener != null) {
            String selectedBrand = mAdapter.getItem(position);
            request = new SearchByBrandRequest(selectedBrand, 0, 100);
            showProgressDialog(getString(R.string.str_loading), this);
            getSpiceManager().execute(request, KEY_SEARCH_DEVICE_BY_BRAND_REQUEST, DurationInMillis.ALWAYS_EXPIRED, mRequestListener);
        }
    }

    @Override
    public void onDetach() {
        mSelectListener = null;
        super.onDetach();
    }

    @Override
    public void onDialogCancel() {
        if(getSpiceManager().isStarted()){
            getSpiceManager().cancel(request);
        }
    }

    private class RequestResponseListener implements PendingRequestListener<SearchDeviceResponseModel.List> {

        @Override
        public void onRequestNotFound() {
            Log.d(getClass().getSimpleName(), "Request Not Found. isAdded: " + isAdded());
        }

        @Override
        public void onRequestSuccess(SearchDeviceResponseModel.List result) {
            Log.d(getClass().getSimpleName(), "Success. isAdded: " + isAdded());
            if (isAdded()) {
                hideProgressDialog();
                if (result != null && mSelectListener!=null) {
                    mSelectListener.onDeviceSelect(result);
                }
            }
            getSpiceManager().removeDataFromCache(SearchDeviceResponseModel.List.class, KEY_SEARCH_DEVICE_BY_BRAND_REQUEST);
        }

        @Override
        public void onRequestFailure(SpiceException spiceException) {
            processError(spiceException);
            getSpiceManager().removeDataFromCache(SearchDeviceResponseModel.List.class, KEY_SEARCH_DEVICE_BY_BRAND_REQUEST);
        }
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_approved_devices;
    }

    public interface OnDeviceSelectListener {
        void onDeviceSelect(final SearchDeviceResponseModel.List _device);
    }
}
