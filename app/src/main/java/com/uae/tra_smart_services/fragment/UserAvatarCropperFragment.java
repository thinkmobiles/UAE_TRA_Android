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
public class UserAvatarCropperFragment extends BaseFragment implements View.OnClickListener, CutterContainer.OnCutterChanged {
    public static UserAvatarCropperFragment newInstance() {
        
        Bundle args = new Bundle();
        
        UserAvatarCropperFragment fragment = new UserAvatarCropperFragment();
        fragment.setArguments(args);
        return fragment;
    }
    
    
    @Override
    protected int getTitle() {
        return 0;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.layout_cutter;
    }

    ImageView background;
    CutterContainer ccContainer;
    FrameLayout alMainContainer;
    TextView doCrop;
    HexagonView hvIcon_LIIHA;
    Bitmap originBitmap;

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
        int width = (int) (ccContainer.getWidth());
        int heght = (int) (ccContainer.getHeight());

        Bitmap bitmap1 = Bitmap.createBitmap(width, heght, Bitmap.Config.ARGB_8888);
        Bitmap bitmap2 = originBitmap;

        Bitmap resultingImage=Bitmap.createBitmap(width, heght, bitmap1.getConfig());

        Canvas canvas = new Canvas(resultingImage);

        Paint paint = new Paint();
        paint.setAntiAlias(true);

//        Path mPath=new Path();
        /*PointF[] mPoints = new PointF[6];
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
        _cropperPath.close();*/

        canvas.drawPath(_cropperPath, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap2, (int) (-_offsetX * 1.5), (int) (-_offsetY * 1.5), paint);

        C.TEMP_USER_IMG = new BitmapDrawable(resultingImage);

        Intent intent = new Intent();
        getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, intent);
        getFragmentManager().popBackStack();
    }

    @Override
    protected void initViews() {
        super.initViews();

        alMainContainer = (FrameLayout) findView(R.id.alMainContainer);
        background = (ImageView) findView(R.id.cutted_image);
        ccContainer = (CutterContainer) findView(R.id.ccContainer);
        ccContainer.setAreaChangeHandler(this);
        doCrop = (TextView) findView(R.id.doCrop);
        doCrop.setOnClickListener(this);
        hvIcon_LIIHA = (HexagonView) findView(R.id.hvIcon_LIIHA);
        alMainContainer.setBackground(C.TEMP_USER_IMG);
        originBitmap = convertAnotherDrawableToBitmap(C.TEMP_USER_IMG);
    }


    private Bitmap convertAnotherDrawableToBitmap(Drawable _res){
        Bitmap mutableBitmap = Bitmap.createBitmap(_res.getIntrinsicWidth(), _res.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(mutableBitmap);
        _res.setBounds(0, 0, _res.getIntrinsicWidth(), _res.getIntrinsicHeight());
        _res.draw(canvas);
        return mutableBitmap;
    }
}
