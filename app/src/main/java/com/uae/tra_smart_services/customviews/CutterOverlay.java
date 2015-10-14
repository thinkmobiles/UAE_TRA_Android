package com.uae.tra_smart_services.customviews;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by and on 13.10.15.
 */

public class CutterOverlay extends ViewGroup implements ImageCutterView.OnCutterChanged, ViewGroup.OnHierarchyChangeListener {

    private float mOffsetX, mOffsetY;
    private ImageCutterView mContainer;
    public CutterOverlay(Context context) { this(context, null); }

    public CutterOverlay(Context context, AttributeSet attrs) {
        super(context, attrs);
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        setWillNotDraw(false);
        setOnHierarchyChangeListener(this);
    }

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
    int width, height;
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(width = w, height = h, oldw, oldh);
        init();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        if(changed && mContainer != null){
            mContainer.layout(left = left, top = top, right = right, bottom = bottom);
            mContainer.setAreaChangeHandler(this);
        }
    }

    @Override
    public void onChildViewAdded(View view, View view1) {
        mContainer = (ImageCutterView) view1;
    }

    @Override
    public void onChildViewRemoved(View view, View view1) {/*Unimplemented*/}

    Bitmap resultBitmap;
    Canvas canvas;
    Paint paint = new Paint();
    Paint transparentPaint = new Paint();
    private void init() {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        Bitmap bitmapOverlay = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
        bitmapOverlay.setHasAlpha(true);
        bitmapOverlay = makeTransparent(bitmapOverlay, 170);
        if (bitmapOverlay.isMutable()) {
            resultBitmap = bitmapOverlay;
        } else {
            resultBitmap = bitmapOverlay.copy(Bitmap.Config.ARGB_8888, true);
            bitmapOverlay.recycle();
        }
        resultBitmap.setHasAlpha(true);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.XOR));
        canvas = new Canvas(resultBitmap);
    }

    @Override
    public void onContainerAreaChanged(int _width, int _height, float _offsetX, float _offsetY) {
        Bitmap mask = Bitmap.createBitmap(_width, _height, Bitmap.Config.RGB_565);
        canvas.restore();
        canvas.drawBitmap(mask, _offsetX, _offsetY, paint);
        mask.recycle();
        mOffsetX = _offsetX;
        mOffsetY = _offsetY;
        invalidate();
    }

    private Bitmap makeTransparent(Bitmap src, int value) {
        Bitmap transBitmap = Bitmap.createBitmap(src.getWidth(), src.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(transBitmap);
        transparentPaint.setAlpha(value);
        canvas.drawARGB(0, 0, 0, 0);
        canvas.drawBitmap(src, 0, 0, transparentPaint);
        return transBitmap;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(resultBitmap, 0, 0, new Paint());
    }
}