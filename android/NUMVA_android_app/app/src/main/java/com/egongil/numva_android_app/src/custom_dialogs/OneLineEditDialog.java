package com.egongil.numva_android_app.src.custom_dialogs;

import androidx.annotation.NonNull;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.egongil.numva_android_app.R;

public class OneLineEditDialog extends Dialog {
    Context mContext;

    public TextView mTvTitle;
    public EditText mEtContext;
    public LinearLayout mLlCrossBtn;
    public ImageView mIvRemoveBtn;
    public TextView mTvGuide, mTvConfirmBtn;

    public OneLineEditDialog(@NonNull Context context) {
        super(context);
        this.mContext = context;
    }
    public void showDialog(){
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.setContentView(R.layout.dialog_oneline_edit_dialog);
        this.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        this.show();

        this.mTvTitle = findViewById(R.id.dialog_oneline_edit_tv_title);
        this.mEtContext = findViewById(R.id.dialog_oneline_edit_et_context);
        this.mLlCrossBtn = findViewById(R.id.dialog_oneline_edit_ll_crossbtn);
        this.mIvRemoveBtn = findViewById(R.id.dialog_oneline_edit_iv_removebtn);
        this.mTvGuide = findViewById(R.id.dialog_oneline_edit_tv_guide);
        this.mTvConfirmBtn = findViewById(R.id.dialog_oneline_edit_tv_confirm);

        setListener();
        mEtContext.requestFocus();
        showKeyboard();
    }
    public void setListener(){
        mIvRemoveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEtContext.setText("");
            }
        });

        mLlCrossBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        mEtContext.addTextChangedListener(new EditTextWatcher(mEtContext));

        setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                closeKeyboard();
            }
        });

    }

    public void setTitleText(String titleText){
        mTvTitle.setText(titleText);
    }
    public void setTextOfEdit(String editText){
        mEtContext.setText(editText);
        mEtContext.setSelection(mEtContext.length());
//        mEtContext.requestFocus();
    }
    public void setEditHint(String hint){
        mEtContext.setHint(hint);
    }
    public void setConfirmBtnText(String confirmBtnText){
        mTvConfirmBtn.setText(confirmBtnText);
    }
    public void setGuideText(String guideText){
        mTvGuide.setText(guideText);
    }
    public void setGuideColor(int color){
        mTvGuide.setTextColor(color);
    }

    public String getTextOfEdit(){
        return mEtContext.getText().toString();
    }


    public void showKeyboard(){
        InputMethodManager inputMethodManager = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);

    }
    public void closeKeyboard(){
        InputMethodManager inputMethodManager = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
    }

    private class EditTextWatcher implements TextWatcher {
        private View view;
        private EditTextWatcher(EditText view){
            this.view = view;
        }
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if(s.toString().equals("")){
                mIvRemoveBtn.setVisibility(View.GONE);
            }else{
                mIvRemoveBtn.setVisibility(View.VISIBLE);
            }
        }
    }
}