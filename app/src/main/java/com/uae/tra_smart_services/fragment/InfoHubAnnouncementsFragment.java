package com.uae.tra_smart_services.fragment;

import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.uae.tra_smart_services.R;
import com.uae.tra_smart_services.adapter.AnnouncementsAdapter;
import com.uae.tra_smart_services.fragment.base.BaseFragment;
import com.uae.tra_smart_services.global.C;
import com.uae.tra_smart_services.global.QueryAdapter;
import com.uae.tra_smart_services.interfaces.OnInfoHubItemClickListener;
import com.uae.tra_smart_services.interfaces.OperationStateManager;
import com.uae.tra_smart_services.rest.model.response.InfoHubAnnouncementsListItemModel;
import com.uae.tra_smart_services.rest.request_listeners.AnnouncementsResponseListener;
import com.uae.tra_smart_services.rest.robo_requests.GetAnnouncementsRequest;
import com.uae.tra_smart_services.util.EndlessScrollListener;

/**
 * Created by ak-buffalo on 18.08.15.
 */
public class InfoHubAnnouncementsFragment extends BaseFragment
        implements OnInfoHubItemClickListener<InfoHubAnnouncementsListItemModel>,
        EndlessScrollListener.OnLoadMoreListener, SearchView.OnQueryTextListener, OperationStateManager, MenuItemCompat.OnActionExpandListener {

    private RecyclerView mList;
    private LinearLayoutManager mLayoutManager;
    private AnnouncementsAdapter mListAdapter;
    private ProgressBar pbLoading;
    private TextView tvNoResult;
    private EndlessScrollListener mEndlessScrollListener;
    private boolean mIsAnnouncementsInLoading;
    private static final int DEFAULT_OFFSET_ANNOUNCEMENTS = 10;
    private int mAnnouncementsPageNum;
    private AnnouncementsResponseListener mAnnouncementsResponseListener;
    private boolean mIsSearching;
    private boolean mIsAllAnnouncementsDownloaded;
    private SearchView svSearchTransaction;

    public static InfoHubAnnouncementsFragment newInstance() {
        return new InfoHubAnnouncementsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_search, menu);
        initSearchView(menu);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        startFirstLoad();
    }

    @Override
    protected void initViews() {
        super.initViews();
        mList = findView(R.id.rvInfoHubAnnList_FIHA);
//        pbLoading = findView(R.id.pbLoadingAnnoncements_FIHA);
        tvNoResult = findView(R.id.tvNoPendingAnnoncements_FIHA);
        initAnnouncementsList();
    }

    private final void initAnnouncementsList(){
        mLayoutManager = new LinearLayoutManager(getActivity());
        mList.setLayoutManager(mLayoutManager);
        mListAdapter = new AnnouncementsAdapter(getActivity(), this, false);
        mList.setAdapter(mListAdapter);
    }

    @Override
    protected void initListeners() {
        super.initListeners();
        mAnnouncementsResponseListener = new AnnouncementsResponseListener(this, this, mListAdapter, mIsAnnouncementsInLoading, mIsAllAnnouncementsDownloaded, 1);
        mListAdapter.setOnItemClickListener(this);
        mList.addOnScrollListener(new EndlessScrollListener(mLayoutManager, this));
    }

    private void initSearchView(Menu menu) {
        final MenuItem searchItem = menu.findItem(R.id.action_search);
        MenuItemCompat.setOnActionExpandListener(searchItem, this);
        svSearchTransaction = (SearchView) MenuItemCompat.getActionView(searchItem);
        svSearchTransaction.setOnQueryTextListener(this);
    }

    @Override
    public void onItemSelected(InfoHubAnnouncementsListItemModel item) {
        Bundle args = new Bundle();
        args.putParcelable(C.INFO_HUB_ANN_DATA, item);
        getFragmentManager()
                .beginTransaction()
                .addToBackStack(null)
                .replace(R.id.flContainer_AH, InfoHubDetailsFragment.newInstance(args))
                .commit();
    }

    private void startFirstLoad() {
        loadAnnouncementsPage(mAnnouncementsPageNum = 1);
    }

    private void loadAnnouncementsPage(final int _page) {
        GetAnnouncementsRequest announcementsRequest = new GetAnnouncementsRequest(QueryAdapter.pageToOffset(_page, DEFAULT_OFFSET_ANNOUNCEMENTS));
        getSpiceManager().execute(announcementsRequest, mAnnouncementsResponseListener);
        mIsAnnouncementsInLoading = true;
    }

    @Override
    public void loadMore() {
        if (mIsSearching) {
            mListAdapter.loadMoreSearchResults();
        } else if (!mIsAllAnnouncementsDownloaded && !mIsAnnouncementsInLoading) {
            loadAnnouncementsPage(++mAnnouncementsPageNum);
        }
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
        tvNoResult.setText(R.string.str_no_search_result);
        hideKeyboard(getView());
        showProgress();
        mLayoutManager.scrollToPosition(0);
        mListAdapter.getFilter().filter(query);
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
        tvNoResult.setText(R.string.fragment_info_hub_no_pending_transactions);
        mListAdapter.showAnnouncements();
        if (mIsAllAnnouncementsDownloaded) {
            mListAdapter.stopLoading();
        } else {
            mListAdapter.startLoading();
        }
        return true;
    }

    @Override
    public final void showProgress() {
//        pbLoading.setVisibility(View.VISIBLE);
        mList.setVisibility(View.INVISIBLE);
        tvNoResult.setVisibility(View.INVISIBLE);
    }

    @Override
    public final void showData() {
//        pbLoading.setVisibility(View.INVISIBLE);
        mList.setVisibility(View.VISIBLE);
        tvNoResult.setVisibility(View.INVISIBLE);
    }

    @Override
    public final void showEmptyView() {
//        pbLoading.setVisibility(View.INVISIBLE);
        mList.setVisibility(View.INVISIBLE);
        tvNoResult.setVisibility(View.VISIBLE);
    }

    @Override
    protected int getTitle() {
        return R.string.str_info_hub_announcements;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_info_hub_announcements;
    }
}