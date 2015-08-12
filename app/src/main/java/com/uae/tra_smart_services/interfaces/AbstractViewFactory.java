package com.uae.tra_smart_services.interfaces;

import android.view.View;

/**
 * Created by Andrey Korneychuk on 7/26/15.
 */
public interface AbstractViewFactory<T extends View,K>{
	T createView(K data);
	void prepareData(T view, K data);
}