package com.egongil.numva_android_app.src.qr_scan.view;

import static com.egongil.numva_android_app.src.config.ApplicationClass.ActivityType.QR_SCAN_ACTIVITY;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;

import com.egongil.numva_android_app.R;
import com.egongil.numva_android_app.src.config.models.base.ErrorResponse;
import com.egongil.numva_android_app.src.config.models.request.ScanQrRequest;
import com.egongil.numva_android_app.src.config.models.response.ScanQrResponse;
import com.egongil.numva_android_app.src.config.view.BaseActivity;
import com.egongil.numva_android_app.src.custom_dialogs.TwoButtonDialog;
import com.egongil.numva_android_app.src.qr_management.view.QrManagementActivity;
import com.egongil.numva_android_app.src.qr_scan.interfaces.QrScanActivityContract;
import com.egongil.numva_android_app.src.qr_scan.models.QrScanService;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class QrScanActivity extends BaseActivity implements QrScanActivityContract {
    IntentIntegrator qrIntegrator;
    String qrId;

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

    //api가 startResultForActivity를 사용하고있기 때문에 launcher로 변경 불가능
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
                String scan_result = result.getContents();
                if(checkUrl(scan_result)){
                    //넘바 URL일 경우
                    qrId = splitQrId(scan_result);
                    scanQr(qrId);
                }else{
                    //넘바 URL이 아닐 경우
                    showCustomToast(getString(R.string.qrscan_result_invalid_qr));
                    finish();
                }
            }
        }else{
            super.onActivityResult(requestCode,resultCode,data);
        }
    }

    public boolean checkUrl(String url){
//        String form = "http://numva.co.kr/qr/";
        String form = "http://211.37.147.142/qr/";

        if(url.contains(form)){
            return true;
        }
        return false;
    }
    public String splitQrId(String url){
//        String form = "http://numva.co.kr/qr/";
        String form = "http://211.37.147.142/qr/";
        String[] qr_id;
        qr_id = url.split(form);
        return qr_id[1];
    }
    public void scanQr(String qr_id){
        QrScanService qrScanService = new QrScanService(this);
        ScanQrRequest scanQrRequest = new ScanQrRequest(qr_id);
        qrScanService.scanQr(scanQrRequest);
    }

    @Override
    public void scanQrSuccess(ScanQrResponse scanQrResponse, ErrorResponse errorResponse) {
        if(scanQrResponse!=null){
            //등록된 QR일 경우
            if(scanQrResponse.getCode()==200 && scanQrResponse.isSuccess()){
                if(scanQrResponse.getResult() !=null){
                    //등록된 qr일 경우
                    Intent intent = new Intent(getApplicationContext(), QrScanResultActivity.class);
                    intent.putExtra("first_phone",scanQrResponse.getResult().getSafetyNumber().getFirst() );
                    intent.putExtra("second_phone", scanQrResponse.getResult().getSafetyNumber().getSecond());
                    intent.putExtra("mesiboAddress", scanQrResponse.getResult().getMesibo_address());
                    intent.putExtra("nickname", scanQrResponse.getResult().getNickname());
                    intent.putExtra("memo", scanQrResponse.getResult().getMemo());
                    startActivity(intent);

                }
            }
        }
        else if(errorResponse != null){
            if(errorResponse.getCode() == -701){
                //등록되지 않은 QR일 경우
                showRegisterDialog(qrId);
            }
            else if(errorResponse.getCode() == -103){
                //유효하지 않은 QR일 경우
                showCustomToast(getString(R.string.qrscan_result_invalid_qr));
                finish();
            }
        }
    }

    @Override
    public void scanQrFailure() {
        showCustomToast(getString(R.string.network_error));
    }

        public void showRegisterDialog(String qr_id){
        TwoButtonDialog registerDialog = new TwoButtonDialog(QrScanActivity.this);
        registerDialog.showDialog();
        registerDialog.setContextText(Html.fromHtml("QR 전화번호판 '"+qr_id+"'을 \n등록할까요?").toString());
        registerDialog.setSelectText("취소", "확인");

        registerDialog.mBtnLeft.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                registerDialog.dismiss();
            }
        });
        registerDialog.mBtnRight.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                Intent finish_intent = new Intent(getApplicationContext(), QrManagementActivity.class);
                finish_intent.putExtra("qr_id", qr_id);
                setResult(QR_SCAN_ACTIVITY, finish_intent);
                finish();
                registerDialog.dismiss();
            }
        });
    }
}