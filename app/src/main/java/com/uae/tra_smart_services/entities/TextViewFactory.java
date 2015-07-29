package com.uae.tra_smart_services.entities;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
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
		textView.setGravity(Gravity.CENTER_VERTICAL);
		textView.setTag(entity.getTag());
		int paddingSide = context.getResources().getDimensionPixelSize(R.dimen.dp_divider_padding_side);
		textView.setPadding(paddingSide, textView.getPaddingTop(), paddingSide, textView.getPaddingBottom());
		textView.setTextAppearance(context, entity.getStyle());
		textView.setTextColor(context.getResources().getColor(entity.getTextColor()));
		textView.setOnClickListener(entity.getHadler());
		ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
				ViewGroup.LayoutParams.WRAP_CONTENT,
				ViewGroup.LayoutParams.MATCH_PARENT
		);
		textView.setLayoutParams(params);
	}
}