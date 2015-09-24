package com.uae.tra_smart_services.customviews;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Typeface;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.LayoutDirection;
import android.view.View;

import com.uae.tra_smart_services.R;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by Vitaliy on 24/09/2015.
 */
public abstract class TutorialTipView extends View {

    //region TUTORIAL TYPES
    public static final int TUTORIAL_AVATAR = 0;
    public static final int TUTORIAL_HOT_BAR = 1;
    public static final int TUTORIAL_TAB_BAR = 2;
    //endregion

    //region PROPERTIES
    protected int mTutorialType;
    protected float mPointRadius;
    protected float mLineWidth;
    protected float mTitleBorderWidth;
    protected String mTitleText;
    protected String mTipText;
    protected View mDependView;
    protected PointF[] mCenterPoints;
    protected PointF mStartSidePoint, mEndSidePoint;
    //endregion

    //region COLORS
    protected int mLineColor;
    protected int mTitleBorderColor;
    protected int mTextColor;
    protected int mViewPointerColor;
    //endregion

    //region TEXT SIZES
    protected float mTitleTextSize;
    protected float mTipTextSize;
    //endregion

    //region PAINTS
    protected TextPaint mTitleTextPaint;
    protected TextPaint mTipTextPaint;
    protected Paint mViewPointerPaint;
    protected Paint mLinePointerPaint;
    protected Paint mTitleBorderPaint;
    //endregion

    //region INIT METHODS

    public TutorialTipView(final Context _context, final AttributeSet _attrs) {
        super(_context, _attrs);
        initProperties(_attrs);
        initPaint();
        calculateStaticProperties();
    }

    private void initProperties(final AttributeSet _attrs) {
        TypedArray typedArrayData = getContext().getTheme().
                obtainStyledAttributes(_attrs, R.styleable.TutorialTip, 0, 0);
        try {
            mTutorialType = typedArrayData.getInt(R.styleable.TutorialTip_tutorialTipType, 0);
            mPointRadius = typedArrayData.getDimension(R.styleable.TutorialTip_viewPointerRadius, 5);
            mLineWidth = typedArrayData.getDimension(R.styleable.TutorialTip_linePointerWidth, 3);
            mTitleBorderWidth = typedArrayData.getDimension(R.styleable.TutorialTip_titleBorderWidth, 3);
            mLineColor = typedArrayData.getColor(R.styleable.TutorialTip_linePointerColor, Color.WHITE);
            mTitleBorderColor = typedArrayData.getColor(R.styleable.TutorialTip_linePointerColor, Color.WHITE);
            mViewPointerColor = typedArrayData.getColor(R.styleable.TutorialTip_viewPointerColor, Color.WHITE);
            mTextColor = typedArrayData.getColor(R.styleable.TutorialTip_tipTextColor, Color.WHITE);
            mTipTextSize = typedArrayData.getDimension(R.styleable.TutorialTip_tipTextSize, 15);
            mTitleTextSize = typedArrayData.getDimension(R.styleable.TutorialTip_titleTextSize, 15);
            mTipText = typedArrayData.getString(R.styleable.TutorialTip_tipText);
            mTitleText = typedArrayData.getString(R.styleable.TutorialTip_titleText);
        } finally {
            typedArrayData.recycle();
        }
    }

    private void initPaint() {
        mTitleTextPaint = new TextPaint();
        mTitleTextPaint.setTextSize(mTitleTextSize);
        mTitleTextPaint.setTypeface(Typeface.createFromAsset(getContext().getAssets(), CalligraphyConfig.get().getFontPath()));
        mTitleTextPaint.setColor(mTextColor);

        mTipTextPaint = new TextPaint();
        mTipTextPaint.setTextSize(mTipTextSize);
        mTipTextPaint.setTypeface(Typeface.createFromAsset(getContext().getAssets(), CalligraphyConfig.get().getFontPath()));
        mTipTextPaint.setColor(mTextColor);

        mViewPointerPaint = new Paint();
        mViewPointerPaint.setStyle(Paint.Style.FILL);
        mViewPointerPaint.setColor(mViewPointerColor);

        mLinePointerPaint = new Paint();
        mLinePointerPaint.setStyle(Paint.Style.STROKE);
        mLinePointerPaint.setColor(mLineColor);
        mLinePointerPaint.setStrokeWidth(mLineWidth);
        mLinePointerPaint.setStrokeCap(Paint.Cap.ROUND);

        mTitleBorderPaint = new Paint();
        mTitleBorderPaint.setStyle(Paint.Style.STROKE);
        mTitleBorderPaint.setColor(mTitleBorderColor);
        mTitleBorderPaint.setStrokeWidth(mTitleBorderWidth);
        mTitleBorderPaint.setStrokeCap(Paint.Cap.ROUND);
    }

    protected abstract void calculateStaticProperties();

    //endregion

    //region PUBLIC METHODS

    public final void setDependView(final View _dependView) {
        mDependView = _dependView;
    }

    public final void setCenterPoints(final PointF[] _centerPoints) {
        mCenterPoints = _centerPoints;
    }

    public final void setSidePoints(final PointF _startPoint, final PointF _endPoint) {
        mStartSidePoint = _startPoint;
        mEndSidePoint = _endPoint;
    }

    //endregion

    //region CALCULATING

    @Override
    protected final void onMeasure(final int _widthMeasureSpec, final int _heightMeasureSpec) {
        final int myHeight;
        final int width;
        final int height;

        final int heightMode = MeasureSpec.getMode(_heightMeasureSpec);
        final int widthMode = MeasureSpec.getMode(_widthMeasureSpec);
        final int widthSize = MeasureSpec.getSize(_widthMeasureSpec);
        final int heightSize = MeasureSpec.getSize(_heightMeasureSpec);

        width = widthSize;

//        if (heightMode == MeasureSpec.EXACTLY) {
//            height = heightSize;
//        } else if (heightMode == MeasureSpec.AT_MOST) {
//            height = Math.min(myHeight, heightSize);
//        } else {
//            height = myHeight;
//        }
        setMeasuredDimension(widthSize, heightSize);
    }
    //endregion


    //region LAYOUT DIRECTION METHODS
    protected float getStartPoint() {
        final int direction = getLayoutDirection();
        switch (direction) {
            default:
            case View.LAYOUT_DIRECTION_LTR:
                return 0;
            case View.LAYOUT_DIRECTION_RTL:
                return getWidth();
        }
    }

    protected float getDirectionCoeff() {
        final int direction = getLayoutDirection();
        switch (direction) {
            default:
            case LayoutDirection.LTR:
                return 1;
            case LayoutDirection.RTL:
                return -1;
        }
    }

    protected float calculateWithCoefficient(final float _number) {
        return getDirectionCoeff() * _number;
    }

    protected float calculateDependsOnDirection(final float _number) {
        return getStartPoint() + getDirectionCoeff() * _number;
    }
    //endregion
}
