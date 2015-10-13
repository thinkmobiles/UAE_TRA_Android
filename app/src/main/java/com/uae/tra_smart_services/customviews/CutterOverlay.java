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

/**
 * Created by and on 13.10.15.
 */

public class CutterOverlay extends View implements CutterContainer.OnCutterChanged{

    private float mOffsetX, mOffsetY;
    private Bitmap resultBitmap;

    public CutterOverlay(Context context) { this(context, null); }

    public CutterOverlay(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void onContainerAreaChanged(int _width, int _height, float _offsetX, float _offsetY) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        Bitmap bitmapOverlay = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.RGB_565);
        bitmapOverlay.setHasAlpha(true);
        bitmapOverlay = makeTransparent(bitmapOverlay, 170);

        Bitmap bitmap;
        if (bitmapOverlay.isMutable()) {
            bitmap = bitmapOverlay;
        } else {
            bitmap = bitmapOverlay.copy(Bitmap.Config.ARGB_8888, true);
            bitmapOverlay.recycle();
        }
        bitmap.setHasAlpha(true);

        Canvas canvas = new Canvas(bitmap);
        Bitmap mask = Bitmap.createBitmap(_width, _height, Bitmap.Config.RGB_565);

        Paint paint = new Paint();
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.XOR));

        canvas.drawBitmap(mask, _offsetX, _offsetY, paint);
        mask.recycle();
        mOffsetX = _offsetX;
        mOffsetY = _offsetY;
        resultBitmap = bitmap;
        invalidate();
    }

    private Bitmap makeTransparent(Bitmap src, int value) {
        int width = src.getWidth();
        int height = src.getHeight();
        Bitmap transBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(transBitmap);
        canvas.drawARGB(0, 0, 0, 0);
        final Paint paint = new Paint();
        paint.setAlpha(value);
        canvas.drawBitmap(src, 0, 0, paint);
        return transBitmap;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(resultBitmap, 0, 0, new Paint());
    }
}