package com.android.woonga.views.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;

import com.android.woonga.R;
import com.android.woonga.WoongaApplication;
import com.android.woonga.utils.Constant;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        splash();
    }


    public void splash() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Thread timerThread = new Thread() {
            public void run() {
                try {
                    sleep(1500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    Intent i;
                    if (WoongaApplication.getAppPreferences().getBoolean(Constant.LOGIN)) {
                        i = new Intent(SplashActivity.this, MainActivity.class);
                    } else {
                        i = new Intent(SplashActivity.this, OtpActivity.class);
                    }
                    startActivity(i);
                    finish();
                }
            }
        };
        timerThread.start();
    }

}
