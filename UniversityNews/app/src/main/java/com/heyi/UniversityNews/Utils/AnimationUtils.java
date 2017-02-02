package com.heyi.UniversityNews.Utils;

import android.content.Context;
import android.view.View;
import android.view.animation.AlphaAnimation;

/**
 * Created by heyi on 2017/1/7.
 */

public class AnimationUtils {
    private AnimationUtils(){}
    public static void showAndHidden( View target, long time){
        AlphaAnimation aa=new AlphaAnimation(0.0f,1.0f);
        aa.setDuration(time);
        aa.setFillAfter(true);
        target.startAnimation(aa);

        AlphaAnimation aa1=new AlphaAnimation(1.0f,0.0f);
        aa1.setDuration(time);
        aa1.setFillAfter(true);
        target.startAnimation(aa1);

    }

    public static void hidden(View target,long time){
        AlphaAnimation aa=new AlphaAnimation(1.0f,0.0f);
        aa.setDuration(time);
        aa.setFillAfter(true);
        target.startAnimation(aa);
    }
}
