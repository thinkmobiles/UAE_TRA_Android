package com.uae.tra_smart_services.customviews;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.uae.tra_smart_services.R;
import com.uae.tra_smart_services.baseentities.BaseCustomSwitcher;

/**
 * Created by Andrey Korneychuk on 23.07.15.
 */
public class FontSizeSwitcherView extends BaseCustomSwitcher implements View.OnClickListener {


    protected float mScaleMin, mScaleMax, mScaleStep, mFontScale, mFontScaleNew;

    TextView makeBigger, makeSmaller;

    public FontSizeSwitcherView(Context _context) {
        super(_context);
    }

    public FontSizeSwitcherView(Context _context, AttributeSet _attrs) {
        super(_context, _attrs);
    }

    @Override
    public <T> void initPreferences(T _prefs) {
        mFontScale = (Float) _prefs;
    }

    @Override
    protected void initData(Context context, AttributeSet attrs){
        TypedArray typedArrayData =
                context.getTheme().obtainStyledAttributes(attrs, R.styleable.FontSizeSwitcherView, 0, 0);
        mFontScale = getResources().getConfiguration().fontScale;
        try {
            mScaleMin = typedArrayData.getFloat(R.styleable.FontSizeSwitcherView_scaleMin, 0.5f);
            mScaleMax = typedArrayData.getFloat(R.styleable.FontSizeSwitcherView_scaleMax, 1.5f);
            mScaleStep = typedArrayData.getFloat(R.styleable.FontSizeSwitcherView_scaleStep, 0.1f);
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
    protected final void initConfigs() {
        if (mFontScale < mScaleMax){
            bindView(makeBigger);
        } else {
            unBindView(makeBigger);
        }

        if (mFontScale > mScaleMin){
            bindView(makeSmaller);
        } else {
            unBindView(makeSmaller);
        }
    }

    @Override
    protected final int getLayoutId() {
        return R.layout.layout_fontsize_swither;
    }

    @Override
    public Type getType() {
        return Type.FONT;
    }

    @Override
    public final void onClick(final View _view) {
        mFontScale = mFontScaleNew == 0 ? mFontScale : mFontScaleNew;
        switch (_view.getId()){
            case R.id.tvMakeBigger_SA:
                makeTextBigger(_view);
                break;
            case R.id.tvMakeSmaller_SA:
                makeTextSmaller(_view);
                break;
        }
        mSettingsChangeListener.onSettingsChanged(this, mFontScaleNew);
    }

    private final void makeTextBigger(final View _view){
        if ((mFontScaleNew = mFontScale + mScaleStep) >= mScaleMax) {
            unBindView(_view);
        } else if((mFontScaleNew = mFontScale + mScaleStep) == mScaleMin + mScaleStep){
            bindView(makeSmaller);
        }
    }

    private final void makeTextSmaller(final View _view){
        if ((mFontScaleNew = mFontScale - mScaleStep) <= mScaleMin) {
            unBindView(_view);
        } else if((mFontScaleNew = mFontScale - mScaleStep) == mScaleMax - mScaleStep){
            bindView(makeBigger);
        }
    }

    @Override
    protected final void bindView(final View _view){
        _view.setOnClickListener(this);
        ((TextView)_view).setTextColor(getResources().getColor(R.color.hex_black_color));
    }

    @Override
    protected final void unBindView(final View _view){
        _view.setOnClickListener(null);
        ((TextView)_view).setTextColor(getResources().getColor(R.color.hex_color_light_gray));
    }
}