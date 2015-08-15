package com.uae.tra_smart_services.customviews;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.DrawableRes;
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
import java.util.HashMap;
import java.util.List;

/**
 * Created by Vitaliy on 01/08/2015.
 */
public class HexagonalHeader extends View {

    private Paint mHexagonPaint;
    private Paint mButtonPaint;
    private Paint mPressedButtonPaint;
    private Paint mDefaultAvatarBorderPaint;
    private Paint mDefaultAvatarBackgroundPaint;
    private int mHexPaintColor;
    private int mButtonColor;
    private int mPressedButtonColor;
    private int mAvatarPlaceholderBackground;
    private int mHexagonPerRow;
    private int mRowCount;
    private float mHexagonAvatarBorderWidth;
    private float mHexagonStrokeWidth;
    private float mTriangleHeight;
    private float mRadius;

    private Drawable mAvatarPlaceholder;
    private HashMap<Integer, Drawable> mDrawables;
    private HashMap<Integer, PointF[]> mHexagons;
    private List<Integer> mInvisibleHexagons;
    private Integer mPressedButton;

    private boolean isDown = false;

    private final float mAvatarRadiusCoefficient = 1.6f;

    public HexagonalHeader(final Context _context, final AttributeSet _attrs) {
        super(_context, _attrs);
        initProperties(_attrs);
        initPaint();
        initButtons();
        setInvisibleHexagons();
        initDrawables();
    }

    private void initProperties(final AttributeSet _attrs) {
        TypedArray typedArrayData = getContext().getTheme().
                obtainStyledAttributes(_attrs, R.styleable.HexagonalHeader, 0, 0);
        try {
            mHexPaintColor = typedArrayData.getColor(R.styleable.HexagonalHeader_hexColor, Color.WHITE);
            mButtonColor = typedArrayData.getColor(R.styleable.HexagonalHeader_buttonColor, Color.GRAY);
            mPressedButtonColor = typedArrayData.getColor(R.styleable.HexagonalHeader_pressedColor, Color.WHITE);
            mHexagonPerRow = typedArrayData.getInt(R.styleable.HexagonalHeader_hexPerRow, 8);
            mRowCount = typedArrayData.getInt(R.styleable.HexagonalHeader_hexagonRowCount, 2);
            mHexagonStrokeWidth = typedArrayData.getDimension(R.styleable.HexagonalHeader_hexagonStrokeWidth, 3);
            mAvatarPlaceholderBackground = typedArrayData.getColor(R.styleable.HexagonalHeader_avatarPlaceholderBackground, 0xFF455560);

            mHexagonAvatarBorderWidth = mHexagonStrokeWidth * 1.5f;
        } finally {
            typedArrayData.recycle();
        }
    }

    private void initPaint() {
        mHexagonPaint = new Paint();
        mHexagonPaint.setColor(mHexPaintColor);
        mHexagonPaint.setStyle(Paint.Style.STROKE);
        mHexagonPaint.setStrokeWidth(mHexagonStrokeWidth);

        mDefaultAvatarBorderPaint = new Paint();
        mDefaultAvatarBorderPaint.setColor(mHexPaintColor);
        mDefaultAvatarBorderPaint.setStyle(Paint.Style.STROKE);
        mDefaultAvatarBorderPaint.setStrokeWidth(mHexagonAvatarBorderWidth);

        mDefaultAvatarBackgroundPaint = new Paint();
        mDefaultAvatarBackgroundPaint.setColor(mAvatarPlaceholderBackground);
        mDefaultAvatarBackgroundPaint.setStyle(Paint.Style.FILL);

        mButtonPaint = new Paint();
        mButtonPaint.setColor(mButtonColor);
        mButtonPaint.setStyle(Paint.Style.FILL);

        mPressedButtonPaint = new Paint();
        mPressedButtonPaint.setColor(mPressedButtonColor);
        mPressedButtonPaint.setStyle(Paint.Style.FILL);
    }

    private void initButtons() {
        mHexagons = new HashMap<>();

        mHexagons.put(7, null);
        mHexagons.put(8, null);
        mHexagons.put(15, null);
    }

    private void setInvisibleHexagons() {
        mInvisibleHexagons = new ArrayList<>();

        mInvisibleHexagons.add(11);
    }

    private void initDrawables() {
        mDrawables = new HashMap<>();
        mDrawables.put(7, ContextCompat.getDrawable(getContext(), R.drawable.ic_lamp));
        mDrawables.put(8, ContextCompat.getDrawable(getContext(), R.drawable.ic_search));
        mDrawables.put(15, ContextCompat.getDrawable(getContext(), R.drawable.ic_not));

        mAvatarPlaceholder = ContextCompat.getDrawable(getContext(), R.drawable.ic_user);
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

        final float triangleHeight = ((width - getPaddingLeft() - getPaddingRight()) / mHexagonPerRow) / 2;
        final float radius = (float) (triangleHeight* 2 / Math.sqrt(3));

        myHeight = (int) Math.ceil(radius * (2 + 1.5 * (mRowCount - 1)) + radius * (mAvatarRadiusCoefficient - 1) + mHexagonStrokeWidth);

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
        measureDrawableBounds();
    }

    private void measureDrawableBounds() {
        float centerY = getPaddingTop() + mRadius * mAvatarRadiusCoefficient + mHexagonAvatarBorderWidth / 2;
        float centerX = getPaddingLeft() + mHexagonStrokeWidth / 2;

        for (final Integer number : mHexagons.keySet()) {
            final int currentRow = (int) Math.floor((number - 1) / mHexagonPerRow);
            final int hexagonInRow = (number % mHexagonPerRow) == 0 ? mHexagonPerRow : (number % mHexagonPerRow);
            float currentY = centerY + currentRow * mRadius * 1.5f;
            float currentX = centerX + (hexagonInRow - 1) * 2 * mTriangleHeight;
            if (currentRow % 2 == 0) {
                currentX += mTriangleHeight / 2;
            } else {
                currentX += mTriangleHeight * 1.5;
            }
            final int drawableWidth = mDrawables.get(number).getMinimumWidth();
            final int drawableHeight = mDrawables.get(number).getMinimumHeight();

            mDrawables.get(number).setBounds((int) (currentX - drawableWidth / 2), (int) (currentY - drawableHeight / 2),
                    (int) (currentX + drawableWidth / 2), (int) (currentY + drawableHeight / 2));
        }
    }

    @Override
    protected final void onDraw(final Canvas _canvas) {
        drawHexagons(_canvas);
        drawDrawables(_canvas);
        drawAvatarHexagon(_canvas);
    }

    private void calculateVariables(final int _w) {
        mTriangleHeight = ((_w - getPaddingLeft() - getPaddingRight())/ mHexagonPerRow) / 2;
        mRadius = (float) (mTriangleHeight * 2 / Math.sqrt(3));
    }

    private void drawHexagons(final Canvas _canvas) {
        Path hexagonPath = new Path();
        Path buttonsPath = new Path();
        Path pressedButtonPath = null;

        float centerY = getPaddingTop() + mAvatarRadiusCoefficient * mRadius + mHexagonAvatarBorderWidth / 2;

        Integer number = 1;
        for (int row = 0; row < mRowCount; row++) {
            centerY += row * mRadius * 1.5;

            float centerX = getPaddingLeft() + mHexagonStrokeWidth / 2;
            if (row % 2 == 0) {
                centerX += mTriangleHeight / 2;
            } else {
                centerX += mTriangleHeight * 1.5;
            }

            for (int hexagon = 0; hexagon < mHexagonPerRow;
                 hexagon++, centerX += mTriangleHeight * 2, number++) {

                if (mInvisibleHexagons.contains(number)) continue;

                if (mPressedButton != null && mPressedButton.equals(number)) {
                    pressedButtonPath = new Path();
                    pressedButtonPath = calculateButtonFill(pressedButtonPath, centerX, centerY);
                    hexagonPath = calculatePath(hexagonPath, centerX, centerY);
                } else if (mHexagons.containsKey(number)) {
                    hexagonPath = calculatePathAndSave(hexagonPath, number, centerX, centerY);
                    buttonsPath = calculateButtonFill(buttonsPath, centerX, centerY);
                } else {
                    hexagonPath = calculatePath(hexagonPath, centerX, centerY);
                }
            }
        }

        _canvas.drawPath(buttonsPath, mButtonPaint);

        if (pressedButtonPath != null) {
            _canvas.drawPath(pressedButtonPath, mPressedButtonPaint);
        }
        _canvas.drawPath(hexagonPath, mHexagonPaint);
    }

    private void drawDrawables(final Canvas _canvas) {
        for (final Integer number : mHexagons.keySet()) {
            mDrawables.get(number).draw(_canvas);
        }
    }

    private Path calculateButtonFill(Path _path, final float _centerX, final float _centerY) {
        final float halfStroke = mHexagonStrokeWidth / 2;

        _path.moveTo(_centerX, _centerY + mRadius - halfStroke);
        _path.lineTo(_centerX - mTriangleHeight + halfStroke, _centerY + mRadius / 2 - 0);
        _path.lineTo(_centerX - mTriangleHeight + halfStroke, _centerY - mRadius / 2 + 0);
        _path.lineTo(_centerX, _centerY - mRadius + halfStroke);
        _path.lineTo((_centerX + mTriangleHeight) - halfStroke, _centerY - mRadius / 2 + 0);
        _path.lineTo((_centerX + mTriangleHeight) - halfStroke, _centerY + mRadius / 2 - 0);
        _path.close();
        return _path;
    }

    private Path calculatePath(Path _path, final float _centerX, final float _centerY) {
        _path.moveTo(_centerX, _centerY + mRadius);
        _path.lineTo(_centerX - mTriangleHeight, _centerY + mRadius / 2);
        _path.lineTo(_centerX - mTriangleHeight, _centerY - mRadius / 2);
        _path.lineTo(_centerX, _centerY - mRadius);
        _path.lineTo(_centerX + mTriangleHeight, _centerY - mRadius / 2);
        _path.lineTo(_centerX + mTriangleHeight, _centerY + mRadius / 2);
        _path.close();

        return _path;
    }

    private Path calculatePathAndSave(Path _path, final int _number,
                                      final float _centerX, final float _centerY) {
        mHexagons.put(_number, createPoints(_centerX, _centerY));

        return calculatePath(_path, _centerX, _centerY);
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

    private void drawAvatarHexagon(final Canvas _canvas) {
        final float avatarTriangleHeight = mTriangleHeight * mAvatarRadiusCoefficient;
        final float radius = mRadius * mAvatarRadiusCoefficient;
        final float centerY = getPaddingTop() + radius + mHexagonAvatarBorderWidth / 2;
        final float centerX = getPaddingLeft() + mHexagonStrokeWidth / 2 + 2.5f * mTriangleHeight;
        Path avatarPath = new Path();
        avatarPath.moveTo(centerX, centerY + radius);
        avatarPath.lineTo(centerX - avatarTriangleHeight, centerY + radius / 2);
        avatarPath.lineTo(centerX - avatarTriangleHeight, centerY - radius / 2);
        avatarPath.lineTo(centerX, centerY - radius);
        avatarPath.lineTo(centerX + avatarTriangleHeight, centerY - radius / 2);
        avatarPath.lineTo(centerX + avatarTriangleHeight, centerY + radius / 2);
        avatarPath.close();

        _canvas.drawPath(avatarPath, mDefaultAvatarBackgroundPaint);
        _canvas.drawPath(avatarPath, mDefaultAvatarBorderPaint);

        float drawableWidth = mAvatarPlaceholder.getMinimumWidth();
        float drawableHeight = mAvatarPlaceholder.getMinimumHeight();

        if (drawableWidth < avatarTriangleHeight && drawableHeight < radius) {
            mAvatarPlaceholder.setBounds((int) (centerX - drawableWidth / 2), (int) (centerY - drawableHeight / 2),
                    (int) (centerX + drawableWidth / 2), (int) (centerY + drawableHeight / 2));
        } else {
            final float widthCoef = avatarTriangleHeight / drawableHeight;

            drawableWidth *= widthCoef;
            drawableHeight *= widthCoef;

            mAvatarPlaceholder.setBounds((int) (centerX - drawableWidth / 2), (int) (centerY - drawableHeight / 2),
                    (int) (centerX + drawableWidth / 2), (int) (centerY + drawableHeight / 2));
        }
        mAvatarPlaceholder.draw(_canvas);
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
        for (final Integer key : mHexagons.keySet()) {
            if (mHexagons.get(key) != null && HexagonUtils.pointInPolygon(clickPoint, mHexagons.get(key))) {
                isDown = true;
                mPressedButton = key;
                invalidate();
                return;
            }
        }
    }

    private void captureClick(final MotionEvent _event) {
        isDown = false;
        final PointF clickPoint = new PointF(_event.getX(), _event.getY());
        if (mPressedButton == null) return;
        if (HexagonUtils.pointInPolygon(clickPoint, mHexagons.get(mPressedButton))) {
            Toast.makeText(getContext(), "Clicked hexagon " + mPressedButton, Toast.LENGTH_SHORT).show();
        }
        mPressedButton = null;
        invalidate();
    }

    private void captureMove(final MotionEvent _event) {
        final PointF clickPoint = new PointF(_event.getX(), _event.getY());
        if (mPressedButton != null && !HexagonUtils.pointInPolygon(clickPoint, mHexagons.get(mPressedButton))) {
            mPressedButton = null;
            invalidate();
        }
    }
}
