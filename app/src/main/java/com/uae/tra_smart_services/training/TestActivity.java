package com.uae.tra_smart_services.training;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.uae.tra_smart_services.R;
import com.uae.tra_smart_services.rest.model.request.PoorCoverageRequestModel;

import java.util.Collections;
import java.util.List;

import de.greenrobot.event.EventBus;
import rx.Observable;
import rx.Observer;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.android.widget.OnTextChangeEvent;
import rx.android.widget.WidgetObservable;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func0;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.functions.Func6;
import rx.schedulers.Schedulers;
import rx.subjects.BehaviorSubject;

/**
 * Created by and on 15.09.15.
 */

public class TestActivity extends Activity {

    private SurfaceViewLayout customLayout;

    private EditText etUsername;
    private Button btnSubmit;
    private ProgressBar pbProgress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        customLayout = new SurfaceViewLayout(this);
        setContentView(R.layout.fragment_login);
        etUsername = (EditText) findViewById(R.id.etEmail_FRP);
        btnSubmit = (Button) findViewById(R.id.btnDoRestorePass_FRP);
        pbProgress = (ProgressBar) findViewById(R.id.pbProgress);

        final BehaviorSubject<String> mNameObservable = BehaviorSubject.create();

        WidgetObservable
            .text(etUsername)
            .filter(new Func1<OnTextChangeEvent, Boolean>() {
                @Override
                public Boolean call(OnTextChangeEvent onTextChangeEvent) {
                    return onTextChangeEvent.text().length() > 3;
                }
            })
            .doOnNext(new Action1<OnTextChangeEvent>() {
                @Override
                public void call(OnTextChangeEvent onTextChangeEvent) {
                    btnSubmit.setEnabled(false);
                    pbProgress.setVisibility(View.INVISIBLE);
                    Toast.makeText(getApplicationContext(), onTextChangeEvent.text(), Toast.LENGTH_SHORT).show();
                }
            })
            .subscribe(new Action1<OnTextChangeEvent>() {
                @Override
                public void call(OnTextChangeEvent onTextChangeEvent) {
                    mNameObservable.onNext(onTextChangeEvent.text().toString());
                }
            });

        mNameObservable
            .subscribe(new Action1<String>() {
                @Override
                public void call(String s) {

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


    public interface Server{
        Observable<JsonObject> findUser(String _name);
        Observable<JsonObject> addUser(String _name);
    }
}
