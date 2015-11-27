package me.dm7.barcodescanner.core;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Build;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class ViewFinderView extends View implements IViewFinder {
    private static final String TAG = "ViewFinderView";

    private Rect mFramingRect;

    private static final int MIN_FRAME_WIDTH = 240;
    private static final int MIN_FRAME_HEIGHT = 240;

    private static final float LANDSCAPE_WIDTH_RATIO = 5f / 8;
    private static final float LANDSCAPE_HEIGHT_RATIO = 5f / 8;
    private static final int LANDSCAPE_MAX_FRAME_WIDTH = (int) (1920 * LANDSCAPE_WIDTH_RATIO); // = 5/8 * 1920
    private static final int LANDSCAPE_MAX_FRAME_HEIGHT = (int) (1080 * LANDSCAPE_HEIGHT_RATIO); // = 5/8 * 1080

    private static final float PORTRAIT_WIDTH_RATIO = 6f / 8;
    private static final float PORTRAIT_HEIGHT_RATIO = 3f / 8;
    private static final int PORTRAIT_MAX_FRAME_WIDTH = (int) (1080 * PORTRAIT_WIDTH_RATIO); // = 7/8 * 1080
    private static final int PORTRAIT_MAX_FRAME_HEIGHT = (int) (1920 * PORTRAIT_HEIGHT_RATIO); // = 3/8 * 1920

    private final int mDefaultMaskColor = getColor(R.color.viewfinder_mask);
    private final int mDefaultBorderColor = getColor(R.color.viewfinder_gray_border);
    private final int mBorderWidth = getResources().getDimensionPixelSize(R.dimen.viewfinder_border_width);
    private final int mDefaultBorderLineLength = getResources().getDimensionPixelSize(R.dimen.viewfinder_border_length);
    private final int mTextSize = getResources().getDimensionPixelSize(R.dimen.viewfinder_text_size);
    private final int mTextMarginBottom = getResources().getDimensionPixelSize(R.dimen.viewfinder_text_margin_bottom);

    protected Paint mFinderMaskPaint;
    protected Paint mBorderPaint;
    protected int mBorderLineLength;

    private StaticLayout mTextLayout;
    private TextPaint mTextPaint;
    private String mText;

    public ViewFinderView(Context context) {
        super(context);
        init();
    }

    public ViewFinderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {

        //finder mask paint
        mFinderMaskPaint = new Paint();
        mFinderMaskPaint.setColor(mDefaultMaskColor);

        //border paint
        mBorderPaint = new Paint();
        mBorderPaint.setColor(mDefaultBorderColor);
        mBorderPaint.setStyle(Paint.Style.STROKE);
        mBorderPaint.setStrokeWidth(mBorderWidth);

        mBorderLineLength = mDefaultBorderLineLength;

        //text layout
        mTextPaint = new TextPaint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setTextSize(mTextSize);
        mTextPaint.setTypeface(Typeface.createFromAsset(getContext().getAssets(), CalligraphyConfig.get().getFontPath()));
        mTextPaint.setColor(getColor(R.color.viewfinder_text_color));

        mText = getResources().getString(R.string.scanner_scan_imei);
    }

    public void setupViewFinder() {
        updateFramingRect();
        invalidate();
    }

    public Rect getFramingRect() {
        return mFramingRect;
    }

    @Override
    public void onDraw(Canvas canvas) {
        if (mFramingRect == null) {
            return;
        }

        drawViewFinderMask(canvas);
        drawViewFinderBorder(canvas);
        drawText(canvas);

    }

    private void drawText(Canvas _canvas) {
        _canvas.save();
        _canvas.translate(0, (mFramingRect.top - mTextLayout.getHeight() - mTextMarginBottom));
        mTextLayout.draw(_canvas);
        _canvas.restore();
    }

    public void drawViewFinderMask(Canvas canvas) {
        int width = canvas.getWidth();
        int height = canvas.getHeight();

        canvas.drawRect(0, 0, width, mFramingRect.top, mFinderMaskPaint);
        canvas.drawRect(0, mFramingRect.top, mFramingRect.left, mFramingRect.bottom, mFinderMaskPaint);
        canvas.drawRect(mFramingRect.right, mFramingRect.top, width, mFramingRect.bottom, mFinderMaskPaint);
        canvas.drawRect(0, mFramingRect.bottom, width, height, mFinderMaskPaint);
    }

    public void drawViewFinderBorder(Canvas canvas) {
        final float halfBorderWidth = mBorderWidth / 2f;

        canvas.drawLine(mFramingRect.left - halfBorderWidth, mFramingRect.top - mBorderWidth, mFramingRect.left - halfBorderWidth, mFramingRect.top + mBorderLineLength, mBorderPaint);
        canvas.drawLine(mFramingRect.left, mFramingRect.top - halfBorderWidth, mFramingRect.left + mBorderLineLength, mFramingRect.top - halfBorderWidth, mBorderPaint);

        canvas.drawLine(mFramingRect.left - halfBorderWidth, mFramingRect.bottom + mBorderWidth, mFramingRect.left - halfBorderWidth, mFramingRect.bottom - mBorderLineLength, mBorderPaint);
        canvas.drawLine(mFramingRect.left, mFramingRect.bottom + halfBorderWidth, mFramingRect.left + mBorderLineLength, mFramingRect.bottom + halfBorderWidth, mBorderPaint);

        canvas.drawLine(mFramingRect.right + halfBorderWidth, mFramingRect.top - mBorderWidth, mFramingRect.right + halfBorderWidth, mFramingRect.top + mBorderLineLength, mBorderPaint);
        canvas.drawLine(mFramingRect.right, mFramingRect.top - halfBorderWidth, mFramingRect.right - mBorderLineLength, mFramingRect.top - halfBorderWidth, mBorderPaint);

        canvas.drawLine(mFramingRect.right + halfBorderWidth, mFramingRect.bottom + mBorderWidth, mFramingRect.right + halfBorderWidth, mFramingRect.bottom - mBorderLineLength, mBorderPaint);
        canvas.drawLine(mFramingRect.right, mFramingRect.bottom + halfBorderWidth, mFramingRect.right - mBorderLineLength, mFramingRect.bottom + halfBorderWidth, mBorderPaint);
    }

    @Override
    protected void onSizeChanged(int xNew, int yNew, int xOld, int yOld) {
        updateFramingRect();
    }

    public synchronized void updateFramingRect() {
        Point viewResolution = new Point(getWidth(), getHeight());
        int width;
        int height;
        int orientation = DisplayUtils.getScreenOrientation(getContext());

        if (orientation != Configuration.ORIENTATION_PORTRAIT) {
            width = findDesiredDimensionInRange(LANDSCAPE_WIDTH_RATIO, viewResolution.x, MIN_FRAME_WIDTH, LANDSCAPE_MAX_FRAME_WIDTH);
            height = findDesiredDimensionInRange(LANDSCAPE_HEIGHT_RATIO, viewResolution.y, MIN_FRAME_HEIGHT, LANDSCAPE_MAX_FRAME_HEIGHT);
        } else {
            width = findDesiredDimensionInRange(PORTRAIT_WIDTH_RATIO, viewResolution.x, MIN_FRAME_WIDTH, PORTRAIT_MAX_FRAME_WIDTH);
            height = findDesiredDimensionInRange(PORTRAIT_HEIGHT_RATIO, viewResolution.y, MIN_FRAME_HEIGHT, PORTRAIT_MAX_FRAME_HEIGHT);
        }

        int leftOffset = (viewResolution.x - width) / 2;
        int topOffset = (viewResolution.y - height) / 2;
        mFramingRect = new Rect(leftOffset, topOffset, leftOffset + width, topOffset + height);

        mTextLayout = new StaticLayout(mText, mTextPaint, getWidth(), Layout.Alignment.ALIGN_CENTER, 1f, 0f, false);
    }

    private static int findDesiredDimensionInRange(float ratio, int resolution, int hardMin, int hardMax) {
        int dim = (int) (ratio * resolution);
        if (dim < hardMin) {
            return hardMin;
        }
        if (dim > hardMax) {
            return hardMax;
        }
        return dim;
    }

    private int getColor(int _colorId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return getContext().getColor(_colorId);
        } else {
            //noinspection deprecation
            return getContext().getResources().getColor(_colorId);
        }
    }
}
