package com.egongil.numva_android_app.src.cert_phone;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.egongil.numva_android_app.R;
import com.egongil.numva_android_app.src.cert_phone.interfaces.PassActivityView;
import com.egongil.numva_android_app.src.cert_phone.models.PassRequest;
import com.egongil.numva_android_app.src.cert_phone.models.PassResponse;
import com.egongil.numva_android_app.src.config.view.BaseActivity;
import com.egongil.numva_android_app.src.config.models.base.ErrorResponse;
import com.egongil.numva_android_app.src.edit_userinfo.EditUserInfoActivity;
import com.egongil.numva_android_app.src.signup.SignupMoreInfoActivity;


public class PassActivity extends BaseActivity implements PassActivityView {
    private WebView mWvPass = null;
    private Handler handler;
//    private String mImp_uid;
    private String mName, mPhone, mBirth, mBirth_only_number;
    private String request_page;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pass);

        Intent intent = getIntent();
        request_page = intent.getStringExtra("request_page");

        mWvPass = findViewById(R.id.pass_webview);
        initWebView();
        handler = new Handler();

    }

    public void initWebView(){
        String webUrl = getString(R.string.danal_pass_url);
        mWvPass.getSettings().setJavaScriptEnabled(true); //JavaScript 실행가능
        mWvPass.getSettings().setJavaScriptCanOpenWindowsAutomatically(true); //JavaScript가 window.open()을 사용 가능
        mWvPass.setWebViewClient(new WebViewClient());
        mWvPass.addJavascriptInterface(new PassBridge(), "PassBridge");
        mWvPass.loadUrl(webUrl);

    }

    @Override
    public void postPassSuccess(PassResponse passResponse, ErrorResponse errorResponse) {
        if(passResponse != null) {
            if(passResponse.getCode()==200 && passResponse.isSuccess()) {
                mName = passResponse.getPassResult().getName();
                mBirth = passResponse.getPassResult().getBirthday();
                mPhone = passResponse.getPassResult().getPhone();
                mBirth_only_number = mBirth.replace("-", "");


                if(request_page.equals("signup")) {
                    Intent intent_signup = new Intent(this, SignupMoreInfoActivity.class);
                    intent_signup.putExtra("name", mName);
                    intent_signup.putExtra("phone", mPhone);
                    intent_signup.putExtra("birth", mBirth_only_number);
                    setResult(RESULT_OK, intent_signup);
                    startActivity(intent_signup);
                }
                else if(request_page.equals("edituserinfo")){
                    Intent intent_edituserinfo = new Intent(this, EditUserInfoActivity.class);
                    intent_edituserinfo.putExtra("name", mName);
                    intent_edituserinfo.putExtra("phone", mPhone);
                    intent_edituserinfo.putExtra("birth", mBirth_only_number);
                    setResult(RESULT_OK, intent_edituserinfo);
                    finish();
                }
            }
        }else if(errorResponse!=null){

        }

    }

    @Override
    public void postPassFailure() {

    }

    public final class PassBridge{
        //휴대폰 인증 완료 후 결과값 전달 받기
        @JavascriptInterface
        public void Result(final String imp_uid)
        {
            handler.post(new Runnable(){
                @Override
                public void run() {
                    postPass(imp_uid);
                }
            });

        }
        //Mobile WebPage에서 JavaScript function이 호출되면 닫기 처리
        public void BestClose(){
            finish();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
        if((keyCode == KeyEvent.KEYCODE_BACK) && mWvPass.canGoBack()){
            mWvPass.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void postPass(String imp_uid){
        PassService passService = new PassService(this);
        PassRequest passRequest = new PassRequest();
        passRequest.setImp_uid(imp_uid);
        passService.postPass(passRequest);
    }





}