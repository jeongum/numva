package com.egongil.numva_android_app.src.second_phone.view;

import static com.egongil.numva_android_app.src.config.ApplicationClass.ActivityType.SECONDPHONE_REGISTER_ACTIVITY;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.egongil.numva_android_app.R;
import com.egongil.numva_android_app.databinding.ActivitySecondPhoneBinding;
import com.egongil.numva_android_app.src.config.ApplicationClass;
import com.egongil.numva_android_app.src.config.view.BaseActivity;
import com.egongil.numva_android_app.src.config.models.base.ErrorResponse;
import com.egongil.numva_android_app.src.custom_dialogs.TwoButtonDialog;
import com.egongil.numva_android_app.src.main.view.MainActivity;
import com.egongil.numva_android_app.src.network.ConnectionReceiver;
import com.egongil.numva_android_app.src.network.NetworkFailureActivity;
import com.egongil.numva_android_app.src.second_phone.models.SecondPhoneRecyclerItem;
import com.egongil.numva_android_app.src.second_phone.models.SecondPhoneService;
import com.egongil.numva_android_app.src.second_phone.interfaces.SecondPhoneActivityContract;
import com.egongil.numva_android_app.src.config.models.request.DeleteSecondPhoneRequest;
import com.egongil.numva_android_app.src.config.models.response.DeleteSecondPhoneResponse;
import com.egongil.numva_android_app.src.config.models.response.GetSecondPhoneResponse;
import com.egongil.numva_android_app.src.config.models.request.RepSecondPhoneRequest;
import com.egongil.numva_android_app.src.config.models.response.RepSecondPhoneResponse;
import com.egongil.numva_android_app.src.second_phone.viewmodel.SecondPhoneViewModel;
import com.egongil.numva_android_app.src.second_phone.viewmodel.SecondPhoneViewModelFactory;

import java.util.ArrayList;

public class SecondPhoneActivity extends BaseActivity implements SecondPhoneActivityContract, ConnectionReceiver.ConnectionReceiverListener {
//    private ArrayList<SecondPhoneRecyclerItem> mSecondPhoneList;
//    public static boolean isEditState = false;
//
    private SecondPhoneRecyclerAdapter mRvAdapter;

    private ActivityResultLauncher<Intent> mActivityResultLauncher;
    private ActivitySecondPhoneBinding binding;
    private SecondPhoneViewModel mSecondPhoneViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_phone);
        checkConnection(); //네트워크 연결 확인
        binding = DataBindingUtil.setContentView(this, R.layout.activity_second_phone);

        //viewModel setting
        mSecondPhoneViewModel = new ViewModelProvider(this,
                new SecondPhoneViewModelFactory(this))
                .get(SecondPhoneViewModel.class);

        binding.setLifecycleOwner(this);
        binding.setViewModel(mSecondPhoneViewModel);

        getSecondPhone(); //2차 전화번호 불러오기

        mActivityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if(result.getResultCode() == SECONDPHONE_REGISTER_ACTIVITY){
                getSecondPhone();
            }
        });

        /////////recyclerview//////
        LinearLayoutManager manager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        binding.secondphoneRv.setLayoutManager(manager);
        mRvAdapter = new SecondPhoneRecyclerAdapter(mSecondPhoneViewModel);
        binding.secondphoneRv.setAdapter(mRvAdapter);

        //ViewModel observe 설정
        mSecondPhoneViewModel.setEditState(false);
        mSecondPhoneViewModel.getEditState().observe(this, state ->{
           mRvAdapter.notifyDataSetChanged();
        });
        mSecondPhoneViewModel.getSecondPhone().observe(this, secondPhoneRecyclerItems -> {
            mRvAdapter.notifyDataSetChanged();
        });
        mSecondPhoneViewModel.getSelectedPos().observe(this, pos -> {
            if(pos!=null){
                putSecondPhoneData(mSecondPhoneViewModel.getSecondPhone().getValue()
                        .get(pos).getSecondphone());
            }
            mRvAdapter.notifyDataSetChanged();
        });

        //x버튼
        binding.secondphoneIvCrossbtn.setOnClickListener(v -> finish());

        //추가버튼
        binding.secondphoneBtnAdd.setOnClickListener(v -> {
            //추가 버튼
            if (mSecondPhoneViewModel.getSecondPhone().getValue().size() < 5) {
                Intent intent = new Intent(getApplicationContext(), SecondPhoneRegisterActivity.class);
                startActivity(intent);
            } else {
                showCustomToast("2차전화번호는 최대 5개까지 저장 가능합니다.");
            }
        });

        //편집버튼
        binding.secondphoneBtnEdit.setOnClickListener(v -> {
            //기존 체크했던 항목 해제
            for(int i = 0; i< mRvAdapter.getItemCount(); i++){
                if(mSecondPhoneViewModel.getSecondPhone().getValue().get(i).getSelected()){
                    mSecondPhoneViewModel.getSecondPhone().getValue().get(i).setSelected(false);
                }
            }
            mSecondPhoneViewModel.setEditState(true);
            mRvAdapter.notifyItemRangeChanged(0, mRvAdapter.getItemCount());
        });

        binding.secondphoneBtnDelete.setOnClickListener(v -> {
            //삭제 버튼
            String strId = "";
            for(int i = 0; i< mRvAdapter.getItemCount(); i++){
                if(mSecondPhoneViewModel.getSecondPhone().getValue().get(i).getSelected()){
                    strId += " "+mSecondPhoneViewModel.getSecondPhone().getValue().get(i).getId();
                }
            }
            delSecondPhone(strId);
            mSecondPhoneViewModel.setEditState(false);
        });

        binding.secondphoneBtnCancel.setOnClickListener(v -> {
            //취소 버튼
            mSecondPhoneViewModel.setEditState(false);
        });

        mRvAdapter.setOnItemClickListener((v, position) -> {
            if(!mSecondPhoneViewModel.getEditState().getValue()){
                TwoButtonDialog setRepDialog = new TwoButtonDialog(SecondPhoneActivity.this);
                setRepDialog.showDialog();
                setRepDialog.setContextText("이 번호를 2차전화번호로 등록하시겠습니까?");
                setRepDialog.setSelectText("취소", "확인");
                setRepDialog.mBtnLeft.setOnClickListener(v12 -> setRepDialog.dismiss());
                setRepDialog.mBtnRight.setOnClickListener(v1 -> {
                    setRepDialog.dismiss();
                    repSecondPhone(mSecondPhoneViewModel.getSecondPhone().getValue().get(position).getId());
                    mSecondPhoneViewModel.setSelectedPos(position);
                });
            }
        });
    }

    //2차전화번호 불러오기
    public void getSecondPhone(){
        SecondPhoneService secondPhoneService = new SecondPhoneService(this);
        secondPhoneService.getSecondPhone();
    }

    //2차전화번호 불러오기 성공
    @Override
    public void getSecondPhoneSuccess(GetSecondPhoneResponse getSecondPhoneResponse, ErrorResponse errorResponse) {
        if(getSecondPhoneResponse != null){
            if(getSecondPhoneResponse.getCode()==200 && getSecondPhoneResponse.isSuccess()){
                ArrayList<SecondPhoneRecyclerItem> mSecondPhoneList = getSecondPhoneResponse.getResult();
                mSecondPhoneViewModel.setSecondPhone(mSecondPhoneList); //viewmodel 데이터 변경

                if(getSecondPhoneResponse.getResult().isEmpty()){
                    //배열 비어있을 때
                    binding.secondphoneTvEmpty.setVisibility(View.VISIBLE);
                    binding.secondphoneRv.setVisibility(View.GONE);
                }
            }
            else if (errorResponse != null){
                //리스트 비어있을 때
                showCustomToast(getString(R.string.network_error));
            }
        }
    }

    //2차전화번호 불러오기 실패
    @Override
    public void getSecondPhoneFailure() {
        showCustomToast(getString(R.string.network_error));
    }

    //대표번호로 설정하기
    public void repSecondPhone(int id){
        SecondPhoneService secondPhoneService = new SecondPhoneService(this);
        RepSecondPhoneRequest repSecondPhoneRequest = new RepSecondPhoneRequest();
        repSecondPhoneRequest.setId(id);
        secondPhoneService.repSecondPhone(repSecondPhoneRequest);
    }

    //2차전화번호 대표번호 설정 성공
    @Override
    public void repSecondPhoneSuccess(RepSecondPhoneResponse repSecondPhoneResponse, ErrorResponse errorResponse) {
        if(repSecondPhoneResponse != null){
            if(repSecondPhoneResponse.getCode()==200 && repSecondPhoneResponse.isSuccess()){
                System.out.println("TOKEN : "+repSecondPhoneResponse.getMessage());

                showCustomToast("2차전화번호가 설정되었습니다.");
                finish();
                }
            else if(errorResponse != null){
                //매칭된 데이터 없음
                showCustomToast(getString(R.string.secondphone_invalid_error));
            }
        }
    }

    //2차전화번호 대표번호 설정 실패
    @Override
    public void repSecondPhoneFailure() {
        showCustomToast(getString(R.string.network_error));
    }

    //2차전화번호 삭제
    public void delSecondPhone(String id){
        SecondPhoneService secondPhoneService = new SecondPhoneService(this);
        DeleteSecondPhoneRequest deleteSecondPhoneRequest = new DeleteSecondPhoneRequest();
        deleteSecondPhoneRequest.setId(id);
        secondPhoneService.deleteSecondPhone(deleteSecondPhoneRequest);
    }

    //2차전화번호 삭제 성공
    @Override
    public void deleteSecondPhoneSuccess(DeleteSecondPhoneResponse deleteSecondPhoneResponse, ErrorResponse errorResponse) {
        if(deleteSecondPhoneResponse != null){
            if(deleteSecondPhoneResponse.getCode()==200 && deleteSecondPhoneResponse.isSuccess()){
                System.out.println("TOKEN : "+deleteSecondPhoneResponse.getMessage());
                showCustomToast("선택하신 전화번호가 삭제되었습니다.");
                mSecondPhoneViewModel.setSecondPhone(deleteSecondPhoneResponse.getResult());
            }
            else if(errorResponse != null){
                showCustomToast(getString(R.string.secondphone_invalid_error));
            }
        }
    }

    //2차전화번호 삭제 실패
    @Override
    public void deleteSecondPhoneFailure() {
        showCustomToast(getString(R.string.network_error));
    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        if(!isConnected){
            //show a No Internet Alert or Dialog
            Intent intent = new Intent(this, NetworkFailureActivity.class);
            startActivity(intent);
        }else{
            //dismiss the dialog or refresh the activity
        }
    }

    @Override
    protected void onResume(){
        super.onResume();
        //register connection status listener
        ApplicationClass.getInstance().setConnectionListener(this);

    }

    private void checkConnection(){
        boolean isConnected = ConnectionReceiver.isConnected();
        if(!isConnected){
            //show a No Internet Alert or Dialog
            Intent intent = new Intent(this, NetworkFailureActivity.class);
            startActivity(intent);
        }
    }

    private void putSecondPhoneData(String second_phone){
        //MyPageFragment에 전달될 intent에 값 put
        Intent finish_intent = new Intent(getApplicationContext(), MainActivity.class);
        finish_intent.putExtra("second_phone", second_phone);
        setResult(SECONDPHONE_REGISTER_ACTIVITY, finish_intent);
    }
}