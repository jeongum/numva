package com.egongil.numva_android_app.src.main;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.egongil.numva_android_app.R;
import com.egongil.numva_android_app.src.config.ApplicationClass;
import com.egongil.numva_android_app.src.config.BaseActivity;
import com.egongil.numva_android_app.src.car_management.CarManagementFragment;
import com.egongil.numva_android_app.src.config.Callback;

import com.egongil.numva_android_app.src.config.ErrorResponse;

import com.egongil.numva_android_app.src.config.GlobalAuthHelper;
import com.egongil.numva_android_app.src.home.HomeFragment;
import com.egongil.numva_android_app.src.login.LoginActivity;
import com.egongil.numva_android_app.src.main.interfaces.MainActivityView;
import com.egongil.numva_android_app.src.mypage.MyPageFragment;

import com.egongil.numva_android_app.src.network.ConnectionReceiver;
import com.egongil.numva_android_app.src.network.NetworkFailureActivity;

import com.egongil.numva_android_app.src.numvatalk.chatlist.NumvaTalkFragment;

import com.egongil.numva_android_app.src.qr_scan.QrScanFragment;

import com.egongil.numva_android_app.src.store.StoreActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import static com.egongil.numva_android_app.src.config.ApplicationClass.MESIBO_TOKEN;
import static com.egongil.numva_android_app.src.config.ApplicationClass.X_ACCESS_TOKEN;
import static com.egongil.numva_android_app.src.config.ApplicationClass.sSharedPreferences;
import com.egongil.numva_android_app.src.main.models.GetUserResponse;
import com.mesibo.api.Mesibo;
import com.mesibo.api.MesiboProfile;
import com.mesibo.calls.api.MesiboCall;

public class MainActivity extends BaseActivity implements MainActivityView , ConnectionReceiver.ConnectionReceiverListener,
Mesibo.MessageListener, Mesibo.ConnectionListener{
    public static String TAG = "MAIN_ACTIVITY";
    public static Context mContext;
    private long backKeyPressedTime = 0;
    Toast exitToast;
    private BottomNavigationView mBottomNV;
    public GetUserResponse.Result userInfo;

    //Mesibo
    MesiboProfile mRemoteProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkConnection(); //네트워크 연결 확인

        mContext = this;

        ImageView mIvQrScan = findViewById(R.id.main_iv_qrscan);
        ImageView mIvStore = findViewById(R.id.main_iv_store);
        ImageView mIvAppLogo = findViewById(R.id.main_iv_applogo);

        this.initializeFragment();

        mBottomNV = findViewById(R.id.nav_view);
        mBottomNV.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                BottomNavigate(menuItem.getItemId());
                return true;
            }
        });

        if(!sSharedPreferences.getString(X_ACCESS_TOKEN,"").equals("")){
            Callback mCallback = new Callback() {
                @Override
                public void callback() {
                    ((HomeFragment)getSupportFragmentManager().findFragmentByTag(String.valueOf(R.id.nav_home))).setInitialLoginState();
                    ((MyPageFragment)getSupportFragmentManager().findFragmentByTag(String.valueOf(R.id.nav_mypage))).setInitialLoginState();
                }
            };
            getUser(mCallback);  //Activity에서 user정보 한 번만 받아온다.
        }

        mBottomNV.setSelectedItemId(R.id.nav_home);

        mIvAppLogo.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                mBottomNV.setSelectedItemId(R.id.nav_home);
            }
        });
        mIvQrScan.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                mBottomNV.setSelectedItemId(R.id.nav_qrscan);
                }
        });
        mIvStore.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                Intent intent = new Intent(getApplicationContext(), StoreActivity.class);
                startActivity(intent);
            }
        });

        mesiboInit();

    }
    public void mesiboInit(){
        Mesibo api = Mesibo.getInstance();
        api.init(getApplicationContext());

        //this user
        Mesibo.addListener(this);
        Mesibo.setSecureConnection(true);

        if(!sSharedPreferences.getString(MESIBO_TOKEN,"").equals("")){
            Callback mCallback = new Callback() {
                @Override
                public void callback() {
                    ((HomeFragment)getSupportFragmentManager().findFragmentByTag(String.valueOf(R.id.nav_home))).setInitialLoginState();
                    ((NumvaTalkFragment)getSupportFragmentManager().findFragmentByTag(String.valueOf(R.id.nav_numvatalk))).setInitialLoginState();
                    ((MyPageFragment)getSupportFragmentManager().findFragmentByTag(String.valueOf(R.id.nav_mypage))).setInitialLoginState();
                }
            };
            getUser(mCallback);  //Activity에서 user정보 한 번만 받아온다.
        }
        Mesibo.setAccessToken(sSharedPreferences.getString(MESIBO_TOKEN, null));
        Log.d("MESIBO_TOKEN", sSharedPreferences.getString(MESIBO_TOKEN, "null"));
        Mesibo.start();

        MesiboCall.getInstance().init(this);
    }
    public void getUser(Callback mCallback){
        MainService mainService = new MainService(this);
        mainService.getUser(mCallback);
    }
    private void initializeFragment(){
        BottomNavigate(R.id.nav_numvatalk);
        BottomNavigate(R.id.nav_car_management);
        BottomNavigate(R.id.nav_mypage);
        BottomNavigate(R.id.nav_qrscan);
        BottomNavigate(R.id.nav_home);
    }

    public void accountLogout(){
        GlobalAuthHelper.accountLogout(getApplicationContext());
    }

    private void BottomNavigate(int id){
        //BottomNavigation 페이지 변경
        String tag = String.valueOf(id);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        Fragment currentFragment = fragmentManager.getPrimaryNavigationFragment();
        if(currentFragment != null){
            fragmentTransaction.hide(currentFragment);
        }

        Fragment fragment = fragmentManager.findFragmentByTag(tag);

        if(fragment == null) {
            if (id == R.id.nav_home) {
                fragment = new HomeFragment();
            }
            else if (id == R.id.nav_numvatalk) {
                fragment = new NumvaTalkFragment();
            }
            else if (id == R.id.nav_car_management) {
                fragment = new CarManagementFragment();
            }
            else if(id == R.id.nav_mypage){
                fragment = new MyPageFragment();
            }
            else if(id == R.id.nav_qrscan){
                fragment = new QrScanFragment();
            }
            fragmentTransaction.add(R.id.content_layout, fragment, tag);
        }
        else{
            fragmentTransaction.show(fragment);
        }
        fragmentTransaction.setPrimaryNavigationFragment(fragment);
        fragmentTransaction.setReorderingAllowed(true);
        fragmentTransaction.commitNow();
    }

    @Override
    public void onBackPressed() {
        if(System.currentTimeMillis() > backKeyPressedTime + 2000){
            backKeyPressedTime = System.currentTimeMillis();
            exitToast = showCustomToast(getResources().getString(R.string.alert_app_exit));
        }else if(System.currentTimeMillis() <= backKeyPressedTime + 2000){
            finish();
            exitToast.cancel();
        }
    }

    @Override
    public void getUserSuccess(GetUserResponse getUserResponse, ErrorResponse errorResponse) {
        if(getUserResponse!=null){
            if(getUserResponse.getCode()==200 && getUserResponse.isSuccess()){
                userInfo = getUserResponse.getUser();
            }
        }
        else if(errorResponse != null){
            /*모든 에러코드에 대해 로그인 세션 만료로 동일 처리하므로, 에러코드 분기 없음*/

            //로그인 상태였을 경우
            if(sSharedPreferences.getString(X_ACCESS_TOKEN,null)!=null){
                //toast 메시지 발생
                showCustomToast(getResources().getString(R.string.alert_invalid_token));

                //로그인 페이지로 이동
                startActivity(new Intent(getApplication(), LoginActivity.class));
                finish();
            }

            //token 초기화
            SharedPreferences.Editor editor = sSharedPreferences.edit();
            editor.putString(X_ACCESS_TOKEN, null);
            editor.commit();

            //비로그인 상태로 정의
            ((HomeFragment)getSupportFragmentManager().findFragmentByTag(String.valueOf(R.id.nav_home))).setInitialLoginState();
            ((MyPageFragment)getSupportFragmentManager().findFragmentByTag(String.valueOf(R.id.nav_mypage))).setInitialLoginState();


//            if(errorResponse.getCode()==401 && !errorResponse.isSuccess()){
//                //token 만료된 경우
//                //token 초기화
//                SharedPreferences.Editor editor = sSharedPreferences.edit();
//                editor.putString(X_ACCESS_TOKEN, null);
//                editor.commit();
//                startActivity(new Intent(getApplication(), LoginActivity.class));
//                finish();
//                showCustomToast(getResources().getString(R.string.alert_invalid_token));
//            }

        }
    }

    @Override
    public void getUserFailure() {
        showCustomToast(getResources().getString(R.string.network_error));
    }

    public void callGetUser(){
        Callback mCallback = new Callback() {
            @Override
            public void callback() {
                ((HomeFragment)getSupportFragmentManager().findFragmentByTag(String.valueOf(R.id.nav_home))).setLoginState();
                ((MyPageFragment)getSupportFragmentManager().findFragmentByTag(String.valueOf(R.id.nav_mypage))).setLoginState();
            }
        };
        getUser(mCallback);
    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        if(!isConnected){
            //show a No Internet Alert or Dialog
            Intent intent = new Intent(this, NetworkFailureActivity.class);
            startActivity(intent);
        }else{
            //dismiss the dialog or refresh the activity
        }
    }

    @Override
    protected void onResume(){
        super.onResume();
        //register connection status listener
        ApplicationClass.getInstance().setConnectionListener(this);

    }

    private void checkConnection(){
        boolean isConnected = ConnectionReceiver.isConnected();
        if(!isConnected){
            //show a No Internet Alert or Dialog
            Intent intent = new Intent(this, NetworkFailureActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void Mesibo_onConnectionStatus(int status) {
        // You will receive the connection status here
        Log.d(TAG, "on Mesibo Connection: " + status);
    }

    @Override
    public boolean Mesibo_onMessage(Mesibo.MessageParams messageParams, byte[] data) {
        // You will receive messages here
        try {
            String message = new String(data, "UTF-8");
//            showCustomToast("You have got a message: " + message);
        } catch (Exception e) {
        }

        return true;
    }

    @Override
    public void Mesibo_onMessageStatus(Mesibo.MessageParams messageParams) {
        // You will receive status of sent messages here
    }

    @Override
    public void Mesibo_onActivity(Mesibo.MessageParams messageParams, int i) {

    }

    @Override
    public void Mesibo_onLocation(Mesibo.MessageParams messageParams, Mesibo.Location location) {

    }

    @Override
    public void Mesibo_onFile(Mesibo.MessageParams messageParams, Mesibo.FileInfo fileInfo) {

    }


}