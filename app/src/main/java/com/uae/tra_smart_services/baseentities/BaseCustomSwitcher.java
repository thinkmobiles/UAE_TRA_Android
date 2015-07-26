package com.uae.tra_smart_services.baseentities;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by ak-buffalo on 24.07.15.
 */
public abstract class BaseCustomSwitcher extends LinearLayout implements View.OnClickListener {

    protected SharedPreferences prefs;

    public BaseCustomSwitcher(Context context) {
        super(context);
    }

    public BaseCustomSwitcher(Context context, AttributeSet attrs) {
        super(context, attrs);
        obtainData(context, attrs);
        initViews();
        initPreferences(context);
        initConfigs();
    }

    protected void obtainData(Context context, AttributeSet attrs){
    }

    protected void initViews() {
        inflate(getContext(), getLayoutId(), this);
    }

    protected void initPreferences(Context context){
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
    }

    protected void initConfigs() {}

    protected abstract int getLayoutId();

    protected String getStrPref(String _prefName, String _def){
        return prefs.getString(_prefName, _def);
    }

    protected void updateStrPref(String _prefName, String _value){
        prefs.edit().putString(_prefName, _value).commit();
    }

    protected int getIntPref(String _prefName, int _def){
        return prefs.getInt(_prefName, _def);
    }

    protected void updateIntPref(String _prefName, int _value){
        prefs.edit().putInt(_prefName, _value).commit();
    }
}