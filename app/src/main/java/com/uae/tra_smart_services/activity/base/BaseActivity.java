package com.uae.tra_smart_services.activity.base;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.uae.tra_smart_services.baseentities.BaseCustomSwitcher;
import com.uae.tra_smart_services.fragment.base.BaseFragment;
import com.uae.tra_smart_services.global.Constants;
import com.uae.tra_smart_services.interfaces.ProgressDialogManager;

import java.util.Locale;

/**
 * Created by Vitaliy on 22/07/2015.
 */
public abstract class BaseActivity extends AppCompatActivity implements ProgressDialogManager, BaseFragment.ThemaDefiner {

    private ProgressDialog mProgressDialog;
    private String mThemaStringValue;
    private Float mFontSize;
    private String mLanguage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setApplicationTheme();
        setApplicationFontSize();
        setApplicationLanguage();
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public final void showProgressDialog() {
        showProgressDialog("");
    }

    @Override
    public final void showProgressDialog(final String _text) {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setCancelable(false);
        } else if (mProgressDialog.isShowing())
            return;
        mProgressDialog.setMessage(_text == null ? "" : _text);
        mProgressDialog.show();
    }

    @Override
    public final void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    public final <T extends View> T findView(@IdRes int id) {
        return (T) findViewById(id);
    }

    @Override
    public String getThemeStringValue() {
        return mThemaStringValue;
    }

    public void setApplicationTheme() {
        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        if (prefs.getBoolean(Constants.KEY_BLACK_AND_WHITE_MODE, false)) {
            mThemaStringValue = Constants.BLACK_AND_WHITE_THEME;
        } else {
            mThemaStringValue = prefs.getString(BaseCustomSwitcher.Type.THEME.toString(), Constants.ORANGE_THEME);
        }
        setTheme(getResources().getIdentifier(mThemaStringValue, "style", getPackageName()));
    }

    public void setApplicationFontSize() {
        mFontSize = PreferenceManager
                .getDefaultSharedPreferences(this)
                .getFloat(BaseCustomSwitcher.Type.FONT.toString(), 1f);
        Configuration config = getResources().getConfiguration();
        config.fontScale = mFontSize;
        getResources().updateConfiguration(config, null);
    }

    public final void setApplicationLanguage() {
        mLanguage = PreferenceManager
                .getDefaultSharedPreferences(this)
                .getString(BaseCustomSwitcher.Type.LANGUAGE.toString(), "en");
        Locale locale = new Locale(mLanguage);
        Locale.setDefault(locale);
        Configuration config = getResources().getConfiguration();
        config.locale = locale;
        getResources().updateConfiguration(config, null);
    }
}