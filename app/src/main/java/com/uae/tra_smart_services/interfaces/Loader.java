package com.uae.tra_smart_services.interfaces;

import com.uae.tra_smart_services.customviews.LoaderView;

/**
 * Created by Andrey Korneychuk on 24.09.15.
 */

public interface Loader {

    void startLoading(String _msg);

    void successLoading(String _msg);

    void dissmissLoading(String _msg);

    void dissmissLoading(Dismiss afterDissmiss);

    void setBackButtonPressedBehaviour(BackButton backButtonPressed);

    void backButtonPressed();

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
