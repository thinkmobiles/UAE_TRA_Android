package com.uae.tra_smart_services.customviews;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.uae.tra_smart_services.R;
import com.uae.tra_smart_services.activities.SettingsActivity;
import com.uae.tra_smart_services.baseentities.BaseCustomSwitcher;
import com.uae.tra_smart_services.entities.LanguageSelector;
import com.uae.tra_smart_services.entities.Separator;
import com.uae.tra_smart_services.entities.SeparatorFactory;
import com.uae.tra_smart_services.entities.TextViewFactory;
import com.uae.tra_smart_services.interfaces.I_ViewFactory;

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

    final I_ViewFactory textViewFactoryFactory = new TextViewFactory(getContext()) {
        @Override
        public void prepareData(TextView textView, LanguageSelector entity) {
            textView.setText(entity.getText());
            textView.setTag(entity.getTag());
            textView.setTextAppearance(getContext(), entity.getStyle());
            textView.setTextColor(getResources().getColor(entity.getTextColor()));
            textView.setOnClickListener(entity.getHadler());
        }
    };

    final I_ViewFactory separatorFactory = new SeparatorFactory(getContext()) {
        @Override
        public void prepareData(View separator, Separator entity) {
            ViewGroup.LayoutParams param1 = new ViewGroup.LayoutParams(entity.getWidth(), entity.getHeight());
            separator.setBackgroundColor(entity.getColor());
            separator.setLayoutParams(param1);
        }
    };

    @Override
    protected void initViews() {
        super.initViews();
        rootView = (LinearLayout) findViewById(R.id.root_view_language_switcher);

        for (int i = 0; i < LANGUAGES.length; i++){
            if (i != 0){
                Separator separator = new Separator(
                        getContext(),
                        R.dimen.dp_separator_width,
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        R.color.hex_auth_fields_separator_color
                );
                rootView.addView(separatorFactory.createView(separator));
            }
            LanguageSelector languageSelector = new LanguageSelector(
                    LANGUAGES[i],LANGUAGES[i], android.R.style.TextAppearance_Medium, R.color.hex_black_color, this
            );
            rootView.addView(textViewFactoryFactory.createView(languageSelector));
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
