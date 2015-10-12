package com.uae.tra_smart_services.customviews;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsoluteLayout;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.uae.tra_smart_services.HexagonUtils;

/**
 * Created by and on 09.10.15.
 */

public class CutterContainer extends ViewGroup implements View.OnTouchListener, ViewGroup.OnHierarchyChangeListener{

    private int left, top, right, bottom;
    private final Path mBorderPath = new Path();
    private final Paint mBorderPaint = new Paint();

    private final Path mLTScalatorPath = new Path();
    private final Path mRBScalatorPath = new Path();
    private PointF[] mLTScalatorArea = new PointF[3];
    private PointF[] mRBScalatorArea = new PointF[3];
    private AbsoluteLayout parent;
    private HexagonCutterView mCutter;

    public CutterContainer(Context context) { this(context, null); }

    public CutterContainer(Context context, AttributeSet attrs) {
        super(context, attrs);
        setWillNotDraw(false);
        setOnTouchListener(this);
        setOnHierarchyChangeListener(this);
    }

    @Override
    public void onChildViewAdded(View view, View view1) {
        mCutter = (HexagonCutterView) view1;
    }

    @Override
    public void onChildViewRemoved(View view, View view1) {/*Unimplemented*/}

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(400, 400);
//        mCutter.measure(400, 400);
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
            mCutter.layout(left = l, top = t, right = r, bottom = b);
            parent = (AbsoluteLayout) getParent();
            parentWidth = parent.getWidth();
            parentHeight = parent.getHeight();
            setTranslationX(/*lastTransitionX = */(parentWidth - width) / 2);
            setTranslationY(/*lastTransitionY = */(parentHeight - height) / 2);
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
        mLTScalatorArea[1] = new PointF(70, 0);
        mLTScalatorPath.lineTo(mLTScalatorArea[1].x, mLTScalatorArea[1].y);
        mLTScalatorArea[2] = new PointF(0, 70);
        mLTScalatorPath.lineTo(mLTScalatorArea[2].x, mLTScalatorArea[2].y);
        mLTScalatorPath.close();

        mRBScalatorArea[0] = new PointF(width, height);
        mRBScalatorPath.moveTo(mRBScalatorArea[0].x, mRBScalatorArea[0].y);
        mRBScalatorArea[1] = new PointF(width - 70, height);
        mRBScalatorPath.lineTo(mRBScalatorArea[1].x, mRBScalatorArea[1].y);
        mRBScalatorArea[2] = new PointF(width, height - 70);
        mRBScalatorPath.lineTo(mRBScalatorArea[2].x, mRBScalatorArea[2].y);
        mRBScalatorPath.close();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        canvas.drawPath(mLTScalatorPath, mBorderPaint);
        canvas.drawPath(mRBScalatorPath, mBorderPaint);
    }

    float downX;
    float downY;
    private Pressed pressed;
    @Override
    public boolean onTouch(View _view, MotionEvent _event) {
        final PointF clickPoint = new PointF(_event.getX(), _event.getY());
        switch (_event.getAction()){
            case MotionEvent.ACTION_DOWN:
                downX = _event.getRawX();
                downY = _event.getRawY();
                if (HexagonUtils.pointInPolygon(clickPoint, mRBScalatorArea)){
                    pressed = Pressed.DOWN;
                } else if (HexagonUtils.pointInPolygon(clickPoint, mLTScalatorArea)){
                    pressed = Pressed.UP;
                } else if(mCutter != null && HexagonUtils.pointInPolygon(clickPoint, mCutter.getPoints())){
                    downX = _event.getX();
                    downY = _event.getY() + 50;
                    pressed = Pressed.HEX;
                } else {
                    pressed = Pressed.NOTHING;
                }

                Log.e("MOVE", "RAW_X:" + _event.getRawX() + ", RAW_Y:" + _event.getRawY()+" | x:" + _event.getX() + ", y:" + _event.getY());
                return true;

            case MotionEvent.ACTION_MOVE:
                if(pressed == Pressed.HEX){
                    moveContainer(_event);
                } else if (pressed == Pressed.DOWN || pressed == Pressed.UP){
                    scaleContainer(_event);
                }
                return true;

            case MotionEvent.ACTION_UP:
//                Log.e("ACTION_UP", "X:" + _event.getRawX() + ", Y:" + _event.getRawY());
                return true;

            default:
                Toast.makeText(getContext(), "default", Toast.LENGTH_SHORT).show();
                return super.onInterceptTouchEvent(_event);
        }
    }
    Rect rect = new Rect();
    float lastTransitionX, lastTransitionY;
    private void moveContainer(MotionEvent _event) {
        getHitRect(rect);
        if(_event.getRawX() - downX >= 0 && _event.getRawX() + (getWidth() - downX) <= parent.getWidth()){
            float transitionX = -downX + _event.getRawX();
            setTranslationX(lastTransitionX = transitionX);
//            layout((int) (left + transitionX), top, (int) (right + transitionX), bottom);
//            layout(left, top, right, bottom);
        }
        if(_event.getRawY() - downY >= 0 && _event.getRawY() + (getHeight() - downY) <= parent.getHeight()){
            float transitionY = -downY + _event.getRawY();
            setTranslationY(lastTransitionY = transitionY);
//            layout(left, (int) (top + transitionY), right, (int) (bottom + transitionY));
//            layout(left, top, right, bottom);
        }
        Log.e("MOVE", "RAW_X:" + _event.getRawX() + ", RAW_Y:" + _event.getRawY()+" | x:" + _event.getX() + ", y:" + _event.getY());
    }

    float scaleX = 1, scaleY = 1;
    private void scaleContainer(MotionEvent _event) {
        float maxDelta;
        float deltaX = _event.getRawX() - downX;
        float deltaY = _event.getRawY() - downY;
        maxDelta = (float) Math.sqrt(deltaX * deltaX + deltaY * deltaY) / 15;
        Rect rect = new Rect();
        getHitRect(rect);
        switch (pressed){
            case DOWN:{
//                    Log.e("SCALE_DOWN", "X:" + _event.getRawX() + ", Y:" + _event.getRawY()+" | x:" + _event.getX() + ", y:" + _event.getY());
                    if(deltaX > 0 && deltaY > 0 && rect.left >= 0 && rect.top >= 0 && rect.right <= parentWidth && rect.bottom <= parentHeight) {
                        scaleX = getWidth() / (getWidth() - maxDelta);
                        scaleY = getHeight() / (getHeight() - maxDelta);
                    } else if(deltaX < 0 && deltaY < 0 && width > parentWidth / 2.5) {
                        scaleX = getWidth() / (getWidth() + maxDelta);
                        scaleY = getHeight() / (getHeight() + maxDelta);
                    } else {
                        return;
                    }
                    layout(0, 0, (int) (getWidth() * scaleX), (int) (getHeight() * scaleY));
                break;
            }
            case UP:{
//                    Log.e("SCALE_UP", "X:" + _event.getRawX() + ", Y:" + _event.getRawY()+" | x:" + _event.getX() + ", y:" + _event.getY());
                    if(deltaX > 0 && deltaY > 0 && width > parentWidth / 2.5) {
                        scaleX = getWidth() / (getWidth() - maxDelta);
                        scaleY = getHeight() / (getHeight() - maxDelta);
                    } else if(deltaX < 0 && deltaY < 0 && rect.left >= 0 && rect.top >= 0 && rect.right <= parentWidth && rect.bottom <= parentHeight ) {
                        scaleX = getWidth() / (getWidth() + maxDelta);
                        scaleY = getHeight() / (getHeight() + maxDelta);
                    } else {
                        return;
                    }
                    layout(0, 0, (int) (getWidth() / scaleX), (int) (getHeight() / scaleY));
                break;
            }
        }
        setTranslationX(lastTransitionX);
        setTranslationY(lastTransitionY);
    }

    private enum Pressed {
        DOWN(-1), HEX(0), UP(1), NOTHING(Integer.MAX_VALUE);

        private int dir;
        Pressed(int _dir){
            dir = _dir;
        }
    }

    public interface OnCutterChanged {
        void onContainerAreaChanged(Path _containerPath);
        void onCutterPathChanged(Path _cutterPath);
    }
}
