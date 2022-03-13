package com.egongil.numva_android_app.src.findLogin;

import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.egongil.numva_android_app.R;
import com.egongil.numva_android_app.src.config.BaseFragment;
import com.egongil.numva_android_app.src.login.LoginActivity;

public class FindIdExistFragment extends BaseFragment {

    TextView mTvEmail;
    Button mBtnLogin;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_find_id_exist, container, false);

        mTvEmail = view.findViewById(R.id.findid_exist_tv_email);
        mBtnLogin = view.findViewById(R.id.findid_exist_btn);


        mTvEmail.setText(getArguments().getString("Email"));

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