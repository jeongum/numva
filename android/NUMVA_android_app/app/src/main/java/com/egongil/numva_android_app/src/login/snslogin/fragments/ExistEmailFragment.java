package com.egongil.numva_android_app.src.login.snslogin.fragments;

import static com.egongil.numva_android_app.src.config.ApplicationClass.USER_EMAIL;
import static com.egongil.numva_android_app.src.config.ApplicationClass.USER_ID;
import static com.egongil.numva_android_app.src.config.ApplicationClass.USER_PROVIDER;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.egongil.numva_android_app.R;
import com.egongil.numva_android_app.src.config.ApplicationClass;
import com.egongil.numva_android_app.src.config.GlobalAuthHelper;
import com.egongil.numva_android_app.src.login.snslogin.SnsLoginActivity;

import java.util.ArrayList;
import java.util.List;

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