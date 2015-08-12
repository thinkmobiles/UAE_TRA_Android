package com.uae.tra_smart_services.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.SearchView.OnQueryTextListener;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.uae.tra_smart_services.R;
import com.uae.tra_smart_services.adapter.DevicesListAdapter;
import com.uae.tra_smart_services.fragment.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mobimaks on 11.08.2015.
 */
public final class ApprovedDevicesFragment extends BaseFragment implements OnItemClickListener, OnQueryTextListener {

    private ListView lvDevices;
    private SearchView svSearchView;

    private DevicesListAdapter mAdapter;
    private OnDeviceSelectListener mSelectListener;

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
    public void onStart() {
        super.onStart();
        getToolbarTitleManager().setTitle(R.string.approved_devices);
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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (mSelectListener != null) {
            mSelectListener.onDeviceSelect(mAdapter.getItem(position));
        }
    }

    @Override
    public void onDetach() {
        mSelectListener = null;
        super.onDetach();
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_approved_devices;
    }

    public interface OnDeviceSelectListener {
        void onDeviceSelect(final String _device);
    }
}
