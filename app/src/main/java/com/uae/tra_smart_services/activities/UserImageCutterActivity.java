package com.uae.tra_smart_services.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.uae.tra_smart_services.R;
import com.uae.tra_smart_services.customviews.ImageCutterView;
import com.uae.tra_smart_services.global.C;

import java.io.IOException;
import java.net.URI;

/**
 * Created by ak-buffalo on 15.09.15.
 */

public class UserImageCutterActivity extends Activity implements ImageCutterView.OnCutterChanged, View.OnClickListener{

    private ImageCutterView ccCutterView;
    private TextView doCropButton;
    private Bitmap originBitmap;
    private Path mCutterPath;
    private int mCutterOffsetX, mCutterOffsetY;
    private int mCutterSide;
    Uri imageUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_cutter);
        imageUri = (Uri) getIntent().getSerializableExtra("fileUri");
        imageUri = getIntent().getData();
        imageUri = (Uri) getIntent().getExtras().get(Intent.EXTRA_STREAM);

        try {
            initData();
            initViews();
            initListeners();
        } catch (Exception e) {
            setResult(RESULT_CANCELED, new Intent());
            finish();
        }
    }

    protected void initData() throws IOException{
        originBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
        originBitmap = doScaleBitmap(originBitmap);
    }

    protected void initViews() {
        ccCutterView = (ImageCutterView) findViewById(R.id.ccContainer);
        ccCutterView.setOriginalImageBitmap(originBitmap, getWindowManager());
        doCropButton = (TextView) findViewById(R.id.doCrop);
    }

    private Bitmap doScaleBitmap(Bitmap _originalBitmap) {
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        return Bitmap.createScaledBitmap(_originalBitmap, metrics.widthPixels, metrics.heightPixels, true);
    }

    protected void initListeners() {
        ccCutterView.setCutterChangeHandler(this);
        doCropButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View _view) {
        if(_view.getId() == R.id.doCrop){
            doCropImage();
        }
    }

    private void doCropImage() {
        Bitmap resultingImage = Bitmap.createBitmap(mCutterSide, mCutterSide, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(resultingImage);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        canvas.drawPath(mCutterPath, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(originBitmap, -mCutterOffsetX, -mCutterOffsetY, paint);

        setResult(RESULT_OK, new Intent());
        finish();
    }

    private Bitmap convertAnotherDrawableToBitmap(Drawable _res){
        Bitmap mutableBitmap = Bitmap.createBitmap(_res.getIntrinsicWidth(), _res.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(mutableBitmap);
        _res.setBounds(0, 0, _res.getIntrinsicWidth(), _res.getIntrinsicHeight());
        _res.draw(canvas);
        return mutableBitmap;
    }

    @Override
    public void onCutterChanged(Path _cropperPath, int _offsetX, int _offsetY, int _cutterSide) {
        mCutterPath = _cropperPath;
        mCutterOffsetX = _offsetX;
        mCutterOffsetY = _offsetY;
        mCutterSide = _cutterSide;
    }
}