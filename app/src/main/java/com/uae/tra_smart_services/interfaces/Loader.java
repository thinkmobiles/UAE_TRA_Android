package com.uae.tra_smart_services.interfaces;

/**
 * Created by Andrey Korneychuk on 23.09.15.
 */

public interface Loader {

    void startLoading();

    void cancelLoading();

    void successLoading();

    void failureLoading();

    void backButtonPressed();
}
