package com.uae.tra_smart_services.customviews;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.MotionEventCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.uae.tra_smart_services.R;
import com.uae.tra_smart_services.util.HexagonUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vitaliy on 13/08/2015.
 */
public class HexagonalButtonsLayout extends View {

    private OnServiceSelected mServiceSelectedListener;

    private Paint mSeparatorPaint;
    private Paint mButtonPaint;
    private Paint mShadowPaint;
    private Paint mButtonSecondColorPaint;
    private Paint mPressedButtonPaint;
    private Paint mOrangeTextPaint;
    private Paint mWhiteTextPain;
    private int mSeparatorColor;
    private int mPressedButtonColor;
    private int mButtonsCount;
    private float mHexagonGapWidth;
    private float mSeparatorStrokeWidth;
    private float mTriangleHeight;
    private float mRadius;
    private float mSeparatorTriangleHeight;
    private float mSeparatorRadius;
    private float mTextSize;

    private List<Drawable> mDrawables;
    private List<PointF[]> mPolygons;

    private boolean isDown = false;
    private Integer mPressedButton;

    public HexagonalButtonsLayout(final Context _context, final AttributeSet _attrs) {
        super(_context, _attrs);
        initProperties(_attrs);
        initPaint();
        initDrawables();
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
    }

    public final float getHalfOuterRadius() {
        return mSeparatorRadius / 2 + mHexagonGapWidth;
    }

    public final void setServiceSelectedListener(final OnServiceSelected _serviceSelectedListener) {
        mServiceSelectedListener = _serviceSelectedListener;
    }

    private void initProperties(final AttributeSet _attrs) {
        TypedArray typedArrayData = getContext().getTheme().
                obtainStyledAttributes(_attrs, R.styleable.HexagonalButtonsLayoutAttrs, 0, 0);
        try {
            mSeparatorColor = typedArrayData.getColor(R.styleable.HexagonalButtonsLayoutAttrs_separatorColor, Color.WHITE);
            mButtonsCount = typedArrayData.getInt(R.styleable.HexagonalButtonsLayoutAttrs_hexagonCount, 4);
            mSeparatorStrokeWidth = typedArrayData.getDimension(R.styleable.HexagonalButtonsLayoutAttrs_separatorStrokeWidth, 3);
            mHexagonGapWidth = typedArrayData.getDimension(R.styleable.HexagonalButtonsLayoutAttrs_hexagonGapWidth, 3);
            mTextSize = typedArrayData.getDimension(R.styleable.HexagonalButtonsLayoutAttrs_hexTextSize, 14);
        } finally {
            typedArrayData.recycle();
        }

        mPolygons = new ArrayList<>();
    }

    private void initPaint() {
        mSeparatorPaint = new Paint();
        mSeparatorPaint.setColor(mSeparatorColor);
        mSeparatorPaint.setStyle(Paint.Style.STROKE);
        mSeparatorPaint.setStrokeWidth(mSeparatorStrokeWidth);

        mButtonPaint = new Paint();
        mButtonPaint.setColor(Color.WHITE);
        mButtonPaint.setStyle(Paint.Style.FILL);

        mShadowPaint = new Paint();
        mShadowPaint.setShadowLayer(3.0f, 4.0f, 4.0f, 0xFF8C8C8C);
        mShadowPaint.setStyle(Paint.Style.FILL);

        mButtonSecondColorPaint = new Paint();
        mButtonSecondColorPaint.setColor(0xFF44545F);
        mButtonSecondColorPaint.setStyle(Paint.Style.FILL);

        mOrangeTextPaint = new Paint();
        mOrangeTextPaint.setTextAlign(Paint.Align.CENTER);
        mOrangeTextPaint.setTextSize(mTextSize);
        mOrangeTextPaint.setColor(0xFFF68F1E);

        mWhiteTextPain = new Paint();
        mWhiteTextPain.setTextAlign(Paint.Align.CENTER);
        mWhiteTextPain.setTextSize(mTextSize);
        mWhiteTextPain.setColor(Color.WHITE);
    }

    private void initDrawables() {
        mDrawables = new ArrayList<>();

        mDrawables.add(ContextCompat.getDrawable(getContext(), R.drawable.ic_verif));
        mDrawables.add(ContextCompat.getDrawable(getContext(), R.drawable.ic_spam));
        mDrawables.add(ContextCompat.getDrawable(getContext(), R.drawable.ic_coverage));
        mDrawables.add(ContextCompat.getDrawable(getContext(), R.drawable.ic_internet));
    }

    @Override
    protected final void onMeasure(final int _widthMeasureSpec, final int _heightMeasureSpec) {
        int myHeight;
        int width;
        int height;

        final int heightMode = MeasureSpec.getMode(_heightMeasureSpec);
        final int widthSize = MeasureSpec.getSize(_widthMeasureSpec);
        final int heightSize = MeasureSpec.getSize(_heightMeasureSpec);

        width = widthSize;

        final float triangleHeight = ((width - getPaddingLeft() - getPaddingRight() -
                mHexagonGapWidth * mButtonsCount - mHexagonGapWidth) / mButtonsCount) / 2;
        final float radius = (float) (triangleHeight * 2 / Math.sqrt(3));

        myHeight = (int) (radius * 2 + mHexagonGapWidth + mSeparatorStrokeWidth);

        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else if (heightMode == MeasureSpec.AT_MOST) {
            height = Math.min(myHeight, heightSize);
        } else {
            height = myHeight;
        }
        setMeasuredDimension(width, height);
    }

    @Override
    protected final void onSizeChanged(final int _w, final int _h,
                                       final int _oldw, final int _oldh) {
        super.onSizeChanged(_w, _h, _oldw, _oldh);

        calculateVariables(_w);
        measureDrawablesBounds();

    }

    private void calculateVariables(final int _w) {
        mTriangleHeight = ((_w - getPaddingLeft() - getPaddingRight() -
                mHexagonGapWidth * mButtonsCount - mHexagonGapWidth) / mButtonsCount) / 2;
        mRadius = (float) (mTriangleHeight * 2 / Math.sqrt(3));

        mSeparatorTriangleHeight = mTriangleHeight + mHexagonGapWidth / 2;
        mSeparatorRadius = (float) (mSeparatorTriangleHeight * 2 / Math.sqrt(3));
    }

    private void measureDrawablesBounds() {
        float centerY = getPaddingTop() + mRadius / 2 + getMaxDrawableHeight(mDrawables);
        float centerX = getPaddingLeft() + mHexagonGapWidth + mTriangleHeight;

        for (int hexagon = 0; hexagon < mButtonsCount;
             hexagon++, centerX += mHexagonGapWidth + mTriangleHeight * 2) {
            final float drawableWidth = mDrawables.get(hexagon).getMinimumWidth();
            final float drawableHeight = mDrawables.get(hexagon).getMinimumHeight();
            mDrawables.get(hexagon).setBounds((int) (centerX - drawableWidth / 2), (int) (centerY - drawableHeight),
                    (int) (centerX + drawableWidth / 2), (int) centerY);
        }
    }

    private float getMaxDrawableHeight(final List<Drawable> _drawables) {
        float max = 0;

        for (final Drawable drawable : _drawables) {
            if (drawable.getMinimumHeight() > max) {
                max = drawable.getMinimumHeight();
            }
        }

        return max;
    }

    @Override
    protected final void onDraw(final Canvas _canvas) {
        super.onDraw(_canvas);

        drawHexagons(_canvas);
        drawBottomSeparator(_canvas);
        drawDrawables(_canvas);

        _canvas.drawText("Verification", getPaddingLeft() + mHexagonGapWidth + mTriangleHeight,
                getPaddingTop() + mRadius + mRadius / 2, mOrangeTextPaint);

        _canvas.drawText("Spam", getPaddingLeft() + mHexagonGapWidth * 2 + mTriangleHeight * 3,
                getPaddingTop() + mRadius + mRadius / 2, mOrangeTextPaint);

        _canvas.drawText("Coverage", getPaddingLeft() + mHexagonGapWidth * 3 + mTriangleHeight * 5,
                getPaddingTop() + mRadius + mRadius / 2, mWhiteTextPain);

        _canvas.drawText("Internet", getPaddingLeft() + mHexagonGapWidth * 4 + mTriangleHeight * 7,
                getPaddingTop() + mRadius + mRadius / 2, mWhiteTextPain);
    }

    private void drawHexagons(final Canvas _canvas) {
        Path buttonsPath = new Path();
        Path buttonsSecondPath = new Path();

        float centerY = getPaddingTop() + mRadius;
        float centerX = getPaddingLeft() + mHexagonGapWidth + mTriangleHeight;

        for (int hexagon = 0; hexagon < mButtonsCount;
             hexagon++, centerX += mHexagonGapWidth + mTriangleHeight * 2) {
            if (hexagon < (mButtonsCount / 2)) {
                buttonsPath = calculatePath(buttonsPath, centerX, centerY);
            } else {
                buttonsSecondPath = calculatePath(buttonsSecondPath, centerX, centerY);
            }
        }

        _canvas.drawPath(buttonsSecondPath, mShadowPaint);
        _canvas.drawPath(buttonsSecondPath, mButtonSecondColorPaint);
        _canvas.drawPath(buttonsPath, mShadowPaint);
        _canvas.drawPath(buttonsPath, mButtonPaint);
    }

    private Path calculatePath(Path _path, final float _centerX, final float _centerY) {
        mPolygons.add(createPoints(_centerX, _centerY));

        _path.moveTo(_centerX, _centerY + mRadius);
        _path.lineTo(_centerX - mTriangleHeight, _centerY + mRadius / 2);
        _path.lineTo(_centerX - mTriangleHeight, _centerY - mRadius / 2);
        _path.lineTo(_centerX, _centerY - mRadius);
        _path.lineTo(_centerX + mTriangleHeight, _centerY - mRadius / 2);
        _path.lineTo(_centerX + mTriangleHeight, _centerY + mRadius / 2);
        _path.close();

        return _path;
    }

    private PointF[] createPoints(final float _centerX, final float _centerY) {
        final PointF[] points = new PointF[6];

        points[0] = new PointF(_centerX, _centerY + mRadius);
        points[1] = new PointF(_centerX - mTriangleHeight, _centerY + mRadius / 2);
        points[2] = new PointF(_centerX - mTriangleHeight, _centerY - mRadius / 2);
        points[3] = new PointF(_centerX, _centerY - mRadius);
        points[4] = new PointF(_centerX + mTriangleHeight, _centerY - mRadius / 2);
        points[5] = new PointF(_centerX + mTriangleHeight, _centerY + mRadius / 2);

        return points;
    }

    private void drawBottomSeparator(final Canvas _canvas) {
        Path separatorPath = new Path();

        float centerY = getPaddingTop() + mRadius + mHexagonGapWidth / 2;
        float centerX = getPaddingLeft() + mHexagonGapWidth + mTriangleHeight;

        separatorPath.moveTo(centerX - 2 * mSeparatorTriangleHeight, centerY + mSeparatorRadius);
        separatorPath.lineTo(centerX - mSeparatorTriangleHeight, centerY + mSeparatorRadius / 2);

        for (int hexagon = 0; hexagon < mButtonsCount;
             hexagon++, centerX += mSeparatorTriangleHeight * 2) {

            separatorPath.lineTo(centerX, centerY + mSeparatorRadius);
            separatorPath.lineTo(centerX + mSeparatorTriangleHeight, centerY + mSeparatorRadius / 2);
        }

        separatorPath.lineTo(centerX + 2 * mSeparatorTriangleHeight, centerY + mSeparatorRadius);

        _canvas.drawPath(separatorPath, mSeparatorPaint);
    }

    private void drawDrawables(final Canvas _canvas) {
        for (final Drawable drawable : mDrawables) {
            drawable.draw(_canvas);
        }
    }

    @Override
    public final boolean onTouchEvent(@NonNull final MotionEvent _event) {
        final int action = MotionEventCompat.getActionMasked(_event);

        switch(action) {
            case (MotionEvent.ACTION_DOWN) :
                capturePress(_event);
                return true;
            case (MotionEvent.ACTION_UP) :
                if (isDown) {
                    captureClick(_event);
                }
                return true;
            case (MotionEvent.ACTION_MOVE) :
                if (isDown) {
                    captureMove(_event);
                }
                return true;
            default :
                return super.onTouchEvent(_event);
        }
    }

    private void capturePress(final MotionEvent _event) {
        final PointF clickPoint = new PointF(_event.getX(), _event.getY());
        for (int i = 0; i < mPolygons.size(); i++) {
            if (mPolygons.get(i) != null && HexagonUtils.pointInPolygon(clickPoint, mPolygons.get(i))) {
                isDown = true;
                mPressedButton = i;
                return;
            }
        }
    }

    private void captureClick(final MotionEvent _event) {
        isDown = false;
        final PointF clickPoint = new PointF(_event.getX(), _event.getY());
        if (mPressedButton == null) return;
        if (HexagonUtils.pointInPolygon(clickPoint, mPolygons.get(mPressedButton))) {
            if (mServiceSelectedListener != null) {
                mServiceSelectedListener.serviceSelected(mPressedButton);
            }
        }
        mPressedButton = null;
    }

    private void captureMove(final MotionEvent _event) {
        final PointF clickPoint = new PointF(_event.getX(), _event.getY());
        if (mPressedButton != null && !HexagonUtils.pointInPolygon(clickPoint, mPolygons.get(mPressedButton))) {
            mPressedButton = null;
        }
    }

    public interface OnServiceSelected {
        void serviceSelected(final int _id);
    }

    public enum StaticService {
        VERIFICATION_SERVICE(0),
        SMS_SPAM_SERVICE(1),
        POOR_COVERAGE_SERVICE(2),
        INTERNET_SPEED_TEST(3);

        private final Integer value;

        StaticService(final Integer newValue) {
            value = newValue;
        }

        public Integer getValue() { return value; }

        public boolean isEquals(final int _value) {
            return value.equals(_value);
        }
    }
}
