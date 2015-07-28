package com.uae.tra_smart_services.customviews;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.uae.tra_smart_services.R;
import com.uae.tra_smart_services.baseentities.BaseCustomSwitcher;


/**
 * Created by ak-buffalo on 23.07.15.
 */
public class FontSizeSwitcherView extends BaseCustomSwitcher{

    protected int scaleMin, scaleMax, scaleStep, fontScale, fontScaleNew;

    TextView makeBigger, makeSmaller;

    public FontSizeSwitcherView(Context context) {
        super(context);
    }

    public FontSizeSwitcherView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public <T> void initPreferences(T prefs) {
        fontScale = (Integer) prefs;
    }

    @Override
    protected void initData(Context context, AttributeSet attrs){
        TypedArray typedArrayData =
                context.getTheme().obtainStyledAttributes(attrs, R.styleable.FontSizeSwitcherView, 0, 0);
        try {
            scaleMin = typedArrayData.getInt(R.styleable.FontSizeSwitcherView_scaleMin, 5);
            scaleMax = typedArrayData.getInt(R.styleable.FontSizeSwitcherView_scaleMax, 15);
            scaleStep = typedArrayData.getInt(R.styleable.FontSizeSwitcherView_scaleStep, 1);
        } finally {
            typedArrayData.recycle();
        }
    }

    @Override
    protected void initViews() {
        super.initViews();
        makeBigger = findView(R.id.tvMakeBigger_SA);
        makeSmaller = findView(R.id.tvMakeSmaller_SA);
    }

    @Override
    protected void initConfigs() {
        if (fontScale < scaleMax){
            bindButton(makeBigger);
        } else {
            unBindButton(makeBigger);
        }

        if (fontScale > scaleMin){
            bindButton(makeSmaller);
        } else {
            unBindButton(makeSmaller);
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.layout_fontsize_swither;
    }

    @Override
    protected int getRootViewId() {
        return 0;
    }

    @Override
    public Type getType() {
        return Type.FONT;
    }

    @Override
    public void onClick(View view) {
        fontScale = fontScaleNew == 0 ? fontScale : fontScaleNew;
        switch (view.getId()){
            case R.id.tvMakeBigger_SA:
                makeTextBigger(view);
                break;
            case R.id.tvMakeSmaller_SA:
                makeTextSmaller(view);
                break;
        }
        mSettingsChangeListener.onSettingsChanged(this, fontScaleNew);
    }

    private void makeTextBigger(View view){
        if ((fontScaleNew = fontScale + scaleStep) >= scaleMax) {
            unBindButton(view);
        } else if((fontScaleNew = fontScale + scaleStep) == scaleMin + scaleStep){
            bindButton(makeSmaller);
        }
    }

    private void makeTextSmaller(View view){
        if ((fontScaleNew = fontScale - scaleStep) <= scaleMin) {
            unBindButton(view);
        } else if((fontScaleNew = fontScale - scaleStep) == scaleMax - scaleStep){
            bindButton(makeBigger);
        }
    }

    @Override
    protected void bindButton(View view){
        ((TextView)view).setOnClickListener(this);
        ((TextView)view).setTextColor(getResources().getColor(R.color.hex_black_color));
    }

    @Override
    protected void unBindButton(View view){
        ((TextView)view).setOnClickListener(null);
        ((TextView)view).setTextColor(getResources().getColor(R.color.hex_color_light_gray));
    }
}