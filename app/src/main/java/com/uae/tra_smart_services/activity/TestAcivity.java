package com.uae.tra_smart_services.activity;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
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
public class TestAcivity extends Activity implements View.OnClickListener, OuterLayout.Listener{
    final String ATTRIBUTE_NAME_TEXT = "text";
    final String ATTRIBUTE_NAME_CHECKED = "checked";
    final String ATTRIBUTE_NAME_IMAGE = "image";

    String[] listData = { "sometext 1", "sometext 2", "sometext 3", "sometext 4", "sometext 5" , "sometext 6", "sometext 7", "sometext 8", "sometext 9",
            "sometext 1", "sometext 2", "sometext 3", "sometext 4", "sometext 5" , "sometext 6", "sometext 7", "sometext 8", "sometext 9"};
    SimpleAdapter adapter;
    String[] from = { ATTRIBUTE_NAME_TEXT, ATTRIBUTE_NAME_CHECKED, ATTRIBUTE_NAME_IMAGE };
    int[] to = { R.id.tvText, R.id.cbChecked, R.id.ivImg };

    private Button mQueen;
    private Button mHidden;
    private OuterLayout mOuterLayout;
    private LinearLayout mMainLayout;
    private ListView listview;
    private TextView noPendingTransactions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_activity);
        mOuterLayout = (OuterLayout) findViewById(R.id.outer_layout);
        mOuterLayout.registerListener(this);
        listview = (ListView) findViewById(R.id.listview);
        noPendingTransactions = (TextView) findViewById(R.id.tvNoPendingTransactions_FIH);
        noPendingTransactions.setOnClickListener(this);
        load();
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.tvNoPendingTransactions_FIH){
            load();
        }
    }

    @Override
    public void onRefresh() {
        load();
    }

    private void load(){
        new AsyncTask<Void,Void,ArrayList<Map<String, Object>>>(){

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                mOuterLayout.onLoadingStart();
            }

            @Override
            protected ArrayList<Map<String, Object>> doInBackground(Void... params) {
                try {
                    Thread.sleep(3000);
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
                    return data;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(ArrayList<Map<String, Object>> _result) {
                super.onPostExecute(_result);
                if(_result != null){
                    adapter = new SimpleAdapter(TestAcivity.this, _result, R.layout.test_list_item, from, to);
                    listview.setAdapter(adapter);
                    noPendingTransactions.setVisibility(View.INVISIBLE);
                    mOuterLayout.onLoadingFinished(true);
                } else {
                    noPendingTransactions.setVisibility(View.VISIBLE);
                    mOuterLayout.onLoadingFinished(false);
                }

            }
        }.execute();
    }
}
