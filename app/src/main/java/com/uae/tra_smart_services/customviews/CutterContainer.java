package com.uae.tra_smart_services.customviews;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.uae.tra_smart_services.util.HexagonUtils;

/**
 * Created by and on 09.10.15.
 */

public class CutterContainer extends ViewGroup implements View.OnTouchListener{

    private int left, top, right, bottom;
    private final Path mBorderpath = new Path();
    private final Paint mBorderPaint = new Paint();
    private HexagonCutterView mCutter;

    public CutterContainer(Context context) { this(context, null); }

    public CutterContainer(Context context, AttributeSet attrs) {
        super(context, attrs);
        setWillNotDraw(false);
        setOnTouchListener(this);
        init();
    }

    private void init(){
        mBorderPaint.setAntiAlias(true);
        mBorderPaint.setColor(Color.BLUE);
        mBorderPaint.setStrokeWidth(5);
        mBorderPaint.setStyle(Paint.Style.STROKE);
    }

    @Override
    public void onViewAdded(View child) {
        super.onViewAdded(child);
        mCutter = (HexagonCutterView) child;
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension((int) (400 * 0.88), 400);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mBorderpath.moveTo(0, 0);
        mBorderpath.lineTo(w, 0);
        mBorderpath.lineTo(w, h);
        mBorderpath.lineTo(0, h);
        mBorderpath.close();
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if(changed && mCutter != null){
            mCutter.layout(l, t, r, b);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPath(mBorderpath, mBorderPaint);
    }

    @Override
    public boolean onTouch(View _view, MotionEvent _event) {
        final PointF clickPoint = new PointF(_event.getX(), _event.getY());
        switch (_event.getAction()){
            case MotionEvent.ACTION_DOWN:
                Toast.makeText(getContext(), "DOWN", Toast.LENGTH_SHORT).show();
                return true;

            case MotionEvent.ACTION_MOVE:
                if(mCutter != null && HexagonUtils.pointInPolygon(clickPoint, mCutter.getPoints())){
                    setX(_event.getX());
                    setY(_event.getY());
                }
                return true;

            case MotionEvent.ACTION_UP:
                Toast.makeText(getContext(), "UP", Toast.LENGTH_SHORT).show();
                return true;

            default:
                Toast.makeText(getContext(), "default", Toast.LENGTH_SHORT).show();
                return super.onInterceptTouchEvent(_event);
        }
    }
}
