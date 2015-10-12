package com.uae.tra_smart_services.activities;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
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
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.AbsoluteLayout;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.uae.tra_smart_services.R;
import com.uae.tra_smart_services.customviews.CutterContainer;

/**
 * Created by and on 15.09.15.
 */

public class TestActivity extends Activity implements CutterContainer.OnCutterChanged, View.OnClickListener{

    ImageView background;
    CutterContainer ccContainer;
    FrameLayout alMainContainer;
    TextView doCrop;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_cutter);
        alMainContainer = (FrameLayout) findViewById(R.id.alMainContainer);
        background = (ImageView) findViewById(R.id.cutted_image);
        ccContainer = (CutterContainer) findViewById(R.id.ccContainer);
        ccContainer.setAreaChangeHandler(this);
        doCrop = (TextView) findViewById(R.id.doCrop);
        doCrop.setOnClickListener(this);
    }

    public Bitmap makeTransparent(Bitmap src, int value) {
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
    public void onContainerAreaChanged(int _width, int _height, float _offsetX, float _offsetY) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        Bitmap bitmapOverlay = Bitmap.createBitmap(alMainContainer.getWidth(), alMainContainer.getHeight(), Bitmap.Config.RGB_565);
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

        background.setImageBitmap(bitmap);
    }

    @Override
    public void onClick(View _view) {
        if(_view.getId() == R.id.doCrop){
            doCropImage(ccContainer.getCutterPath());
        }
    }

    private void doCropImage(Path _cropperPath){
        Bitmap obmp = BitmapFactory.decodeResource(getResources(), R.drawable.pic_test);
        Bitmap resultImg = Bitmap.createBitmap(obmp.getWidth(), obmp.getHeight(), Bitmap.Config.ARGB_8888);
        Bitmap maskImg = Bitmap.createBitmap(obmp.getWidth(), obmp.getHeight(), Bitmap.Config.ARGB_8888);

        Canvas mCanvas = new Canvas(resultImg);
        Canvas maskCanvas = new Canvas(maskImg);

        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);;
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));

//        Path path = new Path();
//        path.moveTo(view.mx,view.my);
//        path.lineTo(view.x1,view.y1);
//        path.lineTo(view.x2,view.y2 );
//        path.lineTo(view.x3,view.y3);
//        path.lineTo(view.x4,view.y4);
//        path.close();

        maskCanvas.drawPath(_cropperPath, paint);
        mCanvas.drawBitmap(obmp, 0, 0, null);
        mCanvas.drawBitmap(maskImg, 0, 0, paint);

        background.setImageBitmap(resultImg);
    }
}
