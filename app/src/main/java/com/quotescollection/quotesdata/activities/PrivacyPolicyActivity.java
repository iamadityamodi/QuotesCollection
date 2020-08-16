package com.quotescollection.quotesdata.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.quotescollection.quotesdata.R;

public class PrivacyPolicyActivity extends AppCompatActivity {

    private WebView webview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy_policy);

        webview = findViewById(R.id.webview);
        webview.loadUrl("https://mindcodedeveloper.blogspot.com/p/privacy-policy-quotes-collection.html");




    }
}
