package com.uae.tra_smart_services.training;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.EditText;

import com.uae.tra_smart_services.rest.model.request.PoorCoverageRequestModel;

import java.util.Collections;
import java.util.List;

import de.greenrobot.event.EventBus;
import rx.Observable;
import rx.Observer;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func0;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.functions.Func6;
import rx.schedulers.Schedulers;

/**
 * Created by and on 15.09.15.
 */

public class TestActivity extends Activity {

    private SurfaceViewLayout customLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        customLayout = new SurfaceViewLayout(this);
        setContentView(customLayout);

        Observable<String> sentenceObservable = Observable.from(new String[]{"b", "e", "d", "c", "a", "f"});
        sentenceObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Func1<String, String>() {
                    @Override
                    public String call(String s) {
                        Log.e("map() - " + Thread.currentThread().getName(), s);
                        return s.toUpperCase() + " ";
                    }
                })
                .toSortedList(new Func2<String, String, Integer>() {
                    @Override
                    public Integer call(String s, String s2) {
                        return s.compareTo(s2);
                    }
                })
                .map(new Func1<List<String>, String>() {
                    @Override
                    public String call(List<String> strings) {
                        Collections.reverse(strings);
                        return strings.toString();
                    }
                })
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        EventBus.getDefault().post(s);
                        Log.e("subscribe() - " + Thread.currentThread().getName(), s);
                    }
                });
    }

    @Override
    protected void onPause() {
        super.onPause();
        customLayout.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        customLayout.resume();
    }
}
