package com.uae.tra_smart_services.activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.uae.tra_smart_services.R;

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


        testListView.setOnTouchListener(new View.OnTouchListener() {
            long mTouchDownTime = 0;
            float dX;
            float dY;
            int position = 0;
            ListView list;
            TextView item;
            ImageView imageView;
            boolean isNewItem = true;
            @Override
            public boolean onTouch(View listView, MotionEvent event) {
                boolean handled = false;

                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        dX = event.getX();
                        dY = event.getY();
                        list = (ListView) listView;
                        position = list.pointToPosition((int) event.getX(), (int) event.getY());
                        item = (TextView) list.getChildAt(position);
                        imageView = loadBitmapFromView(item, (ListView) listView);
                        float imageViewX = imageView.getX();
                        float imageViewY = imageView.getY();
                        item.setBackgroundColor(Color.GRAY);
                        mTouchDownTime = SystemClock.elapsedRealtime();
                        handled = true;
                        isNewItem = false;
                        break;
                    case MotionEvent.ACTION_MOVE:
                        float dX = event.getX();
                        float dY = event.getY();

                        if (SystemClock.elapsedRealtime() - mTouchDownTime >= 250){
                            return move(imageView, event, dX, dY);
                        }
                        handled = true;
                        break;
                    case MotionEvent.ACTION_UP:
                        isNewItem = true;
                        break;
                }
                return handled;
            }

//            boolean handleMove(float dX, float dY, TextView listItem){
//                for(View view : viewArray) {
//                    if (isInArea(view, dX, dY)) {
//                        ((ListView) view).performLongClick();
//                    }
//                }
//                return true;
//            }

//            private boolean isInArea(View view, float x, float y){
//                return x >= view.getX() && x <= view.getX() + view.getWidth()
//                        &&  y >= view.getY() && y <= view.getY() + view.getHeight();
//            }
        });
    }

    public boolean move(ImageView view, MotionEvent event, float dX, float dY) {
        view.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT));
        float imageViewX = view.getX();
        float imageViewY = view.getY();
        float eventX = event.getX();
        float eventY = event.getY();
        float transitionX = imageViewX + eventX;
        float transitionY = imageViewY + eventY;
        view.setTranslationX(eventX);
        view.setTranslationY(eventY);

        Log.e("TESTANIM", view.hashCode()+" should move: X = "+eventX+", Y = "+eventY);
        return true;
    }

    /*View lastAdded;
    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

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

    public ImageView loadBitmapFromView(View v, ListView parent) {
        int width = v.getWidth();
        int height = v.getHeight();
        Bitmap b = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);
        v.layout(v.getLeft(), v.getTop(), v.getRight(), v.getBottom());
        v.draw(c);
        ImageView imageView = new ImageView(getBaseContext());
        imageView.setTag("I am the ImageView");
        imageView.setLayoutParams(new ListView.LayoutParams(ListView.LayoutParams.WRAP_CONTENT, ListView.LayoutParams.WRAP_CONTENT));
        imageView.setImageBitmap(b);
        return imageView;
    }
}