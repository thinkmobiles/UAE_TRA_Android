package com.uae.tra_smart_services.customviews;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.View;

import com.uae.tra_smart_services.R;

/**
 * Created by AndreyKorneychuk on 9/26/2015.
 */
public class CustomViewAnimation extends View {
    private boolean CanDraw = false;
    private SurfaceHolder surfaceHolder;
    private Bitmap picture;
    private Matrix matrix;
    private long prevTime;
    private Thread drawThread;

    public CustomViewAnimation(Context context) { this(context, null); }

    public CustomViewAnimation(Context context, AttributeSet attrs) {
        super(context, attrs);
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        setWillNotDraw(false);

        picture = BitmapFactory.decodeResource(getResources(), R.drawable.ic_complain);

        // формируем матрицу преобразований для картинки
        matrix = new Matrix();
        matrix.postScale(3.0f, 3.0f);
        matrix.postTranslate(100.0f, 100.0f);

        // сохраняем текущее время
        prevTime = System.currentTimeMillis();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        final int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        final float widthSize = MeasureSpec.getSize(widthMeasureSpec);
        final float myWidth = Math.round(800);
        final float width;

        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize;
        } else if (widthMode == MeasureSpec.AT_MOST) {
            width = Math.min(myWidth, widthSize);
        } else {
            width = myWidth;
        }
        setMeasuredDimension((int) width, (int) Math.round(800));
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // получаем текущее время и вычисляем разницу с предыдущим
        // сохраненным моментом времени
        long now = System.currentTimeMillis();
        long elapsedTime = now - prevTime;
        if (elapsedTime > 30){
            // если прошло больше 30 миллисекунд - сохраним текущее время
            // и повернем картинку на 2 градуса.
            // точка вращения - центр картинки
            prevTime = now;
            matrix.preRotate(2.0f, picture.getWidth() / 2, picture.getHeight() / 2);
        }

        canvas.drawColor(Color.BLACK);
        canvas.drawBitmap(picture, matrix, null);

        invalidate();
    }
}
