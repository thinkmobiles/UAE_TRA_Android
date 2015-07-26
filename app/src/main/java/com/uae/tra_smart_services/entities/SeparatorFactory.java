package com.uae.tra_smart_services.entities;

import android.content.Context;
import android.view.View;

/**
 * Created by andrey on 7/26/15.
 */
public abstract class SeparatorFactory extends AbstractViewFactory<View, Separator> {
	protected SeparatorFactory(Context context) {
		super(context);
	}

	@Override
	public View createView(Separator entity) {
		View textView = new View(context);
		prepareData(textView, entity);
		return textView;
	}
}
