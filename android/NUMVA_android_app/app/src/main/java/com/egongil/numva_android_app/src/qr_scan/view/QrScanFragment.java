package com.egongil.numva_android_app.src.qr_scan.view;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.egongil.numva_android_app.R;
import com.egongil.numva_android_app.src.config.view.BaseFragment;


public class QrScanFragment extends BaseFragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_qr_scan, container, false);

        Button mBtnQrScan = view.findViewById(R.id.qrscan_btn_qrscan);

        mBtnQrScan.setOnClickListener(new OnSingleClickListener() {
                                          @Override
                                          public void onSingleClick(View v) {
                                              Intent intent = new Intent(getActivity(), QrScanActivity.class);
                                              startActivity(intent);
                                          }
                                      });

        setHasOptionsMenu(true);
        return view;
    }

}