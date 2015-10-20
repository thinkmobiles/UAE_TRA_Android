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
import com.octo.android.robospice.request.listener.RequestListener;
import com.uae.tra_smart_services.R;
import com.uae.tra_smart_services.adapter.TransactionsAdapter;
import com.uae.tra_smart_services.fragment.base.BaseFragment;
import com.uae.tra_smart_services.interfaces.OperationStateManager;
import com.uae.tra_smart_services.rest.model.response.GetTransactionResponseModel;
import com.uae.tra_smart_services.rest.robo_requests.GetTransactionsRequest;
import com.uae.tra_smart_services.util.EndlessScrollListener;
import com.uae.tra_smart_services.util.EndlessScrollListener.OnLoadMoreListener;

/**
 * Created by ak-buffalo on 19.08.15.
 */
public final class InfoHubFragment extends BaseFragment
        implements OnLoadMoreListener, OnQueryTextListener, OnActionExpandListener, OperationStateManager {

    private static final String KEY_TRANSACTIONS_REQUEST = "TRANSACTIONS_REQUEST";
    private static final int DEFAULT_PAGE_SIZE = 10;

    private int mTransactionPageNum;
    private boolean mIsSearching;
    private boolean mIsAllTransactionDownloaded;

    private ProgressBar pbLoadingTransactions;
    private RecyclerView mTransactionsList;
    private TextView tvNoTransactions;
    private SearchView svSearchTransaction;

    private LinearLayoutManager mTransactionsLayoutManager;
    private TransactionsAdapter mTransactionsListAdapter;
    private TransactionsResponseListener mTransactionsListener;
    private EndlessScrollListener mEndlessScrollListener;
    private boolean mIsInLoading;


    public static InfoHubFragment newInstance() {
        return new InfoHubFragment();
    }

    @Override
    public final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    protected final void initViews() {
        super.initViews();
        pbLoadingTransactions = findView(R.id.pbLoadingTransactions_FIH);
        tvNoTransactions = findView(R.id.tvNoPendingTransactions_FIH);
        initTransactionsList();
    }

    private void initTransactionsList() {
        mTransactionsList = findView(R.id.rvTransactionsList_FIH);

        mTransactionsLayoutManager = new LinearLayoutManager(getActivity());
        mTransactionsList.setLayoutManager(mTransactionsLayoutManager);

        mTransactionsListAdapter = new TransactionsAdapter(getActivity(), this);
        mTransactionsList.setAdapter(mTransactionsListAdapter);
    }

    @Override
    protected void initListeners() {
        super.initListeners();
        mTransactionsListener = new TransactionsResponseListener();
        mEndlessScrollListener = new EndlessScrollListener(mTransactionsLayoutManager, this);
        mTransactionsList.addOnScrollListener(mEndlessScrollListener);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        startFirstLoad();
    }

    private void startFirstLoad() {
        mTransactionPageNum = 1;
        loadTransactionPage(mTransactionPageNum);
    }

    private void loadTransactionPage(final int _page) {
        GetTransactionsRequest transactionsRequest = new GetTransactionsRequest(_page, DEFAULT_PAGE_SIZE);
        mIsInLoading = true;
        getSpiceManager().execute(transactionsRequest, mTransactionsListener);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_favorites, menu);
        initSearchView(menu);
    }

    private void initSearchView(Menu menu) {
        final MenuItem searchItem = menu.findItem(R.id.action_search);
//        searchItem.setVisible(!mTransactionsListAdapter.isEmpty());
        MenuItemCompat.setOnActionExpandListener(searchItem, this);

        svSearchTransaction = (SearchView) MenuItemCompat.getActionView(searchItem);
        svSearchTransaction.setOnQueryTextListener(this);
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        Log.i("SearchI", "onQueryTextChange");
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        Log.i("SearchI", "onQueryTextSubmit");
        mIsSearching = true;
        tvNoTransactions.setText(R.string.str_no_search_result);
        hideKeyboard(getView());
        showProgress();
        mTransactionsLayoutManager.scrollToPosition(0);
        mTransactionsListAdapter.getFilter().filter(query);
        return true;
    }

    @Override
    public boolean onMenuItemActionExpand(MenuItem item) {
        Log.i("SearchI", "onMenuItemActionExpand");
        return true;
    }

    @Override
    public boolean onMenuItemActionCollapse(MenuItem item) {
        Log.i("SearchI", "onMenuItemActionCollapse");
        mIsSearching = false;
        tvNoTransactions.setText(R.string.fragment_info_hub_no_pending_transactions);
        mTransactionsListAdapter.showTransactions();
        if (mIsAllTransactionDownloaded) {
            mTransactionsListAdapter.stopLoading();
        } else {
            mTransactionsListAdapter.startLoading();
        }
        return true;
    }

    @Override
    public final void loadMore() {
        if (mIsSearching) {
            mTransactionsListAdapter.loadMoreSearchResults();
        } else if (!mIsAllTransactionDownloaded && !mIsInLoading) {
            loadTransactionPage(++mTransactionPageNum);
        }
    }

    private final class TransactionsResponseListener implements RequestListener<GetTransactionResponseModel.List> {

        @Override
        public final void onRequestSuccess(GetTransactionResponseModel.List result) {
            mIsInLoading = false;
            if (isAdded() && result != null) {
                mIsAllTransactionDownloaded = result.isEmpty();
                if (mIsAllTransactionDownloaded) {
                    handleNoResult();
                } else {
                    showData();
                    mTransactionsListAdapter.addAll(result);
                }
            } else {
                mTransactionPageNum--;
            }
        }

        private void handleNoResult() {
            if (mTransactionsListAdapter.isEmpty()) {
                showEmptyView();
            } else {
                mTransactionsListAdapter.stopLoading();
            }
        }

        @Override
        public final void onRequestFailure(SpiceException spiceException) {
            mIsInLoading = false;
            mTransactionPageNum--;
            handleNoResult();
            processError(spiceException);
        }
    }

    @Override
    public final void showProgress() {
        pbLoadingTransactions.setVisibility(View.VISIBLE);
        mTransactionsList.setVisibility(View.INVISIBLE);
        tvNoTransactions.setVisibility(View.INVISIBLE);
    }

    @Override
    public final void showData() {
        pbLoadingTransactions.setVisibility(View.INVISIBLE);
        mTransactionsList.setVisibility(View.VISIBLE);
        tvNoTransactions.setVisibility(View.INVISIBLE);
    }

    @Override
    public final void showEmptyView() {
        pbLoadingTransactions.setVisibility(View.INVISIBLE);
        mTransactionsList.setVisibility(View.INVISIBLE);
        tvNoTransactions.setVisibility(View.VISIBLE);
    }

    @Override
    protected final int getTitle() {
        return R.string.str_info_hub_title;
    }

    @Override
    protected final int getLayoutResource() {
        return R.layout.fragment_info_hub;
    }

}