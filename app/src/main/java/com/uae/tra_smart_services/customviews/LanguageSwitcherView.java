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
public class LanguageSwitcherView extends BaseCustomSwitcher {

    private String language;

    private TextView acitveLang;

    private static final Map<String, TextView> stateMap = new HashMap<String, TextView>(){
        {
            put("eng", null);
            put("arab", null);
            put("span", null);
        }
    };

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
    protected void initViews() {
        super.initViews();
        int index = 0;
        for (Map.Entry<String,TextView> entry : stateMap.entrySet()){
            if (index != 0){
                Separator separator = new Separator(
                        getContext(),
                        getResources().getInteger(R.integer.int_separator_width),
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        R.color.hex_auth_fields_separator_color
                );
                rootView.addView(separatorFactory.createView(separator));
            }
            LanguageSelector languageSelector = new LanguageSelector(
                    entry.getKey(), entry.getKey(), android.R.style.TextAppearance_Large, R.color.hex_black_color, this
            );

            TextView view = textViewFactoryFactory.createView(languageSelector);

            if(language.equals(entry.getKey())){
                acitveLang = view;
                unBindButton(view);
            }
            entry.setValue(view);
            rootView.addView(view);
            index++;
        }
    }

    @Override
    protected void initData(Context context, AttributeSet attrs){
        TypedArray typedArrayData =
                context.getTheme().obtainStyledAttributes(attrs, R.styleable.FontSizeSwitcherView, 0, 0);
        try {
            // TODO Change data obtaining logic to have ability declare required languages in XMl
        } finally {
            typedArrayData.recycle();
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.layout_switcher;
    }

    @Override
    protected int getRootViewId() {
        return R.id.container_switcher_view;
    }

    @Override
    public Type getType() {
        return Type.LANGUAGE;
    }

    @Override
    protected void bindButton(View view){
        acitveLang.setOnClickListener(this);
        acitveLang.setTextColor(getResources().getColor(R.color.hex_black_color));
        acitveLang = (TextView) view;
    }

    @Override
    protected void unBindButton(View view){
        ((TextView) view).setOnClickListener(null);
        ((TextView) view).setTextColor(getResources().getColor(R.color.hex_color_light_gray));
    }

    @Override
    public void onClick(View view) {
        unBindButton(view);
        bindButton(view);
        mSettingsChangeListener.onSettingsChanged(this, view.getTag());
    }
}