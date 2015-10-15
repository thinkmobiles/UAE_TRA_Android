package com.uae.tra_smart_services.customviews;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by and on 09.10.15.
 */

public class HexagonCutterView extends View {
    private static final int HEXAGON_BORDER_COUNT = 6;
    private static double HEXAGON_SECTION = 2.0 * Math.PI / HEXAGON_BORDER_COUNT;

    private final Path mPath = new Path();
    private final Paint mPaint = new Paint();
    private double mHexagonSide;
    private final PointF[] mPoints = new PointF[6];

    public HexagonCutterView(Context context) {
        this(context, null);
    }
    public HexagonCutterView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        init();
    }

    private void init(){
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.WHITE);
        mPaint.setStrokeWidth(5);
        mPaint.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        int mCenterWidth = w / 2;
        int mCenterHeight = h / 2;
        mHexagonSide = mCenterHeight;
        mPath.reset();
        mPoints[0] = new PointF((float) (mCenterWidth - mHexagonSide * Math.sin(0)), (float) (mCenterHeight - mHexagonSide * Math.cos(0)));
        mPath.moveTo(mPoints[0].x, mPoints[0].y);
        for (int i = 1; i < HEXAGON_BORDER_COUNT; i++) {
            mPoints[i] = new PointF((float) (mCenterWidth - mHexagonSide * Math.sin(HEXAGON_SECTION * -i)), (float) (mCenterHeight - mHexagonSide * Math.cos(HEXAGON_SECTION * -i)));
            mPath.lineTo(mPoints[i].x, mPoints[i].y);
        }
        mPath.close();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPath(mPath, mPaint);
    }

    public PointF[] getPoints(){
        return mPoints;
    }

    public Path getPath(){
        return mPath;
    }
}