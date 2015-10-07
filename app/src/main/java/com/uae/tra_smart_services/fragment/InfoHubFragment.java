package com.uae.tra_smart_services.fragment;

import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.MenuItemCompat.OnActionExpandListener;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.SearchView.OnQueryTextListener;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.PendingRequestListener;
import com.uae.tra_smart_services.R;
import com.uae.tra_smart_services.adapter.InfoHubTransactionsListAdapter;
import com.uae.tra_smart_services.fragment.base.BaseFragment;
import com.uae.tra_smart_services.rest.model.response.GetTransactionResponseModel;
import com.uae.tra_smart_services.rest.robo_requests.GetTransactionsRequest;
import com.uae.tra_smart_services.util.EndlessScrollListener;
import com.uae.tra_smart_services.util.EndlessScrollListener.OnLoadMore;

/**
 * Created by ak-buffalo on 19.08.15.
 */
public class InfoHubFragment extends BaseFragment implements OnLoadMore, OnQueryTextListener, OnActionExpandListener {

    private static final String KEY_TRANSACTIONS_REQUEST = "TRANSACTIONS_REQUEST";
    private final int DEFAULT_LIMIT = 10;

    //    private ImageView ivBackground;
    private ProgressBar pbLoadingTransactions;
    private RecyclerView mTransactionsList;
    private TextView tvNoTransactions;
    private SearchView svSearchTransaction;

    private LinearLayoutManager mTransactionsLayoutManager;
    private InfoHubTransactionsListAdapter mTransactionsListAdapter;
    private TransactionsResponseListener mTransactionsListener;
    private EndlessScrollListener mEndlessScrollListener;
    private GetTransactionsRequest mTransactionsRequest;

    private boolean mIsAllTransactionDownloaded;

    public static InfoHubFragment newInstance() {
        return new InfoHubFragment();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    protected void initData() {
        super.initData();
    }

    @Override
    protected void initViews() {
        super.initViews();
        pbLoadingTransactions = findView(R.id.pbLoadingTransactions_FIH);
        tvNoTransactions = findView(R.id.tvNoPendingTransactions_FIH);
        initTransactionsList();
    }

    @Override
    protected void initListeners() {
        super.initListeners();
        mTransactionsListener = new TransactionsResponseListener();
        mEndlessScrollListener = new EndlessScrollListener(mTransactionsLayoutManager, this);
        mTransactionsList.addOnScrollListener(mEndlessScrollListener);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        startFirstLoad();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_favorites, menu);

        final MenuItem searchItem = menu.findItem(R.id.action_search);
        searchItem.setVisible(!mTransactionsListAdapter.isEmpty());
        MenuItemCompat.setOnActionExpandListener(searchItem, this);

        svSearchTransaction = (SearchView) MenuItemCompat.getActionView(searchItem);
        svSearchTransaction.setOnQueryTextListener(this);
    }

    private void startFirstLoad() {
        startLoading(1);
    }

    private void startLoading(final int _page) {
        mTransactionsRequest = new GetTransactionsRequest(_page, DEFAULT_LIMIT);
        getSpiceManager().execute(mTransactionsRequest, mTransactionsListener);
    }

    private void initTransactionsList() {
        mTransactionsList = findView(R.id.rvTransactionsList_FIH);
        mTransactionsList.setHasFixedSize(true);
        mTransactionsLayoutManager = new LinearLayoutManager(getActivity());
        mTransactionsList.setLayoutManager(mTransactionsLayoutManager);
        mTransactionsListAdapter = new InfoHubTransactionsListAdapter(getActivity());
        mTransactionsList.setAdapter(mTransactionsListAdapter);
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        mTransactionsListAdapter.getFilter().filter(newText);
        Log.i("SearchI", "onQueryTextChange");
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        Log.i("SearchI", "onQueryTextSubmit");
        hideKeyboard(getView());
        mTransactionsListAdapter.getFilter().filter(query);
        return true;
    }

    @Override
     public boolean onMenuItemActionExpand(MenuItem item) {
        Log.i("SearchI", "onMenuItemActionExpand");
        cancelTransactionRequest();
        mTransactionsList.removeOnScrollListener(mEndlessScrollListener);
        mTransactionsListAdapter.stopLoading();
        return true;
    }

    private void cancelTransactionRequest() {
        if (mTransactionsRequest != null && getSpiceManager().isStarted()) {
            mTransactionsRequest.cancel();
        }
    }

    @Override
    public boolean onMenuItemActionCollapse(MenuItem item) {
        Log.i("SearchI", "onMenuItemActionCollapse");
        mTransactionsList.addOnScrollListener(mEndlessScrollListener);
        if (!mIsAllTransactionDownloaded) {
            loadMore(mEndlessScrollListener.getPage());
            mTransactionsListAdapter.startLoading();
        }
        return true;
    }

    @Override
    protected int getTitle() {
        return R.string.str_info_hub_title;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_info_hub;
    }

    @Override
    public void loadMore(int _page) {
        Log.i("SearchI", "load more " + _page);
        startLoading(_page);
    }

    private class TransactionsResponseListener implements PendingRequestListener<GetTransactionResponseModel.List> {

        @Override
        public void onRequestNotFound() {

        }

        @Override
        public void onRequestFailure(SpiceException spiceException) {
            processError(spiceException);
            mTransactionsListAdapter.stopLoading();
            if (mTransactionsListAdapter.isEmpty()) {
                pbLoadingTransactions.setVisibility(View.GONE);
                mTransactionsList.setVisibility(View.GONE);
                tvNoTransactions.setVisibility(View.VISIBLE);
            }
            if (isAdded()) {
                getActivity().invalidateOptionsMenu();
            }
        }

        @Override
        public void onRequestSuccess(GetTransactionResponseModel.List result) {
            mTransactionsListAdapter.addAll(result);
            mIsAllTransactionDownloaded = result.isEmpty();

            if (mTransactionsListAdapter.isEmpty()) {
                pbLoadingTransactions.setVisibility(View.GONE);
                mTransactionsList.setVisibility(View.GONE);
                tvNoTransactions.setVisibility(View.VISIBLE);
            } else if (View.VISIBLE != mTransactionsList.getVisibility()) {
                mTransactionsList.setVisibility(View.VISIBLE);
                pbLoadingTransactions.setVisibility(View.GONE);
            }
            if (isAdded()) {
                getActivity().invalidateOptionsMenu();
            }
        }
    }
}