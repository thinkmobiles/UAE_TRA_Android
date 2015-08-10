package com.uae.tra_smart_services.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.SearchView.OnQueryTextListener;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.uae.tra_smart_services.R;
import com.uae.tra_smart_services.adapter.ServiceListAdapter;
import com.uae.tra_smart_services.fragment.base.BaseFragment;
import com.uae.tra_smart_services.global.Service;
import com.uae.tra_smart_services.interfaces.ToolbarTitleManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by mobimaks on 10.08.2015.
 */
public class ServiceListFragment extends BaseFragment implements OnClickListener, OnQueryTextListener, OnItemClickListener {

    private SearchView svSearchService;
    private ListView lvServiceList;

    private ServiceListAdapter mAdapter;
    private OnServiceSelectListener mServiceSelectListener;
    private ToolbarTitleManager mToolbarTitleManager;

    public static ServiceListFragment newInstance() {
        return new ServiceListFragment();
    }

    @Override
    public void onAttach(Activity _activity) {
        super.onAttach(_activity);
        if (_activity instanceof OnServiceSelectListener) {
            mServiceSelectListener = (OnServiceSelectListener) _activity;
        }
        if (_activity instanceof ToolbarTitleManager) {
            mToolbarTitleManager = (ToolbarTitleManager) _activity;
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
        lvServiceList = findView(R.id.lvServiceList_FSL);
    }

    @Override
    protected void initListeners() {
        super.initListeners();
        lvServiceList.setOnItemClickListener(this);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initServiceList();
    }

    private void initServiceList() {
        List<Service> serviceList = new ArrayList<>(Arrays.asList(Service.values()));
        mAdapter = new ServiceListAdapter(getActivity(), serviceList);
        lvServiceList.setAdapter(mAdapter);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_services, menu);
        svSearchService = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.search_view));
        svSearchService.setOnQueryTextListener(this);
    }

    @Override
    public void onStart() {
        mToolbarTitleManager.setTitle(R.string.fragment_service_list_title);
        super.onStart();
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        mAdapter.getFilter().filter(newText);
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        mAdapter.getFilter().filter(query);
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.search_view:
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        final Service service = mAdapter.getItem(position);
        if (mServiceSelectListener != null) {
            mServiceSelectListener.onServiceSelect(service);
        }
    }

    @Override
    public void onDetach() {
        mToolbarTitleManager = null;
        mServiceSelectListener = null;
        super.onDetach();
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_service_list;
    }

    public interface OnServiceSelectListener {
        void onServiceSelect(final Service _service);
    }
}
