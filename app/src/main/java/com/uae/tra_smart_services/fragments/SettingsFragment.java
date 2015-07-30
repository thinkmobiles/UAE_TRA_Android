package com.uae.tra_smart_services.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.preference.PreferenceManager;

import com.uae.tra_smart_services.R;
import com.uae.tra_smart_services.activities.HomeActivity;
import com.uae.tra_smart_services.baseentities.BaseCustomSwitcher;
import com.uae.tra_smart_services.baseentities.BaseHomePageFragment;
import com.uae.tra_smart_services.customviews.FontSizeSwitcherView;
import com.uae.tra_smart_services.customviews.LanguageSwitcherView;
import com.uae.tra_smart_services.customviews.ThemeSwitcherView;
import com.uae.tra_smart_services.interfaces.I_SettingsChanged;

import java.util.Locale;

import static com.uae.tra_smart_services.entities.H.coalesce;

/**
 * Created by ak-buffalo on 30.07.15.
 */
public class SettingsFragment extends BaseHomePageFragment implements I_SettingsChanged {


    public static SettingsFragment newInstance() {
        return new SettingsFragment();
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_settings;
    }

    private SharedPreferences prefs;
    @Override
    public void onStart() {
        super.onStart();
        prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        globalInitViews();
    }

    private FontSizeSwitcherView fontSwitch;
    private LanguageSwitcherView langSwitch;
    private ThemeSwitcherView themeSwitch;
    private void globalInitViews(){
        langSwitch = findView(R.id.cvLangSwitch);
        langSwitch.registerObserver(this);
        langSwitch.globalInit();

        fontSwitch = findView(R.id.cvFontSwitch);
        fontSwitch.registerObserver(this);
        fontSwitch.globalInit(coalesce(prefs.getInt(BaseCustomSwitcher.Type.FONT.toString(), 10), 10));

        themeSwitch = findView(R.id.cvThemeSwitch);
        themeSwitch.registerObserver(this);
        themeSwitch.globalInit(
                prefs.getInt(
                        BaseCustomSwitcher.Type.THEME.toString(),
                        Color.parseColor(getResources().getStringArray(R.array.colors)[0])
                )
        );
    }

    @Override
    public void onSettingsChanged(BaseCustomSwitcher caller, Object data) {
        switch (caller.getType()){
            case LANGUAGE:
                updateLocaleAtRuntime((String) data);
                break;
            case FONT:
                prefs.edit()
                        .putInt(BaseCustomSwitcher.Type.FONT.toString(), (int) data)
                        .commit();
                break;
            case THEME:
                prefs.edit()
                        .putInt(BaseCustomSwitcher.Type.THEME.toString(), (int) data)
                        .commit();
                break;
        }
    }

    private void updateLocaleAtRuntime(String lang){
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getActivity().getResources().updateConfiguration(config, null);
        Intent refresh = new Intent(getActivity(), HomeActivity.class);
        refresh.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(refresh);
    }
}
