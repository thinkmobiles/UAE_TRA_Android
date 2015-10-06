package com.uae.tra_smart_services.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.PendingRequestListener;
import com.uae.tra_smart_services.R;
import com.uae.tra_smart_services.adapter.InfoHubTransactionsListAdapter;
import com.uae.tra_smart_services.fragment.base.BaseFragment;
import com.uae.tra_smart_services.rest.model.response.TransactionResponse;
import com.uae.tra_smart_services.rest.robo_requests.GetTransactionsRequest;
import com.uae.tra_smart_services.util.EndlessScrollListener;

/**
 * Created by ak-buffalo on 19.08.15.
 */
public class InfoHubFragment extends BaseFragment implements EndlessScrollListener.OnLoadMore {

    private static final String KEY_TRANSACTIONS_REQUEST = "TRANSACTIONS_REQUEST";
    private final int DEFAULT_LIMIT = 10;

    //    private ImageView ivBackground;
    private ProgressBar pbLoadingTransactions;
    private RecyclerView mTransactionsList;
    private LinearLayoutManager mTransactionsLayoutManager;
    private InfoHubTransactionsListAdapter mTransactionsListAdapter;
    private TransactionsResponseListener mTransactionsListener;

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
        initTransactionsList();
    }

    @Override
    protected void initListeners() {
        super.initListeners();
        mTransactionsListener = new TransactionsResponseListener();
        mTransactionsList.addOnScrollListener(new EndlessScrollListener(mTransactionsLayoutManager, this));
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

    private void startFirstLoad() {
        startLoading(1);
    }

    private void startLoading(final int _page) {
        final GetTransactionsRequest request = new GetTransactionsRequest(_page, DEFAULT_LIMIT);
        getSpiceManager().execute(request, mTransactionsListener);
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
    protected int getTitle() {
        return R.string.str_info_hub_title;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_info_hub;
    }

    @Override
    public void loadMore(int _page) {
        startLoading(_page);
    }

    private class TransactionsResponseListener implements PendingRequestListener<TransactionResponse.List> {

        @Override
        public void onRequestNotFound() {

        }

        @Override
        public void onRequestFailure(SpiceException spiceException) {
            processError(spiceException);
        }

        @Override
        public void onRequestSuccess(TransactionResponse.List result) {
            mTransactionsListAdapter.addAll(result);
            if (View.VISIBLE != mTransactionsList.getVisibility()) {
                mTransactionsList.setVisibility(View.VISIBLE);
                pbLoadingTransactions.setVisibility(View.GONE);
            }
        }
    }
}