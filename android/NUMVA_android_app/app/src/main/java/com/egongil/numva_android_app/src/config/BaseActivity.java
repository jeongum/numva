package com.egongil.numva_android_app.src.config;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.os.Build;
import android.os.SystemClock;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.egongil.numva_android_app.src.network.NetworkFailureActivity;

public class BaseActivity extends AppCompatActivity {

    public Toast showCustomToast(final String message) {
        Toast toast = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG);
        toast.show();
        return toast;
    }

    public abstract class OnSingleClickListener implements View.OnClickListener {
        //중복클릭시간차이
        private static final long MIN_CLICK_INTERVAL=1000;

        //마지막으로 클릭한 시간
        private long mLastClickTime;

        public abstract void onSingleClick(View v);

        @Override
        public final void onClick(View v) {
            //현재 클릭한 시간
            long currentClickTime= SystemClock.uptimeMillis();
            //이전에 클릭한 시간과 현재시간의 차이
            long elapsedTime=currentClickTime-mLastClickTime;
            //마지막클릭시간 업데이트
            mLastClickTime=currentClickTime;

            //내가 정한 중복클릭시간 차이를 안넘었으면 클릭이벤트 발생못하게 return
            if(elapsedTime<=MIN_CLICK_INTERVAL)
                return;
            //중복클릭시간 아니면 이벤트 발생
            onSingleClick(v);
        }
    }

    public void hideKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        if(getCurrentFocus() instanceof EditText){
            inputMethodManager.hideSoftInputFromWindow(
                    activity.getCurrentFocus().getWindowToken(), 0);
        }
    }
    //EditText 바깥 터치 시 focus 해제
    public void editTextSetCancelable(View rootView, Activity activity){
        rootView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(getCurrentFocus() instanceof EditText){
                    EditText editText = (EditText) getCurrentFocus();
                    hideKeyboard(activity);
                    editText.clearFocus();
                }
                return false;
            }
        });
    }

    //네트워크 연결 유형 (예외처리가 필요할 때 사용)
//    public Boolean isNetworkAvailable(){
//
//        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
//        //getSystemService
//
//        if(connectivityManager != null){
//            if(android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
//                NetworkCapabilities capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.getActiveNetwork());
//                if(capabilities != null){
//                    if(capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)){
//                        return true;
//                    }
//                    else if(capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)){
//                        return true;
//                    }
//                    else if(capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)){
//                        return true;
//                    }
//                }
//            }
//        }
//        else{
//            Intent intent = new Intent(this, NetworkFailureActivity.class);
//            startActivity(intent) ;
//            return false;
//        }
//        return false;
//    }
}
