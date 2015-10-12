package com.uae.tra_smart_services.activities;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
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
import com.uae.tra_smart_services.customviews.HexagonView;

import java.io.ByteArrayOutputStream;

/**
 * Created by and on 15.09.15.
 */

public class TestActivity extends Activity implements CutterContainer.OnCutterChanged, View.OnClickListener{

    ImageView background;
    CutterContainer ccContainer;
    FrameLayout alMainContainer;
    TextView doCrop;
    HexagonView hvIcon_LIIHA;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.layout_test);

        setContentView(R.layout.layout_cutter);
        alMainContainer = (FrameLayout) findViewById(R.id.alMainContainer);
        background = (ImageView) findViewById(R.id.cutted_image);
        ccContainer = (CutterContainer) findViewById(R.id.ccContainer);
        ccContainer.setAreaChangeHandler(this);
        doCrop = (TextView) findViewById(R.id.doCrop);
        doCrop.setOnClickListener(this);
        hvIcon_LIIHA = (HexagonView) findViewById(R.id.hvIcon_LIIHA);
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
        mOffsetX = _offsetX;
        mOffsetY = _offsetY;
        background.setImageBitmap(bitmap);
    }
    float mOffsetX, mOffsetY;
    @Override
    public void onClick(View _view) {
        if(_view.getId() == R.id.doCrop){
            doCropImage(ccContainer.getCutterPath(), mOffsetX, mOffsetY);
        }
    }

    private void doCropImage(Path _cropperPath, float _offsetX, float _offsetY){
//        reCalculateCutterPath(_cropperPath, _offsetX, _offsetY);






//        int mainContainerWidth = alMainContainer.getWidth();
//        int mainContainerHeight = alMainContainer.getHeight();
//        int cutterContainerWidth = ccContainer.getWidth();
//        int cutterContainerHeiht = ccContainer.getHeight();
//        int cutterWidth = (int) (ccContainer.getCutter().getWidth());
//        int cutterHeight = (int) (ccContainer.getCutter().getHeight());
//        Bitmap original = BitmapFactory.decodeResource(getResources(), R.drawable.pic_test);
//        Bitmap result = Bitmap.createBitmap(cutterContainerWidth, cutterContainerHeiht, Bitmap.Config.ARGB_8888);
//        Canvas mCanvas = new Canvas(result);
//        Bitmap result_2 = Bitmap.createBitmap(cutterContainerWidth, cutterContainerHeiht, Bitmap.Config.ARGB_8888);
//        Canvas mCanvas_2 = new Canvas(result_2);
//
//        Paint mFillArePaint = new Paint();
//        mFillArePaint.setAntiAlias(true);
//        mFillArePaint.setColor(Color.BLACK);
//        mFillArePaint.setStyle(Paint.Style.FILL_AND_STROKE);
//        mCanvas_2.drawPath(_cropperPath, mFillArePaint);
//
//        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
//        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
//        mCanvas.drawBitmap(original, 0, 0, null);
//        mCanvas.drawBitmap(result_2, 0, 0, paint);
//        paint.setXfermode(null);
//
//        background.setVisibility(View.INVISIBLE);
//        ccContainer.setVisibility(View.INVISIBLE);
//        alMainContainer.setBackgroundColor(Color.GRAY);
//
//        hvIcon_LIIHA.setVisibility(View.VISIBLE);
//        hvIcon_LIIHA.setScaleType(HexagonView.CENTER_CROP);
//        hvIcon_LIIHA.setHexagonSrcDrawable(new BitmapDrawable(result));


//        background.setImageBitmap(result);
//        background.setTranslationX(100);
//        background.setTranslationY(200);
//        background.setScaleType(ImageView.ScaleType.CENTER);
//        background.setBackgroundColor(Color.GREEN);
//        alMainContainer.setBackgroundColor(Color.RED);


//        Intent intent = new Intent(this, ResultActivity.class);
//        intent.putExtra("asdasd", result);
//        startActivity(intent);


//        Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.f1);



//        ByteArrayOutputStream stream = new ByteArrayOutputStream();
//        result.compress(Bitmap.CompressFormat.PNG, 100, stream);
//        byte[] food = stream.toByteArray();
//        Intent intent = new Intent(this, ResultActivity.class);
//        intent.putExtra("picture", food);
//        startActivity(intent);


        int width = (int) (ccContainer.getWidth() * 0.9);
        int heght = (int) (ccContainer.getHeight() * 0.9);

        Bitmap bitmap1=Bitmap.createBitmap(width, heght, Bitmap.Config.ARGB_8888);
        Bitmap bitmap2=BitmapFactory.decodeResource(getResources(), R.drawable.pic_test);

        Bitmap resultingImage=Bitmap.createBitmap(width, heght, bitmap1.getConfig());

        Canvas canvas = new Canvas(resultingImage);

        Paint paint = new Paint();
        paint.setAntiAlias(true);

//        Path mPath=new Path();
        PointF[] mPoints = new PointF[6];
        double section = 2.0 * Math.PI / 6;

        int mCenterWidth = width / 2;
        int mCenterHeight = heght / 2;

        int mHexagonSide = width / 2;

        _cropperPath.reset();
        mPoints[0] = new PointF((float) (mCenterWidth - mHexagonSide * Math.sin(0)), (float) (mCenterHeight - mHexagonSide * Math.cos(0)));
        _cropperPath.moveTo(mPoints[0].x, mPoints[0].y);
        for (int i = 1; i < 6; i++) {
            mPoints[i] = new PointF((float) (mCenterWidth - mHexagonSide * Math.sin(section * -i)), (float) (mCenterHeight - mHexagonSide * Math.cos(section * -i)));
            _cropperPath.lineTo(mPoints[i].x, mPoints[i].y);
        }
        _cropperPath.close();

        canvas.drawPath(_cropperPath, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap2, -_offsetX, -_offsetY, paint);

        HexagonView hexView = (HexagonView)findViewById(R.id.hvIcon_LIIHA);
        hexView.setScaleType(HexagonView.CENTER_CROP);
        hexView.setHexagonSrcDrawable(new BitmapDrawable(resultingImage));
    }
}