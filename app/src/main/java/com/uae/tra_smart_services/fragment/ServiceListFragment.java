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
import android.widget.RadioGroup;
import android.widget.Toast;

import com.uae.tra_smart_services.R;
import com.uae.tra_smart_services.adapter.ServiceListAdapter;
import com.uae.tra_smart_services.fragment.base.BaseFragment;
import com.uae.tra_smart_services.global.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by mobimaks on 10.08.2015.
 */
public class ServiceListFragment extends BaseFragment
                            implements OnClickListener, OnQueryTextListener,
                                    OnItemClickListener, RadioGroup.OnCheckedChangeListener {

    public static ServiceListFragment newInstance() {
        return new ServiceListFragment();
    }

    private OnServiceSelectListener mServiceSelectListener;
    @Override
    public void onAttach(Activity _activity) {
        super.onAttach(_activity);
        if (_activity instanceof OnServiceSelectListener) {
            mServiceSelectListener = (OnServiceSelectListener) _activity;
        }
        if (_activity instanceof )
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    private ListView lvServiceList;
    private RadioGroup bottomNavRadios;
    @Override
    protected void initViews() {
        super.initViews();
        lvServiceList = findView(R.id.lvServiceList_FSL);
        bottomNavRadios = findView(R.id.rgBottomNavRadio_AH);
    }

    @Override
    protected void initListeners() {
        super.initListeners();
        lvServiceList.setOnItemClickListener(this);
        bottomNavRadios.setOnCheckedChangeListener(this);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initServiceList();
    }

    private ServiceListAdapter mAdapter;

    private void initServiceList() {
        List<Service> serviceList = new ArrayList<>(Arrays.asList(Service.values()));
        mAdapter = new ServiceListAdapter(getActivity(), serviceList);
        lvServiceList.setAdapter(mAdapter);
    }

    private SearchView svSearchService;
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_search, menu);
        svSearchService = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.action_search));
        svSearchService.setOnQueryTextListener(this);
    }

    @Override
    protected int getTitle() {
        return R.string.fragment_service_list_title;
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
            case R.id.action_search:
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

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        // find which radio button is selected
        switch (checkedId){
            case R.id.rbHome_BNRG:
                Toast.makeText(getActivity(), "choice: Home",
                        Toast.LENGTH_SHORT).show();
                break;
            case R.id.rbIndex_BNRG:
                Toast.makeText(getActivity(), "choice: Index",
                        Toast.LENGTH_SHORT).show();
                break;
            case R.id.rbCRM_BNRG:
                Toast.makeText(getActivity(), "choice: CRM",
                        Toast.LENGTH_SHORT).show();
                break;
            case R.id.rbSettings_BNRG:
//                replaceFragmentWithBackStack(SettingsFragment.newInstance());
                break;
        }
    }

}