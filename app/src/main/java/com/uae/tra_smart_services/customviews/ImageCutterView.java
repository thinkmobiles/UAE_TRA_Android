package com.uae.tra_smart_services.customviews;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.uae.tra_smart_services.HexagonUtils;
import com.uae.tra_smart_services.R;

/**
 * Created by and on 09.10.15.
 */

public class ImageCutterView extends ViewGroup implements View.OnTouchListener, ViewGroup.OnHierarchyChangeListener{
    /** Coordinates */
    private int layoutWidth, layoutHeight;
    float downX;
    float downY;
    float lastTransitionX, lastTransitionY;
    private Rect containerCoords = new Rect();
    private Pressed currentPressed;
    private int containerSide;
    /** Drawables */
    private final Paint mBorderPaint = new Paint();
    private final Path mRBScalatorPath = new Path();
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
    public ImageCutterView(Context context) { this(context, null); }

    public ImageCutterView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        obtainAttributes(attrs);
        setWillNotDraw(false);
        setOnTouchListener(this);
        setOnHierarchyChangeListener(this);
    }

    private void obtainAttributes(AttributeSet attrs) {
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.HexagonView);
        try {
            containerSide = a.getInteger(R.styleable.ImageCutterView_containerSide, Color.TRANSPARENT);
        } finally {
            a.recycle();
        }
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
        int width = 720;
        int height = 1134;
        if(MeasureSpec.getMode(widthMeasureSpec) == MeasureSpec.EXACTLY){
            width = MeasureSpec.getSize(widthMeasureSpec);
        }
        if(MeasureSpec.getMode(heightMeasureSpec) == MeasureSpec.EXACTLY){
            height = MeasureSpec.getSize(heightMeasureSpec);
        }
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(layoutWidth = w, layoutHeight = h, oldw, oldh);
        initContainerRect();
        initScalatorPaint();
        calculateScalatorPath();
        prepareOverlay();
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if(changed && mCutter != null){
            mCutter.layout(containerCoords.left, containerCoords.top, containerCoords.right, containerCoords.bottom);
        }
    }

    private void initContainerRect(){
        containerCoords.left = (getWidth() - containerSide) / 2;
        containerCoords.top = (getHeight() - containerSide) / 2;
        containerCoords.right = containerCoords.left + containerSide;
        containerCoords.bottom = containerCoords.top + containerSide;
    }

    private void initScalatorPaint(){
        mBorderPaint.setAntiAlias(true);
        mBorderPaint.setColor(Color.WHITE);
        mBorderPaint.setStrokeWidth(5);
        mBorderPaint.setStyle(Paint.Style.FILL_AND_STROKE);
    }

    private void calculateScalatorPath(){
        mRBScalatorPath.reset();
        mRBScalatorArea[0] = new PointF(containerCoords.right, containerCoords.bottom);
        mRBScalatorPath.moveTo(mRBScalatorArea[0].x, mRBScalatorArea[0].y);
        mRBScalatorArea[1] = new PointF(containerCoords.right - 100, containerCoords.bottom);
        mRBScalatorPath.lineTo(mRBScalatorArea[1].x, mRBScalatorArea[1].y);
        mRBScalatorArea[2] = new PointF(containerCoords.right, containerCoords.bottom - 60);
        mRBScalatorPath.lineTo(mRBScalatorArea[2].x, mRBScalatorArea[2].y);
        mRBScalatorPath.close();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawOverlay(canvas);
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
                } else if (containerCoords.contains((int) _event.getX(), (int) _event.getY())){
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
        if(containerCoords.left >= 0 && containerCoords.right <= parent.getWidth()){
            lastTransitionX = -downX + _event.getRawX();
            if(lastTransitionX <= 0) lastTransitionX = 0;
            if(lastTransitionX >= getWidth() - getWidth()) lastTransitionX = parent.getWidth() - getWidth();
//            containerCoords.offsetTo();
            setX(lastTransitionX);
        }
        if(containerCoords.top >= 0 && containerCoords.bottom <= parent.getHeight()){
            lastTransitionY = -downY + _event.getRawY();
            if(lastTransitionY <= 0) lastTransitionY = 0;
            if(lastTransitionY >= parent.getHeight() - getHeight()) lastTransitionY = parent.getHeight() - getHeight();
            setY(lastTransitionY);
        }
//        Log.e("MOVE", "RAW_X:" + _event.getRawX() + ", RAW_Y:" + _event.getRawY() + " | x:" + _event.getX() + ", y:" + _event.getY());
        mAreaChangeHandler.onContainerAreaChanged(getWidth(), getHeight(), getX(), getY());
        invalidate();
    }

    float scaleX = 1, scaleY = 1;
    private void scaleContainer(MotionEvent _event) {
//        float deltaX = _event.getRawX() - downX;
//        float deltaY = _event.getRawY() - downY;
//        containerWidth = getWidth();
//        containerHeight = getHeight();
//        float maxDelta = (float) Math.sqrt(deltaX * deltaX + deltaY * deltaY);
//        getHitRect(containerCoords);
//        switch (currentPressed){
//            case SCALATOR:{
////                    Log.e("SCALE_DOWN", "X:" + _event.getRawX() + ", Y:" + _event.getRawY()+" | x:" + _event.getX() + ", y:" + _event.getY());
//                    if(deltaX >= 0 && deltaY >= 0 && containerCoords.left >= 0 && containerCoords.top >= 0 && containerCoords.right <= parentWidth && containerCoords.bottom <= parentHeight) {
//                        scaleX = containerWidth / (containerWidth - maxDelta);
//                        scaleY = containerHeight / (containerHeight - maxDelta);
//                    } else if(deltaX <= 0 && deltaY <= 0 && containerWidth >= parentWidth / 2.5) {
//                        scaleX = containerWidth / (containerWidth + maxDelta);
//                        scaleY = containerHeight / (containerHeight + maxDelta);
//                    } else {
//                        return;
//                    }
//                    layout(0, 0, (int) (containerWidth * scaleX), (int) (containerHeight * scaleY));
//                break;
//            }
//        }
//        setX((lastTransitionX == 0) ? (parentWidth - containerWidth) / 2 : lastTransitionX);
//        setY((lastTransitionY == 0) ? (parentHeight - containerHeight) / 2 : lastTransitionY);
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

    Canvas canvas;
    Paint paint;
    Bitmap overlayBitmap;
    private void prepareOverlay(){
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        Bitmap bitmapOverlay = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.RGB_565);
        bitmapOverlay.setHasAlpha(true);
        bitmapOverlay = makeTransparent(bitmapOverlay, 170);

        if (bitmapOverlay.isMutable()) {
            overlayBitmap = bitmapOverlay;
        } else {
            overlayBitmap = bitmapOverlay.copy(Bitmap.Config.ARGB_8888, true);
            bitmapOverlay.recycle();
        }
        overlayBitmap.setHasAlpha(true);

        canvas = new Canvas(overlayBitmap);
        paint = new Paint();
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.XOR));

    }

    private void drawOverlay(Canvas _canvas){

        Bitmap mask = Bitmap.createBitmap(containerCoords.right - containerCoords.left, containerCoords.bottom - containerCoords.top, Bitmap.Config.RGB_565);

        canvas.drawBitmap(mask, containerCoords.left, containerCoords.right, paint);
        mask.recycle();

        _canvas.drawBitmap(overlayBitmap, 0, 0, new Paint());
    }



    private Bitmap makeTransparent(Bitmap src, int value) {
        Bitmap transBitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(transBitmap);
        canvas.drawARGB(0, 0, 0, 0);
        final Paint paint = new Paint();
        paint.setAlpha(value);
        canvas.drawBitmap(src, 0, 0, paint);
        return transBitmap;
    }

}
