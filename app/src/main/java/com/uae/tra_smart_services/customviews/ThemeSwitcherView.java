package com.uae.tra_smart_services.customviews;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.uae.tra_smart_services.R;
import com.uae.tra_smart_services.baseentities.BaseCustomSwitcher;

/**
 * Created by ak-buffalo on 27.07.15.
 */
public class ThemeSwitcherView extends BaseCustomSwitcher {

    public ThemeSwitcherView(Context context) {
        super(context);
    }

    public ThemeSwitcherView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public <T> void initPreferences(T prefs) {/*Unimplemented method*/}

    @Override
    protected int getLayoutId() {
        return R.layout.layout_switcher;
    }

    @Override
    protected int getRootViewId() {
        return R.id.container_switcher_view;
    }

    @Override
    protected void initViews() {
        super.initViews();
    }

    @Override
    public Type getType() {
        return Type.THEME;
    }

    @Override
    protected void bindButton(View view) {
        // TODO Implemets logic
    }

    @Override
    protected void unBindButton(View view) {
        // TODO Implemets logic
    }

    @Override
    public void onClick(View v) {
        mSettingsChangeListener.onSettingsChanged(this, ((TextView)v).getText());
    }
}
