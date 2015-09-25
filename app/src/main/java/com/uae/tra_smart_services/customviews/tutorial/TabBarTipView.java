package com.uae.tra_smart_services.customviews.tutorial;

import android.content.Context;
import android.graphics.Path;
import android.graphics.Rect;
import android.text.Layout;
import android.text.StaticLayout;
import android.util.AttributeSet;
import android.util.TypedValue;

/**
 * Created by Vitaliy on 25/09/2015.
 */
public class TabBarTipView extends TutorialTipView {

    //region PROPERTIES
    private float mTipTextPadding;
    //endregion

    //region INIT METHODS
    public TabBarTipView(Context _context, AttributeSet _attrs) {
        super(_context, _attrs);
    }

    @Override
    protected void calculateStaticProperties() {
        mTitleTextPadding = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, getResources().getDisplayMetrics());
        mTitleRectMargin = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 0, getResources().getDisplayMetrics());
    }
    //endregion

    //region CALCULATING
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        calculateLineLengths(h);
        calculateTextLayouts();
        calculateViewPointerPath();
    }

    private void calculateLineLengths(final int _height) {
        mViewPointerLineLength = _height * 0.25f;
        mTextPointerLineLength = mViewPointerLineLength * 0.25f;
    }

    private void calculateTextLayouts() {
        mTipTextPadding = getWidth() * 0.15f;
        mTipTextLayout = new StaticLayout(mTipText, mTipTextPaint,
                (int) (getWidth() - mTipTextPadding * 2), Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false);

        final int maxTextWidth = (int) (getWidth() - mTipTextPadding * 2 - mTitleTextPadding * 2);
        final Rect textRect = new Rect();
        mTitleTextPaint.getTextBounds(mTitleText, 0, mTitleText.length(), textRect);
        final int textWidth = textRect.width();

        mTitleTextLayout = new StaticLayout(mTitleText, mTitleTextPaint,
                textWidth < maxTextWidth ? textWidth + 1 : maxTextWidth,
                Layout.Alignment.ALIGN_CENTER, 1.0f, 0.0f, false);
    }

    private void calculateViewPointerPath() {
        mLinePointerPath = new Path();

        final float centerX = getWidth() / 2 - mLineWidth / 2;

        mLinePointerPath.moveTo(centerX, getHeight() - getPaddingBottom());
        mLinePointerPath.lineTo(centerX, getHeight() - getPaddingBottom() - mViewPointerLineLength);
    }
    //endregion
}
