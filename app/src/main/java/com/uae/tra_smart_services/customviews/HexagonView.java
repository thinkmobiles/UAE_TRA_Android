package com.uae.tra_smart_services.customviews;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.Region;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

import com.uae.tra_smart_services.R;


/**
 * Created by mobimaks on 02.08.2015.
 */
public class HexagonView extends View {

    private final int DEFAULT_HEXAGON_RADIUS = (int) (30 * getResources().getDisplayMetrics().density);
    private final int HEXAGON_BORDER_COUNT = 6;

    private double mHexagonSide, mHexagonInnerRadius;
    private final Path mPath;
    private final Paint mPaint, mShadowPaint;
    private int mBorderWidth;
    private Drawable mBackgroundDrawable;
    private Rect mHexagonRect = new Rect();

    public HexagonView(Context context) {
        this(context, null);
    }

    public HexagonView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.HexagonView);
        try {
            setHexagonSide(a.getDimensionPixelSize(R.styleable.HexagonView_hexagonSideSize, DEFAULT_HEXAGON_RADIUS));
            mBorderWidth = a.getDimensionPixelSize(R.styleable.HexagonView_hexagonBorderSize, 0);
            mBackgroundDrawable = a.getDrawable(R.styleable.HexagonView_hexagonBackground);
        } finally {
            a.recycle();
        }
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(0xFFC8C7C6);
        mPaint.setStrokeWidth(mBorderWidth);
        mPaint.setStyle(Paint.Style.STROKE);

        mShadowPaint = new Paint();
        mShadowPaint.setStyle(Paint.Style.FILL);

        mPath = new Path();
    }

    public final void setHexagonSide(final int _hexagonSideSize) {
        mHexagonSide = _hexagonSideSize;
        mHexagonInnerRadius = Math.sqrt(3) * mHexagonSide / 2;
    }

    public final void setHexagonShadow(final float _radius, final int _color) {
        mShadowPaint.setShadowLayer(_radius, 0, 0, _color);
    }

    public final void setBackgroundDrawable(@DrawableRes final int _drawableRes) {
        mBackgroundDrawable = ContextCompat.getDrawable(getContext(), _drawableRes);
        invalidate();
    }

    public final double getHexagonRadius() {
        return mHexagonSide;
    }

    public int getBorderWidth() {
        return mBorderWidth;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        final int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        final float widthSize = MeasureSpec.getSize(widthMeasureSpec);
        final float myWidth = Math.round(2 * mHexagonInnerRadius + mBorderWidth);
        final float width;

        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize;
        } else if (widthMode == MeasureSpec.AT_MOST) {
            width = Math.min(myWidth, widthSize);
        } else {
            width = myWidth;
        }

        setMeasuredDimension((int) width, (int) Math.round(2 * mHexagonSide + mBorderWidth));
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        double section = 2.0 * Math.PI / HEXAGON_BORDER_COUNT;

        mPath.reset();
        mPath.moveTo(
                (float) (w / 2 + mHexagonSide * Math.sin(0)),
                (float) (h / 2 + mHexagonSide * Math.cos(0)));

        for (int i = 1; i < HEXAGON_BORDER_COUNT; i++) {
            mPath.lineTo(
                    (float) (w / 2 + mHexagonSide * Math.sin(section * i)),
                    (float) (h / 2 + mHexagonSide * Math.cos(section * i)));
        }
        mPath.close();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //region Draw shadow
        canvas.save();
        canvas.clipPath(mPath, Region.Op.DIFFERENCE);
        canvas.drawPath(mPath, mShadowPaint);
        canvas.restore();
        //endregion

        if (mBackgroundDrawable != null) {
            canvas.clipPath(mPath);
            canvas.getClipBounds(mHexagonRect);
            if (mBackgroundDrawable instanceof BitmapDrawable) {

                float centerY = (float) (mHexagonSide + getBorderWidth() / 2f);
                float centerX = (float) getWidth() / 2;

                final int drawableWidth = mBackgroundDrawable.getMinimumWidth();
                final int drawableHeight = mBackgroundDrawable.getMinimumHeight();

                mBackgroundDrawable.setBounds((int) (centerX - drawableWidth / 2), (int) (centerY - drawableHeight / 2),
                        (int) (centerX + drawableWidth / 2), (int) (centerY + drawableHeight / 2));
            } else {
                mBackgroundDrawable.setBounds(mHexagonRect);
            }
            mBackgroundDrawable.draw(canvas);
            canvas.clipRect(0, 0, canvas.getWidth(), canvas.getHeight(), Region.Op.UNION);
        }
        canvas.drawPath(mPath, mPaint);
    }
}