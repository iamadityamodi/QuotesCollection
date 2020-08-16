package com.quotescollection.quotesdata.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;


import com.quotescollection.quotesdata.R;
import com.wang.avi.AVLoadingIndicatorView;
import com.wang.avi.Indicator;


public class Splash_Screen_Activity extends AppCompatActivity {

    AVLoadingIndicatorView avi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);

        avi = (AVLoadingIndicatorView) findViewById(R.id.AVI);
        avi.setIndicator("LineScalePulseOutIndicator");

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(Splash_Screen_Activity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, 3000);

    }
}
