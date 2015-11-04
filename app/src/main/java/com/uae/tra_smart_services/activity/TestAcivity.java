package com.uae.tra_smart_services.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.uae.tra_smart_services.R;
import com.uae.tra_smart_services.customviews.LoaderView;
import com.uae.tra_smart_services.customviews.OuterLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by and on 03.11.15.
 */
public class TestAcivity extends Activity implements View.OnClickListener{
    final String ATTRIBUTE_NAME_TEXT = "text";
    final String ATTRIBUTE_NAME_CHECKED = "checked";
    final String ATTRIBUTE_NAME_IMAGE = "image";

    String[] listData = { "sometext 1", "sometext 2", "sometext 3", "sometext 4", "sometext 5" , "sometext 6", "sometext 7", "sometext 8", "sometext 9",
            "sometext 1", "sometext 2", "sometext 3", "sometext 4", "sometext 5" , "sometext 6", "sometext 7", "sometext 8", "sometext 9"};
    SimpleAdapter adapter;
/*
    OuterLayout outerLayout;
    LinearLayout mMainLayout;
    ListView listView;
    LoaderView loaderView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_activity);
        initList();

        outerLayout = (OuterLayout) findViewById(R.id.outerLayout);
        mMainLayout = (LinearLayout) findViewById(R.id.main_layout);
        mMainLayout.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                if (outerLayout.isMoving()) {
                    v.setTop(oldTop);
                    v.setBottom(oldBottom);
                    v.setLeft(oldLeft);
                    v.setRight(oldRight);
                }
            }
        });

    }

    void initList(){
        outerLayout = (OuterLayout) findViewById(R.id.outerLayout);
//        outerLayout.setOnHierarchyChangeListener(this);
        listView = (ListView) outerLayout.findViewById(R.id.listview);
        loaderView = (LoaderView) outerLayout.findViewById(R.id.loaderView);


        boolean[] checked = { true, false, false, true, false, true, false, false, true, true, false, false, true, false, true, false, false, true };
        int img = R.drawable.ic_lamp;

        ArrayList<Map<String, Object>> data = new ArrayList<Map<String, Object>>(listData.length);
        Map<String, Object> m;
        for (int i = 0; i < listData.length; i++) {
            m = new HashMap<String, Object>();
            m.put(ATTRIBUTE_NAME_TEXT, listData[i]);
            m.put(ATTRIBUTE_NAME_CHECKED, checked[i]);
            m.put(ATTRIBUTE_NAME_IMAGE, img);
            data.add(m);
        }

        String[] from = { ATTRIBUTE_NAME_TEXT, ATTRIBUTE_NAME_CHECKED, ATTRIBUTE_NAME_IMAGE };
        int[] to = { R.id.tvText, R.id.cbChecked, R.id.ivImg };

        adapter = new SimpleAdapter(this, data, R.layout.test_list_item, from, to);

        listView.setAdapter(adapter);
    }

    @Override
    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
        return super.onCreateView(parent, name, context, attrs);
    }*/


    private Button mQueen;
    private Button mHidden;
    private OuterLayout mOuterLayout;
    private LinearLayout mMainLayout;
    private ListView listview;
    private LoaderView loaderView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_activity);
        mOuterLayout = (OuterLayout) findViewById(R.id.outer_layout);
//        mMainLayout = (LinearLayout) findViewById(R.id.main_layout);
        loaderView = (LoaderView) findViewById(R.id.loaderView);
//        mQueen = (Button) findViewById(R.id.queen_button);
//        mQueen.setOnClickListener(this);
        initList();

    }

    @Override
    public void onClick(View v) {
        Button b = (Button) v;
        Toast t = Toast.makeText(this, b.getText() + " clicked", Toast.LENGTH_SHORT);
        t.show();
    }

    void initList(){
        listview = (ListView) findViewById(R.id.listview);

        listview.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                if (mOuterLayout.isMoving()) {
                    v.setTop(oldTop);
                    v.setBottom(oldBottom);
                    v.setLeft(oldLeft);
                    v.setRight(oldRight);
                }
            }
        });

        boolean[] checked = { true, false, false, true, false, true, false, false, true, true, false, false, true, false, true, false, false, true };
        int img = R.drawable.ic_lamp;

        ArrayList<Map<String, Object>> data = new ArrayList<Map<String, Object>>(listData.length);
        Map<String, Object> m;
        for (int i = 0; i < listData.length; i++) {
            m = new HashMap<String, Object>();
            m.put(ATTRIBUTE_NAME_TEXT, listData[i]);
            m.put(ATTRIBUTE_NAME_CHECKED, checked[i]);
            m.put(ATTRIBUTE_NAME_IMAGE, img);
            data.add(m);
        }

        String[] from = { ATTRIBUTE_NAME_TEXT, ATTRIBUTE_NAME_CHECKED, ATTRIBUTE_NAME_IMAGE };
        int[] to = { R.id.tvText, R.id.cbChecked, R.id.ivImg };

        adapter = new SimpleAdapter(this, data, R.layout.test_list_item, from, to);

        listview.setAdapter(adapter);
    }
}
