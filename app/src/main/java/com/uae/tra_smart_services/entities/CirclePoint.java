package com.uae.tra_smart_services.entities;

import android.graphics.Paint;

/**
 * Created by Andrey Korneychuk on 29.07.15.
 */
public class CirclePoint {
    public int dX;
    public String colorThema;
    public Paint paint;

    public final void setPaintStyle(final Paint.Style _paintStyle) {
        if (paint == null) {
            paint = new Paint();
        }
        paint.setStyle(_paintStyle);
    }

}
