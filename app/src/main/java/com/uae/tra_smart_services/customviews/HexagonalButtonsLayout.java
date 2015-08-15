package com.uae.tra_smart_services.customviews;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

import com.uae.tra_smart_services.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vitaliy on 13/08/2015.
 */
public class HexagonalButtonsLayout extends View {

    private Paint mSeparatorPaint;
    private Paint mButtonPaint;
    private Paint mShadowPaint;
    private Paint mButtonSecondColorPaint;
    private Paint mPressedButtonPaint;
    private int mSeparatorColor;
    private int mPressedButtonColor;
    private int mButtonsCount;
    private float mHexagonGapWidth;
    private float mSeparatorStrokeWidth;
    private float mTriangleHeight;
    private float mRadius;
    private float mSeparatorTriangleHeight;
    private float mSeparatorRadius;

    private List<Drawable> mDrawables;

    public HexagonalButtonsLayout(final Context _context, final AttributeSet _attrs) {
        super(_context, _attrs);
        initProperties(_attrs);
        initPaint();
        initDrawables();
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
    }

    public final float getHalfOuterRadius() {
        return mSeparatorRadius / 2 + mHexagonGapWidth;
    }

    private void initProperties(final AttributeSet _attrs) {
        TypedArray typedArrayData = getContext().getTheme().
                obtainStyledAttributes(_attrs, R.styleable.HexagonalButtonsLayoutAttrs, 0, 0);
        try {
            mSeparatorColor = typedArrayData.getColor(R.styleable.HexagonalButtonsLayoutAttrs_separatorColor, Color.WHITE);
            mButtonsCount = typedArrayData.getInt(R.styleable.HexagonalButtonsLayoutAttrs_hexagonCount, 4);
            mSeparatorStrokeWidth = typedArrayData.getDimension(R.styleable.HexagonalButtonsLayoutAttrs_separatorStrokeWidth, 3);
            mHexagonGapWidth = typedArrayData.getDimension(R.styleable.HexagonalButtonsLayoutAttrs_hexagonGapWidth, 3);
        } finally {
            typedArrayData.recycle();
        }
    }

    private void initPaint() {
        mSeparatorPaint = new Paint();
        mSeparatorPaint.setColor(mSeparatorColor);
        mSeparatorPaint.setStyle(Paint.Style.STROKE);
        mSeparatorPaint.setStrokeWidth(mSeparatorStrokeWidth);

        mButtonPaint = new Paint();
        mButtonPaint.setColor(Color.WHITE);
        mButtonPaint.setStyle(Paint.Style.FILL);

        mShadowPaint = new Paint();
        mShadowPaint.setShadowLayer(3.0f, 4.0f, 4.0f, 0xFF8C8C8C);
        mShadowPaint.setStyle(Paint.Style.FILL);

        mButtonSecondColorPaint = new Paint();
        mButtonSecondColorPaint.setColor(0xFF44545F);
        mButtonSecondColorPaint.setStyle(Paint.Style.FILL);
    }

    private void initDrawables() {
        mDrawables = new ArrayList<>();

        mDrawables.add(ContextCompat.getDrawable(getContext(), R.drawable.ic_verif));
        mDrawables.add(ContextCompat.getDrawable(getContext(), R.drawable.ic_spam));
        mDrawables.add(ContextCompat.getDrawable(getContext(), R.drawable.ic_internet));
        mDrawables.add(ContextCompat.getDrawable(getContext(), R.drawable.ic_internet));
    }

    @Override
    protected final void onMeasure(final int _widthMeasureSpec, final int _heightMeasureSpec) {
        int myHeight;
        int width;
        int height;

        final int heightMode = MeasureSpec.getMode(_heightMeasureSpec);
        final int widthSize = MeasureSpec.getSize(_widthMeasureSpec);
        final int heightSize = MeasureSpec.getSize(_heightMeasureSpec);

        width = widthSize;

        final float triangleHeight = ((width - getPaddingLeft() - getPaddingRight() -
                mHexagonGapWidth * mButtonsCount - mHexagonGapWidth) / mButtonsCount) / 2;
        final float radius = (float) (triangleHeight * 2 / Math.sqrt(3));

        myHeight = (int) (radius * 2 + mHexagonGapWidth + mSeparatorStrokeWidth);

        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else if (heightMode == MeasureSpec.AT_MOST) {
            height = Math.min(myHeight, heightSize);
        } else {
            height = myHeight;
        }
        setMeasuredDimension(width, height);
    }

    @Override
    protected final void onSizeChanged(final int _w, final int _h,
                                       final int _oldw, final int _oldh) {
        super.onSizeChanged(_w, _h, _oldw, _oldh);

        calculateVariables(_w);
        measureDrawablesBounds();
    }

    private void calculateVariables(final int _w) {
        mTriangleHeight = ((_w - getPaddingLeft() - getPaddingRight() -
                mHexagonGapWidth * mButtonsCount - mHexagonGapWidth) / mButtonsCount) / 2;
        mRadius = (float) (mTriangleHeight * 2 / Math.sqrt(3));

        mSeparatorTriangleHeight = mTriangleHeight + mHexagonGapWidth / 2;
        mSeparatorRadius = (float) (mSeparatorTriangleHeight * 2 / Math.sqrt(3));
    }

    private void measureDrawablesBounds() {
        float centerY = getPaddingTop() + mRadius / 2 + getMaxDrawableHeight(mDrawables);
        float centerX = getPaddingLeft() + mHexagonGapWidth + mTriangleHeight;

        for (int hexagon = 0; hexagon < mButtonsCount;
             hexagon++, centerX += mHexagonGapWidth + mTriangleHeight * 2) {
            final float drawableWidth = mDrawables.get(hexagon).getMinimumWidth();
            final float drawableHeight = mDrawables.get(hexagon).getMinimumHeight();
            mDrawables.get(hexagon).setBounds((int) (centerX - drawableWidth / 2), (int) (centerY - drawableHeight),
                    (int) (centerX + drawableWidth / 2), (int) centerY);
        }
    }

    private float getMaxDrawableHeight(final List<Drawable> _drawables) {
        float max = 0;

        for (final Drawable drawable : _drawables) {
            if (drawable.getMinimumHeight() > max) {
                max = drawable.getMinimumHeight();
            }
        }

        return max;
    }

    @Override
    protected final void onDraw(final Canvas _canvas) {
        super.onDraw(_canvas);

        drawHexagons(_canvas);
        drawBottomSeparator(_canvas);
        drawDrawables(_canvas);
    }

    private void drawHexagons(final Canvas _canvas) {
        Path buttonsPath = new Path();
        Path buttonsSecondPath = new Path();

        float centerY = getPaddingTop() + mRadius;
        float centerX = getPaddingLeft() + mHexagonGapWidth + mTriangleHeight;

        for (int hexagon = 0; hexagon < mButtonsCount;
             hexagon++, centerX += mHexagonGapWidth + mTriangleHeight * 2) {
            if (hexagon < (mButtonsCount / 2)) {
                buttonsPath = calculatePath(buttonsPath, centerX, centerY);
            } else {
                buttonsSecondPath = calculatePath(buttonsSecondPath, centerX, centerY);
            }
        }

        _canvas.drawPath(buttonsSecondPath, mShadowPaint);
        _canvas.drawPath(buttonsSecondPath, mButtonSecondColorPaint);
        _canvas.drawPath(buttonsPath, mShadowPaint);
        _canvas.drawPath(buttonsPath, mButtonPaint);
    }

    private Path calculatePath(Path _path, final float _centerX, final float _centerY) {
        _path.moveTo(_centerX, _centerY + mRadius);
        _path.lineTo(_centerX - mTriangleHeight, _centerY + mRadius / 2);
        _path.lineTo(_centerX - mTriangleHeight, _centerY - mRadius / 2);
        _path.lineTo(_centerX, _centerY - mRadius);
        _path.lineTo(_centerX + mTriangleHeight, _centerY - mRadius / 2);
        _path.lineTo(_centerX + mTriangleHeight, _centerY + mRadius / 2);
        _path.close();

        return _path;
    }

    private void drawBottomSeparator(final Canvas _canvas) {
        Path separatorPath = new Path();

        float centerY = getPaddingTop() + mRadius + mHexagonGapWidth / 2;
        float centerX = getPaddingLeft() + mHexagonGapWidth + mTriangleHeight;

        separatorPath.moveTo(centerX - 2 * mSeparatorTriangleHeight, centerY + mSeparatorRadius);
        separatorPath.lineTo(centerX - mSeparatorTriangleHeight, centerY + mSeparatorRadius / 2);

        for (int hexagon = 0; hexagon < mButtonsCount;
             hexagon++, centerX += mSeparatorTriangleHeight * 2) {

            separatorPath.lineTo(centerX, centerY + mSeparatorRadius);
            separatorPath.lineTo(centerX + mSeparatorTriangleHeight, centerY + mSeparatorRadius / 2);
        }

        separatorPath.lineTo(centerX + 2 * mSeparatorTriangleHeight, centerY + mSeparatorRadius);

        _canvas.drawPath(separatorPath, mSeparatorPaint);
    }

    private void drawDrawables(final Canvas _canvas) {
        for (final Drawable drawable : mDrawables) {
            drawable.draw(_canvas);
        }
    }
}
