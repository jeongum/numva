package com.egongil.numva_android_app.src.custom_dialogs;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.egongil.numva_android_app.R;

public class TwoButtonDialog extends Dialog {
    private Context context;

    public TextView mTvContext;
    public Button mBtnLeft, mBtnRight;

    public TwoButtonDialog(@NonNull Context context) {
        super(context);
        this.context = context; //호출한 activity 전달
    }
    public void showDialog(){
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.setContentView(R.layout.dialog_twobtn);
        this.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        this.show();

        this.mTvContext = findViewById(R.id.dialog_twobtn_tv_context);
        this.mBtnLeft = findViewById(R.id.dialog_twobtn_btn_left);
        this.mBtnRight = findViewById(R.id.dialog_twobtn_btn_right);
    }
    public void setContextText(String str){
        mTvContext.setText(str);
    }

    public void setSelectText(String left, String right){
        mBtnLeft.setText(left);
        mBtnRight.setText(right);
    }
}
