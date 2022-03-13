package com.egongil.numva_android_app.src.second_phone;

public class SecondPhoneRecyclerItem {
    private  int id;
    private int user_id;
    private String second_phone;
    private String isrep;

    private boolean isSelected; //recyclerview > checkbox > 선택 or 해제

    public SecondPhoneRecyclerItem(int id, int user_id, String second_phone, String isrep){
        this.id = id;
        this.user_id = user_id;
        this.second_phone = second_phone;
        this.isrep = isrep;
    }

    public SecondPhoneRecyclerItem(String second_phone, boolean isSelected){
        this.second_phone = second_phone;
        this.isSelected = isSelected;
    }



    public int getId(){return id;}
    public int getUser_id(){return user_id;}
    public String getSecondphone(){return second_phone; }
    public String getIsrep(){return isrep;}

    public void setId(int id){this.id = id;}
    public void setSecondphone(String secondphone){this.second_phone = secondphone;}
    public void setIsrep(String isrep){this.isrep = isrep;}


    public boolean getSelected(){return isSelected;}
    public void setSelected(boolean selected){isSelected = selected;}
}
