package com.uae.tra_smart_services.activities;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.widget.ImageView;

import com.uae.tra_smart_services.R;
import com.uae.tra_smart_services.customviews.HexagonView;

/**
 * Created by and on 12.10.15.
 */

public class ResultActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_result);
        Bundle extras = getIntent().getExtras();
        byte[] food = extras.getByteArray("picture");
        Bitmap bitmapResult = BitmapFactory.decodeByteArray(food, 0, food.length);
        HexagonView hexagonView = (HexagonView) findViewById(R.id.hvIcon_LIIHA);
        hexagonView.setScaleType(HexagonView.CENTER_CROP);
        hexagonView.setHexagonSrcDrawable(new BitmapDrawable(bitmapResult));
    }
}
