package com.uae.tra_smart_services.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.uae.tra_smart_services.R;
import com.uae.tra_smart_services.customviews.CustomTestLayout;
import com.uae.tra_smart_services.entities.CirclePoint;

import java.util.ArrayList;

/**
 * Created by ak-buffalo on 21.08.15.
 */
public class TestAnim extends Activity/* implements ListView.OnItemLongClickListener */{
    ListView testListView;
    TextView testText;
    RelativeLayout rootView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_anim);
        rootView = (RelativeLayout) findViewById(R.id.rootView);

        testListView = (ListView) findViewById(R.id.testListView);
        testListView.setAdapter(new ArrayAdapter<String>(this, R.layout.layout_test_anim_list_item, new ArrayList<String>() {
            {
                add("asdasdasdasdads");
                add("asdasdasdasdads");
                add("asdasdasdasdads");
                add("asdasdasdasdads");
                add("asdasdasdasdads");
                add("asdasdasdasdads");
            }
        }));


        rootView.setOnTouchListener(new View.OnTouchListener() {
            protected final ArrayList<View> viewArray = new ArrayList<View>(){
                {
                    add(testListView);
                }
            };
            long mTouchDownTime = 0;
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                boolean handled = false;
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        mTouchDownTime = SystemClock.elapsedRealtime();
                        handled = true;
                        break;
                    case MotionEvent.ACTION_MOVE:
                        if (SystemClock.elapsedRealtime() - mTouchDownTime >= 250){
                            return handleClick(event.getX(), event.getY(), viewArray);
                        }
                        handled = false;
                        break;
                }
                return handled;
            }

            boolean handleClick(float dX, float dY, ArrayList<View> viewArray){
                for(View view : viewArray) {
                    if (isInArea(view, dX, dY)) {
                        ((ListView) view).performLongClick();
                    }
                }
                return true;
            }

            private boolean isInArea(View view, float x, float y){
                return x >= view.getX() && x <= view.getX() + view.getWidth()
                        &&  y >= view.getY() && y <= view.getY() + view.getHeight();
            }
        });

    }

    public void move(View view, MotionEvent event, float dX, float dY) {
        testText.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT));
        testText.setTranslationX(testText.getX() - dX + event.getX());
        testText.setTranslationY(testText.getY() - dY + event.getY());
    }

    /*View lastAdded;
    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        final ImageView imageView = loadBitmapFromView(view, parent);
        ((TextView)view).setBackgroundColor(Color.GRAY);
        imageView.setOnTouchListener(new View.OnTouchListener() {
            float dX;
            float dY;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        dX = event.getX();
                        dY = event.getY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        move(imageView, event, dX, dY);
                        break;
                }
                return true;
            }
        });
        imageView.start
        return true;
    }*/

        /*rootView.setOnTouchListener(new View.OnTouchListener() {
            private float dX;
            private float dY;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        dX = event.getX();
                        dY = event.getY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        move(imageView, event, dX, dY);
                        break;
                }
                return true;
            }
        });*//*
        imageView.setLayoutParams(rootView.getLayoutParams());
        imageView.setTranslationY(imageView.getY() + 250);
        parent.getParent().requestDisallowInterceptTouchEvent(true);
        return false;
    }*/

    public ImageView loadBitmapFromView(View v, AdapterView<?> parent) {
        int width = v.getWidth();
        int height = v.getHeight();
        Bitmap b = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);
        v.layout(v.getLeft(), v.getTop(), v.getRight(), v.getBottom());
        v.draw(c);
        ImageView imageView = new ImageView(getBaseContext());
        imageView.setTag("I am the ImageView");
        imageView.setTranslationY(imageView.getY() + 250);
        imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        imageView.setImageBitmap(b);
        return imageView;
    }
}