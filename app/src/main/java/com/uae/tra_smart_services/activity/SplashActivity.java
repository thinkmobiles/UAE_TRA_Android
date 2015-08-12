package com.uae.tra_smart_services.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.uae.tra_smart_services.R;

public class SplashActivity extends AppCompatActivity {

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
        }, getResources().getInteger(R.integer.int_splash_delay));
    }
}
