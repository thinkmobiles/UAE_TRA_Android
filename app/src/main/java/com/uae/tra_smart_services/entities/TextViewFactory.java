package com.uae.tra_smart_services.entities;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.TextView;

import com.uae.tra_smart_services.R;

/**
 * Created by andrey on 7/26/15.
 */
public class TextViewFactory extends AbstractViewFactory<TextView, LanguageSelector>{

	public TextViewFactory(Context context) {
		super(context);
	}

	@Override
	public TextView createView(LanguageSelector entity) {
		TextView textView = new TextView(context);
		prepareData(textView, entity);
		return textView;
	}

	@Override
	public void prepareData(TextView textView, LanguageSelector entity) {
		textView.setText(entity.getText());
		textView.setTag(entity.getTag());
		textView.setPadding(2, 2, 2, 2);
		textView.setTextAppearance(context, entity.getStyle());
		textView.setTextColor(context.getResources().getColor(entity.getTextColor()));
		textView.setOnClickListener(entity.getHadler());
		ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
				ViewGroup.LayoutParams.WRAP_CONTENT,
				ViewGroup.LayoutParams.MATCH_PARENT
		);
		textView.setGravity(TextView.TEXT_ALIGNMENT_GRAVITY);
		textView.setLayoutParams(params);
	}
}