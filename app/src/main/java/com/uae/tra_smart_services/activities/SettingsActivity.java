package com.uae.tra_smart_services.activities;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;

import com.uae.tra_smart_services.R;
import com.uae.tra_smart_services.baseentities.BaseCustomSwitcher;
import com.uae.tra_smart_services.customviews.FontSizeSwitcherView;
import com.uae.tra_smart_services.customviews.LanguageSwitcherView;
import com.uae.tra_smart_services.customviews.ThemeSwitcherView;
import com.uae.tra_smart_services.interfaces.I_SettingsChanged;

import static com.uae.tra_smart_services.entities.H.coalesce;

/**
 * Created by ak-buffalo on 24.07.15.
 */
public class SettingsActivity extends Activity
                            implements I_SettingsChanged{

    private SharedPreferences prefs;

    private FontSizeSwitcherView fontSwitch;
    private LanguageSwitcherView langSwitch;
    private ThemeSwitcherView themeSwitch;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        prefs = PreferenceManager.getDefaultSharedPreferences(this);

        setContentView(R.layout.activity_settings);

        globalInitViews();
    }

    private void globalInitViews(){
        fontSwitch = (FontSizeSwitcherView) findViewById(R.id.cvFontSwitch);
        fontSwitch.globalInit(coalesce(prefs.getInt(BaseCustomSwitcher.Type.FONT.toString(), 10), 10));

        langSwitch = (LanguageSwitcherView) findViewById(R.id.cvLangSwitch);
        langSwitch.globalInit(
                prefs.getString(
                        BaseCustomSwitcher.Type.LANGUAGE.toString(),
                        getResources().getStringArray(R.array.languages)[0]
                )
        );

        themeSwitch = (ThemeSwitcherView) findViewById(R.id.cvThemeSwitch);
        themeSwitch.globalInit(
                prefs.getInt(
                        BaseCustomSwitcher.Type.THEME.toString(),
                        Color.parseColor(getResources().getStringArray(R.array.colors)[0])
                )
        );
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onSettingsChanged(BaseCustomSwitcher caller, Object data) {
        // TODO Change preference change logic
        switch (caller.getType()){
            case FONT:
                prefs.edit()
                    .putInt(BaseCustomSwitcher.Type.FONT.toString(), (int) data)
                    .commit();
                break;
            case LANGUAGE:
                prefs.edit()
                    .putString(BaseCustomSwitcher.Type.LANGUAGE.toString(), (String) data)
                    .commit();
                break;
            case THEME:
                prefs.edit()
                        .putInt(BaseCustomSwitcher.Type.THEME.toString(), (int) data)
                        .commit();
                break;
        }
    }
}
