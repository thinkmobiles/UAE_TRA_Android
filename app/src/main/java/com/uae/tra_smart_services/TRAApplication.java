package com.uae.tra_smart_services;

import android.app.Application;

import com.crashlytics.android.Crashlytics;

import io.fabric.sdk.android.Fabric;

/**
 * Created by Mikazme on 24/08/2015.
 */
public class TRAApplication extends Application {

    private static boolean isLoggedIn = false;

    @Override
    public void onCreate() {
        Fabric.with(this, new Crashlytics());
        isLoggedIn = false;
        super.onCreate();


    }

    public static boolean isLoggedIn() {
        return isLoggedIn;
    }

    public static void setIsLoggedIn(boolean _isLoggedIn) {
        isLoggedIn = _isLoggedIn;
    }
}
