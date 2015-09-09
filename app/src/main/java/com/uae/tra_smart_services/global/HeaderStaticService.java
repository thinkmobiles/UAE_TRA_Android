package com.uae.tra_smart_services.global;

import com.uae.tra_smart_services.customviews.HexagonalHeader;

/**
 * Created by ak-buffalo on 9/8/2015.
 */
public enum HeaderStaticService {
    HINT(HexagonalHeader.HEXAGON_BUTTON_HINT),
    SEARCH(HexagonalHeader.HEXAGON_BUTTON_SEARCH),
    NOTIFICATION(HexagonalHeader.HEXAGON_BUTTON_NOTIFICATION);

    private final int mServiceOrder;
    HeaderStaticService(int _serviceorder){
        mServiceOrder = _serviceorder;
    }
    
    public boolean equals(@HexagonalHeader.HexagonButton final int _hexagonButton){
        return mServiceOrder == _hexagonButton;
    }
}
