package com.uae.tra_smart_services.baseentities;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.uae.tra_smart_services.interfaces.I_SettingsChanged;

/**
 * Created by ak-buffalo on 24.07.15.
 */
public abstract class BaseCustomSwitcher extends LinearLayout implements View.OnClickListener {

    protected I_SettingsChanged mSettingsChangeListener;

    public enum Type {
        FONT, LANGUAGE, THEME
    }

    protected ViewGroup rootView;

    protected SharedPreferences prefs;

    public BaseCustomSwitcher(Context context) {
        super(context);
    }

    public BaseCustomSwitcher(Context context, AttributeSet _attrs) {
        super(context, _attrs);
        initData(getContext(), _attrs);
    }

    public <T> void globalInit(T prefs){
        initPreferences(prefs);
        initFactories();
        initViews();
        initConfigs();
        try {
            registerObserver((I_SettingsChanged) getContext());
        } catch (ClassCastException e) {
            throw new ClassCastException(getContext().toString()
                    + " activity must implement SettingsChangeListener interface");
        }
    }

    public abstract <T> void initPreferences(T prefs);

    protected void initFactories(){};

    protected void initData(Context context, AttributeSet attrs){}

    protected void initViews() {
        inflate(getContext(), getLayoutId(), this);
        rootView = findView(getRootViewId());
    }

    protected void initConfigs() {}

    protected void registerObserver(I_SettingsChanged _listener){
        mSettingsChangeListener = _listener;
    }

    protected void unRegisterObserver(){
        mSettingsChangeListener = null;
    }

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

    protected abstract void bindButton(View view);

    protected abstract void unBindButton(View view);

    protected abstract @LayoutRes int getLayoutId();

    protected abstract @IdRes int getRootViewId();

    public abstract Type getType();

    public final <T extends View> T findView(@IdRes int id) {
        return (T) findViewById(id);
    }
}