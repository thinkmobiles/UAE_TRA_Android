package com.uae.tra_smart_services.entities;

import android.content.Context;
import android.view.View;

import com.uae.tra_smart_services.interfaces.I_ViewFactory;

/**
 * Created by andrey on 7/26/15.
 */
public abstract class AbstractViewFactory<T extends View,K> implements I_ViewFactory<T,K> {
	protected Context context;

	protected AbstractViewFactory(Context context){
		this.context = context;
	}
}