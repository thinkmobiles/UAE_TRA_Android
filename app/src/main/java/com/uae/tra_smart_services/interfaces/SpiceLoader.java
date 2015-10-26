package com.uae.tra_smart_services.interfaces;

import com.octo.android.robospice.SpiceManager;
import com.uae.tra_smart_services.global.C;

/**
 * Created by mobimaks on 28.09.2015.
 */
public interface SpiceLoader {
    SpiceManager getSpiceManager(@C.SpiceManager int _spiceManager);
}
