package com.uae.tra_smart_services.customviews;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Region;
import android.support.annotation.ColorInt;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.SurfaceView;
import android.view.View;

import com.uae.tra_smart_services.R;

/**
 * Created by and on 21.09.15.
 */

public class LoaderView extends SurfaceView {

    private final Path mPath;
    private final Paint mPaint;
    private final int HEXAGON_BORDER_COUNT = 6;
    private double mHexagonSide, mHexagonInnerRadius;
    private final int DEFAULT_HEXAGON_RADIUS = Math.round(30 * getResources().getDisplayMetrics().density);
    @ColorInt
    private int mBorderColor, mProcessBorderColor;
    private float mBorderWidth;


    public LoaderView(Context context) {
        this(context, null);
    }

    public LoaderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.HexagonView);

        mHexagonSide = array.getDimensionPixelSize(R.styleable.HexagonView_hexagonSideSize, DEFAULT_HEXAGON_RADIUS);
        mBorderColor = array.getColor(R.styleable.HexagonView_hexagonBorderColor, 0xFFC8C7C6);
        mProcessBorderColor = array.getColor(R.styleable.HexagonView_hexagonProcessBorderColor, 0xFFC8C7C6);
        mBorderWidth = array.getDimensionPixelSize(R.styleable.HexagonView_hexagonBorderSize, 0);

        mHexagonInnerRadius = Math.sqrt(3) * mHexagonSide / 2;

        mPath = new Path();
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(mBorderColor);
        mPaint.setStrokeWidth(mBorderWidth);
        mPaint.setStyle(Paint.Style.STROKE);
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

        canvas.clipPath(mPath);
        /*if (mSrcDrawable != null) {
            canvas.getClipBounds(mHexagonRect);
            drawSrc(canvas, mSrcDrawable);
        }

        if (!TextUtils.isEmpty(mText)) {
            drawText(canvas);
        }*/
//        canvas.clipRect(0, 0, canvas.getWidth(), canvas.getHeight(), Region.Op.UNION);

        canvas.drawPath(mPath, mPaint);
    }
}
