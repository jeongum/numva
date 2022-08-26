package com.egongil.numva_android_app.src.notification_setting;

import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;

import com.egongil.numva_android_app.R;
import com.egongil.numva_android_app.src.config.view.BaseActivity;

public class NotiSettingActivity extends BaseActivity{
    Switch mSwAll, mSwNumva, mSwMarketing;
    ImageView mIvExit;
//    Intent intent = new Intent(this, NetworkFailureActivity.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noti_setting);


        mSwAll = findViewById(R.id.noti_setting_sw_all);
        mSwNumva = findViewById(R.id.noti_setting_sw_numva);
        mSwMarketing = findViewById(R.id.noti_setting_sw_marketing);

        mIvExit = findViewById(R.id.noti_setting_iv_crossbtn);

        mSwAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mSwAll.isChecked()){
                    mSwNumva.setChecked(true);
                    mSwMarketing.setChecked(true);
                }
                else{
                    mSwNumva.setChecked(false);
                    mSwMarketing.setChecked(false);
                }
            }
        });
        mSwNumva.setOnCheckedChangeListener(swListener);
        mSwMarketing.setOnCheckedChangeListener(swListener);

        mIvExit.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                finish();
            }
        });



    }

    CompoundButton.OnCheckedChangeListener swListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if(mSwNumva.isChecked() && mSwMarketing.isChecked()){   //둘 다 체크되어있으면 all도 체크
                mSwAll.setChecked(true);
            }else if (!mSwNumva.isChecked() || !mSwMarketing.isChecked()){  //둘 중 하나라도 체크 안되면 all도 체크해제
                mSwAll.setChecked(false);
            }

        }
    };


}