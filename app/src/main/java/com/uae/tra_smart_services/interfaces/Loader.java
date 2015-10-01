package com.uae.tra_smart_services.interfaces;

import com.uae.tra_smart_services.customviews.LoaderView;

/**
 * Created by ak-buffalo on 24.09.15.
 */

public interface Loader {

    void startLoading(String _msg);

    void successLoading(String _msg);

    void cancelLoading(String _msg);

    void failedLoading(String _msg);

    void dissmissLoadingWithAction(Dismiss afterDissmiss);

    void setButtonPressedBehavior(BackButton backButtonPressed);

    interface Dismiss {
        void onLoadingDismissed();
    }

    interface Cancelled{
        void onLoadingCanceled();
    }

    interface BackButton{
        void onBackButtonPressed(LoaderView.State _currentState);
    }
}
