package com.uae.tra_smart_services.customviews;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.uae.tra_smart_services.R;
import com.uae.tra_smart_services.baseentities.BaseCustomSwitcher;
import com.uae.tra_smart_services.entities.ButtonFactory;
import com.uae.tra_smart_services.entities.SeparatorFactory;

import java.util.Locale;
import java.util.Map;

import static com.uae.tra_smart_services.global.H.parseXmlToMap;

/**
 * Created by Andrey Korneychuk on 24.07.15.
 */
public class LanguageSwitcherView extends BaseCustomSwitcher implements View.OnClickListener {
    private TextView mAcitveLang, enLang, arLang;
    private TextView[] langViews;
    private ButtonFactory mButtonfactory;
    private SeparatorFactory mSeparatorFactory;

    public LanguageSwitcherView(Context context) {
        super(context);
    }

    public LanguageSwitcherView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void initFactories(){
        mButtonfactory = new ButtonFactory(getContext());
        mSeparatorFactory = new SeparatorFactory(getContext());
    }

    @Override
    public <T> void initPreferences(T prefs){}

    private Map<String, String> mLangsMap;

    @Override
    protected void initData(Context context, AttributeSet attrs){
        try {
            mLangsMap = parseXmlToMap(context, R.xml.languages);
        } catch(Exception ex) {
            Log.e(this.getClass().getSimpleName().toString(), ex.toString());
        }
    }

    @Override
    protected void initViews() {
        super.initViews();
//        int index = 0;
        setOrientation(HORIZONTAL);
        setPadding(5, 5, 5, 5);
        langViews = new TextView[]{
                enLang = findView(R.id.tvEnglishLang_LS),
                arLang = findView(R.id.tvArabicLang_LS)
        };

        for (TextView langView : langViews){
            if(Locale.getDefault().getLanguage().equals(langView.getTag().toString())){
                unBindView(langView);
            } else {
                bindView(langView);
            }
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.layout_language_switcher;
    }

    @Override
    public Type getType() {
        return Type.LANGUAGE;
    }

    @Override
    protected void bindView(View view){
        view.setBackgroundColor(Color.LTGRAY);
        view.setOnClickListener(this);
    }

    @Override
    protected void unBindView(View view){
        view.setOnClickListener(null);
    }

    @Override
    public void onClick(View view) {
        mSettingsChangeListener.onSettingsChanged(this, view.getTag());
    }
}