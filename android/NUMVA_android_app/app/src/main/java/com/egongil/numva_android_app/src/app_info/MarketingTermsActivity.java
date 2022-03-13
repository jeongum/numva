package com.egongil.numva_android_app.src.app_info;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.egongil.numva_android_app.R;
import com.egongil.numva_android_app.src.config.BaseActivity;

public class MarketingTermsActivity extends BaseActivity {

    ImageView mIvBack, mIvExit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marketing_terms);

        mIvBack = findViewById(R.id.marketing_terms_iv_backbtn); //뒤로가기버튼
        mIvExit = findViewById(R.id.marketing_terms_iv_crossbtn); //x버튼

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
    }
}