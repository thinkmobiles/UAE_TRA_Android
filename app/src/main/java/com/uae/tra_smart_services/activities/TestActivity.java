package com.uae.tra_smart_services.activities;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.PathShape;
import android.media.Image;
import android.os.Bundle;
import android.widget.ImageView;

import com.uae.tra_smart_services.R;

/**
 * Created by and on 15.09.15.
 */

public class TestActivity extends Activity {

    ImageView background;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_cutter);
        background = (ImageView) findViewById(R.id.cutted_image);

        /*Path pathTriangulo = new Path();
        pathTriangulo.moveTo(0, 0);
        pathTriangulo.lineTo(50, 0);
        pathTriangulo.lineTo(50, 50);
        pathTriangulo.lineTo(0, 0);

        ShapeDrawable miImagen = new ShapeDrawable(new PathShape(pathTriangulo,50,50)){
            @Override
            protected void onBoundsChange(Rect bounds) {
                super.onBoundsChange(bounds);
                Path pathTriangulo = new Path();
                pathTriangulo.moveTo(0, 0);
                pathTriangulo.lineTo(50, 0);
                pathTriangulo.lineTo(50, 50);
                pathTriangulo.lineTo(0, 0);
                setShape( new PathShape(pathTriangulo, bounds.width(), bounds.height()));

            }
        };
        miImagen.getPaint().setColor(Color.BLACK);
        miImagen.getPaint().setStyle(Paint.Style.STROKE);
        miImagen.setBounds(0, 0, 5000, 5000);

        background.setImageDrawable(miImagen);*/
    }
}
