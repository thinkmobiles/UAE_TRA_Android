package com.uae.tra_smart_services.customviews;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.Region;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.support.annotation.DrawableRes;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.uae.tra_smart_services.R;
import com.uae.tra_smart_services.util.ImageUtils;


/**
 * Created by mobimaks on 02.08.2015.
 */
public class HexagonView extends View implements Target {

    private final int DEFAULT_HEXAGON_RADIUS = (int) (30 * getResources().getDisplayMetrics().density);
    private final int HEXAGON_BORDER_COUNT = 6;

    private double mHexagonSide, mHexagonInnerRadius;
    private final Path mPath;
    private Paint mPaint, mShadowPaint;
    private int mBorderColor, mBackgroundColor, mBorderWidth;
    private Drawable mSrcDrawable;
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
            mBorderColor = a.getColor(R.styleable.HexagonView_hexagonBorderColor, 0xFFC8C7C6);
            mBackgroundColor = a.getColor(R.styleable.HexagonView_hexagonBackgroundColor, Color.TRANSPARENT);
            mSrcDrawable = a.getDrawable(R.styleable.HexagonView_hexagonSrc);

        } finally {
            a.recycle();
        }
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(mBorderColor);
        mPaint.setStrokeWidth(mBorderWidth);
        mPaint.setStyle(Paint.Style.STROKE);

        mPath = new Path();
    }

    public final void setHexagonSide(final int _hexagonSideSize) {
        mHexagonSide = _hexagonSideSize;
        mHexagonInnerRadius = Math.sqrt(3) * mHexagonSide / 2;
    }

    @Override
    public void setBackgroundColor(int _backgroundColor) {
        mBackgroundColor = _backgroundColor;
        invalidate();
    }

    public final void setHexagonShadow(final float _radius, final int _color) {
        mShadowPaint = new Paint();
        mShadowPaint.setStyle(Paint.Style.FILL);
        mShadowPaint.setShadowLayer(_radius, 0, 0, _color);
    }

    public final void setHexagonSrcDrawable(@DrawableRes final int _drawableRes) {
        setHexagonSrcDrawable(ContextCompat.getDrawable(getContext(), _drawableRes));
    }

    public final void setHexagonSrcDrawable(final Drawable _backgroundDrawable) {
        mSrcDrawable = _backgroundDrawable;
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
//        canvas.save();
//        canvas.clipPath(mPath, Region.Op.DIFFERENCE);
        if (mShadowPaint != null) {
            canvas.drawPath(mPath, mShadowPaint);
        }
//        canvas.restore();
        //endregion

        if (mSrcDrawable != null) {
            canvas.clipPath(mPath);
            canvas.drawColor(mBackgroundColor);
            canvas.getClipBounds(mHexagonRect);
            if (mSrcDrawable instanceof BitmapDrawable) {

                float centerY = (float) (mHexagonSide + getBorderWidth() / 2f);
                float centerX = (float) getWidth() / 2;

                final int drawableWidth = mSrcDrawable.getMinimumWidth();
                final int drawableHeight = mSrcDrawable.getMinimumHeight();

                mSrcDrawable.setBounds((int) (centerX - drawableWidth / 2), (int) (centerY - drawableHeight / 2),
                        (int) (centerX + drawableWidth / 2), (int) (centerY + drawableHeight / 2));
            } else {
                mSrcDrawable.setBounds(mHexagonRect);
            }
            mSrcDrawable.draw(canvas);
            canvas.clipRect(0, 0, canvas.getWidth(), canvas.getHeight(), Region.Op.UNION);
        }

        canvas.drawPath(mPath, mPaint);
    }

    @Override
    public final void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
        final LayerDrawable layerDrawable = (LayerDrawable) ContextCompat.getDrawable(getContext(), R.drawable.layerlist_infohub_icon);
        layerDrawable.setDrawableByLayerId(R.id.lli_infohub_icon_background, new ColorDrawable(Color.MAGENTA));
        layerDrawable.setDrawableByLayerId(R.id.lli_infohub_icon_front, new BitmapDrawable(getResources(), bitmap));
        setHexagonSrcDrawable(ImageUtils.getFilteredDrawable(getContext(), layerDrawable));
    }

    @Override
    public final void onBitmapFailed(Drawable errorDrawable) {
    }

    @Override
    public final void onPrepareLoad(Drawable placeHolderDrawable) {
    }
}