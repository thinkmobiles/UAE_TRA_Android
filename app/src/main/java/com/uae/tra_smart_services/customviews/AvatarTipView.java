package com.uae.tra_smart_services.customviews;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.text.Layout;
import android.text.StaticLayout;
import android.util.AttributeSet;
import android.util.TypedValue;

/**
 * Created by Vitaliy on 24/09/2015.
 */
public class AvatarTipView extends TutorialTipView {

    //region STATIC PROPERTIES
    private float mViewPointerLineLength;
    private float mTextPointerLineLength;
    private float mTitleTextPadding;
    private StaticLayout mTipTextLayout;
    private StaticLayout mTitleTextLayout;
    private float mSideOffset;
    //endregion

    //region PATHS
    private Path mLinePointerPath;
    private Path mTitleBorderPath;
    private Path mTextPointerPath;
    //endregion

    private boolean initialized = false;

    //region INIT METHODS
    public AvatarTipView(Context _context, AttributeSet _attrs) {
        super(_context, _attrs);
    }

    @Override
    protected void calculateStaticProperties() {
        mViewPointerLineLength = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20, getResources().getDisplayMetrics());
        mTextPointerLineLength = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, getResources().getDisplayMetrics());
        mTitleTextPadding = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, getResources().getDisplayMetrics());
    }
    //endregion

    public final void setSideOffset(final float _sideOffset) {
        mSideOffset = _sideOffset;
        initialized = true;
        calculateViewPointerPath();
        calculateTextLayouts();
        requestLayout();
        invalidate();
    }

    @Override
    protected void onSizeChanged(int _width, int _height, int _oldw, int _oldh) {
        super.onSizeChanged(_width, _height, _oldw, _oldh);
    }

    private void calculateTextLayouts() {
        mTipTextLayout = new StaticLayout(mTipText, mTipTextPaint,
                (int) (getWidth() - mSideOffset * 2), Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false);

        final int maxTextWidth = (int) (getWidth() - mSideOffset * 2 - mTitleTextPadding * 2);
        final Rect textRect = new Rect();
        mTitleTextPaint.getTextBounds(mTitleText, 0, mTitleText.length(), textRect);
        final int textWidth = textRect.width();

        mTitleTextLayout = new StaticLayout(mTitleText, mTitleTextPaint,
                textWidth < maxTextWidth ? textWidth + 20 : maxTextWidth,
                Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false);
    }

    //region CALCULATE PATHS

    private void calculateViewPointerPath() {
        mLinePointerPath = new Path();

        final float startY = getPaddingTop();
        final float startX = mCenterPoints[0].x;

        final float endY = startY + mViewPointerLineLength;
        final float endX = startX;

        mLinePointerPath.moveTo(startX, startY);
        mLinePointerPath.lineTo(endX, endY);
    }

    //endregion


    //region DRAWING
    @Override
    public final void draw(@NonNull final Canvas _canvas) {
        super.draw(_canvas);

        if (initialized) {
            drawViewPointer(_canvas);
            drawTitleRect(_canvas);
        }
    }

    private void drawViewPointer(final Canvas _canvas) {
        _canvas.drawCircle(mCenterPoints[0].x, getPaddingTop() + mPointRadius, mPointRadius, mViewPointerPaint);
        _canvas.drawPath(mLinePointerPath, mLinePointerPaint);
    }

    private void drawTitleRect(final Canvas _canvas) {
        mTitleTextLayout.getWidth();

        final float start = calculateDependsOnDirection(mSideOffset);
        final float end = calculateDependsOnDirection(mSideOffset) +
                calculateWithCoefficient(mTitleTextLayout.getWidth() + mTitleTextPadding * 2);

        _canvas.drawRect(start < end ? start : end,
                getPaddingTop() + mViewPointerLineLength,
                start < end ? end : start,
                getPaddingTop() + mViewPointerLineLength + mTitleTextPadding * 2 + mTipTextLayout.getHeight(),
                mTitleBorderPaint);

        _canvas.save();
        _canvas.translate(start < end ? start : end + calculateWithCoefficient(mTitleTextPadding),
                getPaddingTop() + mViewPointerLineLength + mTitleTextPadding);
        mTitleTextLayout.draw(_canvas);
        _canvas.restore();
    }
    //endregion


}
