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
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.uae.tra_smart_services.R;
import com.uae.tra_smart_services.customviews.ImageCutterView;
import com.uae.tra_smart_services.customviews.CutterOverlay;
import com.uae.tra_smart_services.global.C;

/**
 * Created by and on 15.09.15.
 */

public class UserImageCutterActivity extends Activity implements /*CutterContainer.OnCutterChanged,*/ View.OnClickListener{

    private ImageView background;
    private ImageCutterView ccContainer;
    private FrameLayout alMainContainer;
    private TextView doCrop;
    private Bitmap originBitmap;
    private CutterOverlay overlay;
    private float mOffsetX, mOffsetY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_cutter);
        initViews();
        initListeners();
    }

    protected void initViews() {
        alMainContainer = (FrameLayout) findViewById(R.id.alMainContainer);
        background = (ImageView) findViewById(R.id.cutted_image);
        ccContainer = (ImageCutterView) findViewById(R.id.ccContainer);
        doCrop = (TextView) findViewById(R.id.doCrop);
        C.TEMP_USER_IMG = new BitmapDrawable(BitmapFactory.decodeResource(getResources(), R.drawable.pic_test));
        alMainContainer.setBackground(C.TEMP_USER_IMG);
        originBitmap = convertAnotherDrawableToBitmap(C.TEMP_USER_IMG);
    }

    protected void initListeners() {
//        ccContainer.setAreaChangeHandler(this);
        doCrop.setOnClickListener(this);
    }

    @Override
    public void onClick(View _view) {
        if(_view.getId() == R.id.doCrop){
            doCropImage(ccContainer.getCutterPath(), mOffsetX, mOffsetY);
        }
    }

    private void doCropImage(Path _cropperPath, float _offsetX, float _offsetY) {
        int width = (int) (ccContainer.getWidth());
        int height = (int) (ccContainer.getHeight());

        Bitmap bitmap1 = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Bitmap bitmap2 = originBitmap;

        Bitmap resultingImage = Bitmap.createBitmap(width, height, bitmap1.getConfig());

        Canvas canvas = new Canvas(resultingImage);

        Paint paint = new Paint();
        paint.setAntiAlias(true);

        canvas.drawPath(_cropperPath, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap2, (int) (-_offsetX * 1.5), (int) (-_offsetY * 1.5), paint);

        C.TEMP_USER_IMG = new BitmapDrawable(resultingImage);

        finishActivity(10001);
    }

    private Bitmap convertAnotherDrawableToBitmap(Drawable _res){
        Bitmap mutableBitmap = Bitmap.createBitmap(_res.getIntrinsicWidth(), _res.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(mutableBitmap);
        _res.setBounds(0, 0, _res.getIntrinsicWidth(), _res.getIntrinsicHeight());
        _res.draw(canvas);
        return mutableBitmap;
}
}