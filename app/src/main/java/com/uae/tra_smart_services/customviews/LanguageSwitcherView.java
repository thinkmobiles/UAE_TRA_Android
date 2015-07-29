package com.uae.tra_smart_services.customviews;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.uae.tra_smart_services.R;
import com.uae.tra_smart_services.baseentities.BaseCustomSwitcher;
import com.uae.tra_smart_services.entities.LanguageSelector;
import com.uae.tra_smart_services.entities.Separator;
import com.uae.tra_smart_services.entities.SeparatorFactory;
import com.uae.tra_smart_services.entities.TextViewFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ak-buffalo on 24.07.15.
 */
public class LanguageSwitcherView extends BaseCustomSwitcher implements View.OnClickListener {

    private String language;

    private TextView acitveLang;

    private static final Map<String, TextView> stateMap = new HashMap<>();

    private TextViewFactory textViewFactoryFactory;

    private SeparatorFactory separatorFactory;

    public LanguageSwitcherView(Context context) {
        super(context);
    }

    public LanguageSwitcherView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void initFactories(){
        textViewFactoryFactory = new TextViewFactory(getContext());
        separatorFactory = new SeparatorFactory(getContext());
    }

    @Override
    public <T> void initPreferences(T prefs){
        language = (String) prefs;
    }

    @Override
    protected void initData(Context context, AttributeSet attrs){
        TypedArray typedArrayData =
                context.getTheme().obtainStyledAttributes(attrs, R.styleable.LanguageSwitcherView, 0, 0);

        CharSequence[] str = typedArrayData.getTextArray(R.styleable.LanguageSwitcherView_android_entries);

        try {
            for(CharSequence lang : typedArrayData.getTextArray(R.styleable.LanguageSwitcherView_android_entries)){
                stateMap.put(lang.toString(), null);
            }
        } finally {
            typedArrayData.recycle();
        }
    }

    @Override
    protected void initViews() {
        super.initViews();
        int index = 0;
        for (Map.Entry<String,TextView> entry : stateMap.entrySet()){
            LanguageSelector languageSelector = new LanguageSelector(
                    entry.getKey(), entry.getKey(), android.R.style.TextAppearance_Medium, R.color.hex_black_color, this
            );
            TextView view = textViewFactoryFactory.createView(languageSelector);

            if (index != 0){
                Separator separator = new Separator(
                        getContext(),
                        getResources().getDimensionPixelSize(R.dimen.dp_authorization_fields_separator_height),
                        view.getLineHeight() * 4 / 3,
                        R.color.hex_auth_fields_separator_color
                );
                addView(separatorFactory.createView(separator));
            }

            if(language.equals(entry.getKey())){
                acitveLang = view;
                unBindView(view);
            }
            entry.setValue(view);
            addView(view);
            index++;
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.layout_switcher;
    }

    @Override
    public Type getType() {
        return Type.LANGUAGE;
    }

    @Override
    protected void bindView(View view){
        acitveLang.setOnClickListener(this);
        acitveLang.setTextColor(getResources().getColor(R.color.hex_black_color));
        acitveLang = (TextView) view;
    }

    @Override
    protected void unBindView(View view){
        view.setOnClickListener(null);
        ((TextView) view).setTextColor(getResources().getColor(R.color.hex_color_light_gray));
    }

    @Override
    public void onClick(View view) {
        unBindView(view);
        bindView(view);
        mSettingsChangeListener.onSettingsChanged(this, view.getTag());
    }
}