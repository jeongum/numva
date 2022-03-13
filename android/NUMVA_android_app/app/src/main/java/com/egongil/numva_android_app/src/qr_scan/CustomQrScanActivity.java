package com.egongil.numva_android_app.src.qr_scan;

import androidx.annotation.NonNull;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.egongil.numva_android_app.R;
import com.journeyapps.barcodescanner.CaptureManager;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;

public class CustomQrScanActivity extends Activity implements DecoratedBarcodeView.TorchListener{
    private CaptureManager mCaptureManager;
    private boolean isFlashOn = false;
    private ImageView mIvtorch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_qr_scan);

        mIvtorch = findViewById(R.id.custom_qrscan_iv_torch);
//        ImageView mIvBack = findViewById(R.id.custom_qrscan_iv_backbtn);

        final DecoratedBarcodeView mDecoratedBarcodeView = findViewById(R.id.custom_qrscan_dbv);
        mDecoratedBarcodeView.setTorchListener(this);

        mIvtorch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isFlashOn){
                    mDecoratedBarcodeView.setTorchOff();
                }else{
                    mDecoratedBarcodeView.setTorchOn();
                }
            }
        });

        mCaptureManager = new CaptureManager(this, mDecoratedBarcodeView);
        mCaptureManager.initializeFromIntent(getIntent(), savedInstanceState);
        mCaptureManager.decode();

//        mIvBack.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });
    }

    @Override
    public void onTorchOn() {
        isFlashOn = true;
        //플래시 켜진 이미지로 바꾸기
        mIvtorch.setImageResource(R.drawable.ic_flash_on);
    }

    @Override
    public void onTorchOff() {
        isFlashOn = false;
        //플래시 꺼진 이미지로 바꾸기
        mIvtorch.setImageResource(R.drawable.ic_flash_off);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mCaptureManager.onSaveInstanceState(outState);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mCaptureManager.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mCaptureManager.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mCaptureManager.onDestroy();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        mCaptureManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}