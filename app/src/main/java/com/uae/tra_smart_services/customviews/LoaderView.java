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
import android.view.animation.DecelerateInterpolator;

import com.uae.tra_smart_services.R;

/**
 * Created by Andrey Korneychuk on 21.09.15.
 */

public class LoaderView extends View {

    private enum State{
        INITIALL(0),
        PROCESSING(1),
        FILLING(2),
        SUCCESS(3),
        FAILURE(4);

        private int state;
        State(int _state){
            state = _state;
        }
    }

    @ColorInt
    private int mBorderColor, mProcessBorderColor, mSuccessBorderColor;
    private float mBorderSize, mProcessBorderSize, mSuccessBorderSize;
    private final Path mHexagonPath, okIconPath;
    private final Paint mBorderPaint, mProcessPaint, mEndProcessPaint, mFillArePaint, mSuccessPaint;
    private final int HEXAGON_BORDER_COUNT = 6;
    private double mHexagonSide, mHexagonInnerRadius;
    private final int DEFAULT_HEXAGON_RADIUS = Math.round(30 * getResources().getDisplayMetrics().density);
    private float mSuccessAnimationLength;
    private State mAnimationState = State.INITIALL;
    private double mHexagonSection;
    private float mProcessAnimationLength;
    private int successFigureOffsetX = 25;
    private int successFigureOffsetY = 35;

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

        mHexagonPath = new Path();
        okIconPath = new Path();
        mBorderPaint = new Paint();
        mProcessPaint = new Paint();
        mEndProcessPaint = new Paint();
        mFillArePaint = new Paint();
        mSuccessPaint = new Paint();

        initParams(context, attrs);
        initPaints();
        initAnimators();
    }


    private void initParams(Context context, AttributeSet attrs){
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.HexagonView);

        mHexagonSide = array.getDimensionPixelSize(R.styleable.HexagonView_hexagonSideSize, DEFAULT_HEXAGON_RADIUS);
        mBorderColor = array.getColor(R.styleable.HexagonView_hexagonBorderColor, 0xFFC8C7C6);
        mProcessBorderColor = array.getColor(R.styleable.HexagonView_hexagonProcessBorderColor, 0xFFFFFFFF);
        mSuccessBorderColor = array.getColor(R.styleable.HexagonView_hexagonSuccessBorderColor, 0xFFFFFFFF);
        mBorderSize = array.getDimensionPixelSize(R.styleable.HexagonView_hexagonBorderSize, 3);
        mProcessBorderSize = array.getDimensionPixelSize(R.styleable.HexagonView_hexagonProcessBorderSize, 3);
        mSuccessBorderSize = array.getDimensionPixelSize(R.styleable.HexagonView_hexagonSuccessBorderSize, 5);
        mHexagonInnerRadius = Math.sqrt(3) * mHexagonSide / 2;
    }

    private void initPaints(){
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
    }

    private void initAnimators(){
        animatorStart = ObjectAnimator.ofFloat(LoaderView.this, "phaseStart", 1.0f, 0.0f);
        animatorStart.setDuration(1500);
        animatorStart.setInterpolator(new DecelerateInterpolator());
        animatorStart.setRepeatCount(ObjectAnimator.INFINITE);
        animatorStart.setRepeatMode(ObjectAnimator.RESTART);

        animatorEnd = ObjectAnimator.ofFloat(LoaderView.this, "phaseEnd", 1.0f, 0.0f);
        animatorEnd.setDuration(1500);
        animatorEnd.setInterpolator(new AccelerateInterpolator());
        animatorEnd.setRepeatCount(ObjectAnimator.INFINITE);
        animatorEnd.setRepeatMode(ObjectAnimator.RESTART);
        animatorEnd.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationCancel(Animator animation) {
                animatorFilling.start();
            }

            @Override
            public void onAnimationStart(Animator animation) {/* Is not implemented */}

            @Override
            public void onAnimationEnd(Animator animation) {/* Is not implemented */}

            @Override
            public void onAnimationRepeat(Animator animation) {/* Is not implemented */}
        });

        animatorFilling = ObjectAnimator.ofFloat(LoaderView.this, "phaseFilling", 0.0f, 255.0f);
        animatorFilling.setDuration(710);
        animatorFilling.setInterpolator(new DecelerateInterpolator());
        animatorFilling.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationEnd(Animator animation) {
                startDrawSuccessFigure();
            }

            @Override
            public void onAnimationStart(Animator animation) {/* Is not implemented */}

            @Override
            public void onAnimationCancel(Animator animation) {/* Is not implemented */}

            @Override
            public void onAnimationRepeat(Animator animation) {/* Is not implemented */}
        });

        animatorSuccess = ObjectAnimator.ofFloat(LoaderView.this, "phaseSuccess", 1.0f, 0.0f);
        animatorSuccess.setDuration(710);
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


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mHexagonSection = 2.0 * Math.PI / HEXAGON_BORDER_COUNT;

        mHexagonPath.reset();
        mHexagonPath.moveTo(
                (float) (w / 2 - mHexagonSide * Math.sin(0)),
                (float) (h / 2 - mHexagonSide * Math.cos(0)));

        for (int i = 1; i < HEXAGON_BORDER_COUNT; i++) {
            mHexagonPath.lineTo(
                    (float) (w / 2 - mHexagonSide * Math.sin(mHexagonSection * -i)),
                    (float) (h / 2 - mHexagonSide * Math.cos(mHexagonSection * -i)));
        }
        mHexagonPath.close();

        okIconPath.reset();
        okIconPath.moveTo(successFigureOffsetX + 20, successFigureOffsetY + 60);
        okIconPath.lineTo(successFigureOffsetX + 45, successFigureOffsetY + 95);
        okIconPath.lineTo(successFigureOffsetX + 100, successFigureOffsetY + 40);
    }

    public void startProcessing(){
        mAnimationState = State.PROCESSING;

        PathMeasure measure = new PathMeasure(mHexagonPath, false);
        mProcessAnimationLength = measure.getLength();

        animatorStart.start();
        animatorEnd.start();
    }

    public void startFilling(){
        mAnimationState = State.FILLING;

        animatorStart.cancel();
        animatorEnd.cancel();
    }

    private void startDrawSuccessFigure() {
        mAnimationState = State.SUCCESS;

        PathMeasure measure = new PathMeasure(okIconPath, false);
        mSuccessAnimationLength = measure.getLength();
        animatorSuccess.start();
    }

    /** It will be called by animator to draw the start of loading animation */
    public void setPhaseStart(float _phaseStart) {
        mProcessPaint.setPathEffect(createPathEffect(mProcessAnimationLength, _phaseStart, 0.0f));
        invalidate();
    }

    /** It will be called by animator to draw the end of loading animation */
    public void setPhaseEnd(float _phaseEnd) {
        mEndProcessPaint.setPathEffect(createPathEffect(mProcessAnimationLength, _phaseEnd, 0.0f));
        invalidate();
    }

    /** It will be called by animator to fill the hexagon area smoothly after loading finished */
    public void setPhaseFilling(float _phaseFilling){
        mFillArePaint.setAlpha((int) _phaseFilling);
        invalidate();
    }

    /** It will be called by animator to draw success figure on filled hexagon area*/
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

    @Override
    public void onDraw(Canvas _canvas) {
        super.onDraw(_canvas);
        switch (mAnimationState){
            case PROCESSING:
                _canvas.drawPath(mHexagonPath, mBorderPaint);
                _canvas.drawPath(mHexagonPath, mProcessPaint);
                _canvas.drawPath(mHexagonPath, mEndProcessPaint);
                break;
            case FILLING:
                _canvas.drawPath(mHexagonPath, mFillArePaint);
                break;
            case SUCCESS:
                _canvas.drawPath(mHexagonPath, mFillArePaint);
                _canvas.drawPath(okIconPath, mSuccessPaint);
                break;
            case FAILURE:
                // TODO: Have to implemented to draw failure figure on the hexagon
                break;
        }
    }
}


