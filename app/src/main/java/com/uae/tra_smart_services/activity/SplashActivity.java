package com.uae.tra_smart_services.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.uae.tra_smart_services.R;

public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // Run post delayed activity start
        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             */
            @Override
            public void run() {

                // We are starting the Main activity
                Intent i = new Intent(SplashActivity.this, AuthorizationActivity.class);
                startActivity(i);

                // close this activity
                finish();
            }
        }, 0);
    }
}