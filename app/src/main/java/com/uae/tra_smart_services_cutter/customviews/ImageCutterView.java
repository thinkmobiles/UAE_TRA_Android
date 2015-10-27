package com.uae.tra_smart_services_cutter.customviews;

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
import android.view.ViewTreeObserver;

import com.uae.tra_smart_services_cutter.HexagonUtils;
import com.uae.tra_smart_services_cutter.R;

/**
 * Created by ak-buffalo on 09.10.15.
 */

public class ImageCutterView extends ViewGroup implements View.OnTouchListener, ViewGroup.OnHierarchyChangeListener, ViewTreeObserver.OnGlobalLayoutListener{
    /** Coordinates */
    private boolean isChanged;
    float downX;
    float downY;
    float cutterOffsetX, cutterOffsetY;
    private Rect cutterCoords = new Rect();
    private Pressed currentPressed;
    private int containerSide;
    private int cropperButtonHeight;
    private static final int plusPressInteractionArea = 50;
    private static final int offSetScalator = 5;
    /** Drawables */
    private final Paint mBorderPaint = new Paint();
    private final Path mRBScalatorPath = new Path();
    private PointF[] mRBScalatorArea = new PointF[3];
    private Bitmap bitmapOverlay;
    private Bitmap originalBitmap;
    private Canvas canvas;
    private Paint paint;
    /** Views */
    private HexagonCutterView mCutter;
    private OnCutterChanged mAreaChangeHandler;

    /** Entities */
    private enum Pressed {
        SCALATOR, NOTHONG, CUTTER
    }
    /** Constructors */
    public ImageCutterView(Context context) { this(context, null); }

    public ImageCutterView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setWillNotDraw(false);
        obtainAttributes(attrs);
        setOnTouchListener(this);
        setOnHierarchyChangeListener(this);
        getViewTreeObserver().addOnGlobalLayoutListener(this);
    }

    private void obtainAttributes(AttributeSet attrs) {
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.ImageCutterView);
        try {
            containerSide = a.getInteger(R.styleable.ImageCutterView_containerSide, 400);
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
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        isChanged = changed;
    }

    @Override
    public void onGlobalLayout() {
        if(isChanged && mCutter != null){
            initContainerRect();
            initScalatorPaint();
            calculateScalatorPath();
            prepareOverlay();
            mCutter.layout(cutterCoords.left, cutterCoords.top, cutterCoords.right, cutterCoords.bottom);
            mAreaChangeHandler.onCutterChanged(mCutter.getPath(), cutterCoords.left, cutterCoords.top, containerSide);
        }
    }

    private void initContainerRect(){
        cropperButtonHeight = ((View) getParent()).findViewById(R.id.doCrop).getHeight();
        cutterCoords.left = (getWidth() - containerSide) / 2;
        cutterCoords.top = (getHeight() - containerSide - cropperButtonHeight) / 2;
        cutterCoords.right = cutterCoords.left + containerSide;
        cutterCoords.bottom = cutterCoords.top + containerSide;
    }

    private void initScalatorPaint(){
        mBorderPaint.setAntiAlias(true);
        mBorderPaint.setColor(Color.WHITE);
        mBorderPaint.setStrokeWidth(5);
        mBorderPaint.setStyle(Paint.Style.FILL_AND_STROKE);
    }

    private void calculateScalatorPath(){
        float x;
        float y;
        mRBScalatorPath.reset();
        x = cutterCoords.right;
        y = cutterCoords.bottom;
        mRBScalatorArea[0] = new PointF(x, y + plusPressInteractionArea);
        mRBScalatorPath.moveTo(x - offSetScalator, y - offSetScalator);
        x = cutterCoords.right - 100;
        y = cutterCoords.bottom;
        mRBScalatorArea[1] = new PointF(x - plusPressInteractionArea, y + plusPressInteractionArea);
        mRBScalatorPath.lineTo(x, y - offSetScalator);
        x = cutterCoords.right;
        y = cutterCoords.bottom - 60;
        mRBScalatorArea[2] = new PointF(x + plusPressInteractionArea, y - plusPressInteractionArea);
        mRBScalatorPath.lineTo(x - offSetScalator, y);
        mRBScalatorPath.close();
    }


    @Override
    public boolean onTouch(View _view, MotionEvent _event) {
        final PointF clickPoint = new PointF(_event.getX(), _event.getY());
        switch (_event.getAction()){
            case MotionEvent.ACTION_DOWN:
                downX = _event.getRawX() - cutterCoords.left;
                downY = _event.getRawY() - cutterCoords.top;
                if (HexagonUtils.pointInPolygon(clickPoint, mRBScalatorArea)){
                    currentPressed = Pressed.SCALATOR;
                    downX = _event.getRawX();
                    downY = _event.getRawY();
                } else if (cutterCoords.contains((int) _event.getX(), (int) _event.getY())){
                    currentPressed = Pressed.CUTTER;
                } else {
                    currentPressed = Pressed.NOTHONG;
                }
                return true;

            case MotionEvent.ACTION_MOVE:
                if(currentPressed == Pressed.SCALATOR){
                    scaleContainer(_event);
                } else if(currentPressed == Pressed.CUTTER){
                    moveContainer(_event);
                }
                recalculateAndInvalidate();
                return true;
        }

        return false;
    }

    private void recalculateAndInvalidate(){
        calculateScalatorPath();
        mCutter.layout(cutterCoords.left, cutterCoords.top, cutterCoords.right, cutterCoords.bottom);
        invalidate();
        mAreaChangeHandler.onCutterChanged(mCutter.getPath(), cutterCoords.left, cutterCoords.top, containerSide);
    }

    private void moveContainer(MotionEvent _event) {
        if(cutterCoords.left >= 0 && cutterCoords.right <= getWidth()){
            cutterOffsetX = _event.getRawX() - downX;
            if(cutterOffsetX < 0) cutterOffsetX = 0;
            if(cutterOffsetX > getWidth() - containerSide) cutterOffsetX = getWidth() - containerSide;
        }
        if(cutterCoords.top >= 0 && cutterCoords.bottom <= getHeight() - cropperButtonHeight){
            cutterOffsetY = _event.getRawY() - downY;
            if(cutterOffsetY < 0) cutterOffsetY = 0;
            if(cutterOffsetY > getHeight() - containerSide - cropperButtonHeight)
                cutterOffsetY = getHeight() - containerSide - cropperButtonHeight;
        }
        cutterCoords.offsetTo((int) cutterOffsetX, (int) cutterOffsetY);
    }

    private void scaleContainer(MotionEvent _event) {
        float deltaX = _event.getRawX() - downX;
        float deltaY = _event.getRawY() - downY;
        float delta = (float) Math.sqrt(Math.sqrt(deltaX * deltaX + deltaY * deltaY));
        switch (currentPressed) {
            case SCALATOR: {
                    if(deltaX >= 0 && deltaY >= 0 && cutterCoords.right  + delta <= getWidth() && cutterCoords.bottom  + delta <= getHeight()) {
                        cutterCoords.right += delta;
                        cutterCoords.bottom += delta;
                    } else if(deltaX <= 0 && deltaY <= 0 && containerSide - delta >= getWidth() / 2.5) {
                        cutterCoords.right -= delta;
                        cutterCoords.bottom -= delta;
                    } else {
                        return;
                    }
                    containerSide = cutterCoords.bottom - cutterCoords.top;
                break;
            }
        }
    }

    public void setCutterChangeHandler(OnCutterChanged _handler){
        mAreaChangeHandler = _handler;
    }

    public void setOriginalImageBitmap(Bitmap _originalBitmap){
        originalBitmap = _originalBitmap;
    }

    private void prepareOverlay(){
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        bitmapOverlay = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.RGB_565);
        bitmapOverlay.setHasAlpha(true);
        bitmapOverlay = makeTransparent(bitmapOverlay, 170);
        paint = new Paint();
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.XOR));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(originalBitmap, 0, 0, null);
        drawOverlay(canvas);
        canvas.drawPath(mRBScalatorPath, mBorderPaint);
    }

    private void drawOverlay(Canvas _canvas){
        Bitmap overlayBitmap;
        if (bitmapOverlay.isMutable()) {
            overlayBitmap = bitmapOverlay.copy(Bitmap.Config.ARGB_8888, true);
        } else {
            overlayBitmap = bitmapOverlay;
        }
        canvas = new Canvas(overlayBitmap);
         Bitmap mask = Bitmap.createBitmap(containerSide, containerSide, Bitmap.Config.RGB_565);
        canvas.drawBitmap(mask, cutterCoords.left, cutterCoords.top, paint);
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

    public interface OnCutterChanged {
        void onCutterChanged(Path _cropperPath, int _offsetX, int _offsetY, int _cutterSide);
    }
}