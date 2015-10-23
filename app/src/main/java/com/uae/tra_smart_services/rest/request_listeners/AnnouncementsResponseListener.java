package com.uae.tra_smart_services.rest.request_listeners;

import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.uae.tra_smart_services.adapter.AnnouncementsAdapter;
import com.uae.tra_smart_services.fragment.base.BaseFragment;
import com.uae.tra_smart_services.interfaces.OperationStateManager;
import com.uae.tra_smart_services.rest.model.response.GetAnnouncementsResponseModel;

/**
 * Created by and on 23.10.15.
 */
public final class AnnouncementsResponseListener implements RequestListener<GetAnnouncementsResponseModel> {

    private BaseFragment mFragment;
    private OperationStateManager mOperationStateManager;
    private AnnouncementsAdapter mAnnouncementsListAdapter;
    private boolean mIsAnnouncementsInLoading;
    private boolean mIsAllTransactionDownloaded;
    private int mAnnouncementsPageNum;

    public AnnouncementsResponseListener(BaseFragment _fragment, AnnouncementsAdapter _announcementsListAdapter,
            boolean _isAnnouncementsInLoading, boolean _isAllTransactionDownloaded, int _announcementsPageNum) {
        mFragment = _fragment;
        mOperationStateManager = (OperationStateManager) _fragment;
        mAnnouncementsListAdapter = _announcementsListAdapter;
        mIsAnnouncementsInLoading = _isAnnouncementsInLoading;
        mIsAllTransactionDownloaded = _isAllTransactionDownloaded;
        mAnnouncementsPageNum = _announcementsPageNum;
    }

    @Override
    public final void onRequestSuccess(GetAnnouncementsResponseModel result) {
        mIsAnnouncementsInLoading = false;
        if (mFragment.isAdded() && result != null) {
            if (mIsAllTransactionDownloaded = result.announcements.isEmpty()) {
                handleNoResult();
            } else {
                mOperationStateManager.showData();
                mAnnouncementsListAdapter.addAll(result.announcements);
            }
        } else {
            mAnnouncementsPageNum--;
        }
    }

    @Override
    public final void onRequestFailure(SpiceException spiceException) {
        mIsAnnouncementsInLoading = false;
        mAnnouncementsPageNum--;
        handleNoResult();
        mFragment.processError(spiceException);
    }

    private void handleNoResult() {
        if (mAnnouncementsListAdapter.isEmpty()) {
            mOperationStateManager.showEmptyView();
        } else {
            mAnnouncementsListAdapter.stopLoading();
        }
    }
}