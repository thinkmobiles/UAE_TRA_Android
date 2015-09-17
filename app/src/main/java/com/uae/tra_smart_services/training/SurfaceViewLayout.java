package com.uae.tra_smart_services.training;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.uae.tra_smart_services.R;

/**
 * Created by and on 15.09.15.
 */

public class SurfaceViewLayout extends SurfaceView implements Runnable {

    private Thread worker;
    private boolean canDraw;

    private Bitmap backgroundBitmap;
    private Canvas canvas;
    private SurfaceHolder surfaceHolder;

    public SurfaceViewLayout(Context context) {
        super(context);
        surfaceHolder = getHolder();
        backgroundBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.res_bg_2);
    }

    @Override
    public void run() {
        while (canDraw){
            if (!surfaceHolder.getSurface().isValid()){
                continue;
            }
            canvas = surfaceHolder.lockCanvas();
            canvas.drawBitmap(backgroundBitmap, 0, 0, null);
            surfaceHolder.unlockCanvasAndPost(canvas);
            canDraw = false;
        }
    }

    public void pause(){
        canDraw = false;

        while (true){
            try {
                worker.join();
                break;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        worker = null;
    }

    public void resume(){
        canDraw = true;
        worker = new Thread(this);
        worker.start();
    }
}
