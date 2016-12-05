package com.heyi.UniversityNews.View;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.accessibility.AccessibilityEvent;

import com.heyi.UniversityNews.Pager.MePager;

/**
 * Created by heyi on 2016/11/26.
 */

public class NoScrollViewPager extends ViewPager {

    public NoScrollViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public NoScrollViewPager(Context context) {
        super(context);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
            return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
            return false;
    }
}
