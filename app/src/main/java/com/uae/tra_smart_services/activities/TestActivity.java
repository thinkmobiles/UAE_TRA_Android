package com.uae.tra_smart_services.activities;

import android.app.Activity;
import android.content.Context;
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

import com.uae.tra_smart_services.R;
import com.uae.tra_smart_services.customviews.CutterContainer;

/**
 * Created by and on 15.09.15.
 */

public class TestActivity extends Activity {

    ImageView background;
    CutterContainer ccContainer;
    FrameLayout alMainContainer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_cutter);
        alMainContainer = (FrameLayout) findViewById(R.id.alMainContainer);
        background = (ImageView) findViewById(R.id.cutted_image);
        ccContainer = (CutterContainer) findViewById(R.id.ccContainer);
    }

    @Override
    protected void onResume() {
        alMainContainer.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Bitmap bitmapMaster = BitmapFactory.decodeResource(getResources(), R.drawable.pic_test);
                Bitmap bitmapSecond = Bitmap.createBitmap(ccContainer.getWidth(), ccContainer.getHeight(), Bitmap.Config.ARGB_8888);
                Bitmap combinedBitmap = getCombinedBitmap(bitmapMaster, bitmapSecond);
                background.setImageBitmap(combinedBitmap);


                ViewTreeObserver obs = alMainContainer.getViewTreeObserver();
                obs.removeGlobalOnLayoutListener(this);
            }
        });
        super.onResume();
    }

    private Bitmap getCombinedBitmap(Bitmap _bitmapMaster, Bitmap _bitmapSecond){
        Bitmap combinedBitmap = Bitmap.createBitmap(_bitmapMaster.getWidth(), _bitmapMaster.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas combinedCanvas = new Canvas(combinedBitmap);

        combinedCanvas.drawBitmap(_bitmapMaster, 0, 0, null);

        combinedCanvas.drawBitmap(_bitmapSecond, 1, 20, null);

        return combinedBitmap;
    }
}
