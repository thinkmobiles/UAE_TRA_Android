package com.uae.tra_smart_services.fragment;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.uae.tra_smart_services.R;
import com.uae.tra_smart_services.adapter.SearchRecyclerViewAdapter;
import com.uae.tra_smart_services.entities.SearchResult;
import com.uae.tra_smart_services.fragment.base.BaseFragment;

/**
 * Created by Andrey Korneychuk on 09.09.15.
 */
public class SearchFragment extends BaseFragment implements SearchRecyclerViewAdapter.OnSearchResultItemClickListener {

    private EditText etSearch;
    private TextView tvNoNotifications;
    private RecyclerView rvSearchResultList;
    private SearchResult mSearchResult;
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
        mSearchResult = new SearchResult(){
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
        etSearch = findView(R.id.etSearch_FS);
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {/**Not implemented method*/}

            @Override
            public void onTextChanged(CharSequence text, int start, int before, int count) {
                if (text.length() != 0) {
                    mAdapter.getFilter().filter(text);
                    rvSearchResultList.setVisibility(View.VISIBLE);
                    tvNoNotifications.setVisibility(View.GONE);
                } else {
                    mAdapter.clear();
                    rvSearchResultList.setVisibility(View.GONE);
                    tvNoNotifications.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {/**Not implemented method*/}
        });
        tvNoNotifications = findView(R.id.tvNoNotifications_FN);
        rvSearchResultList = findView(R.id.rvSearchResultList_FS);
        mAdapter = new SearchRecyclerViewAdapter(getActivity(), mSearchResult);
        mAdapter.setOnSearchResultItemClickListener(this);
        rvSearchResultList.setAdapter(mAdapter);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_search;
    }

    @Override
    public void onSearchResultItemClicked(SearchResult.SearchResultItem _item) {
        Toast.makeText(getActivity(), _item.getText(), Toast.LENGTH_LONG);
    }
}
