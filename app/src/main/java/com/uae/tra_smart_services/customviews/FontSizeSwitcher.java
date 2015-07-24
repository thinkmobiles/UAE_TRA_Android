package com.uae.tra_smart_services.customviews;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.uae.tra_smart_services.R;
import com.uae.tra_smart_services.activities.SettingsActivity;
import com.uae.tra_smart_services.baseentities.BaseCustomSwitcher;


/**
 * Created by ak-buffalo on 23.07.15.
 */
public class FontSizeSwitcher extends BaseCustomSwitcher{

    protected int scaleMin, scaleMax, scaleStep, fontScale, fontScaleNew;

    TextView makeBigger, makeSmaller;

    public FontSizeSwitcher(Context context) {
        super(context);
    }

    public FontSizeSwitcher(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void obtainData(Context context, AttributeSet attrs){
        TypedArray typedArrayData =
                context.getTheme().obtainStyledAttributes(attrs, R.styleable.FontSizeSwitcher, 0, 0);
        try {
            scaleMin = typedArrayData.getInt(R.styleable.FontSizeSwitcher_scaleMin, 5);
            scaleMax = typedArrayData.getInt(R.styleable.FontSizeSwitcher_scaleMax, 15);
            scaleStep = typedArrayData.getInt(R.styleable.FontSizeSwitcher_scaleStep, 1);
        } finally {
            typedArrayData.recycle();
        }
    }

    @Override
    protected void initViews() {
        super.initViews();
        makeBigger = (TextView)findViewById(R.id.tvMakeBigger_SA);
        makeSmaller = (TextView)findViewById(R.id.tvMakeSmaller_SA);
    }

    @Override
    protected void initConfigs() {
        fontScale = getIntPref(SettingsActivity.FONT_SIZE_SCALE, 10);
        if (fontScale < scaleMax){
            bindBigButton();
        } else {
            unBindBigButton();
        }

        if (fontScale > scaleMin){
            bindSmallButton();
        } else {
            unBindSmallButton();
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.layout_fontsize_swither;
    }

    @Override
    public void onClick(View v) {
        fontScale = getIntPref(SettingsActivity.FONT_SIZE_SCALE, 10);
        switch (v.getId()){
            case R.id.tvMakeBigger_SA:
                makeTextBigger();
                break;
            case R.id.tvMakeSmaller_SA:
                makeTextSmaller();
                break;
        }
        updateIntPref(SettingsActivity.FONT_SIZE_SCALE, fontScaleNew);
    }

    private void makeTextBigger(){
        if ((fontScaleNew = fontScale + scaleStep) >= scaleMax) {
            unBindBigButton();
        } else if((fontScaleNew = fontScale + scaleStep) == scaleMin + scaleStep){
            bindSmallButton();
        }
    }

    private void makeTextSmaller(){
        if ((fontScaleNew = fontScale - scaleStep) <= scaleMin) {
            unBindSmallButton();
        } else if((fontScaleNew = fontScale - scaleStep) == scaleMax - scaleStep){
            bindBigButton();
        }
    }

    private void bindBigButton(){
        makeBigger.setOnClickListener(this);
        makeBigger.setTextColor(getResources().getColor(R.color.hex_black_color));
    }

    private void bindSmallButton(){
        makeSmaller.setOnClickListener(this);
        makeSmaller.setTextColor(getResources().getColor(R.color.hex_black_color));
    }

    private void unBindBigButton(){
        makeBigger.setOnClickListener(null);
        makeBigger.setTextColor(getResources().getColor(R.color.hex_color_light_gray));
    }

    private void unBindSmallButton(){
        makeSmaller.setOnClickListener(null);
        makeSmaller.setTextColor(getResources().getColor(R.color.hex_color_light_gray));
    }
}