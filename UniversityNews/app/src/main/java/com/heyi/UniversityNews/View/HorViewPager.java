package com.heyi.UniversityNews.View;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by heyi on 2016/11/27.
 */

public class HorViewPager extends ViewPager {
    public HorViewPager(Context context) {
        super(context);
    }

    public HorViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if(getCurrentItem()==0){
            getParent().requestDisallowInterceptTouchEvent(false);
        }else{
            getParent().requestDisallowInterceptTouchEvent(true);
        }
        return super.dispatchTouchEvent(ev);
    }
}
