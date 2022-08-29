package com.egongil.numva_android_app.src.findLogin.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.egongil.numva_android_app.R;
import com.egongil.numva_android_app.src.config.view.BaseFragment;
import com.egongil.numva_android_app.src.login.LoginActivity;

public class PwResetDoneFragment extends BaseFragment {

    Button mBtnLogin;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_reset_pw_done, container, false);

        mBtnLogin = view.findViewById(R.id.resetpw_btn_login);

        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

}
