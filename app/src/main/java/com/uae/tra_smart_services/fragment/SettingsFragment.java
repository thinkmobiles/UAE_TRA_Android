package com.uae.tra_smart_services.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;

import com.uae.tra_smart_services.R;
import com.uae.tra_smart_services.activity.HomeActivity;
import com.uae.tra_smart_services.baseentities.BaseCustomSwitcher;
import com.uae.tra_smart_services.customviews.FontSizeSwitcherView;
import com.uae.tra_smart_services.customviews.LanguageSwitcherView;
import com.uae.tra_smart_services.customviews.ThemeSwitcherView;
import com.uae.tra_smart_services.fragment.base.BaseHomePageFragment;
import com.uae.tra_smart_services.interfaces.I_SettingsChanged;

/**
 * Created by Andrey Korneychuk on 30.07.15.
 */
public class SettingsFragment extends BaseHomePageFragment implements I_SettingsChanged {

    public static SettingsFragment newInstance() {
        return new SettingsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    protected int getTitle() {
        return R.string.str_settings;
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
        langSwitch.globalInit();
        langSwitch.registerObserver(this);

        fontSwitch = findView(R.id.cvFontSwitch);
        fontSwitch.globalInit(prefs.getFloat(BaseCustomSwitcher.Type.FONT.toString(), 1f));
        fontSwitch.registerObserver(this);

        themeSwitch = findView(R.id.cvThemeSwitch);
        themeSwitch.globalInit(mThemaDefiner.getThemeStringValue());
        themeSwitch.registerObserver(this);
    }

    @Override
    public void onSettingsChanged(BaseCustomSwitcher caller, Object data) {
        switch (caller.getType()){
            case LANGUAGE:
                updateLocale((String) data);
                break;
            case FONT:
                updateFont((float) data);
                break;
            case THEME:
                updateTheme((String) data);
                break;
        }
        Intent refresh = new Intent(getActivity(), HomeActivity.class);
        refresh.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(refresh);
    }

    private void updateLocale(String _lang){
        prefs.edit()
                .putString(
                        BaseCustomSwitcher.Type.LANGUAGE.toString(),
                        _lang)
                .commit();
    }

    private void updateFont(final float _scale) {
        prefs.edit()
                .putFloat(
                        BaseCustomSwitcher.Type.FONT.toString(),
                        _scale)
                .commit();
    }

    private void updateTheme(String _themaStrValue){
        prefs.edit()
            .putString(
                    BaseCustomSwitcher.Type.THEME.toString(),
                    _themaStrValue
            ).commit();
    }
}
