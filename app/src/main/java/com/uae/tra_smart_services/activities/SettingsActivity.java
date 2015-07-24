package com.uae.tra_smart_services.activities;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Toast;

import com.uae.tra_smart_services.R;

/**
 * Created by ak-buffalo on 24.07.15.
 */
public class SettingsActivity extends Activity
                            implements SharedPreferences.OnSharedPreferenceChangeListener {

    public static final String FONT_SIZE_SCALE = "fontSizeScale";
    public static final String LANGUAGE = "language";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
    }

    @Override
    protected void onResume() {
        super.onResume();
        PreferenceManager
                .getDefaultSharedPreferences(this)
                .registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        PreferenceManager
                .getDefaultSharedPreferences(this)
                .unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        // TODO Make logic to change text scale according to preferences changes
        switch (key){
            case FONT_SIZE_SCALE:
                String scale = String.valueOf(sharedPreferences.getInt(key, 1));
                Toast.makeText(getApplicationContext(), scale, Toast.LENGTH_SHORT).show();
                break;
            case LANGUAGE:
                String lang = String.valueOf(sharedPreferences.getString(key, ""));
                Toast.makeText(getApplicationContext(), lang, Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
