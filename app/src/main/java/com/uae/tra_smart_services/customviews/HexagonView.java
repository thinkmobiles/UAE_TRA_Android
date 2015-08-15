package com.uae.tra_smart_services.customviews;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.Region;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

import com.uae.tra_smart_services.R;


/**
 * Created by mobimaks on 02.08.2015.
 */
public class HexagonView extends View {

    private final int DEFAULT_HEXAGON_RADIUS = (int) (30 * getResources().getDisplayMetrics().density);
    private final int HEXAGON_BORDER_COUNT = 6;

    private final double mHexagonSide, mHexagonInnerRadius;
    private final Path mPath;
    private final Paint mPaint, mPaint2, mPaint3;
//    private List<Line> lines;
//    private List<Point> points;
    private int mBorderWidth;
    private Drawable mBackgroundDrawable;

    public HexagonView(Context context) {
        this(context, null);
    }

    public HexagonView(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.HexagonView);
        try {
            mHexagonSide = a.getDimensionPixelSize(R.styleable.HexagonView_hexagonSideSize, DEFAULT_HEXAGON_RADIUS);
            mHexagonInnerRadius = Math.sqrt(3) * mHexagonSide / 2;
            mBorderWidth = a.getDimensionPixelSize(R.styleable.HexagonView_hexagonBorderSize, 0);
            mBackgroundDrawable = a.getDrawable(R.styleable.HexagonView_hexagonBackground);
        } finally {
            a.recycle();
        }
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(0xFFC8C7C6);
        mPaint.setStrokeWidth(mBorderWidth);
        mPaint.setStyle(Paint.Style.STROKE);

        mPaint3 = new Paint();


        mPaint2 = new Paint(mPaint);
        mPaint2.setStyle(Paint.Style.FILL);
        mPaint2.setColor(Color.argb(255, 255, 255, 255));

        mPath = new Path();
    }

    public final void setBackgroundDrawable(@DrawableRes final int _drawableRes) {
        mBackgroundDrawable = ContextCompat.getDrawable(getContext(), _drawableRes);
        invalidate();
    }

    public final double getHexagonRadius() {
        return mHexagonSide;
    }

    public int getBorderWidth() {
        return mBorderWidth;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension((int) Math.round(2 * mHexagonInnerRadius + mBorderWidth), (int) Math.round(2 * mHexagonSide + mBorderWidth));
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        double section = 2.0 * Math.PI / HEXAGON_BORDER_COUNT;

        mPath.reset();
        mPath.moveTo(
                (float) (w / 2 + mHexagonSide * Math.sin(0)),
                (float) (h / 2 + mHexagonSide * Math.cos(0)));

        for (int i = 1; i < HEXAGON_BORDER_COUNT; i++) {
            mPath.lineTo(
                    (float) (w / 2 + mHexagonSide * Math.sin(section * i)),
                    (float) (h / 2 + mHexagonSide * Math.cos(section * i)));
        }
        mPath.close();

//        lines = new ArrayList<>();
//        points = new ArrayList<>();
//
//        int[] colors = new int[]{
//                Color.BLACK,
//                Color.BLUE,
//                Color.RED,
//                Color.GREEN,
//                Color.MAGENTA,
//                Color.YELLOW,
//                Color.BLACK,
//                Color.BLUE,
//                Color.RED,
//                Color.GREEN,
//                Color.MAGENTA,
//                Color.YELLOW
//        };
//
//
//        points.add(new Point(
//                (w / 2 + mHexagonSide * Math.sin(0)),
//                (h / 2 + mHexagonSide * Math.cos(0)),
//                mPaint,
//                colors[0]));
//
//        for (int i = 0; i < HEXAGON_BORDER_COUNT; i++) {
//            lines.add(new Line(
//                    (w / 2 + mHexagonSide * Math.sin(section * i)),
//                    (w / 2 + mHexagonSide * Math.sin(section * (i + 1))),
//                    (h / 2 + mHexagonSide * Math.cos(section * i)),
//                    (h / 2 + mHexagonSide * Math.cos(section * (i + 1))),
//                    mPaint,
//                    colors[i]));
//
//            points.add(new Point(
//                    (w / 2 + mHexagonSide * Math.sin(section * (i + 1))),
//                    (h / 2 + mHexagonSide * Math.cos(section * (i + 1))),
//                    mPaint,
//                    colors[i]));
//        }

    }

    private Rect bounds = new Rect(), drawableRect = new Rect();

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setAntiAlias(true);
        if (mBackgroundDrawable != null) {
            canvas.clipPath(mPath);
            canvas.getClipBounds(bounds);
            if (mBackgroundDrawable instanceof BitmapDrawable) {

                float centerY = (float) (mHexagonSide + getBorderWidth() / 2f);
                float centerX = (float) mHexagonInnerRadius;

                final int drawableWidth = mBackgroundDrawable.getMinimumWidth();
                final int drawableHeight = mBackgroundDrawable.getMinimumHeight();

                mBackgroundDrawable.setBounds((int) (centerX - drawableWidth / 2), (int) (centerY - drawableHeight / 2),
                        (int) (centerX + drawableWidth / 2), (int) (centerY + drawableHeight / 2));
//                }

//                if (mBackgroundDrawable.getIntrinsicWidth() < bounds.width()) {
//                    drawableRect.left = (bounds.width() - mBackgroundDrawable.getIntrinsicWidth()) / 2;
//                    drawableRect.right = drawableRect.left + mBackgroundDrawable.getIntrinsicWidth();
//                } else {
//                    drawableRect.left = bounds.left;
//                    drawableRect.right = bounds.right;
//                }
//
//                if (mBackgroundDrawable.getIntrinsicHeight() < bounds.height()) {
//                    drawableRect.top = (bounds.height() - mBackgroundDrawable.getIntrinsicHeight()) / 2;
//                    drawableRect.bottom = drawableRect.top + mBackgroundDrawable.getIntrinsicHeight();
//                } else {
//                    drawableRect.top = bounds.top;
//                    drawableRect.bottom = bounds.bottom;
//                }
//            } else {
//                drawableRect = bounds;
//            }
//
//
//            if (mBackgroundDrawable instanceof BitmapDrawable) {
//                ScaleDrawable scaleDrawable = new ScaleDrawable(mBackgroundDrawable, Gravity.FILL_VERTICAL|Gravity.CENTER_HORIZONTAL, 0f, 0.8f);
//                scaleDrawable.setLevel(1);
//                scaleDrawable.setBounds(bounds);
//                scaleDrawable.draw(canvas);
//                canvas
//                float dwidth = mBackgroundDrawable.getIntrinsicWidth(), dheight = mBackgroundDrawable.getIntrinsicHeight();
//                float vwidth = bounds.width(), vheight = bounds.height();
//                Matrix mDrawMatrix = new Matrix();
//                float scale;
//                float dx;
//                float dy;
//
//                if (dwidth <= vwidth && dheight <= vheight) {
//                    scale = 1.0f;
//                } else {
//                    scale = Math.min(vwidth / dwidth, vheight / dheight);
//                }
//
//                dx = (int) ((vwidth - dwidth * scale) * 0.5f + 0.5f);
//                dy = (int) ((vheight - dheight * scale) * 0.5f + 0.5f);
//
//                mDrawMatrix.setScale(scale, scale);
//                mDrawMatrix.postTranslate(dx, dy);
//                canvas.concat(mDrawMatrix);
//                mBackgroundDrawable.setBounds(bounds);
//                mBackgroundDrawable.draw(canvas);
//                canvas.setMatrix(null);
//            } else {
//                mBackgroundDrawable.setBounds(drawableRect);
                mBackgroundDrawable.draw(canvas);
//            }
                canvas.clipRect(0, 0, canvas.getWidth(), canvas.getHeight(), Region.Op.UNION);
            }
            canvas.drawPath(mPath, mPaint);
//        Path path = new Path(mPath);
//        path.setFillType(Path.FillType.INVERSE_WINDING);
//        canvas.drawPath(path, mPaint2);

//        Paint paint2 = new Paint(mPaint);
//        paint2.setStrokeWidth(0);
//        paint2.setColor(Color.RED);
//        canvas.drawPath(mPath, paint2);

//        Paint paint3 = new Paint(paint2);
//        paint3.setColor(Color.GREEN);
//        canvas.drawLine(canvas.getWidth() / 2, canvas.getHeight() / 2, canvas.getWidth() / 2 + mHexagonInnerRadius, canvas.getHeight() / 2, paint3);
//        paint3.setColor(Color.BLUE);
//        canvas.drawLine(canvas.getWidth() / 2, canvas.getHeight() / 2, canvas.getWidth() / 2, canvas.getHeight() / 2 - mHexagonSide - mBorderWidth / 2, paint3);
//        for (Line line : lines) {
//            canvas.drawLine(line.startX, line.startY, line.stopX, line.stopY, line.paint);
//        }
//
//        for (Point point : points) {
//            canvas.drawPoint(point.x, point.y, point.paint);
//        }
        }
    }

//    private static class Line {
//
//        float startX, stopX;
//        float startY, stopY;
//        Paint paint;
//
//        public Line(double startX, double stopX, double startY, double stopY, Paint paint, int color) {
//            this.startX = (float) startX;
//            this.stopX = (float) stopX;
//            this.startY = (float) startY;
//            this.stopY = (float) stopY;
//            this.paint = new Paint(paint);
//            this.paint.setColor(color);
//        }
//    }
//
//    private static class Point {
//
//        float x, y;
//        Paint paint;
//
//        public Point(double x, double y, Paint paint, int color) {
//            this.x = (float) x;
//            this.y = (float) y;
//            this.paint = new Paint(paint);
//            this.paint.setColor(color);
//        }
//    }

}