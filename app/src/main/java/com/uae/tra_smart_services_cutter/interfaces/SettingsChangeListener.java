package com.uae.tra_smart_services_cutter.interfaces;

        import com.uae.tra_smart_services_cutter.baseentities.BaseCustomSwitcher;

/**
 * Created by ak-buffalo on 27.07.15.
 */
public interface SettingsChangeListener {
    void onSettingsChanged(BaseCustomSwitcher caller, Object data);
}
