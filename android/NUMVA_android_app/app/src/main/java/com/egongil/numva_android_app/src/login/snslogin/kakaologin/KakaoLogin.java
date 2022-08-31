package com.egongil.numva_android_app.src.login.snslogin.kakaologin;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

import com.egongil.numva_android_app.src.config.ApplicationClass;
import com.egongil.numva_android_app.src.login.view.LoginActivity;
import com.kakao.auth.ISessionCallback;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.MeV2ResponseCallback;
import com.kakao.usermgmt.response.MeV2Response;
import com.kakao.util.exception.KakaoException;

import java.util.ArrayList;
import java.util.List;

public class KakaoLogin extends Activity {

    public static class KaKaoSessionCallback implements ISessionCallback {
        private Context mContext;
        private LoginActivity loginActivity;

        public KaKaoSessionCallback(Context context, LoginActivity activity){
            this.mContext = context;
            this.loginActivity = activity;
        }

        @Override
        public void onSessionOpened() {
            requestMe();
        }

        @Override
        public void onSessionOpenFailed(KakaoException exception) {
            Toast.makeText(mContext, "KaKao 로그인 오류가 발생했습니다. " + exception.toString(), Toast.LENGTH_SHORT).show();
        }

        protected void requestMe(){
            UserManagement.getInstance().me(new MeV2ResponseCallback() {
                @Override
                public void onSessionClosed(ErrorResult errorResult) {
                    loginActivity.directToSecondActivity(false);
                }

                @Override
                public void onSuccess(MeV2Response result) {
                    List userInfo = new ArrayList<>();

                    userInfo.add(String.valueOf(result.getId()));   //회원번호(0)
                    userInfo.add(result.getKakaoAccount().getProfile().getNickname());  //닉네임(이름)(1)
                    userInfo.add(result.getKakaoAccount().getEmail());  //이메일(2)
                    userInfo.add(result.getKakaoAccount().getPhoneNumber()); //휴대폰번호(3)
                    userInfo.add(result.getKakaoAccount().getBirthyear()); //생년(4)
                    userInfo.add(result.getKakaoAccount().getBirthday()); //월일(5)

                    userInfo.add("kakao"); //provider(6);

                    ApplicationClass mGlobalHelper = new ApplicationClass();
                    mGlobalHelper.setGlobalUserLoginInfo(userInfo);

                    loginActivity.directToSecondActivity(true);
                }
            });
        }
    }
}
