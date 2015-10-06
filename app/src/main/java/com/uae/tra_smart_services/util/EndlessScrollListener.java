package com.uae.tra_smart_services.util;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by Vitaliy on 05/10/2015.
 */
public class EndlessScrollListener extends RecyclerView.OnScrollListener {

    private final int VISIBLE_TRESHOLD= 5;
    private int mPreviousTotal = 0;
    private boolean mLoading = true;
    private int mFirstVisibleItem, mVisibleItemCount, mTotalItemCount;
    private int mCurrentPage = 1;

    private LinearLayoutManager mLinearLayoutManager;
    private OnLoadMore mOnLoadMoreListener;

    public EndlessScrollListener(final LinearLayoutManager _linearLayoutManager,
                                 final OnLoadMore _loadMoreListener) {
        mLinearLayoutManager = _linearLayoutManager;
        mOnLoadMoreListener = _loadMoreListener;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        mVisibleItemCount = recyclerView.getChildCount();
        mTotalItemCount = mLinearLayoutManager.getItemCount();
        mFirstVisibleItem = mLinearLayoutManager.findFirstVisibleItemPosition();

        if (mLoading && mTotalItemCount > mPreviousTotal) {
            mLoading = false;
            mPreviousTotal = mTotalItemCount;
        }

        if (!mLoading && (mTotalItemCount - VISIBLE_TRESHOLD)
                <= (mFirstVisibleItem + VISIBLE_TRESHOLD)) {

            mCurrentPage++;
            if (mOnLoadMoreListener != null)
                mOnLoadMoreListener.loadMore(mCurrentPage);

            mLoading = true;
        }
    }

    public interface OnLoadMore {
        void loadMore(final int _page);
    }
}
