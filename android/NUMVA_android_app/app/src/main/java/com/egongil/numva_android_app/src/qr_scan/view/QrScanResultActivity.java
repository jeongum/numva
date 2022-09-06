package com.egongil.numva_android_app.src.qr_scan.view;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.egongil.numva_android_app.R;
import com.egongil.numva_android_app.src.config.view.BaseActivity;
import com.egongil.numva_android_app.src.numvatalk.NumvatalkActivity;
import com.mesibo.calls.api.MesiboCall;
import com.skydoves.balloon.ArrowOrientation;
import com.skydoves.balloon.ArrowPositionRules;
import com.skydoves.balloon.Balloon;
import com.skydoves.balloon.BalloonAnimation;
import com.skydoves.balloon.BalloonSizeSpec;
import com.skydoves.balloon.OnBalloonClickListener;
import com.skydoves.balloon.OnBalloonOutsideTouchListener;


public class QrScanResultActivity extends BaseActivity{

    private String first_phone, second_phone;
    private String mesiboAddress;

    TextView mTvMemo, mTvNick, mTvPhone;
    LinearLayout mLlNumvaGuide;
    ImageView mIvNumvaGuide;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_scan_result);

        ImageView mIvIcon = findViewById(R.id.qrscan_result_iv_icon);
        mTvNick = findViewById(R.id.qrscan_result_tv_nickname);
        LinearLayout mLlNumvaCall = findViewById(R.id.qrscan_result_ll_numvacall);
        LinearLayout mLlNumvaTalk = findViewById(R.id.qrscan_result_ll_numvatalk);
        mLlNumvaGuide = findViewById(R.id.qrscan_result_ll_numvacall_guide);
        mIvNumvaGuide = findViewById(R.id.qrscan_result_iv_numvacall_guide);
        TextView mTvCall = findViewById(R.id.qrscan_result_tv_call);
        TextView mTvMessage = findViewById(R.id.qrscan_result_tv_message);
        TextView mTvSecondCall = findViewById(R.id.qrscan_result_tv_second_call);
        TextView mTvSecondMessage = findViewById(R.id.qrscan_result_tv_second_message);
        ImageView mIvCrossBtn = findViewById(R.id.qr_result_iv_closebtn);

        mIvCrossBtn.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                finish();
            }
        });
        mTvPhone = findViewById(R.id.qrscan_result_tv_phone);
        mTvMemo = findViewById(R.id.qrscan_result_tv_memo);

        final GradientDrawable iconDrawable = (GradientDrawable) ContextCompat.getDrawable(this, R.drawable.circle_70dp);
        getRandomColor(iconDrawable);
        mIvIcon.setImageDrawable(iconDrawable);

        mLlNumvaCall.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
//                if(mesiboAddress!=null){
//                    if(!MesiboCall.getInstance().callUi(QrScanResultActivity.this, mesiboAddress, false))
//                        MesiboCall.getInstance().callUiForExistingCall(QrScanResultActivity.this);
//
//                }else{
//                    showCustomToast(getResources().getString(R.string.alert_not_prepared));
//                }
            }
        });
        mLlNumvaTalk.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
//                if(mesiboAddress!=null){
//                    Intent intent = new Intent(QrScanResultActivity.this, NumvatalkActivity.class);
//                    intent.putExtra("peer", mesiboAddress);
//                    intent.putExtra("groupid", 0L);
//                    intent.putExtra("mid", 0L);
//                    startActivity(intent);
//                }else{
//                    showCustomToast(getResources().getString(R.string.alert_not_prepared));
//                }
            }
        });

        mLlNumvaGuide.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                showNumvaGuideTooltip();
            }
        });

        mTvCall.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+ first_phone));
                startActivity(intent);
            }
        });
        mTvMessage.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("smsto:"+first_phone));
                startActivity(intent);
            }
        });
        mTvSecondCall.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+ second_phone));
                startActivity(intent);
            }
        });
        mTvSecondMessage.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("smsto:"+second_phone));
                startActivity(intent);
            }
        });


        //QrScanActivity에서 전달받은 데이터 세팅
        first_phone = getIntent().getStringExtra("first_phone");
        second_phone = getIntent().getStringExtra("second_phone");
//        mesiboAddress = getIntent().getStringExtra("mesiboAddress");

        mTvNick.setText(getIntent().getStringExtra("nickname"));
        mTvMemo.setText(getIntent().getStringExtra("memo"));
        mTvPhone.setText(first_phone);
    }

    public static void getRandomColor(GradientDrawable iconDrawable) {
        int r, g, b;
        do {
            r = (int) (Math.random() * 255);
            g = (int) (Math.random() * 255);
            b = (int) (Math.random() * 255);
        } while ((r == 255 && g == 255 && b == 255)
                || (r < 200 || g < 200 || b < 200));

        iconDrawable.setColor(Color.rgb(r, g, b));
        iconDrawable.setStroke(8, Color.rgb(r-20,g-20,b-20));
    }

    public void showNumvaGuideTooltip(){
        Balloon balloon = new Balloon.Builder(this)
                .setArrowSize(10)
                .setArrowOrientation(ArrowOrientation.BOTTOM)
                .setArrowPosition(0.2f)
                .setArrowPositionRules(ArrowPositionRules.ALIGN_BALLOON)
                .setWidth(BalloonSizeSpec.WRAP)
                .setHeight(BalloonSizeSpec.WRAP)
                .setTextSize(15f)
                .setCornerRadius(5f)
                .setText(getString(R.string.qrscan_result_guide))
                .setTextTypeface(R.font.font_regular)
                .setTextColor(getColor(R.color.colorPrimary))
                .setTextIsHtml(false)
                .setBackgroundColor(getColor(R.color.colorPrimaryBackground))
                .setBalloonAnimation(BalloonAnimation.ELASTIC)
                .setAutoDismissDuration(5000L)  //3초 뒤 자동으로 사라짐
                .setMarginLeft(15)  // 풍선의 왼쪽 여백 설정
                .setPadding(5)
                .build();

        balloon.setOnBalloonClickListener(new OnBalloonClickListener() {
            @Override
            public void onBalloonClick(@NonNull View view) {
                balloon.dismiss();
            }
        });

        balloon.setOnBalloonOutsideTouchListener(new OnBalloonOutsideTouchListener() {
            @Override
            public void onBalloonOutsideTouch(@NonNull View view, @NonNull MotionEvent motionEvent) {
                balloon.dismiss();
            }
        });

        balloon.showAlignBottom(mLlNumvaGuide);
    }
}