package com.egongil.numva_android_app.src.custom_dialogs;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.egongil.numva_android_app.R;

public class EditTextDialog extends Dialog {
    Context mContext;

    public EditText mEtText;
    public TextView mTvSave;
    public LinearLayout mLlClose;

    public EditTextDialog(@NonNull Context context) {
        super(context);
        this.mContext = context;
    }
    public void showDialog(){
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.setContentView(R.layout.dialog_edittext);
        this.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        this.show();

        this.mEtText = findViewById(R.id.dialog_editText_et);
        this.mLlClose = findViewById(R.id.dialog_edittext_ll_crossbtn);
        this.mTvSave = findViewById(R.id.dialog_edittext_tv_save);
    }

    public void setBtnText(String btnText){
        mTvSave.setText(btnText);
    }
    public void setEditText(String editText){
        mEtText.setText(editText);
        mEtText.setSelection(mEtText.length());
    }
    public void showKeyboard(){
        InputMethodManager inputMethodManager = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);

    }
    public void closeKeyboard(){
        InputMethodManager inputMethodManager = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
    }
}
