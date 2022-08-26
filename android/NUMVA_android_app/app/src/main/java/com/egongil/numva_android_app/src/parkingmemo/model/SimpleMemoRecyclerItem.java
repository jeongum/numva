package com.egongil.numva_android_app.src.parkingmemo.model;

public class SimpleMemoRecyclerItem {
    private int id;
    private String memo;
    private int viewType;

    public SimpleMemoRecyclerItem(int id, String memo, int viewType){
        this.id = id;
        this.memo = memo;
        this.viewType = viewType;
    }

    public int getId() {
        return id;
    }

    public String getMemo() {
        return memo;
    }

    public int getViewType() {
        return viewType;
    }

    public void setViewType(int viewType) {
        this.viewType = viewType;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
}
