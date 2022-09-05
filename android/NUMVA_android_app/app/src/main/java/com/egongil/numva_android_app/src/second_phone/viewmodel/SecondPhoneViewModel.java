package com.egongil.numva_android_app.src.second_phone.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.egongil.numva_android_app.src.config.models.MutableListLiveData;
import com.egongil.numva_android_app.src.second_phone.interfaces.SecondPhoneActivityContract;
import com.egongil.numva_android_app.src.second_phone.models.SecondPhoneRecyclerItem;

import java.util.ArrayList;

public class SecondPhoneViewModel extends ViewModel {
    public MutableListLiveData<SecondPhoneRecyclerItem> mSecondPhone;
    public  MutableLiveData<Boolean> mEditState;
    public MutableLiveData<Integer> mSelectedPos;

    private SecondPhoneActivityContract mSecondPhoneActivityContract;

    public SecondPhoneViewModel(SecondPhoneActivityContract mSecondPhoneActivityContract) {
        this.mSecondPhoneActivityContract = mSecondPhoneActivityContract;
    }
    public MutableListLiveData<SecondPhoneRecyclerItem> getSecondPhone(){
        if(mSecondPhone == null){
            mSecondPhone = new MutableListLiveData<>();
        }
        return mSecondPhone;
    }

    public void setSecondPhone(ArrayList<SecondPhoneRecyclerItem> secondPhone){
        if(mSecondPhone == null){
            getSecondPhone();
        }
        mSecondPhone.setValue(secondPhone);
    }
    public MutableLiveData<Boolean> getEditState(){
        if(mEditState == null){
            mEditState = new MutableLiveData<>();
        }
        return mEditState;
    }

    public void setEditState(boolean state){
        if(mEditState == null){
            getEditState();
        }
        mEditState.setValue(state);
    }

    public MutableLiveData<Integer> getSelectedPos(){
        if(mSelectedPos == null){
            mSelectedPos = new MutableLiveData<>();
        }
        return mSelectedPos;
    }

    public void setSelectedPos(int pos){
        if(mSelectedPos == null){
            getEditState();
        }
        mSelectedPos.setValue(pos);
    }

}
