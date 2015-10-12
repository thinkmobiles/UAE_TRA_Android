package com.uae.tra_smart_services.activities;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.PathShape;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.widget.ImageView;

import com.uae.tra_smart_services.R;

/**
 * Created by and on 15.09.15.
 */

public class TestActivity extends Activity {

    ImageView background;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_cutter);
        background = (ImageView) findViewById(R.id.cutted_image);


        Bitmap bitmapMaster = BitmapFactory.decodeResource(getResources(), R.drawable.pic_test);
        Bitmap bmOut = Bitmap.createBitmap(bitmapMaster.getWidth(), bitmapMaster.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bmOut);
        canvas.drawBitmap(bitmapMaster, 0, 0, new Paint());

    }

    private void loadGrayBitmap(Bitmap bitmapMaster) {
//        if (bitmapMaster != null) {
            Bitmap mImage = BitmapFactory.decodeResource(getResources(), R.drawable.pic_test);
//            Bitmap mMask;  // png mask with transparency
//
//
//            final Paint imagePaint;
//

//
//            imagePaint = new Paint();
//            imagePaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OVER));
            int w = bitmapMaster.getWidth();
            int h = bitmapMaster.getHeight();
//
//            RectF rectF = new RectF(w/4, h/4, w*3/4, h*3/4);
//            float blurRadius = 100.0f;
//
//
//
////            Paint blurPaintOuter = new Paint();
////            blurPaintOuter.setColor(Color.DKGRAY);
////            blurPaintOuter.setMaskFilter(new BlurMaskFilter(blurRadius, BlurMaskFilter.Blur.OUTER));
////            canvasResult.drawBitmap(bitmapMaster, 0, 0, blurPaintOuter);
////
////            Paint blurPaintInner = new Paint();
////            blurPaintInner.setColor(Color.DKGRAY);
////            blurPaintInner.setMaskFilter(new BlurMaskFilter(blurRadius, BlurMaskFilter.Blur.OUTER));
////            canvasResult.drawRect(rectF, blurPaintInner);
//
            Bitmap bitmapResult = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
            Canvas canvasResult = new Canvas(bitmapResult);

            final Paint maskPaint = new Paint();
            maskPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DARKEN));
            maskPaint.setColor(Color.TRANSPARENT);
            maskPaint.setStyle(Paint.Style.FILL);

            canvasResult.drawBitmap(mImage, 0, 0, null);
            canvasResult.drawCircle(w, h, 150, maskPaint);
            background.setImageBitmap(bitmapResult);
//
//        }

//        int w = bitmapMaster.getWidth();
//        int h = bitmapMaster.getHeight();
//
//        Canvas canvas = new Canvas();
//        Bitmap mainImage = BitmapFactory.decodeResource(getResources(), R.drawable.pic_test);
//        Bitmap mask = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
//        Bitmap result = Bitmap.createBitmap(mainImage.getWidth(), mainImage.getHeight(), Bitmap.Config.ARGB_8888);
//
//        canvas.setBitmap(result);
//        Paint paint = new Paint();
//        paint.setFilterBitmap(false);
//
//        canvas.drawBitmap(mainImage, 0, 0, paint);
//        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
//        canvas.drawBitmap(mask, 0, 0, paint);
//        paint.setXfermode(null);

//        background.setImageBitmap(result);
//        background.invalidate();
    }

    public void highlightImage(ImageView _imageView, @IdRes int _idFrom) {
        Bitmap bitmapMaster = BitmapFactory.decodeResource(getResources(), _idFrom);
        // create new bitmap, which will be painted and becomes result image
        Bitmap bmOut = Bitmap.createBitmap(bitmapMaster.getWidth(), bitmapMaster.getHeight(), Bitmap.Config.ARGB_8888);
        // setup canvas for painting
        Canvas canvas = new Canvas(bmOut);
        // setup default color
        canvas.drawColor(0, PorterDuff.Mode.CLEAR);
        // create a blur paint for capturing alpha
        Paint ptBlur = new Paint();
        ptBlur.setMaskFilter(new BlurMaskFilter(15, BlurMaskFilter.Blur.NORMAL));
        int[] offsetXY = new int[2];
        // capture alpha into a bitmap
        Bitmap bmAlpha = bitmapMaster.extractAlpha(ptBlur, offsetXY);
        // create a color paint
        Paint ptAlphaColor = new Paint();
        ptAlphaColor.setColor(0xFF333333);
        // paint color for captured alpha region (bitmap)
        canvas.drawBitmap(bmAlpha, offsetXY[0], offsetXY[1], ptAlphaColor);
        // free memory
        bmAlpha.recycle();

        // paint the image source
        canvas.drawBitmap(bitmapMaster, 0, 0, null);

        // return out final image
        _imageView.setImageBitmap(bmOut);
    }


    private void asdasdasd(){

        Paint mMaskPaint;
        mMaskPaint = new Paint();
        mMaskPaint.setColor(0xFF0000);
        mMaskPaint.setAlpha(128);
        Bitmap
        mMaskBitmap = Bitmap.createBitmap(128, 128, Bitmap.Config.ARGB_8888);
        mMaskBitmap.eraseColor(Color.TRANSPARENT);
        Canvas
        mMaskCanvas = new Canvas(mMaskBitmap);
        mMaskCanvas.drawCircle(64, 64, 10, mMaskPaint);

        background.setImageBitmap(mMaskBitmap);
    }
}
