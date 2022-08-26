package com.egongil.numva_android_app.src.home.view;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;

public class QrViewPagerTransformer implements ViewPager.PageTransformer{
    private int baseElevation;
    private int rasingElevation;
    private float smallerScale;
    private float startOffset;

    public QrViewPagerTransformer(int baseElevation, int rasingElevation, float smallerScale, float startOffset) {
        this.baseElevation = baseElevation;
        this.rasingElevation = rasingElevation;
        this.smallerScale = smallerScale;
        this.startOffset = startOffset;
    }
    @Override
    public void transformPage(@NonNull View page, float position) {
        float absPosition = Math.abs(position - startOffset);

        if(absPosition >=1){
            page.setElevation(baseElevation);
            page.setScaleY(smallerScale);
        }else{
            //This will be during transformation
            page.setElevation(((1-absPosition)* rasingElevation + baseElevation));
            page.setScaleY((smallerScale - 1) * absPosition + 1);
        }
    }
}
