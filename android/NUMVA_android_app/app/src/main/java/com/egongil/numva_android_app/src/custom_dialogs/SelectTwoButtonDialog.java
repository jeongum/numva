package com.egongil.numva_android_app.src.custom_dialogs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.egongil.numva_android_app.R;

public class SelectTwoButtonDialog extends Dialog {
    private Context mContext;
    public LinearLayout mLlCrossBtn;
    public TextView mTvTitle, mTvOption1, mTvOption2;

    public SelectTwoButtonDialog(@NonNull Context context) {
        super(context);
        this.mContext = context;
    }

    public void showDialog(){
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.setContentView(R.layout.dialog_select_twobtn_dialog);
        this.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        this.show();

        mLlCrossBtn = findViewById(R.id.dialog_select_twobtn_ll_crossbtn);
        mTvTitle = findViewById(R.id.dialog_select_twobtn_tv_title);
        mTvOption1 = findViewById(R.id.dialog_select_twobtn_tv_option1);
        mTvOption2 = findViewById(R.id.dialog_select_twobtn_tv_option2);

        setListener();
    }
    public void setTitleText(String title){
        mTvTitle.setText(title);
    }

    public void setOptionText(String option1, String option2){
        mTvOption1.setText(option1);
        mTvOption2.setText(option2);
    }

    public void setListener(){
        mLlCrossBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

}