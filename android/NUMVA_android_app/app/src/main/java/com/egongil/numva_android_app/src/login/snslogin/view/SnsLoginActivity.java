package com.egongil.numva_android_app.src.login.snslogin.view;

import static com.egongil.numva_android_app.src.config.ApplicationClass.USER_BIRTHDAY;
import static com.egongil.numva_android_app.src.config.ApplicationClass.USER_BIRTHYEAR;
import static com.egongil.numva_android_app.src.config.ApplicationClass.USER_EMAIL;
import static com.egongil.numva_android_app.src.config.ApplicationClass.USER_ID;
import static com.egongil.numva_android_app.src.config.ApplicationClass.USER_NAME;
import static com.egongil.numva_android_app.src.config.ApplicationClass.USER_PHONE;
import static com.egongil.numva_android_app.src.config.ApplicationClass.USER_PROVIDER;
import static com.egongil.numva_android_app.src.config.ApplicationClass.X_ACCESS_TOKEN;
import static com.egongil.numva_android_app.src.config.ApplicationClass.sSharedPreferences;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.egongil.numva_android_app.R;
import com.egongil.numva_android_app.src.config.ApplicationClass;
import com.egongil.numva_android_app.src.config.view.BaseActivity;
import com.egongil.numva_android_app.src.config.models.base.ErrorResponse;
import com.egongil.numva_android_app.src.config.GlobalAuthHelper;
import com.egongil.numva_android_app.src.login.view.LoginActivity;
import com.egongil.numva_android_app.src.login.models.SnSLoginService;
import com.egongil.numva_android_app.src.login.interfaces.SnsLoginActivityContract;
import com.egongil.numva_android_app.src.config.models.request.LinkSocialRequest;
import com.egongil.numva_android_app.src.config.models.response.LinkSocialResponse;
import com.egongil.numva_android_app.src.config.models.request.SocialRegisterRequest;
import com.egongil.numva_android_app.src.main.view.MainActivity;

import java.util.ArrayList;
import java.util.List;

public class SnsLoginActivity extends BaseActivity implements SnsLoginActivityContract {
    public static final int EXIST_EMAIL = 0;
    public static final int MORE_INFO = 1;

    public static Context mContext;
    private List<String> userInfo = new ArrayList<>();

    boolean isLoginSuccess = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sns_login);
        mContext = this;

        //userInfo 가져옴
        ApplicationClass mGlobalHelper = new ApplicationClass();
        userInfo = mGlobalHelper.getGlobalUserLoginInfo();

        //필요한 화면 fragment를 보여줌
        setFragment(getIntent().getIntExtra("fragment",-1));


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(!isLoginSuccess){
            accountResign();
        }
    }

    //fragment 설정 메소드
    private void setFragment(int id){
        if(id == -1){
            return;
        }

        String tag;
        if(id == EXIST_EMAIL){
            tag = "EXIST_EMAIL";
        }else{
            tag = "MORE_INFO";
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        Fragment currentFragment = fragmentManager.getPrimaryNavigationFragment();
        if(currentFragment!=null){
            fragmentTransaction.hide(currentFragment);
        }

        Fragment fragment = fragmentManager.findFragmentByTag(tag);
        if(fragment == null){
            if(id == EXIST_EMAIL){
                fragment = new ExistEmailFragment();
            }else{
                fragment = new MoreInfoFragment();
            }
            fragmentTransaction.add(R.id.snslogin_fl_content_layout, fragment, tag);
        }
        else{
            fragmentTransaction.show(fragment);
        }
        fragmentTransaction.setPrimaryNavigationFragment(fragment);
        fragmentTransaction.setReorderingAllowed(true);
        fragmentTransaction.commitNow();
    }

    public void accountResign(){
        GlobalAuthHelper.accountResign(getApplicationContext());

        Intent intent = new Intent(SnsLoginActivity.this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public void linkSocial(){
        SnSLoginService snsLoginService = new SnSLoginService(this);
        LinkSocialRequest linkSocialRequest = new LinkSocialRequest();

        linkSocialRequest.setSocial_id(userInfo.get(USER_ID));
        linkSocialRequest.setProvider(userInfo.get(USER_PROVIDER));
        linkSocialRequest.setEmail(userInfo.get(USER_EMAIL));

        snsLoginService.linkSocial(linkSocialRequest);
    }

    @Override
    public void linkSocialSuccess(LinkSocialResponse linkSocialResponse, ErrorResponse errorResponse) {
        if(linkSocialResponse!=null) {
            //링크 성공 시
            if (linkSocialResponse.getCode() == 200 && linkSocialResponse.isSuccess()) {
                SharedPreferences.Editor editor = sSharedPreferences.edit();
                editor.putString(X_ACCESS_TOKEN, linkSocialResponse.getAccess_token());
                editor.commit();

                isLoginSuccess = true;

                System.out.println("TOKEN: " + linkSocialResponse.getAccess_token());
                System.out.println("SHARED_TOKEN: " + sSharedPreferences.getString(X_ACCESS_TOKEN, "NULL"));

                Intent intent = new Intent(SnsLoginActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        }
        else if(errorResponse !=null){
            showCustomToast(getResources().getString(R.string.login_failure_toast));
        }
    }

    @Override
    public void linkSocialFailure() {
        showCustomToast(getResources().getString(R.string.network_error));
    }


    public void socialRegister(String nickname, int agreement_marketing){
        SnSLoginService snsLoginService = new SnSLoginService(this);
        SocialRegisterRequest socialRegisterRequest = new SocialRegisterRequest();

        socialRegisterRequest.setName(userInfo.get(USER_NAME));
        socialRegisterRequest.setEmail(userInfo.get(USER_EMAIL));
        socialRegisterRequest.setPhone(userInfo.get(USER_PHONE));
        socialRegisterRequest.setBirth(userInfo.get(USER_BIRTHYEAR)+userInfo.get(USER_BIRTHDAY));
        socialRegisterRequest.setNickname(nickname);
        socialRegisterRequest.setProvider(userInfo.get(USER_PROVIDER));
        socialRegisterRequest.setSocial_id(userInfo.get(USER_ID));
        socialRegisterRequest.setAgreement_marketing(agreement_marketing);

        snsLoginService.socialRegister(socialRegisterRequest);

    }
    @Override
    public void socialRegisterSuccess(LinkSocialResponse linkSocialResponse, ErrorResponse errorResponse) {
        if(linkSocialResponse!=null) {
            //링크 성공 시
            if (linkSocialResponse.getCode() == 200 && linkSocialResponse.isSuccess()) {
                SharedPreferences.Editor editor = sSharedPreferences.edit();
                editor.putString(X_ACCESS_TOKEN, linkSocialResponse.getAccess_token());
                editor.commit();

                isLoginSuccess = true;

                System.out.println("TOKEN: " + linkSocialResponse.getAccess_token());
                System.out.println("SHARED_TOKEN: " + sSharedPreferences.getString(X_ACCESS_TOKEN, "NULL"));

                Intent intent = new Intent(SnsLoginActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        }
        else if(errorResponse !=null){
            showCustomToast(getResources().getString(R.string.login_failure_toast));
        }
    }

    @Override
    public void socialRegisterFailure() {
        showCustomToast(getResources().getString(R.string.network_error));
    }
}