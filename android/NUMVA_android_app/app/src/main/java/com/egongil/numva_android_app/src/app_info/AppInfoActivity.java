package com.egongil.numva_android_app.src.app_info;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.egongil.numva_android_app.R;
import com.egongil.numva_android_app.src.config.BaseActivity;

public class AppInfoActivity extends BaseActivity {
    TextView mTvAppVersion;
    ConstraintLayout mBtnService, mBtnPrivacy, mBtnMarketing;
    ImageView mBtnExit;
    String version;  //versionName
    int verCode;  //versionCode

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_info);

        mTvAppVersion = findViewById(R.id.app_info_tv_version);
        mBtnService = findViewById(R.id.app_info_cl_service);
        mBtnPrivacy = findViewById(R.id.app_info_cl_privacy);
        mBtnMarketing = findViewById(R.id.app_info_cl_marketing);
        mBtnExit = findViewById(R.id.app_info_iv_crossbtn);

        try {
            PackageInfo pInfo = this.getPackageManager().getPackageInfo(this.getPackageName(), 0);
            version = pInfo.versionName;
            verCode = pInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        mTvAppVersion.setText(version);

        mBtnExit.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                finish();
            }
        });

        mBtnService.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                Intent intent_service = new Intent(AppInfoActivity.this, ServiceTermsActivity.class);
                startActivity(intent_service);
            }
        });

        mBtnPrivacy.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                Intent intent_privacy = new Intent(AppInfoActivity.this, PrivacyTermsActivity.class);
                startActivity(intent_privacy);
            }
        });

        mBtnMarketing.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                Intent intent_marketing = new Intent(AppInfoActivity.this, MarketingTermsActivity.class);
                startActivity(intent_marketing);
            }
        });
    }
}