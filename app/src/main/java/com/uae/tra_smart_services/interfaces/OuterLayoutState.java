package com.uae.tra_smart_services.interfaces;

/**
 * Created by and on 05.11.15.
 */

public interface OuterLayoutState {
    void onLoadingStart();
    void onRefreshStart();
    void onRefreshSucceed();
    void onLoadingFinished(boolean _isSucceed);
}