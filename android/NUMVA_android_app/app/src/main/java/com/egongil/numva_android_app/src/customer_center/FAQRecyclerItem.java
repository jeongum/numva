package com.egongil.numva_android_app.src.customer_center;

public class FAQRecyclerItem {
    private int id;
    private String question;
    private String answer;

    public FAQRecyclerItem(int id, String question, String answer){
        this.id = id;
        this.question = question;
        this.answer = answer;
    }


    public int getId(){return id;}
    public String getQuestion(){return question;}
    public String getAnswer(){return answer;}

    public void setId(int id){this.id = id;}
    public void setQuestion(String question){this.question = question;}
    public void setAnswer(String answer){this.answer = answer;}

}
