package com.uae.tra_smart_services.customviews;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.ClipData;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;
import android.view.View.OnDragListener;
import android.view.animation.OvershootInterpolator;
import android.widget.FrameLayout;
import android.widget.TextView;

/**
 * Created by mobimaks on 18.08.2015.
 */
public class DragFrameLayout extends FrameLayout implements OnDragListener {

    private final int mStartDeleteArcAngle = 180, mDeleteArcSweepAngle = 180;

    private final int OVERLAY_ITEM_COLOR = Color.parseColor("#f1f1f1");
    private final float SHADOW_RADIUS = 5 * getResources().getDisplayMetrics().density;


    private View mDragTarget;
    private PointF mPoint = new PointF();
    private final float deleteArcVisibleHeight = 150 * getResources().getDisplayMetrics().density;
    private Float deleteArcVisibleAnimHeight = 0f;
    private ValueAnimator mAnimator;

    public DragFrameLayout(Context context) {
        this(context, null);
    }

    public DragFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        setLayerType(LAYER_TYPE_SOFTWARE, null);
        setOnDragListener(this);
        initPaints();
    }

    private void initPaints() {
        mItemOverlayPaint.setStyle(Paint.Style.FILL);
        mItemOverlayPaint.setColor(OVERLAY_ITEM_COLOR);

        mShadowPaint.setStyle(Paint.Style.FILL);
        mShadowPaint.setColor(Color.parseColor("#fff5ea"));
        mShadowPaint.setShadowLayer(SHADOW_RADIUS, 0, 0, Color.GRAY);

        mShadowShadowPaint.setStyle(Paint.Style.FILL);
        mShadowShadowPaint.setColor(Color.GREEN);

        deletePaint.setAntiAlias(true);
        deletePaint.setStyle(Paint.Style.FILL);
        deletePaint.setColor(Color.RED);
        deletePaint.setAlpha(Math.round(255 * 0.25f));
    }

    private boolean draw;
    private boolean isAnimated;


    @Override
    public boolean onDrag(View v, DragEvent event) {
        logState(event);

        switch (event.getAction()) {
            case DragEvent.ACTION_DRAG_ENTERED:
                isDragging = true;
                mPoint.x = event.getX();
                mPoint.y = event.getY();
                startAnimation();
                break;
            case DragEvent.ACTION_DRAG_LOCATION:
                mPoint.x = event.getX();
                mPoint.y = event.getY();
                if (!isAnimated) {
                    draw = true;
                    invalidate();
                }
                break;
            case DragEvent.ACTION_DRAG_ENDED:
                TextView textView = (TextView) mDragTarget.getTag();
                if (textView != null) {
                    textView.setTextColor(mDefaultTextColor);
                }
                isFinishing = true;
                isDragging = false;
                mAnimator.reverse();
                break;
        }

        return true;
    }

    boolean isDragging; //  need draw shadow and overlay indicator
    boolean isFinishing;


    private void startAnimation() {
        mAnimator = ValueAnimator.ofFloat(0, deleteArcVisibleHeight);
        mAnimator.setInterpolator(new OvershootInterpolator());
        mAnimator.setDuration(200);
        mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                deleteArcVisibleAnimHeight = (Float) animation.getAnimatedValue();
                draw = true;
                invalidateDeleteArea(getWidth(), getHeight());
                invalidate();
            }
        });

        mAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                isAnimated = true;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                isAnimated = false;
                if (isFinishing) {
                    mDragTarget = null;
                    isFinishing = false;
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                isAnimated = false;
            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        mAnimator.start();
    }

    //region LOG Drag&Drop state
    int previousState = Integer.MIN_VALUE;

    private void logState(DragEvent event) {
        if (previousState != event.getAction()) {
            previousState = event.getAction();
            switch (previousState) {
                case DragEvent.ACTION_DRAG_LOCATION:
                    Log.d(getClass().getSimpleName(), "DragEvent: ACTION_DRAG_LOCATION");
                    break;
                case DragEvent.ACTION_DRAG_ENDED:
                    Log.d(getClass().getSimpleName(), "DragEvent: ACTION_DRAG_ENDED");
                    break;
                case DragEvent.ACTION_DRAG_ENTERED:
                    Log.d(getClass().getSimpleName(), "DragEvent: ACTION_DRAG_ENTERED");
                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                    Log.d(getClass().getSimpleName(), "DragEvent: ACTION_DRAG_EXITED");
                    break;
                case DragEvent.ACTION_DRAG_STARTED:
                    Log.d(getClass().getSimpleName(), "DragEvent: ACTION_DRAG_STARTED");
                    break;
                case DragEvent.ACTION_DROP:
                    Log.d(getClass().getSimpleName(), "DragEvent: ACTION_DROP");
                    break;
            }
        }
    }
    //endregion

    private ColorStateList mDefaultTextColor;

    public void starDragChild(final View _view, final ClipData _data) {
        mDragTarget = _view;

        TextView textView = (TextView) _view.getTag();
        if (textView != null) {
            mDefaultTextColor = textView.getTextColors();
            textView.setTextColor(Color.RED);
        }

        origRect.set(mDragTarget.getX(), mDragTarget.getY(), mDragTarget.getX() + mDragTarget.getWidth(), mDragTarget.getY() + mDragTarget.getHeight());
        mRect.set(0, 0, mDragTarget.getWidth(), mDragTarget.getHeight());

        _view.startDrag(_data, new EmptyDragShadowBuilder(_view), null, 0);
    }

    Rect mRect = new Rect();
    RectF origRect = new RectF();
    Paint mItemOverlayPaint = new Paint(), mShadowPaint = new Paint(), mShadowShadowPaint = new Paint();
    final float scale = 0.95F;

    @Override
    protected void dispatchDraw(@NonNull Canvas canvas) {
        super.dispatchDraw(canvas);
        if (mDragTarget != null && draw) {

            if (isDragging) {
                canvas.drawRect(origRect, mItemOverlayPaint);//draw overlay for original item background
            }

            canvas.drawArc(deleteArcRect, mStartDeleteArcAngle, mDeleteArcSweepAngle, true, deletePaint);// draw delete area background

            if (isDragging) {
                canvas.save();
                //region Calc shadow position
                int translateX = Math.round(mPoint.x - mDragTarget.getWidth() / 2);
                if (translateX < 0) {
                    translateX = 0;
                } else if (translateX > (canvas.getWidth() - mDragTarget.getWidth() * scale)) {
                    translateX = Math.round(canvas.getWidth() - mDragTarget.getWidth() * scale);
                }

                int translateY = Math.round(mPoint.y - mDragTarget.getHeight() / 2);
                if (translateY < 0) {
                    translateY = 0;
                } else if (translateY > canvas.getHeight() - mDragTarget.getHeight() * scale) {
                    translateY = Math.round(canvas.getHeight() - mDragTarget.getHeight() * scale);
                }
                //endregion
                canvas.translate(translateX, translateY);
                canvas.scale(scale, scale);

                canvas.drawRect(mRect, mShadowPaint);//draw shadow background
                mDragTarget.draw(canvas);//draw shadow view
                canvas.restore();
            }
        }
        draw = false;
    }

    final float deleteArcHeight = 400 * getResources().getDisplayMetrics().density;
    final Paint deletePaint = new Paint();
    final RectF deleteArcRect = new RectF();

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        invalidateDeleteArea(w, h);
    }

    private void invalidateDeleteArea(final int _width, final int _height) {
        float canvasOffset = _width * 0.3f;
        deleteArcRect.set(-canvasOffset, _height - deleteArcVisibleAnimHeight, _width + canvasOffset, _height + deleteArcHeight - deleteArcVisibleAnimHeight);
    }

    private static class EmptyDragShadowBuilder extends DragShadowBuilder {

        public EmptyDragShadowBuilder(View _view) {
            super(_view);
        }

        @Override
        public void onProvideShadowMetrics(@NonNull Point _shadowSize, @NonNull Point _shadowTouchPoint) {
            super.onProvideShadowMetrics(_shadowSize, _shadowTouchPoint);
        }

        @Override
        public void onDrawShadow(@NonNull Canvas canvas) {
        }

    }
}
