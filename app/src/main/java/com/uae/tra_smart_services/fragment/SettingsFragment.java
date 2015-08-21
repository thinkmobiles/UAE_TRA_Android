package com.uae.tra_smart_services.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.uae.tra_smart_services.R;
import com.uae.tra_smart_services.activity.HomeActivity;
import com.uae.tra_smart_services.baseentities.BaseCustomSwitcher;
import com.uae.tra_smart_services.customviews.FontSizeSwitcherView;
import com.uae.tra_smart_services.customviews.LanguageSwitcherView;
import com.uae.tra_smart_services.customviews.ThemeSwitcherView;
import com.uae.tra_smart_services.dialog.AlertDialogFragment;
import com.uae.tra_smart_services.entities.CustomFilterPool;
import com.uae.tra_smart_services.entities.Filter;
import com.uae.tra_smart_services.fragment.base.BaseHomePageFragment;
import com.uae.tra_smart_services.global.Constants;
import com.uae.tra_smart_services.global.ServerConstants;
import com.uae.tra_smart_services.interfaces.SettingsChanged;

/**
 * Created by Andrey Korneychuk on 30.07.15.
 */
public class SettingsFragment extends BaseHomePageFragment implements SettingsChanged, OnClickListener, AlertDialogFragment.OnOkListener {

    public static final String CHANGED = "changed";

    private EditText etServer;
    private Button btnChangeServer;

    public static SettingsFragment newInstance() {
        return new SettingsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initViews() {
        super.initViews();
        etServer = findView(R.id.etServer_FS);
        btnChangeServer = findView(R.id.btnChangeServer_FS);
    }

    @Override
    protected void initListeners() {
        super.initListeners();
        btnChangeServer.setOnClickListener(this);
    }

    private static CustomFilterPool<String> filters = new CustomFilterPool<String>(){
        {
            addFilter(new Filter<String>() {
                @Override
                public boolean check(String _data) {
                    return Patterns.DOMAIN_NAME.matcher(_data).matches();
                }
            });
        }
    };

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
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        globalInitViews();
    }

    private FontSizeSwitcherView fontSwitch;
    private LanguageSwitcherView langSwitch;
    private ThemeSwitcherView themeSwitch;

    private void globalInitViews() {
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
        switch (caller.getType()) {
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
        refresh.putExtra(CHANGED, true);
        refresh.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(refresh);
    }

    private void updateLocale(String _lang) {
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

    private void updateTheme(String _themaStrValue) {
        prefs.edit()
                .putString(
                        BaseCustomSwitcher.Type.THEME.toString(),
                        _themaStrValue
                ).commit();
    }

    @Override
    public void onClick(View v) {
        String baseUrl = etServer.getText().toString();
        if (v.getId() == R.id.btnChangeServer_FS && filters.check(baseUrl)) {
            setBaseUrl(baseUrl);
        } else {
            showMessage(R.string.str_error, R.string.str_invalid_url);
        }
    }

    private void setBaseUrl(String _url) {
        ServerConstants.BASE_URL = _url;
        prefs.edit().putString(Constants.KEY_BASE_URL, _url).commit();
        Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onOkPressed() {
        // Unimplemented method
        // Used exceptionally to specify OK button in dialog
    }
}
