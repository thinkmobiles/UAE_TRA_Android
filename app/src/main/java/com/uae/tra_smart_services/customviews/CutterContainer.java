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
import android.widget.FrameLayout;
import android.widget.Toast;

import com.uae.tra_smart_services.util.HexagonUtils;

/**
 * Created by and on 09.10.15.
 */

public class CutterContainer extends ViewGroup implements View.OnTouchListener{

    private int left, top, right, bottom;
    private final Path mBorderPath = new Path();
    private final Paint mBorderPaint = new Paint();

    private final Path mLTScalatorPath = new Path();
    private final Path mRBScalatorPath = new Path();
    private PointF[] mLTScalatorArea = new PointF[3];
    private PointF[] mRBScalatorArea = new PointF[3];
    private FrameLayout parent;
    private HexagonCutterView mCutter;

    public CutterContainer(Context context) { this(context, null); }

    public CutterContainer(Context context, AttributeSet attrs) {
        super(context, attrs);
        setWillNotDraw(false);
        setOnTouchListener(this);
    }

    @Override
    public void onViewAdded(View child) {
        super.onViewAdded(child);
        mCutter = (HexagonCutterView) child;
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(400, 400);
    }
    private int width, height;
    private int parentWidth, parentHeight;
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(width = w, height = h, oldw, oldh);
        initPaints();
        initContainerPath();
        initScalatorsPath();
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if(changed && mCutter != null){
            mCutter.layout(l, t, r, b);
            parent = (FrameLayout) getParent();
            parentWidth = parent.getWidth();
            parentHeight = parent.getHeight();
//            setTranslationX((parentWidth + width) / 2);
//            setTranslationY((parentHeight + height) / 2);
        }
    }

    private void initPaints(){
        mBorderPaint.setAntiAlias(true);
        mBorderPaint.setColor(Color.DKGRAY);
        mBorderPaint.setStrokeWidth(5);
        mBorderPaint.setStyle(Paint.Style.FILL_AND_STROKE);
    }

    private void initContainerPath(){
        mBorderPath.moveTo(0, 0);
        mBorderPath.lineTo(width, 0);
        mBorderPath.lineTo(width, height);
        mBorderPath.lineTo(0, height);
        mBorderPath.close();
    }

    private void initScalatorsPath(){
        mLTScalatorArea[0] = new PointF(0,0);
        mLTScalatorPath.moveTo(mLTScalatorArea[0].x, mLTScalatorArea[0].y);
        mLTScalatorArea[1] = new PointF(50, 0);
        mLTScalatorPath.lineTo(mLTScalatorArea[1].x, mLTScalatorArea[1].y);
        mLTScalatorArea[2] = new PointF(0, 50);
        mLTScalatorPath.lineTo(mLTScalatorArea[2].x, mLTScalatorArea[2].y);
        mLTScalatorPath.close();

        mRBScalatorArea[0] = new PointF(width, height);
        mRBScalatorPath.moveTo(mRBScalatorArea[0].x, mRBScalatorArea[0].y);
        mRBScalatorArea[1] = new PointF(width - 50, height);
        mRBScalatorPath.lineTo(mRBScalatorArea[1].x, mRBScalatorArea[1].y);
        mRBScalatorArea[2] = new PointF(width, height - 50);
        mRBScalatorPath.lineTo(mRBScalatorArea[2].x, mRBScalatorArea[2].y);
        mRBScalatorPath.close();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPath(mLTScalatorPath, mBorderPaint);
        canvas.drawPath(mRBScalatorPath, mBorderPaint);
    }

    float downX;
    float downY;
    boolean isHexagon;
    private ScaleDirection direction;
    @Override
    public boolean onTouch(View _view, MotionEvent _event) {
        final PointF clickPoint = new PointF(_event.getX(), _event.getY());
        switch (_event.getAction()){
            case MotionEvent.ACTION_DOWN:
                Log.e("DOWN  ", "X:" + _event.getRawX() + ", Y:" + _event.getRawY());
//                if(mCutter != null && HexagonUtils.pointInPolygon(clickPoint, mCutter.getPoints())){
//                    isHexagon = true;
//                    downX = _event.getX();
//                    downY = _event.getY();
//                } else if (HexagonUtils.pointInPolygon(clickPoint, mRBScalatorArea)){
//                    isHexagon = false;
//                }
                downX = _event.getRawX();
                downY = _event.getRawY();
                if (HexagonUtils.pointInPolygon(clickPoint, mRBScalatorArea)){
                    isHexagon = false;
                    direction = ScaleDirection.DOWN;
                } else if (HexagonUtils.pointInPolygon(clickPoint, mLTScalatorArea)){
                    isHexagon = false;
                    direction = ScaleDirection.UP;
                } else {
                    isHexagon = true;
                    downX = _event.getX();
                    downY = _event.getY();
                }

                return true;

            case MotionEvent.ACTION_MOVE:
                if(isHexagon){
                    moveContainer(_event);
                } else {
                    scaleContainer(_event);
                }
                return true;

            case MotionEvent.ACTION_UP:
                isHexagon = false;
                Log.e("ACTION_UP", "X:" + _event.getRawX() + ", Y:" + _event.getRawY());
                return true;

            default:
                Toast.makeText(getContext(), "default", Toast.LENGTH_SHORT).show();
                return super.onInterceptTouchEvent(_event);
        }
    }

    private void moveContainer(MotionEvent _event){
        if(_event.getRawX() - downX >= 0 && _event.getRawX() + (getWidth() - downX) <= parent.getWidth()){
            setX(-downX + _event.getRawX());
        }
        if(_event.getRawY() - downY >= 0 && _event.getRawY() + (getHeight() - downY) <= parent.getHeight()){
            setY(-downY + _event.getRawY());
        }
        Log.e("MOVE", "X:" + _event.getRawX() + ", Y:" + _event.getRawY()+" | x:" + _event.getX() + ", y:" + _event.getY());
    }
    float scaleX = 1, scaleY = 1;
    private void scaleContainer(MotionEvent _event) {
        switch (direction){
            case DOWN:{
                    Log.e("SCALE_DOWN", "X:" + _event.getRawX() + ", Y:" + _event.getRawY()+" | x:" + _event.getX() + ", y:" + _event.getY());
                    float maxDelta;
                    float deltaX = _event.getRawX() - downX;
                    float deltaY = _event.getRawY() - downY;
                    maxDelta = (float) Math.sqrt(deltaX * deltaX + deltaY * deltaY);
                    if(deltaX > 0 && deltaY > 0) {
                        scaleX = getWidth() / (getWidth() - maxDelta);
                        scaleY = getHeight() / (getHeight() - maxDelta);
                    } else if(deltaX < 0 && deltaY < 0) {
                        scaleX = getWidth() / (getWidth() + maxDelta);
                        scaleY = getHeight() / (getHeight() + maxDelta);
                    } else {
                        return;
                    }
                    if(getWidth() <= parent.getWidth() && getWidth() < parent.getWidth() / 3){
                        layout(0, 0, (int) (getWidth() * scaleX), (int) (getHeight() * scaleX));
                    }
//                    setScaleX(scaleX);
//                    setScaleY(scaleY);
                break;
            }
            case UP:{
                    Log.e("SCALE_UP", "X:" + _event.getRawX() + ", Y:" + _event.getRawY()+" | x:" + _event.getX() + ", y:" + _event.getY());
                break;
            }
        }
    }

    private enum ScaleDirection{
        DOWN(-1), UP(1);

        private int dir;
        ScaleDirection(int _dir){
            dir = _dir;
        }
    }
}
