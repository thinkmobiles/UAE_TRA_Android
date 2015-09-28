package com.uae.tra_smart_services.customviews;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateInterpolator;

import com.uae.tra_smart_services.R;

/**
 * Created by AndreyKorneychuk on 9/26/2015.
 */
public class ProgressLoaderView extends View {

    private int actualWidth;
    private int actualHeight;

    private Drawable mSrcDrawable;

    public ProgressLoaderView(Context context) {
        this(context, null);
    }

    public ProgressLoaderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.HexagonView);
        try {
            mSrcDrawable = array.getDrawable(R.styleable.HexagonView_hexagonSrc);
        } finally {
            array.recycle();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int desiredWidth = 300;
        int desiredHeight = 300;

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        if (widthMode == MeasureSpec.EXACTLY) {
            actualWidth = widthSize;
        } else if (widthMode == MeasureSpec.AT_MOST) {
            actualWidth = Math.min(desiredWidth, widthSize);
        } else {
            actualWidth = desiredWidth;
        }

        if (heightMode == MeasureSpec.EXACTLY) {
            actualHeight = heightSize;
        } else if (heightMode == MeasureSpec.AT_MOST) {
            actualHeight = Math.min(desiredHeight, heightSize);
        } else {
            actualHeight = desiredHeight;
        }

        setMeasuredDimension(actualWidth, actualHeight);
    }

    private RectF[] rects = new RectF[5];
    private final Paint rectPaint = new Paint();
    private ObjectAnimator animator;
    private ObjectAnimator animator2;
    private float elementWidth;
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        rectPaint.setAntiAlias(true);
        rectPaint.setColor(Color.GRAY);
        rectPaint.setStyle(Paint.Style.FILL_AND_STROKE);

        elementWidth = actualWidth / 20;
        for (int i = 0; i < rects.length; i++){
            rects[i] = new RectF(-elementWidth, 0, 0, actualHeight);
        }

        AnimatorSet animSetXY = new AnimatorSet();

        animator = ObjectAnimator.ofFloat(ProgressLoaderView.this, "phase1", 0.0f, Float.MAX_VALUE - 1);
        animator.setDuration(10000);
        animator.setRepeatCount(ObjectAnimator.INFINITE);
        animator.setRepeatMode(ObjectAnimator.RESTART);
    }

    /** It will be called by animator to draw the start of loading animation */
    public void setPhase1(float _phase) {
//        for (int i = 0; i < rects.length; i++){
            rects[0].set(_phase * _phase - elementWidth, 0, _phase * _phase, actualHeight);
//        }
        invalidate();
    }

    @Override
    protected void onDraw(Canvas _canvas) {
        super.onDraw(_canvas);
        if(CanDraw){
            _canvas.drawColor(Color.LTGRAY);
            for (RectF rect : rects){
                _canvas.drawRect(rect, rectPaint);
            }
        }
    }

    boolean CanDraw;
    public void start(){
        CanDraw = true;
        animator.start();
    }

    public void stop(){
        CanDraw = false;
        animator.cancel();
    }
}
