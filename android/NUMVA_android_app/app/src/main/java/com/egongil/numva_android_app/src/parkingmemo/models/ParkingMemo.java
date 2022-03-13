package com.egongil.numva_android_app.src.parkingmemo.models;

public class ParkingMemo {
    int id;
    int safety_info_id;
    String memo;
    String created_at;
    String updated_at;

    public int getId() {
        return id;
    }

    public int getSafety_info_id() {
        return safety_info_id;
    }

    public String getMemo() {
        return memo;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }
}
