package com.uae.tra_smart_services.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.uae.tra_smart_services.R;
import com.uae.tra_smart_services.adapter.SearchRecyclerViewAdapter;
import com.uae.tra_smart_services.entities.SearchResult;
import com.uae.tra_smart_services.fragment.base.BaseFragment;
import com.uae.tra_smart_services.global.Service;
import com.uae.tra_smart_services.fragment.HexagonHomeFragment.OnServiceSelectListener;

/**
 * Created by Andrey Korneychuk on 09.09.15.
 */
public class SearchFragment extends BaseFragment
        implements SearchRecyclerViewAdapter.OnSearchResultItemClickListener, TextWatcher, View.OnTouchListener, View.OnClickListener {

    private ImageView ivSearchClose;
    private EditText etSearch;
    private TextView tvNoSearchResult, tvHint;
    private RecyclerView rvSearchResultList;
    private static SearchResult INITIAL_DATA;
    private SearchRecyclerViewAdapter mAdapter;
    private OnServiceSelectListener mServiceSelectListener;

    public static SearchFragment newInstance() {
        return new SearchFragment();
    }

    @Override
    protected int getTitle() {
        return 0;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_search;
    }

    @Override
    public void onAttach(Activity _activity) {
        super.onAttach(_activity);
        if (_activity instanceof OnServiceSelectListener) {
            mServiceSelectListener = (OnServiceSelectListener) _activity;
        }
    }

    @Override
    protected void initData() {
        super.initData();
        INITIAL_DATA = new SearchResult(){
            {
                initFromServicesList(Service.getUniqueServices(), getActivity());
            }
        };
    }

    @Override
    protected void initViews() {
        super.initViews();
        ivSearchClose = findView(R.id.ivSearchClose_FS);
        tvNoSearchResult = findView(R.id.tvNoSearchResult_FS);
        tvHint = findView(R.id.tvHint_FS);
        etSearch = findView(R.id.etSearch_FS);
        rvSearchResultList = findView(R.id.rvSearchResultList_FS);
    }

    @Override
    protected void initListeners() {
        super.initListeners();
        ivSearchClose.setOnClickListener(this);
        etSearch.addTextChangedListener(this);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.setOnTouchListener(this);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mAdapter = new SearchRecyclerViewAdapter(getActivity(), INITIAL_DATA);
        mAdapter.setOnSearchResultItemClickListener(this);
        rvSearchResultList.setLayoutManager(
                new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false)
        );
        rvSearchResultList.setAdapter(mAdapter);
        mAdapter.getFilter().initFromAdapter(mAdapter);
    }

    @Override
    public void onSearchResultItemClicked(SearchResult.SearchResultItem _item) {
        if(mServiceSelectListener != null){
            getFragmentManager().popBackStackImmediate();
            mServiceSelectListener.onServiceSelect(_item.getBindedService(), null);
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return true;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {/**Not implemented method*/}

    @Override
    public void onTextChanged(CharSequence text, int start, int before, int count) {
        if (text.length() != 0) {
            mAdapter.getFilter().filter(text);
            rvSearchResultList.setVisibility(View.VISIBLE);
            tvNoSearchResult.setVisibility(View.GONE);
            tvHint.setVisibility(View.VISIBLE);
        } else {
            mAdapter.clear();
            rvSearchResultList.setVisibility(View.GONE);
            tvNoSearchResult.setVisibility(View.VISIBLE);
            tvHint.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {/**Not implemented method*/}

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivSearchClose_FS:
                hideKeyboard(v);
                getFragmentManager().popBackStackImmediate();
                break;
        }
    }

    @Override
    protected void setToolbarVisibility() {
        toolbarTitleManager.setToolbarVisibility(false);
    }
}
