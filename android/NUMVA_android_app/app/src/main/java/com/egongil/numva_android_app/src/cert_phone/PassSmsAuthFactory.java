package com.egongil.numva_android_app.src.cert_phone;

import android.content.Intent;
import android.webkit.JavascriptInterface;

import static android.app.Activity.RESULT_OK;

public class PassSmsAuthFactory {
    private static final String TAG = "SmsAuthFactory";

    @JavascriptInterface
    public void resultAuth(String message, String impKey){
        //javascript로 부터 온 parameter (message)를 처리

        PassActivity activity = new PassActivity();

        Intent intent = new Intent();
        if(message.equals("success") && impKey!=null){
            intent.putExtra("result", "success");
            intent.putExtra("imp_key", impKey); //인증에 성공했을 시 받을 수 있는 자바스크립트 상에서의 imp_key
            activity.setResult(RESULT_OK, intent);
            activity.finish();
        }
        else{
            intent.putExtra("result", "failure");
            activity.setResult(RESULT_OK, intent);
            activity.finish();
        }
    }
}