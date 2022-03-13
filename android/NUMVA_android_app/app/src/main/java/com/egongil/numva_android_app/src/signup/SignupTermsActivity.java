package com.egongil.numva_android_app.src.signup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.egongil.numva_android_app.R;
import com.egongil.numva_android_app.src.app_info.MarketingTermsActivity;
import com.egongil.numva_android_app.src.app_info.PrivacyTermsActivity;
import com.egongil.numva_android_app.src.app_info.ServiceTermsActivity;
import com.egongil.numva_android_app.src.config.BaseActivity;

public class SignupTermsActivity extends BaseActivity {

    CheckBox mCheckAll, mCheckService, mCheckPrivacy, mCheckMarketing;
    Button mBtnNext;
    ImageView mBtnBack;
    TextView mTvDetailService, mTvDetailPrivacy, mTvDetailMarketing;
    int mAgreement_marketing = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_terms);

        mBtnBack = findViewById(R.id.signup_iv_backbtn);

        mCheckAll = findViewById(R.id.signup_check_all);  //전체동의
        mCheckService = findViewById(R.id.signup_check_service);  //이용약관동의
        mCheckPrivacy = findViewById(R.id.signup_check_privacy);  //개인정보 취급방침 동의
        mCheckMarketing = findViewById(R.id.signup_check_marketing);  //마케팅 정보 수신 동의

        mTvDetailService = findViewById(R.id.signup_tv_service_detail); //서비스 이용약관 상세 뷰
        mTvDetailPrivacy = findViewById(R.id.signup_tv_privacy_detail); //개인정보 취급방침 상세 뷰
        mTvDetailMarketing = findViewById(R.id.signup_tv_marketing_detail); //마케팅 정보 수신 상세 뷰

        mBtnNext = findViewById(R.id.signup_btn_terms); //cert화면으로 넘어가는 버튼

        mBtnBack.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                finish();
            }
        });

        mBtnNext.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                if(!checkRegisterAvailable()){
                    return;
                }
                else{
                    if(mCheckMarketing.isChecked()){mAgreement_marketing=1;}

                    Intent intent = new Intent(SignupTermsActivity.this, SignupCertActivity.class);
                    intent.putExtra("CheckMarketing", mAgreement_marketing);
                    startActivity(intent);
                }
            }
        });


        //전체동의 체크박스
        mCheckAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){ //전체 동의박스 체크 시 모두 체크
                    mCheckService.setChecked(true);
                    mCheckPrivacy.setChecked(true);
                    mCheckMarketing.setChecked(true);
                }else{
                    if(mCheckService.isChecked() && mCheckPrivacy.isChecked() && mCheckMarketing.isChecked()){
                        mCheckService.setChecked(false);
                        mCheckPrivacy.setChecked(false);
                        mCheckMarketing.setChecked(false);
                    }
                }
            }
        });
        mCheckService.setOnCheckedChangeListener(subCheckListener);
        mCheckPrivacy.setOnCheckedChangeListener(subCheckListener);
        mCheckMarketing.setOnCheckedChangeListener(subCheckListener);

        mTvDetailService.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                Intent intent_service = new Intent(SignupTermsActivity.this, ServiceTermsActivity.class);
                startActivity(intent_service);
            }
        });

        mTvDetailPrivacy.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                Intent intent_privacy = new Intent(SignupTermsActivity.this, PrivacyTermsActivity.class);
                startActivity(intent_privacy);
            }
        });

        mTvDetailMarketing.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                Intent intent_marketing = new Intent(SignupTermsActivity.this, MarketingTermsActivity.class);
                startActivity(intent_marketing);
            }
        });
    }

    private CompoundButton.OnCheckedChangeListener subCheckListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if(mCheckService.isChecked() && mCheckPrivacy.isChecked() && mCheckMarketing.isChecked()){
                mCheckAll.setChecked(true);   //sub 세개 다 체크하면 all 체크
            }else if(!mCheckService.isChecked() || !mCheckPrivacy.isChecked() || !mCheckMarketing.isChecked()) {
                mCheckAll.setChecked(false);
            }
        }
    };

    //동의여부 회원가입 가능상태인지 확인하는 메소드
    private boolean isChecked(CheckBox check1, CheckBox check2, CheckBox check3){
        if(check1.isChecked() || check2.isChecked() && check3.isChecked()){
            return true;
        }
        return false;
    }

    private boolean checkRegisterAvailable(){
        Boolean registerAvailable = true;

        //이용약관
        if(!isChecked(mCheckAll, mCheckService, mCheckPrivacy)){
            showCustomToast("필수항목에 동의해주세요.");
            registerAvailable = false;
        }

        if(registerAvailable){
            return true;
        }else{
            return false;
        }
    }
}