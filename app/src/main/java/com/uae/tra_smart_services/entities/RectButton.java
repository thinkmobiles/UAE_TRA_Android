package com.uae.tra_smart_services.entities;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.View;

/**
 * Created by Andrey Korneychuk on 29.07.15.
 */
public class RectButton {
    public boolean isSelected;
    public int dX;
    public int dY;
    public String colorThema;
    public Paint paint;
    public RectF rect = new RectF();
    public RectF shadow;
    public Paint shadowpaint;

    public final void setPaintStyle(final Paint.Style _paintStyle) {
        if (paint == null) {
            paint = new Paint();
        }
        paint.setStyle(_paintStyle);
    }
    
    public void drawShadow(Canvas _canvas){
        shadowpaint = new Paint();
        shadowpaint.setStrokeWidth(5f);
        shadowpaint.setColor(Color.LTGRAY);
        shadowpaint.setStyle(Paint.Style.STROKE);
        shadow = new RectF(rect.left-3, rect.top-3, rect.right+3, rect.bottom+3);
        _canvas.drawRect(shadow, shadowpaint);
    }
}
