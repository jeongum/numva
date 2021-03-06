package com.egongil.numva_android_app.src.qr_scan;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.egongil.numva_android_app.R;
import com.egongil.numva_android_app.src.config.BaseActivity;
import com.egongil.numva_android_app.src.config.ErrorResponse;
import com.egongil.numva_android_app.src.custom_dialogs.TwoButtonDialog;
import com.egongil.numva_android_app.src.login.LoginActivity;
import com.egongil.numva_android_app.src.main.MainActivity;
import com.egongil.numva_android_app.src.numvatalk.NumvatalkActivity;
import com.egongil.numva_android_app.src.numvatalk.chatlist.NumvaTalkFragment;
import com.egongil.numva_android_app.src.qr_management.QrManagementActivity;
import com.egongil.numva_android_app.src.qr_management.QrManagementService;
import com.egongil.numva_android_app.src.qr_management.QrRecyclerAdapter;
import com.egongil.numva_android_app.src.qr_management.models.RegisterQrRequest;
import com.egongil.numva_android_app.src.qr_management.models.RegisterQrResponse;
import com.egongil.numva_android_app.src.qr_scan.interfaces.QrScanResultActivityView;
import com.egongil.numva_android_app.src.qr_scan.models.ScanQrRequest;
import com.egongil.numva_android_app.src.qr_scan.models.ScanQrResponse;
import com.mesibo.calls.api.MesiboCall;
import com.skydoves.balloon.ArrowOrientation;
import com.skydoves.balloon.ArrowPositionRules;
import com.skydoves.balloon.Balloon;
import com.skydoves.balloon.BalloonAnimation;
import com.skydoves.balloon.BalloonSizeSpec;
import com.skydoves.balloon.OnBalloonClickListener;
import com.skydoves.balloon.OnBalloonOutsideTouchListener;


public class QrScanResultActivity extends BaseActivity implements QrScanResultActivityView {

    private String first_phone, second_phone;
    private String qrId;
    private String mesiboAddress;

    TextView mTvMemo, mTvNick, mTvPhone;
    LinearLayout mLlNumvaGuide;
    ImageView mIvNumvaGuide;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_scan_result_register);

        ImageView mIvCrossBtn = findViewById(R.id.qr_result_iv_closebtn);

        mIvCrossBtn.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                finish();
            }
        });

        //url?????? qr_id ??????
        Intent intent = getIntent();
        String scan_result = intent.getStringExtra("scan_result");
        if(checkUrl(scan_result)){
            //?????? URL??? ??????
            qrId = splitQrId(scan_result);
            scanQr(qrId);
        }else{
            //?????? URL??? ?????? ??????
            showCustomToast(getString(R.string.qrscan_result_invalid_qr));
            finish();
        }

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

    public boolean checkUrl(String url){
        String form = "http://numva.co.kr/qr/";

          if(url.contains(form)){
              return true;
          }
          return false;
    }
    public String splitQrId(String url){
        String form = "http://numva.co.kr/qr/";
        String[] qr_id;
        qr_id = url.split(form);
        return qr_id[1];
    }

    public void setContentLayout(){
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
                if(mesiboAddress!=null){
                    if(!MesiboCall.getInstance().callUi(QrScanResultActivity.this, mesiboAddress, false))
                        MesiboCall.getInstance().callUiForExistingCall(QrScanResultActivity.this);

                }else{
                    showCustomToast(getResources().getString(R.string.alert_not_prepared));
                }
            }
        });
        mLlNumvaTalk.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                if(mesiboAddress!=null){
                    Intent intent = new Intent(QrScanResultActivity.this, NumvatalkActivity.class);
                    intent.putExtra("peer", mesiboAddress);
                    intent.putExtra("groupid", 0L);
                    intent.putExtra("mid", 0L);
                    startActivity(intent);
                }else{
                    showCustomToast(getResources().getString(R.string.alert_not_prepared));
                }
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
                .setAutoDismissDuration(5000L)  //3??? ??? ???????????? ?????????
                .setMarginLeft(15)  // ????????? ?????? ?????? ??????
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
    public void showRegisterDialog(String qr_id){
        TwoButtonDialog registerDialog = new TwoButtonDialog(QrScanResultActivity.this);
        registerDialog.showDialog();
        registerDialog.setContextText(Html.fromHtml("QR ??????????????? '"+qr_id+"'??? \n????????????????").toString());
        registerDialog.setSelectText("??????", "??????");

        registerDialog.mBtnLeft.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                registerDialog.dismiss();
            }
        });
        registerDialog.mBtnRight.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                registerQr(qrId);
                registerDialog.dismiss();
            }
        });
    }

    public void scanQr(String qr_id){
        QrScanResultService qrScanResultService = new QrScanResultService(this);
        ScanQrRequest scanQrRequest = new ScanQrRequest(qr_id);
        qrScanResultService.scanQr(scanQrRequest);
    }

    @Override
    public void scanQrSuccess(ScanQrResponse scanQrResponse, ErrorResponse errorResponse) {
        if(scanQrResponse!=null){
            //????????? QR??? ??????
            if(scanQrResponse.getCode()==200 && scanQrResponse.isSuccess()){
                if(scanQrResponse.getResult() !=null){
                    setContentLayout();

                    first_phone = scanQrResponse.getResult().getSafetyNumber().getFirst();
                    second_phone = scanQrResponse.getResult().getSafetyNumber().getSecond();
                    mesiboAddress = scanQrResponse.getResult().getMesibo_address();

                    mTvNick.setText(scanQrResponse.getResult().getNickname());
                    mTvMemo.setText(scanQrResponse.getResult().getMemo());
                    mTvPhone.setText(first_phone);


                }
            }
        }
        else if(errorResponse != null){
            if(errorResponse.getCode() == -701){
                //???????????? ?????? QR??? ??????
                showRegisterDialog(qrId);
            }
            else if(errorResponse.getCode() == -103){
                //???????????? ?????? QR??? ??????
                showCustomToast(getString(R.string.qrscan_result_invalid_qr));
                finish();
            }
        }
    }

    @Override
    public void scanQrFailure() {
        showCustomToast(getString(R.string.network_error));
    }

    public void registerQr(String qr_id){
        RegisterQrRequest registerQrRequest = new RegisterQrRequest(qr_id);

        QrScanResultService qrScanResultService  = new QrScanResultService(this);
        qrScanResultService.registerQr(registerQrRequest);
    }

    @Override
    public void registerQrSuccess(RegisterQrResponse registerQrResponse, ErrorResponse errorResponse) {
        if(registerQrResponse!=null){
            if(registerQrResponse.getCode()==200 && registerQrResponse.isSuccess()){
                showCustomToast(getString(R.string.qr_manage_register_success));
                Intent intent = new Intent(QrScanResultActivity.this, QrManagementActivity.class);
                startActivity(intent);
                finish();
            }
        }
        else if(errorResponse != null){
            //?????? ??? ??????
            showCustomToast(getResources().getString(R.string.network_error));
        }
    }

    @Override
    public void registerQrFailure() {
        showCustomToast(getResources().getString(R.string.network_error));
    }
}