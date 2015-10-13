package com.uae.tra_smart_services.fragment;

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
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.uae.tra_smart_services.R;
import com.uae.tra_smart_services.customviews.CutterContainer;
import com.uae.tra_smart_services.customviews.HexagonView;
import com.uae.tra_smart_services.fragment.base.BaseFragment;
import com.uae.tra_smart_services.global.C;

/**
 * Created by AndreyKorneychuk on 10/13/2015.
 */
public class UserAvatarCropperFragment extends BaseFragment
        implements View.OnClickListener, CutterContainer.OnCutterChanged, View.OnTouchListener {

    private ImageView background;
    private CutterContainer ccContainer;
    private FrameLayout alMainContainer;
    private TextView doCrop;
    private Bitmap originBitmap;
    private float mOffsetX, mOffsetY;

    public static UserAvatarCropperFragment newInstance() {
        return new UserAvatarCropperFragment();
    }

    @Override
    protected int getTitle() { return 0; }

    @Override
    protected int getLayoutResource() { return R.layout.layout_cutter; }

    @Override
    protected void initViews() {
        super.initViews();
        alMainContainer = findView(R.id.alMainContainer);
        background = findView(R.id.cutted_image);
        ccContainer = findView(R.id.ccContainer);
        doCrop = findView(R.id.doCrop);
        alMainContainer.setBackground(C.TEMP_USER_IMG);
        originBitmap = convertAnotherDrawableToBitmap(C.TEMP_USER_IMG);
    }

    @Override
    protected void initListeners() {
        super.initListeners();
        alMainContainer.setOnTouchListener(this);
        ccContainer.setAreaChangeHandler(this);
        doCrop.setOnClickListener(this);
    }

    @Override
    public void onClick(View _view) {
        if(_view.getId() == R.id.doCrop){
            doCropImage(ccContainer.getCutterPath(), mOffsetX, mOffsetY);
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) { return true; }

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

    private void doCropImage(Path _cropperPath, float _offsetX, float _offsetY){
        int width = (int) (ccContainer.getWidth());
        int heght = (int) (ccContainer.getHeight());

        Bitmap bitmap1 = Bitmap.createBitmap(width, heght, Bitmap.Config.ARGB_8888);
        Bitmap bitmap2 = originBitmap;

        Bitmap resultingImage = Bitmap.createBitmap(width, heght, bitmap1.getConfig());

        Canvas canvas = new Canvas(resultingImage);

        Paint paint = new Paint();
        paint.setAntiAlias(true);

        canvas.drawPath(_cropperPath, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap2, (int) (-_offsetX * 1.5), (int) (-_offsetY * 1.5), paint);

        C.TEMP_USER_IMG = new BitmapDrawable(resultingImage);

        Intent intent = new Intent();
        getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, intent);
        getFragmentManager().popBackStack();
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

    private Bitmap convertAnotherDrawableToBitmap(Drawable _res){
        Bitmap mutableBitmap = Bitmap.createBitmap(_res.getIntrinsicWidth(), _res.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(mutableBitmap);
        _res.setBounds(0, 0, _res.getIntrinsicWidth(), _res.getIntrinsicHeight());
        _res.draw(canvas);
        return mutableBitmap;
    }
}