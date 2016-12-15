package com.heyi.UniversityNews.View;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by heyi on 2016/12/6.
 */

public class NewsPagerViewPager extends ViewPager {
    public NewsPagerViewPager(Context context) {
        super(context);
    }

    public NewsPagerViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if(getCurrentItem()!=0){
            getParent().requestDisallowInterceptTouchEvent(true);
        }

        return super.dispatchTouchEvent(ev);
    }
}
