package com.egongil.numva_android_app.src.customer_center;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.egongil.numva_android_app.R;
import com.egongil.numva_android_app.src.config.view.BaseActivity;

public class CustomerCenterDetailActivity extends BaseActivity{

    ImageView mIvExit, mIvBack;
    TextView mTvQuestion, mTvAnswer;
    int id;
    String mQuestion, mAnswer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_center_detail);

        mIvBack = findViewById(R.id.customer_center_detail_iv_backbtn);  //뒤로가기 버튼
        mIvExit = findViewById(R.id.customer_center_detail_iv_crossbtn);  //x버튼
        mTvQuestion = findViewById(R.id.customer_center_detail_tv_question);  //질문 TextView
        mTvAnswer = findViewById(R.id.customer_center_detail_tv_answer);  //답변 TextView

        Intent intent = getIntent();
        id = intent.getIntExtra("id", 0);
        mQuestion = intent.getStringExtra("question");
        mAnswer = intent.getStringExtra("answer");

        mTvQuestion.setText(mQuestion);
        mTvAnswer.setText(mAnswer);

        mIvExit.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                finish();
            }
        });

        mIvBack.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                finish();
            }
        });

    }

}