package com.egongil.numva_android_app.src.config;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.egongil.numva_android_app.src.login.snslogin.SnsLoginActivity;
import com.egongil.numva_android_app.src.login.snslogin.naverlogin.NaverLogin;
import com.kakao.auth.ApiErrorCode;
import com.kakao.auth.Session;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.LogoutResponseCallback;
import com.kakao.usermgmt.callback.UnLinkResponseCallback;
import com.nhn.android.naverlogin.OAuthLogin;
import com.nhn.android.naverlogin.data.OAuthLoginState;

public class GlobalAuthHelper {
    //로그아웃
        public static void accountLogout(Context context){
        if(Session.getCurrentSession().checkAndImplicitOpen()){
            //kakao
            UserManagement.getInstance().requestLogout(new LogoutResponseCallback() {
                @Override
                public void onCompleteLogout() {
                }
            });
        }else if(OAuthLoginState.OK.equals(OAuthLogin.getInstance().getState(context))){
            //naver
            OAuthLogin.getInstance().logout(context);
        }
    }

    //연동 해제
    public static void accountResign(final Context context){
        if (Session.getCurrentSession().checkAndImplicitOpen()) {
            // 카카오 연동 해제
            UserManagement.getInstance().requestUnlink(new UnLinkResponseCallback() {
                @Override
                public void onFailure(ErrorResult errorResult) {
                    if (errorResult.getErrorCode() == ApiErrorCode.CLIENT_ERROR_CODE) {
                        Toast.makeText(context, "네트워크가 불안정합니다.", Toast.LENGTH_SHORT).show();
                        Log.e("GlobalAuthHelper","accountResign: onFailure");
                    }
                }

                @Override
                public void onSessionClosed(ErrorResult errorResult) {
                    Toast.makeText(context, "세션이 닫혀있습니다.", Toast.LENGTH_SHORT).show();
                    Log.e("GlobalAuthHelper","accountResign: onSessionClosed");
                }

                @Override
                public void onSuccess(Long result) {
                    Log.d("GlobalAuthHelper","accountResign: onSuccess");
                }
            });
        }else if(OAuthLoginState.OK.equals(OAuthLogin.getInstance().getState(context))){
            //네이버 연동 해제
            try{
                NaverLogin.DeleteTokenTask deleteTokenTask = new NaverLogin.DeleteTokenTask(context);
                deleteTokenTask.execute(context).get();
                //todo: 연동해제 비정상 동작일 경우 예외처리
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }
}
