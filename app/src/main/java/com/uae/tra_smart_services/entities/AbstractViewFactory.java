package com.uae.tra_smart_services.entities;

import android.content.Context;
import android.view.View;

import com.uae.tra_smart_services.interfaces.ViewFactory;

/**
 * Created by ak-buffalo on 7/26/15.
 */
public abstract class AbstractViewFactory<T extends View,K> implements ViewFactory<T,K> {
	protected Context mContext;

	protected AbstractViewFactory(Context _context){
		mContext = _context;
	}
}