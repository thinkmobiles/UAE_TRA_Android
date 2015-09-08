package com.uae.tra_smart_services.entities;

import android.content.Context;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.uae.tra_smart_services.R;

/**
 * Created by PC on 9/7/2015.
 */
public class ButtonFactory extends AbstractViewFactory<Button, LanguageSelector>{

    public ButtonFactory(Context context) {
        super(context);
    }

    @Override
    public Button createView(LanguageSelector entity) {
        Button textView = new Button(mContext);
        prepareData(textView, entity);
        return textView;
    }

    @Override
    public void prepareData(Button button, LanguageSelector entity) {
        button.setText(entity.getText());
        button.setGravity(Gravity.CENTER);
        button.setTag(entity.getTag());
        int paddingSide = 0;
        button.setPadding(paddingSide, button.getPaddingTop(), paddingSide, button.getPaddingBottom());
        button.setOnClickListener(entity.getHadler());
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        button.setLayoutParams(params);
    }
}
