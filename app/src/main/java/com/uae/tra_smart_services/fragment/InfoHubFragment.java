package com.uae.tra_smart_services.fragment;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.MenuItemCompat.OnActionExpandListener;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.SearchView.OnQueryTextListener;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.uae.tra_smart_services.R;
import com.uae.tra_smart_services.adapter.AnnouncementsAdapter;
import com.uae.tra_smart_services.adapter.TransactionsAdapter;
import com.uae.tra_smart_services.customviews.HexagonSwipeRefreshLayout;
import com.uae.tra_smart_services.customviews.LoaderView;
import com.uae.tra_smart_services.fragment.base.BaseFragment;
import com.uae.tra_smart_services.fragment.InfoHubAnnouncementsFragment.BooleanHolder;
import com.uae.tra_smart_services.global.C;
import com.uae.tra_smart_services.global.QueryAdapter;
import com.uae.tra_smart_services.interfaces.OnInfoHubItemClickListener;
import com.uae.tra_smart_services.interfaces.OperationStateManager;
import com.uae.tra_smart_services.rest.model.response.GetAnnouncementsResponseModel;
import com.uae.tra_smart_services.rest.model.response.GetTransactionResponseModel;
import com.uae.tra_smart_services.rest.request_listeners.AnnouncementsResponseListener;
import com.uae.tra_smart_services.rest.robo_requests.GetAnnouncementsRequest;
import com.uae.tra_smart_services.rest.robo_requests.GetTransactionsRequest;
import com.uae.tra_smart_services.util.EndlessScrollListener;
import com.uae.tra_smart_services.util.EndlessScrollListener.OnLoadMoreListener;

import java.util.Arrays;

/**
 * Created by ak-buffalo on 19.08.15.
 */
public final class InfoHubFragment extends BaseFragment
        implements OnLoadMoreListener, OnQueryTextListener, OnActionExpandListener, View.OnClickListener, HexagonSwipeRefreshLayout.Listener {

    private static final String KEY_TRANSACTIONS_REQUEST = "TRANSACTIONS_REQUEST";
    private static final int DEFAULT_PAGE_SIZE_TRANSACTIONS = 10;

    private int mTransactionPageNum;
    private boolean mIsSearching;
    private boolean mIsAllTransactionDownloaded, mIsAllAnnouncementsDownloaded;

    private LoaderView pbLoadingTransactions, pbLoadingAnnouncements;
    private TextView tvSeeMoreAnnouncements;
    private RecyclerView mAnnouncementsListPreview;
    private RecyclerView mTransactionsList;
    private LinearLayoutManager mAnnouncementsLayoutManager;
    private TextView tvNoTransactions, tvNoAnnouncements;
    private SearchView svSearchTransaction;

    private LinearLayoutManager mTransactionsLayoutManager;
    private AnnouncementsAdapter mAnnouncementsListAdapter;
    private TransactionsAdapter mTransactionsListAdapter;
    private TransactionsResponseListener mTransactionsListener;
    private AnnouncementsResponseListener mAnnouncementsResponseListener;
    private EndlessScrollListener mEndlessScrollListener;
    private boolean mIsTransactionsInLoading;
    private BooleanHolder mIsAnnouncementsInLoading = new BooleanHolder();
    private LoaderView loaderView;
    private CoordinatorLayout transactionCoordinator;
    private HexagonSwipeRefreshLayout mHexagonSwipeRefreshLayout;
    private SwipeRefreshLayout transactionsRefresher;

    public static InfoHubFragment newInstance() {
        return new InfoHubFragment();
    }

    private final OperationStateManager mAnnouncementsOperationStateManager = new OperationStateManager() {

        @Override
        public final void showProgress() {
            pbLoadingAnnouncements.setVisibility(View.VISIBLE);
            pbLoadingAnnouncements.startProcessing();
            mAnnouncementsListPreview.setVisibility(View.INVISIBLE);
            tvNoAnnouncements.setVisibility(View.INVISIBLE);
        }

        @Override
        public final void showData() {
            pbLoadingAnnouncements.stopProcessing();
            pbLoadingAnnouncements.setVisibility(View.INVISIBLE);
            mAnnouncementsListPreview.setVisibility(View.VISIBLE);
            tvNoAnnouncements.setVisibility(View.INVISIBLE);
        }

        @Override
        public final void showEmptyView() {
            pbLoadingAnnouncements.stopProcessing();
            pbLoadingAnnouncements.setVisibility(View.INVISIBLE);
            mAnnouncementsListPreview.setVisibility(View.INVISIBLE);
            tvNoAnnouncements.setVisibility(View.VISIBLE);
        }
    };

    private final OperationStateManager mTransactionsOperationStateManager = new OperationStateManager() {

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
    };

    @Override
    public final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        startFirstLoad();
    }

    @Override
    protected final void initViews() {
        super.initViews();
        pbLoadingAnnouncements = findView(R.id.pbLoadingAnnoncements_FIH);
        pbLoadingAnnouncements.init(Color.WHITE);
        tvNoAnnouncements = findView(R.id.tvNoAnnouncements_FIH);
        tvNoTransactions = findView(R.id.tvNoTransactions_FIH);
        tvSeeMoreAnnouncements = findView(R.id.tvSeeMorebAnn_FIH);
        mAnnouncementsListPreview = findView(R.id.rvInfoHubListPrev_FIH);
        mHexagonSwipeRefreshLayout = findView(R.id.hsrlTransactionRefresher);
        mTransactionsList = findView(R.id.rvTransactionsList_FIH);
        initAnnouncementsListPreview();
        initTransactionsList();
    }

    private void initAnnouncementsListPreview() {
        mAnnouncementsListPreview.setHasFixedSize(true);
        mAnnouncementsLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        mAnnouncementsListPreview.setLayoutManager(mAnnouncementsLayoutManager);
        mAnnouncementsListAdapter = new AnnouncementsAdapter(getActivity(), mAnnouncementsOperationStateManager, true);
        mAnnouncementsListPreview.setAdapter(mAnnouncementsListAdapter);
    }

    private void initTransactionsList() {
        mTransactionsLayoutManager = new LinearLayoutManager(getActivity());
        mTransactionsList.setLayoutManager(mTransactionsLayoutManager);
        mTransactionsListAdapter = new TransactionsAdapter(getActivity(), mTransactionsOperationStateManager);
        mTransactionsList.setAdapter(mTransactionsListAdapter);
    }

    @Override
    protected void initListeners() {
        super.initListeners();
        mTransactionsListener = new TransactionsResponseListener();
        mAnnouncementsResponseListener =
                new AnnouncementsResponseListener(
                        this, mAnnouncementsOperationStateManager, mAnnouncementsListAdapter,
                        mIsAnnouncementsInLoading, mIsAllAnnouncementsDownloaded, mTransactionPageNum);
        mEndlessScrollListener = new EndlessScrollListener(mTransactionsLayoutManager, this);
        mTransactionsList.addOnScrollListener(mEndlessScrollListener);
        tvNoTransactions.setOnClickListener(this);
        mHexagonSwipeRefreshLayout.registerListener(this);
        tvSeeMoreAnnouncements.setOnClickListener(this);
        mAnnouncementsListAdapter.setOnItemClickListener(new OnInfoHubItemClickListener<GetAnnouncementsResponseModel.Announcement>() {
            @Override
            public void onItemSelected(GetAnnouncementsResponseModel.Announcement item) {
                Bundle args = new Bundle();
                args.putParcelable(C.INFO_HUB_ANN_DATA, item);
                getFragmentManager()
                        .beginTransaction()
                        .addToBackStack(null)
                        .replace(R.id.flContainer_AH, InfoHubDetailsFragment.newInstance(args))
                        .commit();
            }
        });
    }

    @Override
    public void onClick(View _view) {
        switch (_view.getId()) {
            case R.id.tvSeeMorebAnn_FIH:
                    getFragmentManager()
                            .beginTransaction()
                            .addToBackStack(null)
                            .replace(R.id.flContainer_AH, InfoHubAnnouncementsFragment.newInstance())
                            .commit();
                break;
            case R.id.tvNoTransactions_FIH:
                    onRefresh();
                break;
        }
    }

    private void startFirstLoad() {
        mHexagonSwipeRefreshLayout.onLoadingStart();
        mAnnouncementsOperationStateManager.showProgress();
        loadTransactionPage(mTransactionPageNum = 1);
        loadAnnouncementsPage(1);
    }

    private void loadAnnouncementsPage(final int _page) {
        mIsAnnouncementsInLoading.trueV();
        GetAnnouncementsRequest announcementsRequest = new GetAnnouncementsRequest(QueryAdapter.pageToOffset(_page, 3));
        getSpiceManager().execute(announcementsRequest, mAnnouncementsResponseListener);
    }
    GetTransactionsRequest transactionsRequest;
    private void loadTransactionPage(final int _page) {
        mIsTransactionsInLoading = true;
        transactionsRequest = new GetTransactionsRequest(_page, DEFAULT_PAGE_SIZE_TRANSACTIONS);
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
//        mTransactionsOperationStateManager.showProgress();
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
    public final void onLoadMoreEvent() {
        if (mIsSearching) {
            mTransactionsListAdapter.loadMoreSearchResults();
        } else if (!mIsAllTransactionDownloaded && !mIsTransactionsInLoading) {
            loadTransactionPage(++mTransactionPageNum);
        }
    }

    @Override
    public void onRefresh() {
        mHexagonSwipeRefreshLayout.onLoadingStart();
        loadTransactionPage(mTransactionPageNum = 1);
    }

    /** STUB!! */
    private class TransactionLoader extends AsyncTask<Void, Void, GetTransactionResponseModel.List>{
        private TransactionsResponseListener listener;
        TransactionLoader(){
            listener = new TransactionsResponseListener();
        }

        @Override
        protected GetTransactionResponseModel.List doInBackground(Void... params) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            GetTransactionResponseModel.List list = new GetTransactionResponseModel.List();
            GetTransactionResponseModel[] models = new GetTransactionResponseModel[10];
            for (int i = 0; i < 10; i++){
                models[i] = new GetTransactionResponseModel();
                models[i].title = "title" + i;
                models[i].description = "description description description description" + i;
            }

            list.addAll(Arrays.asList(models));

            return list;
        }

        @Override
        protected void onPostExecute(GetTransactionResponseModel.List getTransactionResponseModels) {
            /*if(transactionsRefresher.isRefreshing()){
                transactionsRefresher.setRefreshing(false);
            }*/
            if(getTransactionResponseModels != null){
//                listener.onRequestSuccess(getTransactionResponseModels);
                mHexagonSwipeRefreshLayout.onLoadingFinished(true);
            } else {
                mHexagonSwipeRefreshLayout.onLoadingFinished(false);
//                listener.onRequestFailure(new SpiceException("Something went wrong..."));
            }
//            mTransactionsList.scrollToPosition(0);
//            loaderView.startFilling(LoaderView.State.SUCCESS);
        }
    }

    private final class TransactionsResponseListener implements RequestListener<GetTransactionResponseModel.List> {

        @Override
        public final void onRequestSuccess(GetTransactionResponseModel.List result) {
            mIsTransactionsInLoading = false;
            if (isAdded() && result != null) {
                mIsAllTransactionDownloaded = result.isEmpty();
                if (mIsAllTransactionDownloaded) {
                    handleNoResult();
                } else {
//                    mTransactionsOperationStateManager.showData();
                    mTransactionsListAdapter.addAll(result);
                    mHexagonSwipeRefreshLayout.onLoadingFinished(true);
                }
            } else {
                mTransactionPageNum--;
            }
        }

        private void handleNoResult() {
            if (mTransactionsListAdapter.isEmpty()) {
//                mTransactionsOperationStateManager.showEmptyView();
                mHexagonSwipeRefreshLayout.onLoadingFinished(false);
            } else {
                mTransactionsListAdapter.stopLoading();
            }
        }

        @Override
        public final void onRequestFailure(SpiceException spiceException) {
            mHexagonSwipeRefreshLayout.onLoadingFinished(false);
            mIsTransactionsInLoading = false;
            mTransactionPageNum--;
            handleNoResult();
            processError(spiceException);
        }
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