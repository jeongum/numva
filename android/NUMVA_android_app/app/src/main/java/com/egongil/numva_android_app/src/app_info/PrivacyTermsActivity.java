package com.egongil.numva_android_app.src.app_info;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.egongil.numva_android_app.R;
import com.egongil.numva_android_app.src.config.view.BaseActivity;

public class PrivacyTermsActivity extends BaseActivity {

    ImageView mIvExit, mIvBack;
    WebView mWv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy_terms);

        mIvBack = findViewById(R.id.privacy_terms_iv_backbtn); //뒤로가기버튼
        mIvExit = findViewById(R.id.privacy_terms_iv_crossbtn); //x버튼
        mWv = findViewById(R.id.privacy_terms_wv);

        mIvBack.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                finish();
            }
        });

        mIvExit.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                finish();
            }
        });

        mWv.setWebViewClient(new WebViewClient());
        mWv.setWebChromeClient(new WebChromeClient());
        mWv.loadUrl("http://211.37.147.142/privacy");
    }
}