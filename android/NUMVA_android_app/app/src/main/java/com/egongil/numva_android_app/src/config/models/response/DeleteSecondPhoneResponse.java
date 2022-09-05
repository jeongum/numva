package com.egongil.numva_android_app.src.config.models.response;

import com.egongil.numva_android_app.src.config.models.base.RetrofitResponse;
import com.egongil.numva_android_app.src.second_phone.models.SecondPhoneRecyclerItem;

import java.util.ArrayList;

public class DeleteSecondPhoneResponse extends RetrofitResponse {
    ArrayList<SecondPhoneRecyclerItem> result;

    public ArrayList<SecondPhoneRecyclerItem> getResult() {
        return result;
    }
}
