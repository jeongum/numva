package com.egongil.numva_android_app.src.numvatalk.chatlist;

public class TalkListItem {
    private int user_id;
    private String nickname;
    private String latest_message;
    private int num_new_message;

    public TalkListItem(int user_id, String nickname, String latest_message, int num_new_message) {
        this.user_id = user_id;
        this.nickname = nickname;
        this.latest_message = latest_message;
        this.num_new_message = num_new_message;
    }
    public TalkListItem(TalkListItem item){
        this.user_id = item.user_id;
        this.nickname = item.nickname;
        this.latest_message = item.latest_message;
        this.num_new_message = item.num_new_message;
    }

    public int getNum_new_message() {
        return num_new_message;
    }

    public void setNum_new_message(int num_new_message) {
        this.num_new_message = num_new_message;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getLatest_message() {
        return latest_message;
    }

    public void setLatest_message(String latest_message) {
        this.latest_message = latest_message;
    }
}
