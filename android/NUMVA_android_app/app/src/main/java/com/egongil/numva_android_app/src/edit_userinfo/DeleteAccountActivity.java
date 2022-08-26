package com.egongil.numva_android_app.src.edit_userinfo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;

import com.egongil.numva_android_app.R;
import com.egongil.numva_android_app.src.config.BaseActivity;
import com.egongil.numva_android_app.src.config.ErrorResponse;
import com.egongil.numva_android_app.src.edit_userinfo.interfaces.DeleteAccountActivityView;
import com.egongil.numva_android_app.src.config.models.DeleteAccountResponse;
import com.egongil.numva_android_app.src.network.ConnectionReceiver;
import com.egongil.numva_android_app.src.network.NetworkFailureActivity;

public class DeleteAccountActivity extends BaseActivity implements DeleteAccountActivityView, ConnectionReceiver.ConnectionReceiverListener {

    ImageView mBtnBack;
    Button mBtnDelete;
    CheckBox mCheckAgree;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_account);

        checkConnection(); //네트워크 확인

        mBtnBack = findViewById(R.id.delete_account_iv_backbtn);  //뒤로가기 버튼
        mCheckAgree = findViewById(R.id.delete_account_check_agree); //탈퇴 동의 CheckBox
        mBtnDelete = findViewById(R.id.delete_account_btn_save); //탈퇴 버튼

        mBtnBack.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                finish();
            }
        });


        mBtnDelete.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                if(mCheckAgree.isChecked()) {
                    deleteAccount();
                    finish();
                }
                else{
                    //TODO : CheckBox 해제 시
                }
            }
        });
    }

    public void deleteAccount(){
        DeleteAccountService deleteAccountService = new DeleteAccountService(this);
        deleteAccountService.deleteAccount();
    }

    @Override
    public void deleteAccountSuccess(DeleteAccountResponse deleteAccountResponse, ErrorResponse errorResponse) {

    }

    @Override
    public void deleteAccountFailure() {

    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        if(!isConnected){
            Intent intent = new Intent(this, NetworkFailureActivity.class);
            startActivity(intent);
        }
    }

    private void checkConnection(){
        boolean isConnected = ConnectionReceiver.isConnected();
        if(!isConnected){
            Intent intent = new Intent(this, NetworkFailureActivity.class);
            startActivity(intent);
        }
    }
}