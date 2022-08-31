package com.egongil.numva_android_app.src.findLogin.view;

import static com.egongil.numva_android_app.src.config.ApplicationClass.FragmentType.FIND_ID_FRAGMENT;
import static com.egongil.numva_android_app.src.config.ApplicationClass.FragmentType.FIND_PW_FRAGMENT;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.egongil.numva_android_app.R;
import com.egongil.numva_android_app.src.config.ApplicationClass;
import com.egongil.numva_android_app.src.config.view.BaseActivity;
import com.egongil.numva_android_app.src.network.ConnectionReceiver;
import com.egongil.numva_android_app.src.network.NetworkFailureActivity;

public class FindLoginActivity extends BaseActivity implements ConnectionReceiver.ConnectionReceiverListener{
    private final int Fragment_id = 1;
    private final int Fragment_pw = 2;
    TextView mTvTitle, mTvIDTapTitle, mTvPWTapTitle;
    View line_id_active, line_pw_active;
    View line_id_inactive, line_pw_inactive;
    ImageView mIvBackBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_login);

        checkConnection(); //네트워크 확인

        mTvTitle = findViewById(R.id.find_login_tv_title);
        mTvIDTapTitle = findViewById(R.id.find_login_tv_idtap);
        mTvPWTapTitle = findViewById(R.id.find_login_tv_pwtap);
        line_id_active = findViewById(R.id.find_login_view_idtap_active);
        line_pw_active = findViewById(R.id.find_login_view_pwtap_active);
        line_id_inactive = findViewById(R.id.find_login_view_idtap_inactive);
        line_pw_inactive = findViewById(R.id.find_login_view_pwtap_inactive);
        mIvBackBtn = findViewById(R.id.find_login_iv_backbtn);

        //fragment 초기 설정
        Intent intent = getIntent();
        int selected_fragment = intent.getExtras().getInt("fragment_name");
        if(selected_fragment == FIND_ID_FRAGMENT){
            //ID찾기로 진입
            FragmentView(Fragment_id);
            idTapClicked();
        }else if(selected_fragment == FIND_PW_FRAGMENT){
            //PW찾기로 진입
            FragmentView(Fragment_pw);
            pwTapClicked();
        }

        mIvBackBtn.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                finish();
            }
        });

        //id찾기 텍스트 눌렀을 때 화면 전환
        findViewById(R.id.find_login_tv_idtap).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                // change fragment context
                FragmentView(Fragment_id);

                //change other things
                idTapClicked();
            }
        });

        //pwd찾기 텍스트 눌렀을 때 화면 전환
        findViewById(R.id.find_login_tv_pwtap).setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                // change fragment context
                FragmentView(Fragment_pw);

                //change other things
                pwTapClicked();
            }
        });
    }

    private void FragmentView(int fragment){
        //FragmentTransaction을 이용해 프래그먼트 사용
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        switch (fragment){
            case 1:
                //id찾기 프래그먼트 호출
                FindIdFragment fragment_find_id = new FindIdFragment();
                transaction.replace(R.id.find_login_fl, fragment_find_id);
                transaction.commit();
                break;
            case 2:
                //pwd찾기 프래그먼트 호출
                FindPwFragment fragment_find_pw = new FindPwFragment();
                transaction.replace(R.id.find_login_fl, fragment_find_pw);
                transaction.commit();
                break;
        }
    }

    //Fragment -> Fragment 호출 (FindPwResetFragment에서 사용)
    public void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.find_login_fl, fragment).commit();
    }

    public void idTapClicked(){
        // change fragment context
        FragmentView(Fragment_id);

        //change title
        mTvIDTapTitle.setTextColor(getColor(R.color.colorBlack));
        mTvPWTapTitle.setTextColor(getColor(R.color.colorGrayInactive));

        //tap_title_underline active/inactive
        line_id_active.setVisibility(View.VISIBLE);
        line_id_inactive.setVisibility(View.GONE);
        line_pw_active.setVisibility(View.GONE);
        line_pw_inactive.setVisibility(View.VISIBLE);
    }

    public void pwTapClicked(){
        //change title
        mTvPWTapTitle.setTextColor(getColor(R.color.colorBlack));
        mTvIDTapTitle.setTextColor(getColor(R.color.colorGrayInactive));

        //tap_title_underline active/inactive
        line_id_active.setVisibility(View.GONE);
        line_id_inactive.setVisibility(View.VISIBLE);
        line_pw_active.setVisibility(View.VISIBLE);
        line_pw_inactive.setVisibility(View.GONE);
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
}