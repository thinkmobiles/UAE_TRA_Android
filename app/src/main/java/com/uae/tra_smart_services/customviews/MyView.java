package com.uae.tra_smart_services.customviews;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by AndreyKorneychuk on 10/11/2015.
 */
public class MyView extends View {

    public MyView(Context context) {
        super(context);
    }

    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    protected void onDraw( Canvas canvas ) {
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth( 8 );
        paint.setColor(Color.BLUE);

        Path path = new Path();
        path.moveTo(75, 11);
        path.quadTo(62, 87, 10, 144);
        canvas.drawPath( path, paint );

        path.reset();
        path.moveTo(50, 100);
        path.lineTo(150, 200);
        canvas.drawPath( path, paint );
    }
}