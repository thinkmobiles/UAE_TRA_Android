package com.uae.tra_smart_services;

import android.app.Application;

import com.crashlytics.android.Crashlytics;

import io.fabric.sdk.android.Fabric;

/**
 * Created by Vitaliy on 24/08/2015.
 */
public class TRAApplication extends Application {

    @Override
    public void onCreate() {
        Fabric.with(this, new Crashlytics());
        super.onCreate();
    }
}
