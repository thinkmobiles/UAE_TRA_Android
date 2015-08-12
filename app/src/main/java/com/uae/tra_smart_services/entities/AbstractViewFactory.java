package com.uae.tra_smart_services.entities;

import android.content.Context;
import android.view.View;

/**
 * Created by Andrey Korneychuk on 7/26/15.
 */
public abstract class AbstractViewFactory<T extends View,K> implements com.uae.tra_smart_services.interfaces.AbstractViewFactory<T,K> {
	protected Context mContext;

	protected AbstractViewFactory(Context _context){
		mContext = _context;
	}
}