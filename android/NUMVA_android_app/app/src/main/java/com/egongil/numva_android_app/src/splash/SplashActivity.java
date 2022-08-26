package com.egongil.numva_android_app.src.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import com.egongil.numva_android_app.R;
import com.egongil.numva_android_app.src.config.view.BaseActivity;
import com.egongil.numva_android_app.src.login.LoginActivity;
import com.egongil.numva_android_app.src.main.view.MainActivity;

import java.util.Random;

import static com.egongil.numva_android_app.src.config.ApplicationClass.X_ACCESS_TOKEN;
import static com.egongil.numva_android_app.src.config.ApplicationClass.sSharedPreferences;

public class SplashActivity extends BaseActivity {
    /*스플래시 화면이 표시되는 시간(ms)*/
    private final int SPLASH_DISPLAY_TIME = 2000;
    TextView mTvRand;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        mTvRand = findViewById(R.id.splash_tv_random);
        mTvRand.setText(setRandSplash());

        Handler handler = new Handler();
        handler.postDelayed(new Runnable(){
            @Override
            public void run() {
                String jwtToken = sSharedPreferences.getString(X_ACCESS_TOKEN,"");
                if(jwtToken.equals("")){
                    startActivity(new Intent(getApplication(), LoginActivity.class));
                }
                else{
                    startActivity(new Intent(getApplication(), MainActivity.class));
                }
                SplashActivity.this.finish();
            }
        }, SPLASH_DISPLAY_TIME);

        //액티비티 전환 애니메이션 설정
        //첫 번째 인자: 새로 나타나는 화면이 취해야하 하는 애니메이션
        //두 번째 인자: 지금 화면이 취하는 애니메이션

    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
    }

    //랜덤 텍스트 발생
    private String setRandSplash(){
        Random rand = new Random();
        int n;
        String strRand;

        String[] splash_random = getResources().getStringArray(R.array.splash_random);
        n = rand.nextInt(splash_random.length-1);
        strRand = splash_random[n];

        return strRand;
    }
}