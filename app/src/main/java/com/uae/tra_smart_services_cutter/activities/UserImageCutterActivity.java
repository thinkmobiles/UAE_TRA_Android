package com.uae.tra_smart_services_cutter.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.uae.tra_smart_services_cutter.R;
import com.uae.tra_smart_services_cutter.customviews.ImageCutterView;
import com.uae.tra_smart_services_cutter.util.IntentUtils;

import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by ak-buffalo on 15.09.15.
 */

public class UserImageCutterActivity extends Activity implements ImageCutterView.OnCutterChanged, View.OnClickListener {

    private ImageCutterView ccCutterView;
    private TextView doCropButton;
    private Bitmap originBitmap;
    private Path mCutterPath;
    private int mCutterOffsetX, mCutterOffsetY;
    private int mCutterSide;
    private Uri imageUri;
    private Uri cuttedImageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            initData();
        } catch (Throwable ex) {
            ex.printStackTrace();
            setResult(RESULT_CANCELED, new Intent());
            finish();
        }
    }

    protected void initData() {
        imageUri = Uri.parse(getIntent().getStringExtra(IntentUtils.STRING_IMAGE_URI));
        cuttedImageUri = Uri.parse(getIntent().getStringExtra(IntentUtils.STRING_CUTTED_IMAGE_URI));
        Glide.with(this)
            .load(imageUri)
            .into(new SimpleTarget<GlideDrawable>() {
                @Override
                public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                    originBitmap = doScaleBitmap(convertDrawableToBitmap(resource));
                    setContentView(R.layout.layout_cutter);
                    initViews();
                    initListeners();
                }
            });
    }

    private static Bitmap convertDrawableToBitmap(Drawable _source){
        Bitmap bitmap = Bitmap.createBitmap(_source.getIntrinsicWidth(), _source.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        _source.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        _source.draw(canvas);
        return bitmap;
    }

    protected void initViews(){
        ccCutterView = (ImageCutterView) findViewById(R.id.ccContainer);
        ccCutterView.setOriginalImageBitmap(originBitmap);
        doCropButton = (TextView) findViewById(R.id.doCrop);
    }

    protected void initListeners() {
        ccCutterView.setCutterChangeHandler(this);
        doCropButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View _view) {
        if(_view.getId() == R.id.doCrop){
            doCropImage(new OnImageProcess() {
                @Override
                public void onSaved() {
                    setResult(RESULT_OK, new Intent());
                    finish();
                }
                @Override
                public void onFailed() {
                    setResult(RESULT_CANCELED, new Intent());
                    finish();
                }
            });
        }
    }

    private Bitmap doScaleBitmap(Bitmap _originalBitmap) {
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        return Bitmap.createScaledBitmap(_originalBitmap, metrics.widthPixels, metrics.heightPixels, true);
    }

    private void doCropImage(OnImageProcess _handler) {
        Bitmap resultingImage = Bitmap.createBitmap(mCutterSide, mCutterSide, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(resultingImage);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        canvas.drawPath(mCutterPath, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(originBitmap, -mCutterOffsetX, -mCutterOffsetY, paint);

        reWriteCuttedImage(resultingImage, _handler);
    }

    private void reWriteCuttedImage(Bitmap _resultBitmap, OnImageProcess _handler){
        String sourceFilename = cuttedImageUri.getPath();
        FileOutputStream fOut = null;
        try {
            fOut = new FileOutputStream(sourceFilename, false);
            _resultBitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
            fOut.flush();
            fOut.close();
            _resultBitmap.recycle();
            System.gc();
            _handler.onSaved();
        } catch (IOException e) {
            _handler.onFailed();
        } finally {
            try {
                if (fOut != null) fOut.close();
            } catch (IOException e) {

            }
        }
    }

    @Override
    public void onCutterChanged(Path _cropperPath, int _offsetX, int _offsetY, int _cutterSide) {
        mCutterPath = _cropperPath;
        mCutterOffsetX = _offsetX;
        mCutterOffsetY = _offsetY;
        mCutterSide = _cutterSide;
    }

    interface OnImageProcess{
        void onSaved();
        void onFailed();
    }
}