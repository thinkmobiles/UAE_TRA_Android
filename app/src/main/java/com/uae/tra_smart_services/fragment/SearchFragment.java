package com.uae.tra_smart_services.fragment;

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

    public static SearchFragment newInstance() {
        return new SearchFragment();
    }

    @Override
    protected int getTitle() {
        return 0;
    }

    @Override
    protected void initData() {
        super.initData();
        INITIAL_DATA = new SearchResult() {
            {
                addItem("Spectrum application");
                addItem("Check spectrum requirements");
                addItem("Spectrum");
                addItem("Hello world");
                addItem("Lorem ipsum text");
                addItem("Dummy text");
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
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mAdapter = new SearchRecyclerViewAdapter(getActivity(), INITIAL_DATA);
        mAdapter.setOnSearchResultItemClickListener(this);
        rvSearchResultList.setLayoutManager(
                new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false)
        );
        rvSearchResultList.setAdapter(mAdapter);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.setOnTouchListener(this);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_search;
    }

    @Override
    public void onSearchResultItemClicked(SearchResult.SearchResultItem _item) {
        Toast.makeText(getActivity(), _item.getSpannedText(), Toast.LENGTH_LONG).show();
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
}
