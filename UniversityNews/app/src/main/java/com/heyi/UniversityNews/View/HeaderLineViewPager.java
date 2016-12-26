package com.heyi.UniversityNews.View;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

import static android.R.attr.endX;
import static android.R.attr.endY;
import static android.R.attr.startX;
import static android.R.attr.startY;

/**
 * Created by heyi on 2016/12/7.
 */

public class HeaderLineViewPager extends ViewPager {
    private int startX;
    private int startY;
    private int endX;
    private int endY;

    public HeaderLineViewPager(Context context) {
        super(context);
    }

    public HeaderLineViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        getParent().requestDisallowInterceptTouchEvent(true);//先让父控件不拦截
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startX = (int) ev.getRawX();
                startY = (int) ev.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                endX = (int) ev.getRawX();
                endY = (int) ev.getRawY();
                if(Math.abs(endX-startX)>Math.abs(endY-startY)){
                    //左右滑动
                    if(endX>startX){
                        //向右滑
                        if(getCurrentItem()==0){
                            getParent().requestDisallowInterceptTouchEvent(false);
                        }
                    }else{
                        if(getCurrentItem()==getAdapter().getCount()-1){
                            //最后一个页面，需要拦截
                            getParent().requestDisallowInterceptTouchEvent(false);
                        }
                    }
                }else{
                    getParent().requestDisallowInterceptTouchEvent(false);
                }
                break;

        }

        return super.dispatchTouchEvent(ev);
    }
}
