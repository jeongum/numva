package com.egongil.numva_android_app.src.qr_management.view;

import static com.egongil.numva_android_app.src.config.ApplicationClass.ActivityType.QR_MANAGEMENT_ACTIVITY;

import androidx.databinding.BindingAdapter;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.View;

import com.egongil.numva_android_app.R;
import com.egongil.numva_android_app.databinding.ActivityQrManagementBinding;
import com.egongil.numva_android_app.src.config.view.BaseActivity;
import com.egongil.numva_android_app.src.config.models.base.ErrorResponse;
import com.egongil.numva_android_app.src.config.view.RecyclerTouchListener;
import com.egongil.numva_android_app.src.config.models.SafetyInfo;
import com.egongil.numva_android_app.src.custom_dialogs.OneLineEditDialog;
import com.egongil.numva_android_app.src.custom_dialogs.SelectTwoButtonDialog;
import com.egongil.numva_android_app.src.custom_dialogs.TwoButtonDialog;
import com.egongil.numva_android_app.src.main.view.MainActivity;
import com.egongil.numva_android_app.src.qr_management.interfaces.QrManagementActivityContract;
import com.egongil.numva_android_app.src.config.models.request.DeleteQrRequest;
import com.egongil.numva_android_app.src.config.models.response.DeleteQrResponse;
import com.egongil.numva_android_app.src.config.models.request.RegisterQrRequest;
import com.egongil.numva_android_app.src.config.models.response.RegisterQrResponse;
import com.egongil.numva_android_app.src.config.models.request.SetQrNameRequest;
import com.egongil.numva_android_app.src.config.models.response.SetQrNameResponse;
import com.egongil.numva_android_app.src.qr_management.viewmodel.QrManagementViewModel;
import com.egongil.numva_android_app.src.qr_management.viewmodel.QrManagementViewModelFactory;
import com.egongil.numva_android_app.src.qr_scan.view.QrScanActivity;

import java.util.ArrayList;

public class QrManagementActivity extends BaseActivity implements QrManagementActivityContract {
    final static int QRNAME_VALID = 0;
    final static int QRNAME_EMPTY = 1;
    final static int QRNAME_EXIST = 2;

    ActivityQrManagementBinding binding;
    private QrManagementViewModel mQrManagementViewModel;
    private QrRecyclerAdapter mQrRvAdapter;

    private OneLineEditDialog directRegisterDialog;
    private TwoButtonDialog deleteDialog;
    private OneLineEditDialog editDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_qr_management);

        mQrManagementViewModel = new ViewModelProvider(this,
                new QrManagementViewModelFactory(this))
                .get(QrManagementViewModel.class);
        binding.setViewModel(mQrManagementViewModel);
        binding.setLifecycleOwner(this);

        binding.qrManageBtnAddQr.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                showRegisterDialog();
            }
        });

        //RecyclerView 초기화
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        binding.qrManageRvQrlist.setLayoutManager(manager);
        mQrRvAdapter = new QrRecyclerAdapter(mQrManagementViewModel);
        binding.qrManageRvQrlist.setAdapter(mQrRvAdapter);

        //RecyclerView Swipe menu
        RecyclerTouchListener touchListener = new RecyclerTouchListener(this, binding.qrManageRvQrlist);
        touchListener.setSwipeOptionViews(R.id.qrlist_rl_deletebtn, R.id.qrlist_rl_editbtn)
                .setSwipeable(R.id.qrlist_ll_FG, R.id.qrlist_ll_BG, (viewID, position) -> {
                    switch (viewID) {
                        case R.id.qrlist_rl_deletebtn:
                            //삭제버튼 눌렀을 때
                            showDeleteDialog(position);
                            break;
                        case R.id.qrlist_rl_editbtn:
                            //swipemenu 수정버튼 눌렀을 때
                            showEditDialog(position);
                            break;
                    }
                });
        binding.qrManageRvQrlist.addOnItemTouchListener(touchListener);

        initSafetyInfo();

        //safetyInfo 변경되면 recyclerView update해줌
        mQrManagementViewModel.getSafetyInfoData(); //null방지
        mQrManagementViewModel.mSafetyInfo.observe(this, safetyInfos -> {
                    mQrRvAdapter.notifyDataSetChanged();
                    putIntentSafetyInfo();
                });

        binding.qrManageIvClosebtn.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                finish();
            }
        });
    }

    public int checkQrNameValidity(String name) {
        if (name.equals("")) {
            return QRNAME_EMPTY;
        }
        ArrayList<SafetyInfo> mListQR = mQrManagementViewModel.getSafetyInfoData().getValue();
        for (int i = 0; i < mListQR.size(); i++) {
            if (mListQR.get(i).getName().equals(name)) {
                return QRNAME_EXIST;
            }
        }
        return QRNAME_VALID;
    }

    public void initSafetyInfo() {
        ArrayList<SafetyInfo> mListQR = (ArrayList<SafetyInfo>) getIntent().getSerializableExtra("safety_info");
        mQrManagementViewModel.setSafetyInfoData(mListQR);
    }

    public void showRegisterDialog() {
        SelectTwoButtonDialog registerDialog = new SelectTwoButtonDialog(QrManagementActivity.this);
        registerDialog.showDialog();

        registerDialog.setTitleText("새 QR 추가");
        registerDialog.setOptionText("직접 입력할게요", "QR 스캔할게요");

        //직접 입력 클릭 시
        registerDialog.mTvOption1.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                registerDialog.dismiss();
                showDirectRegisterDialog();
            }
        });
        //qr스캔 클릭 시
        registerDialog.mTvOption2.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                //qr스캔 카메라 연결
                Intent intent = new Intent(QrManagementActivity.this, QrScanActivity.class);
                startActivity(intent);
                registerDialog.dismiss();
                finish();
            }
        });
    }

    public void showDirectRegisterDialog() {
        directRegisterDialog = new OneLineEditDialog(QrManagementActivity.this);
        directRegisterDialog.showDialog();

        directRegisterDialog.setTitleText("제품 일련번호 등록");
        directRegisterDialog.setEditHint("일련번호 입력");
        directRegisterDialog.setConfirmBtnText("등록하기");

        directRegisterDialog.mTvConfirmBtn.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                registerQr(directRegisterDialog.getTextOfEdit());
            }
        });
    }

    public void showEditDialog(int position) {
        editDialog = new OneLineEditDialog(QrManagementActivity.this);
        editDialog.showDialog();

        editDialog.setTitleText("QR 별명 설정");    //타이틀 text 설정

        //editText 글자수 제한(8자리), 가이드라인 설정
        editDialog.mEtContext.setFilters(new InputFilter[]{new InputFilter.LengthFilter(8)});
        editDialog.setGuideText("별명은 최대 8글자까지 가능합니다.");
        editDialog.setGuideColor(getColor(R.color.colorPrimary));

        editDialog.setConfirmBtnText("설정하기");   //버튼 text 설정
        editDialog.setTextOfEdit(mQrManagementViewModel.getSafetyInfoData().getValue()
                .get(position).getName());  //editText 내용 설정

        editDialog.mTvConfirmBtn.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                String strEdit = editDialog.getTextOfEdit();
                int isQrNameValid = checkQrNameValidity(strEdit);
                if (isQrNameValid == QRNAME_VALID) {
                    ArrayList<SafetyInfo> mListQR = mQrManagementViewModel.getSafetyInfoData().getValue();
                    mListQR.get(position).setName(strEdit);
                    mQrManagementViewModel.setSafetyInfoData(mListQR);

                    setQrName(mListQR.get(position).getId(), strEdit);  //api
                } else if (isQrNameValid == QRNAME_EMPTY) {
                    editDialog.setGuideText("한 글자 이상 입력해주세요.");
                    editDialog.setGuideColor(getColor(R.color.colorErrorRed));
                } else if (isQrNameValid == QRNAME_EXIST) {
                    editDialog.setGuideText("동일한 별명을 가진 QR이 이미 존재합니다.");
                    editDialog.setGuideColor(getColor(R.color.colorErrorRed));
                }
            }
        });
    }

    public void showDeleteDialog(int position) {
        deleteDialog = new TwoButtonDialog(QrManagementActivity.this);
        deleteDialog.showDialog();
        deleteDialog.setContextText("이 QR을 삭제할까요?");
        deleteDialog.setSelectText("취소", "확인");

        deleteDialog.mBtnLeft.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                deleteDialog.dismiss();
            }
        });
        deleteDialog.mBtnRight.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                ArrayList<SafetyInfo> mListQR = mQrManagementViewModel.getSafetyInfoData().getValue();
                deleteQr(mListQR.get(position).getId());    //api

                mListQR.remove(position);
                mQrManagementViewModel.setSafetyInfoData(mListQR);

                deleteDialog.dismiss();
            }
        });
    }

    //Register
    public void registerQr(String qr_id) {
        RegisterQrRequest registerQrRequest = new RegisterQrRequest(qr_id);
        mQrManagementViewModel.registerQr(registerQrRequest);
    }

    @Override
    public void registerQrSuccess(RegisterQrResponse registerQrResponse, ErrorResponse errorResponse) {
        if (registerQrResponse != null) {
            if (registerQrResponse.getCode() == 200 && registerQrResponse.isSuccess()) {
                directRegisterDialog.dismiss();

                showCustomToast(getString(R.string.qr_manage_register_success));
                ArrayList<SafetyInfo> mListQR = registerQrResponse.getResult();
                mQrManagementViewModel.setSafetyInfoData(mListQR);
            }
        } else if (errorResponse != null) {
            //에러 시 동작
            if (errorResponse.getCode() == -102) {
                //이미 등록된 qr
                directRegisterDialog.setGuideText("이미 등록된 일련번호입니다. 다시 한 번 확인해주세요.");
                directRegisterDialog.setGuideColor(getColor(R.color.colorErrorRed));

            } else if (errorResponse.getCode() == -103) {
                //유효하지 않은 일련번호
                directRegisterDialog.setGuideText("유효하지 않은 일련번호입니다. 다시 한 번 확인해주세요.");
                directRegisterDialog.setGuideColor(getColor(R.color.colorErrorRed));
            } else {
                showCustomToast(getResources().getString(R.string.network_error));
            }
        }
    }

    @Override
    public void registerQrFailure() {
        showCustomToast(getResources().getString(R.string.network_error));
    }

    //Edit
    public void setQrName(int id, String name) {
        SetQrNameRequest setQrNameRequest = new SetQrNameRequest(id, name);
        mQrManagementViewModel.setQrName(setQrNameRequest);
    }

    @Override
    public void setQrNameSuccess(SetQrNameResponse setQrNameResponse, ErrorResponse errorResponse) {
        if (setQrNameResponse != null) {
            if (setQrNameResponse.getCode() == 200 && setQrNameResponse.isSuccess()) {
                showCustomToast(getResources().getString(R.string.qr_manage_edit_success));
                editDialog.dismiss();
            }
        } else if (errorResponse != null) {
            //에러 시 동작
            if (errorResponse.getCode() == -103) {
                showCustomToast(getResources().getString(R.string.alert_data_not_matched_error));
            } else {
                showCustomToast(getResources().getString(R.string.network_error));
            }
        }
    }

    @Override
    public void setQrNameFailure() {
        showCustomToast(getResources().getString(R.string.network_error));
    }

    //Delete
    public void deleteQr(int id) {
        DeleteQrRequest deleteQrRequest = new DeleteQrRequest(id);
        mQrManagementViewModel.deleteQr(deleteQrRequest);
    }

    @Override
    public void deleteQrSuccess(DeleteQrResponse deleteQrResponse, ErrorResponse errorResponse) {
        if (deleteQrResponse != null) {
            if (deleteQrResponse.getCode() == 200 && deleteQrResponse.isSuccess()) {
                showCustomToast(getString(R.string.qr_manage_delete_success));
            }
        } else if (errorResponse != null) {
            //에러 시 동작
            showCustomToast(getResources().getString(R.string.network_error));
        }
    }

    @Override
    public void deleteQrFailure() {
        showCustomToast(getResources().getString(R.string.network_error));
    }

    //HomeFragment/MyPageFragment로 향할 intent에 mListQR담기
    public void putIntentSafetyInfo() {
        Intent finish_intent = new Intent(getApplicationContext(), MainActivity.class);
        ArrayList<SafetyInfo> mListQR = mQrManagementViewModel.getSafetyInfoData().getValue();
        finish_intent.putExtra("safety_info", mListQR);
        setResult(QR_MANAGEMENT_ACTIVITY, finish_intent);
    }
}