package com.uae.tra_smart_services.customviews;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.PathMeasure;
import android.support.annotation.ColorInt;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;

import com.uae.tra_smart_services.R;

/**
 * Created by Andrey Korneychuk on 21.09.15.
 */

public class LoaderView extends View {

    @ColorInt
    private int mBorderColor, mProcessBorderColor, mSuccessBorderColor;
    private float mBorderSize, mProcessBorderSize, mSuccessBorderSize;
    private final Path mHexagonPath, okIconPath;
    private final Paint mBorderPaint, mProcessPaint, mEndProcessPaint, mFillArePaint, mSuccessPaint;
    private final int HEXAGON_BORDER_COUNT = 6;
    private double mHexagonSide, mHexagonInnerRadius;
    private final int DEFAULT_HEXAGON_RADIUS = Math.round(30 * getResources().getDisplayMetrics().density);

    private ObjectAnimator animatorStart;
    private ObjectAnimator animatorEnd;
    private ObjectAnimator animatorFilling;
    private ObjectAnimator animatorSuccess;

    public LoaderView(Context context) {
        this(context, null);
    }

    public LoaderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.HexagonView);

        mHexagonSide = array.getDimensionPixelSize(R.styleable.HexagonView_hexagonSideSize, DEFAULT_HEXAGON_RADIUS);
        mBorderColor = array.getColor(R.styleable.HexagonView_hexagonBorderColor, 0xFFC8C7C6);
        mProcessBorderColor = array.getColor(R.styleable.HexagonView_hexagonProcessBorderColor, 0xFFFFFFFF);
        mSuccessBorderColor = array.getColor(R.styleable.HexagonView_hexagonSuccessBorderColor, 0xFFFFFFFF);
        mBorderSize = array.getDimensionPixelSize(R.styleable.HexagonView_hexagonBorderSize, 3);
        mProcessBorderSize = array.getDimensionPixelSize(R.styleable.HexagonView_hexagonProcessBorderSize, 3);
        mSuccessBorderSize = array.getDimensionPixelSize(R.styleable.HexagonView_hexagonSuccessBorderSize, 5);

        mHexagonInnerRadius = Math.sqrt(3) * mHexagonSide / 2;

        mHexagonPath = new Path();
        okIconPath = new Path();
        mBorderPaint = new Paint();
        mProcessPaint = new Paint();
        mEndProcessPaint = new Paint();
        mFillArePaint = new Paint();
        mSuccessPaint = new Paint();

        init();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        final int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        final float widthSize = MeasureSpec.getSize(widthMeasureSpec);
        final float myWidth = Math.round(2 * mHexagonInnerRadius + mBorderSize);
        final float width;

        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize;
        } else if (widthMode == MeasureSpec.AT_MOST) {
            width = Math.min(myWidth, widthSize);
        } else {
            width = myWidth;
        }
        setMeasuredDimension((int) width, (int) Math.round(2 * mHexagonSide + mBorderSize));
    }

    double section;
    float mAnimationLength;

    int width;
    int heigth;

    int offSetSeccessX = 30;
    int offSetSeccessY = 35;
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        state = 0;

        width = w;
        heigth = h;

        section = 2.0 * Math.PI / HEXAGON_BORDER_COUNT;

        mHexagonPath.reset();
        mHexagonPath.moveTo(
                (float) (w / 2 - mHexagonSide * Math.sin(0)),
                (float) (h / 2 - mHexagonSide * Math.cos(0)));

        for (int i = 1; i < HEXAGON_BORDER_COUNT; i++) {
            mHexagonPath.lineTo(
                    (float) (w / 2 - mHexagonSide * Math.sin(section * -i)),
                    (float) (h / 2 - mHexagonSide * Math.cos(section * -i)));
        }
        mHexagonPath.close();

        okIconPath.reset();
        okIconPath.moveTo(offSetSeccessX + 20, offSetSeccessY + 60);
        okIconPath.lineTo(offSetSeccessX + 45, offSetSeccessY + 95);
        okIconPath.lineTo(offSetSeccessX + 100, offSetSeccessY + 40);
    }

    public void init(){
        /** Define paints*/
        mBorderPaint.setAntiAlias(true);
        mBorderPaint.setColor(mBorderColor);
        mBorderPaint.setStrokeWidth(mBorderSize);
        mBorderPaint.setStyle(Paint.Style.STROKE);

        mProcessPaint.setAntiAlias(true);
        mProcessPaint.setColor(mProcessBorderColor);
        mProcessPaint.setStrokeWidth(mProcessBorderSize);
        mProcessPaint.setStyle(Paint.Style.STROKE);

        mEndProcessPaint.setAntiAlias(true);
        mEndProcessPaint.setColor(mBorderColor);
        mEndProcessPaint.setStrokeWidth(mBorderSize);
        mEndProcessPaint.setStyle(Paint.Style.STROKE);

        mFillArePaint.setAntiAlias(true);
        mFillArePaint.setColor(mProcessBorderColor);
        mFillArePaint.setStrokeWidth(mBorderSize);
        mFillArePaint.setStyle(Paint.Style.FILL);

        mSuccessPaint.setAntiAlias(true);
        mSuccessPaint.setColor(mSuccessBorderColor);
        mSuccessPaint.setStrokeWidth(mSuccessBorderSize);
        mSuccessPaint.setStyle(Paint.Style.STROKE);

        /** Define animators*/
        animatorStart = ObjectAnimator.ofFloat(LoaderView.this, "phaseStart", 1.0f, 0.0f);
        animatorStart.setDuration(1500);
        animatorStart.setInterpolator(new DecelerateInterpolator());
        animatorStart.setRepeatCount(ObjectAnimator.INFINITE);
        animatorStart.setRepeatMode(ObjectAnimator.RESTART);

        animatorEnd = ObjectAnimator.ofFloat(LoaderView.this, "phaseEnd", 1.0f, 0.0f);
        animatorEnd.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) { }

            @Override
            public void onAnimationEnd(Animator animation) { }

            @Override
            public void onAnimationCancel(Animator animation) {
                animatorFilling.start();
            }

            @Override
            public void onAnimationRepeat(Animator animation) { }
        });
        animatorEnd.setDuration(1500);
        animatorEnd.setInterpolator(new AccelerateInterpolator());
        animatorEnd.setRepeatCount(ObjectAnimator.INFINITE);
        animatorEnd.setRepeatMode(ObjectAnimator.RESTART);

        animatorFilling = ObjectAnimator.ofFloat(LoaderView.this, "phaseFilling", 0.0f, 255.0f);
        animatorFilling.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                drawOkIcon();
            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });
        animatorFilling.setDuration(710);
        animatorFilling.setInterpolator(new DecelerateInterpolator());

        animatorSuccess = ObjectAnimator.ofFloat(LoaderView.this, "phaseSuccess", 1.0f, 0.0f);
        animatorSuccess.setDuration(1000);
    }

    public void startLoading(){
        state = 1;

        PathMeasure measure = new PathMeasure(mHexagonPath, false);
        mAnimationLength = measure.getLength();

        animatorStart.start();
        animatorEnd.start();
    }

    public void finishLoading(){
        state = 2;

        animatorStart.cancel();
        animatorEnd.cancel();
    }

    float mSuccessAnimationLength;
    private void drawOkIcon() {
        state = 3;

        PathMeasure measure = new PathMeasure(okIconPath, false);
        mSuccessAnimationLength = measure.getLength();
        animatorSuccess.start();
    }

    /** It will be called by animator */
    public void setPhaseStart(float _phaseStart) {
        mProcessPaint.setPathEffect(createPathEffect(mAnimationLength, _phaseStart, 0.0f));
        invalidate();
    }

    /** It will be called by animator */
    public void setPhaseEnd(float _phaseEnd) {
        mEndProcessPaint.setPathEffect(createPathEffect(mAnimationLength, _phaseEnd, 0.0f));
        invalidate();
    }

    /** It will be called by animator */
    public void setPhaseFilling(float _phaseFilling){
        mFillArePaint.setAlpha((int) _phaseFilling);
        invalidate();
    }

    /** It will be called by animator */
    public void setPhaseSuccess(float _phaseSuccess){
        mSuccessPaint.setPathEffect(createPathEffect(mSuccessAnimationLength, _phaseSuccess, 0.0f));
        invalidate();
    }

    private static PathEffect createPathEffect(float _pathLength, float _phase, float _offset) {
        return new DashPathEffect(
                new float[] { _pathLength, _pathLength },
                Math.max(_phase * _pathLength, _offset)
            );
    }
    int state;
    @Override
    public void onDraw(Canvas _canvas) {
        super.onDraw(_canvas);
        switch (state){
            case 0:
                _canvas.drawPath(mHexagonPath, mBorderPaint);
                break;
            case 1:
                _canvas.drawPath(mHexagonPath, mBorderPaint);
                _canvas.drawPath(mHexagonPath, mProcessPaint);
                _canvas.drawPath(mHexagonPath, mEndProcessPaint);
                break;
            case 2:
                _canvas.drawPath(mHexagonPath, mFillArePaint);
                break;
            case 3:
                _canvas.drawPath(mHexagonPath, mFillArePaint);
                _canvas.drawPath(okIconPath, mSuccessPaint);
                break;
        }
    }
}


