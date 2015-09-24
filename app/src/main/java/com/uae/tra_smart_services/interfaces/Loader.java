package com.uae.tra_smart_services.interfaces;

/**
 * Created by Andrey Korneychuk on 24.09.15.
 */

public interface Loader {

    void startLoading(String _msg);

    void successLoading(String _msg);

    void dissmissLoading(String _msg);

    void dissmissLoading(AfterDissmiss afterDissmiss);

    void backButtonPressed();

    interface AfterDissmiss {
        void onAfterDissmiss();
    }
}
