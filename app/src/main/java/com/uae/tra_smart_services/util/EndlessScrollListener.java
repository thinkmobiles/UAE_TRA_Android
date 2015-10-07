package com.uae.tra_smart_services.util;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.OnScrollListener;

/**
 * Created by Vitaliy on 05/10/2015.
 */
public final class EndlessScrollListener extends OnScrollListener {

    private static final int VISIBLE_THRESHOLD = 8;

    private LinearLayoutManager mLinearLayoutManager;
    private OnLoadMoreListener mOnLoadMoreListener;

    public EndlessScrollListener(@NonNull final LinearLayoutManager _linearLayoutManager,
                                 @NonNull final OnLoadMoreListener _loadMoreListener) {
        mLinearLayoutManager = _linearLayoutManager;
        mOnLoadMoreListener = _loadMoreListener;
    }

    @Override
    public final void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        final int totalItemCount = mLinearLayoutManager.getItemCount();
        final int lastVisibleItemPosition = mLinearLayoutManager.findLastVisibleItemPosition();

        if ((totalItemCount - VISIBLE_THRESHOLD) <= lastVisibleItemPosition) {
            mOnLoadMoreListener.loadMore();
        }
    }

    public interface OnLoadMoreListener {
        void loadMore();
    }
}
