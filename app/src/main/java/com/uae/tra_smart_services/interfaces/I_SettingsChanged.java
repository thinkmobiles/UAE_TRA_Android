package com.uae.tra_smart_services.interfaces;

import com.uae.tra_smart_services.baseentities.BaseCustomSwitcher;

/**
 * Created by Andrey Korneychuk on 27.07.15.
 */
public interface I_SettingsChanged {
    void onSettingsChanged(BaseCustomSwitcher caller, Object data);
}
