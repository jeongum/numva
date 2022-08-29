package com.egongil.numva_android_app.src.signup;

import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.egongil.numva_android_app.R;
import com.egongil.numva_android_app.src.cert_phone.view.PassActivity;
import com.egongil.numva_android_app.src.config.view.BaseActivity;

public class SignupCertActivity extends BaseActivity {

    ImageView mBtnBack;
    ConstraintLayout mBtnPass;
    int mCheckMarketing;
    static final int PASS_SIGNUP_CODE = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_cert);

        mBtnBack = findViewById(R.id.signup_iv_backbtn);
        mBtnPass = findViewById(R.id.signup_cl_pass);  //pass로 연결하는 버튼\





        Intent intent = getIntent();
        mCheckMarketing = intent.getIntExtra("CheckMarketing", 0);

        mBtnBack.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                finish();
            }
        });

        mBtnPass.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                Intent intent = new Intent(SignupCertActivity.this, PassActivity.class);
//                startActivity(intent);
                intent.putExtra("request_page", "signup");
                startActivityForResult(intent,PASS_SIGNUP_CODE);
            }
        });

//        mBtnPass.setOnClickListener(new OnSingleClickListener() {
//            @Override
//            public void onSingleClick(View v) {
//                Intent intent = new Intent(SignupCertActivity.this, SignupMoreInfoActivity.class);
//                intent.putExtra("CheckMarketing", mCheckMarketing);
//                startActivity(intent);
//            }
//        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==PASS_SIGNUP_CODE){
            if(resultCode==RESULT_OK){
                String result = data.getStringExtra("result");
//                String imp_key = viewModel.smsAuthPostAccessToken(getString(R.string.imp_key), getString(R.string.imp_secret),impKey);

            }
        }
    }
}