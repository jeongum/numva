package com.egongil.numva_android_app.src.login.snslogin.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.egongil.numva_android_app.R;

public class ExistEmailFragment extends Fragment {

    Button mBtnBack, mBtnLink;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_exist_email, container, false);

        mBtnBack = view.findViewById(R.id.sns_login_exist_btn_back);
        mBtnLink = view.findViewById(R.id.sns_login_exist_btn_confirm);

        mBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //연동 해제
                ((SnsLoginActivity)SnsLoginActivity.mContext).accountResign();
            }
        });

        mBtnLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //연동
                ((SnsLoginActivity)SnsLoginActivity.mContext).linkSocial();
            }
        });

        return view;
    }
}