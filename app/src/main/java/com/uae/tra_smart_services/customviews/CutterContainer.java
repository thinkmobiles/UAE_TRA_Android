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
import android.widget.FrameLayout;

import com.uae.tra_smart_services.HexagonUtils;

/**
 * Created by and on 09.10.15.
 */

public class CutterContainer extends ViewGroup implements View.OnTouchListener, ViewGroup.OnHierarchyChangeListener{
    /** Coordinates */
    private int left, top, right, bottom;
    private int width, height;
    private int parentWidth, parentHeight;
    float downX;
    float downY;
    float lastTransitionX, lastTransitionY;
    Rect rect = new Rect();
    private Pressed currentPressed;
    /** Drawables */
    private final Path mBorderPath = new Path();
    private final Paint mBorderPaint = new Paint();
    private final Path mLTScalatorPath = new Path();
    private final Path mRBScalatorPath = new Path();
    private PointF[] mLTScalatorArea = new PointF[3];
    private PointF[] mRBScalatorArea = new PointF[3];
    /** Views */
    private CutterOverlay parent;
    private HexagonCutterView mCutter;
    private OnCutterChanged mAreaChangeHandler;
    /** Entities */
    private enum Pressed {
        SCALATOR(-1), CUTTER(0);
        private int dir;
        Pressed(int _dir){
            dir = _dir;
        }
    }
    /** Constructors */
    public CutterContainer(Context context) { this(context, null); }

    public CutterContainer(Context context, AttributeSet attrs) {
        super(context, attrs);
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
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
    }

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
            parent = (CutterOverlay) getParent();
            mAreaChangeHandler = (OnCutterChanged) getParent();
            parentWidth = parent.getWidth();
            parentHeight = parent.getHeight();
            setX((parentWidth - width) / 2);
            setY((parentHeight - height) / 2);
            mAreaChangeHandler.onContainerAreaChanged(
                    getWidth(),
                    getHeight(),
                    (lastTransitionX == 0) ? getX() : lastTransitionX,
                    (lastTransitionY == 0) ? getY() : lastTransitionY
            );
        }
    }

    private void initPaints(){
        mBorderPaint.setAntiAlias(true);
        mBorderPaint.setColor(Color.WHITE);
        mBorderPaint.setStrokeWidth(5);
        mBorderPaint.setStyle(Paint.Style.FILL_AND_STROKE);
    }

    private void initContainerPath(){
        mBorderPath.reset();
        mBorderPath.moveTo(0, 0);
        mBorderPath.lineTo(width, 0);
        mBorderPath.lineTo(width, height);
        mBorderPath.lineTo(0, height);
        mBorderPath.close();
    }

    private void initScalatorsPath(){
        mRBScalatorPath.reset();
        mRBScalatorArea[0] = new PointF(width, height);
        mRBScalatorPath.moveTo(mRBScalatorArea[0].x, mRBScalatorArea[0].y);
        mRBScalatorArea[1] = new PointF(width - 100, height);
        mRBScalatorPath.lineTo(mRBScalatorArea[1].x, mRBScalatorArea[1].y);
        mRBScalatorArea[2] = new PointF(width, height - 60);
        mRBScalatorPath.lineTo(mRBScalatorArea[2].x, mRBScalatorArea[2].y);
        mRBScalatorPath.close();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPath(mRBScalatorPath, mBorderPaint);
    }

    @Override
    public boolean onTouch(View _view, MotionEvent _event) {
        final PointF clickPoint = new PointF(_event.getX(), _event.getY());
        switch (_event.getAction()){
            case MotionEvent.ACTION_DOWN:
                downX = _event.getRawX();
                downY = _event.getRawY();
                if (HexagonUtils.pointInPolygon(clickPoint, mRBScalatorArea)){
                    currentPressed = Pressed.SCALATOR;
                } else {
                    downX = _event.getX();
                    downY = _event.getY() + 50;
                    currentPressed = Pressed.CUTTER;
                }
//                Log.e("MOVE", "RAW_X:" + _event.getRawX() + ", RAW_Y:" + _event.getRawY()+" | x:" + _event.getX() + ", y:" + _event.getY());
                return true;

            case MotionEvent.ACTION_MOVE:
                if(currentPressed == Pressed.SCALATOR){
                    scaleContainer(_event);
                } else {
                    moveContainer(_event);
                }
                return true;
        }
        return false;
    }

    private void moveContainer(MotionEvent _event) {
        getHitRect(rect);
        if(rect.left >= 0 && rect.right <= parent.getWidth()){
            lastTransitionX = -downX + _event.getRawX();
            if(lastTransitionX <= 0) lastTransitionX = 0;
            if(lastTransitionX >= parent.getWidth() - getWidth()) lastTransitionX = parent.getWidth() - getWidth();
            setX(lastTransitionX);
        }
        if(rect.top >= 0 && rect.bottom <= parent.getHeight()){
            lastTransitionY = -downY + _event.getRawY();
            if(lastTransitionY <= 0) lastTransitionY = 0;
            if(lastTransitionY >= parent.getHeight() - getHeight()) lastTransitionY = parent.getHeight() - getHeight();
            setY(lastTransitionY);
        }
//        Log.e("MOVE", "RAW_X:" + _event.getRawX() + ", RAW_Y:" + _event.getRawY() + " | x:" + _event.getX() + ", y:" + _event.getY());
        mAreaChangeHandler.onContainerAreaChanged(getWidth(), getHeight(), getX(), getY());
    }

    float scaleX = 1, scaleY = 1;
    private void scaleContainer(MotionEvent _event) {
        float deltaX = _event.getRawX() - downX;
        float deltaY = _event.getRawY() - downY;
        width = getWidth();
        height = getHeight();
        float maxDelta = (float) Math.sqrt(deltaX * deltaX + deltaY * deltaY);
        getHitRect(rect);
        switch (currentPressed){
            case SCALATOR:{
//                    Log.e("SCALE_DOWN", "X:" + _event.getRawX() + ", Y:" + _event.getRawY()+" | x:" + _event.getX() + ", y:" + _event.getY());
                    if(deltaX >= 0 && deltaY >= 0 && rect.left >= 0 && rect.top >= 0 && rect.right <= parentWidth && rect.bottom <= parentHeight) {
                        scaleX = width / (width - maxDelta);
                        scaleY = height / (height - maxDelta);
                    } else if(deltaX <= 0 && deltaY <= 0 && width >= parentWidth / 2.5) {
                        scaleX = width / (width + maxDelta);
                        scaleY = height / (height + maxDelta);
                    } else {
                        return;
                    }
                    layout(0, 0, (int) (width * scaleX), (int) (height * scaleY));
                break;
            }
        }
        setX((lastTransitionX == 0) ? (parentWidth - width) / 2 : lastTransitionX);
        setY((lastTransitionY == 0) ? (parentHeight - height) / 2 : lastTransitionY);
    }

    public Path getCutterPath(){
        return mCutter.getPath();
    }

    public HexagonCutterView getCutter(){
        return mCutter;
    }

    public void setAreaChangeHandler(OnCutterChanged _handler){
        mAreaChangeHandler = _handler;
    }

    public interface OnCutterChanged {
        void onContainerAreaChanged(int _width, int _height, float _offsetX, float _offsetY);
    }
}
