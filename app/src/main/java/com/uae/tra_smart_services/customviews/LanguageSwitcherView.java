package com.uae.tra_smart_services.customviews;

import android.content.Context;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.uae.tra_smart_services.R;
import com.uae.tra_smart_services.baseentities.BaseCustomSwitcher;
import com.uae.tra_smart_services.entities.LanguageSelector;
import com.uae.tra_smart_services.entities.Separator;
import com.uae.tra_smart_services.entities.SeparatorFactory;
import com.uae.tra_smart_services.entities.TextViewFactory;

import org.xmlpull.v1.XmlPullParserException;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static com.uae.tra_smart_services.entities.H.parseXmlToMap;

/**
 * Created by ak-buffalo on 24.07.15.
 */
public class LanguageSwitcherView extends BaseCustomSwitcher implements View.OnClickListener {

    private TextView acitveLang;

    private Map<String, String> langsMap;

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
    public <T> void initPreferences(T prefs){}

    @Override
    protected void initData(Context context, AttributeSet attrs){
        try {
            XmlResourceParser parser = context.getResources().getXml(R.xml.languages);
            langsMap = parseXmlToMap(parser);
        } catch(Exception ex) {
            Log.e(this.getClass().getSimpleName().toString(), ex.toString());
        }
    }

    @Override
    protected void initViews() {
        super.initViews();
        int index = 0;
        for (Map.Entry<String,String> entry : langsMap.entrySet()){
            LanguageSelector languageSelector = new LanguageSelector(
                    entry.getValue(), entry.getKey(), android.R.style.TextAppearance_Medium, R.color.hex_black_color, this
            );
            TextView langSelector = textViewFactoryFactory.createView(languageSelector);

            if (index != 0){
                Separator separator = new Separator(
                        getContext(),
                        getResources().getDimensionPixelSize(R.dimen.dp_authorization_fields_separator_height),
                        langSelector.getLineHeight() * 4 / 3,
                        R.color.hex_auth_fields_separator_color
                );
                addView(separatorFactory.createView(separator));
            }

            if(Locale.getDefault().getLanguage().equals(entry.getKey())){
                acitveLang = langSelector;
                unBindView(langSelector);
            }
            addView(langSelector);
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