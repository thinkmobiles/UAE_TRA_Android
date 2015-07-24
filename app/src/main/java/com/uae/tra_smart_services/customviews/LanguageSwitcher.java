package com.uae.tra_smart_services.customviews;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.uae.tra_smart_services.R;
import com.uae.tra_smart_services.activities.SettingsActivity;
import com.uae.tra_smart_services.baseentities.BaseCustomSwitcher;

/**
 * Created by ak-buffalo on 24.07.15.
 */
public class LanguageSwitcher extends BaseCustomSwitcher {

    private static final String[] LANGUAGES = {
            "english",
            "arabic"
    };

    private LinearLayout rootView;

    public LanguageSwitcher(Context context) {
        super(context);
    }

    public LanguageSwitcher(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void initViews() {
        super.initViews();
        rootView = (LinearLayout) findViewById(R.id.root_view_language_switcher);
        for (int i = 0; i < LANGUAGES.length; i++){
            if (i != 0){
                View separator = new View(getContext());
                ViewGroup.LayoutParams param1 = new ViewGroup.LayoutParams(rootView.getHeight(), ViewGroup.LayoutParams.MATCH_PARENT);
                ViewGroup.LayoutParams param2 = new ViewGroup.LayoutParams(3, ViewGroup.LayoutParams.MATCH_PARENT);
                separator.setBackgroundColor(getResources().getColor(R.color.hex_auth_fields_separator_color));
                separator.setLayoutParams(param1);
                separator.setLayoutParams(param2);
                rootView.addView(separator);
            }

            TextView textView = new TextView(getContext());
            textView.setText(LANGUAGES[i]);
            textView.setTag(LANGUAGES[i]);
            textView.setTextAppearance(getContext(), android.R.style.TextAppearance_Medium);
            textView.setTextColor(getResources().getColor(R.color.hex_black_color));
            textView.setOnClickListener(this);
            rootView.addView(textView);
        }
    }

    @Override
    protected void obtainData(Context context, AttributeSet attrs){
        TypedArray typedArrayData =
                context.getTheme().obtainStyledAttributes(attrs, R.styleable.FontSizeSwitcher, 0, 0);
        try {
            // TODO Change data obtaining logic to have ability declare required languages in XMl
        } finally {
            typedArrayData.recycle();
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.layout_language_switcher;
    }

    @Override
    public void onClick(View v) {
        updateStrPref(SettingsActivity.LANGUAGE, ((String) v.getTag()).toString());
    }
}
