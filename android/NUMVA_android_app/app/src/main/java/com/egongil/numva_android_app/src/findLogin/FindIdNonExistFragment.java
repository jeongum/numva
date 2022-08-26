package com.egongil.numva_android_app.src.findLogin;

import android.content.Intent;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.egongil.numva_android_app.R;
import com.egongil.numva_android_app.src.config.view.BaseFragment;
import com.egongil.numva_android_app.src.main.view.MainActivity;
import com.egongil.numva_android_app.src.signup.SignupTermsActivity;

public class FindIdNonExistFragment extends BaseFragment {

    Button mBtnHome, mBtnSignup;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_find_id_non_exist, container, false);

        mBtnHome = view.findViewById(R.id.findid_nonexist_btn_home);
        mBtnSignup = view.findViewById(R.id.findid_nonexist_btn_signup);

        mBtnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
            }
        });

        mBtnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SignupTermsActivity.class);
                startActivity(intent);
            }
        });


        return view;
    }
}