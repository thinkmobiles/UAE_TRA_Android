package com.uae.tra_smart_services.activities;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.XmlResourceParser;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.uae.tra_smart_services.R;
import com.uae.tra_smart_services.baseentities.BaseCustomSwitcher;
import com.uae.tra_smart_services.customviews.FontSizeSwitcherView;
import com.uae.tra_smart_services.customviews.LanguageSwitcherView;
import com.uae.tra_smart_services.customviews.ThemeSwitcherView;
import com.uae.tra_smart_services.interfaces.I_SettingsChanged;

import java.util.Locale;
import java.util.Map;

import static com.uae.tra_smart_services.entities.H.coalesce;
import static com.uae.tra_smart_services.entities.H.parseXml;

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

        updateLocale(null);

        setContentView(R.layout.activity_settings);

        globalInitViews();
    }

    private void globalInitViews(){
        langSwitch = (LanguageSwitcherView) findViewById(R.id.cvLangSwitch);
        langSwitch.globalInit(
                prefs.getString(
                        BaseCustomSwitcher.Type.LANGUAGE.toString(),
                        getResources().getStringArray(R.array.languages)[0]
                )
        );

        fontSwitch = (FontSizeSwitcherView) findViewById(R.id.cvFontSwitch);
        fontSwitch.globalInit(coalesce(prefs.getInt(BaseCustomSwitcher.Type.FONT.toString(), 10), 10));

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
                updateLocale((String) data);
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

    private void updateLocale(@Nullable String lang){
        if(lang == null){
            lang = prefs.getString(
                    BaseCustomSwitcher.Type.LANGUAGE.toString(),
                    getResources().getStringArray(R.array.languages)[0]
            );

            Map<String, String> langs = parseXml(getResources().getXml(R.xml.maps));
        }
        switch(lang){
            case "english":
                Locale locale = new Locale("en");
                Locale.setDefault(locale);
                Configuration config = new Configuration();
                config.locale = locale;
                getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());

                Toast.makeText(this, "Locale in English !", Toast.LENGTH_LONG).show();
                break;

            case "arabic":
                Locale locale2 = new Locale("ar");
                Locale.setDefault(locale2);
                Configuration config2 = new Configuration();
                config2.locale = locale2;
                getBaseContext().getResources().updateConfiguration(config2, getBaseContext().getResources().getDisplayMetrics());

                Toast.makeText(this, "لغة باللغة العربية!", Toast.LENGTH_LONG).show();
                break;

            case "spanish":
                Locale locale3 = new Locale("es");
                Locale.setDefault(locale3);
                Configuration config3 = new Configuration();
                config3.locale = locale3;
                getBaseContext().getResources().updateConfiguration(config3, getBaseContext().getResources().getDisplayMetrics());

                Toast.makeText(this, "Locale in Spain !", Toast.LENGTH_LONG).show();
                break;
        }
    }


}
