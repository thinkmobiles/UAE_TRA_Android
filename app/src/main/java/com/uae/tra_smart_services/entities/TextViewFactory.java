package com.uae.tra_smart_services.entities;

import android.content.Context;
import android.widget.TextView;

/**
 * Created by andrey on 7/26/15.
 */
public abstract class TextViewFactory extends AbstractViewFactory<TextView, LanguageSelector>{

	protected TextViewFactory(Context context) {
		super(context);
	}

	@Override
	public TextView createView(LanguageSelector entity) {
		TextView textView = new TextView(context);
		prepareData(textView, entity);
		return textView;
	}
}