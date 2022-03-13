package com.egongil.numva_android_app.src.cert_phone;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.animation.LinearInterpolator;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;

import org.jetbrains.annotations.NotNull;

public class TimerView extends AppCompatTextView {

    private ObjectAnimator animator;
    private int time;

    private boolean certification = false;

    public TimerView(@NonNull @NotNull Context context) {
        super(context);
    }

    public TimerView(Context context, AttributeSet attrs){
        super(context, attrs);
    }

    public void start(int allTime){
        setTime(allTime);
        setCertification(true);
        animator = ObjectAnimator.ofInt(this, "time", 0);
        animator.setDuration(allTime);
        animator.setInterpolator(new LinearInterpolator());
        animator.start();
    }

    public void stop(){
        animator.cancel();
        setTime(0);
        setCertification(false);
    }

    public boolean isCertification(){
        return certification;
    }

    private void setCertification(boolean certification){
        this.certification = certification;
    }

    public int getTime(){
        return time;
    }

    public void setTime(int time){
        this.time = time;

        int h = time/3600000;
        int m = (time-h*3600000)/60000;
        int s = (time-h*3600000-m*60000)/1000;

        String mm = m<10?"0"+m : m+"";
        String ss = s<10?"0"+s : s+"";

        this.setText(mm+":"+ss);

        if(this.time==0){
            setCertification(false);
        }
    }
}
