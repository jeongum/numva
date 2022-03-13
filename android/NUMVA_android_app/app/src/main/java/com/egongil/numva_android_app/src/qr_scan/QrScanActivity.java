package com.egongil.numva_android_app.src.qr_scan;

import android.content.Intent;
import android.os.Bundle;

import com.egongil.numva_android_app.R;
import com.egongil.numva_android_app.src.config.BaseActivity;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class QrScanActivity extends BaseActivity {

    IntentIntegrator qrIntegrator;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_scan);

        qrIntegrator = new IntentIntegrator(this);
        qrIntegrator.setPrompt("QR코드를 스캔해주세요!");
        qrIntegrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE);
        qrIntegrator.setCaptureActivity(CustomQrScanActivity.class);
        qrIntegrator.setBeepEnabled(false); //Beep소리 제거
        qrIntegrator.initiateScan(); //스캔 시작
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null){
            //Activity에 result 들어옴
            if(result.getContents() == null){
                //뒤로가기 버튼 눌렸을 때
                showCustomToast("스캔이 취소되었습니다.");
                finish();
            }else{
                //바코드 스캔되었을 때
                //TODO: 스캔된 정보 정제하여 우리 QR일 경우에만 인식
                // TODO: 등록된 qr이면 ResultActivity 실행, 등록 x qr이면 등록 다이얼로그 띄우기

                Intent intent = new Intent(getApplicationContext(), QrScanResultActivity.class);
                intent.putExtra("scan_result", result.getContents());
                startActivity(intent);

                finish();
            }
        }else{
            super.onActivityResult(requestCode,resultCode,data);
        }
    }

}