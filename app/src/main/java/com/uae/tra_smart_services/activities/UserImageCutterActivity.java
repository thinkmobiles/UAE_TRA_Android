package com.uae.tra_smart_services.activities;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.uae.tra_smart_services.R;
import com.uae.tra_smart_services.customviews.ImageCutterView;
import com.uae.tra_smart_services.global.C;

/**
 * Created by and on 15.09.15.
 */

public class UserImageCutterActivity extends Activity implements ImageCutterView.OnCutterChanged, View.OnClickListener{

    private ImageCutterView ccCutterView;
    private TextView doCropButton;
    private Bitmap originBitmap;
    private Path mCutterPath;
    private int mCutterOffsetX, mCutterOffsetY;
    private int mCutterSide;

    ImageView cutted_image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_cutter);
        initData();
        initViews();
        initListeners();

        cutted_image = (ImageView) findViewById(R.id.cutted_image);
    }

    protected void initData(){
        C.TEMP_USER_IMG = new BitmapDrawable(BitmapFactory.decodeResource(getResources(), R.drawable.pic_test));
        originBitmap = convertAnotherDrawableToBitmap(C.TEMP_USER_IMG);
    }

    protected void initViews() {
        ccCutterView = (ImageCutterView) findViewById(R.id.ccContainer);
        ccCutterView.setBackgroundDrawable(new BitmapDrawable(originBitmap));
        doCropButton = (TextView) findViewById(R.id.doCrop);
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
        Bitmap resultingImage = Bitmap.createBitmap(ccCutterView.getMeasuredWidth(), ccCutterView.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(resultingImage);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        canvas.drawPath(mCutterPath, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(originBitmap, /*(int) (-mCutterOffsetX)*/0, /*(int) (-mCutterOffsetY)*/0, paint);

//        C.TEMP_USER_IMG = new BitmapDrawable(resultingImage);
//        finishActivity(10001);
        doCropButton.setVisibility(View.INVISIBLE);
        ccCutterView.setVisibility(View.INVISIBLE);
        cutted_image.setVisibility(View.VISIBLE);
        cutted_image.setImageBitmap(resultingImage);
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