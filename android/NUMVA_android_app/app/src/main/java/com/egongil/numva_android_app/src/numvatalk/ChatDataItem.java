package com.egongil.numva_android_app.src.numvatalk;

public class ChatDataItem {
    private String content;
    private String time;
    private boolean isread;

    private int viewType;

    public ChatDataItem(String content, String time, boolean isread, int viewType) {
        this.content = content;
        this.time = time;
        this.isread = isread;
        this.viewType = viewType;
    }

    public String getTime() {
        return time;
    }

    public boolean isRead(){
        return isread;
    }

    public String getContent() {
        return content;
    }

    public int getViewType() {
        return viewType;
    }
}
