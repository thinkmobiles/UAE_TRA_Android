package com.uae.tra_smart_services.training;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;

import com.uae.tra_smart_services.rest.model.request.PoorCoverageRequestModel;

import rx.Observable;
import rx.Observer;
import rx.Scheduler;
import rx.functions.Func1;
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
        EditText etUserName = new EditText(this);

        Observable
                .just(etUserName.getText().toString())
                .map(
                    new Func1<String, PoorCoverageRequestModel>() {
                        @Override
                        public PoorCoverageRequestModel call(String s) {
                            return new PoorCoverageRequestModel(){
                                {
                                    setAddress("address");
                                    setSignalLevel(10);
                                    setLocation("123.00", "123.00");
                                }
                            };
                        }
                    }
                )
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<PoorCoverageRequestModel>() {

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(PoorCoverageRequestModel poorCoverageRequestModel) {

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
