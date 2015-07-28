package com.uae.tra_smart_services.entities;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by andrey on 7/26/15.
 */
public class SeparatorFactory extends AbstractViewFactory<View, Separator> {
	public SeparatorFactory(Context context) {
		super(context);
	}

	@Override
	public View createView(Separator entity) {
		View textView = new View(context);
		prepareData(textView, entity);
		return textView;
	}

	@Override
	public void prepareData(View separator, Separator entity) {
		ViewGroup.LayoutParams param1 = new ViewGroup.LayoutParams(entity.getWidth(), entity.getHeight());
		separator.setBackgroundColor(entity.getColor());
		separator.setLayoutParams(param1);
	}

}
