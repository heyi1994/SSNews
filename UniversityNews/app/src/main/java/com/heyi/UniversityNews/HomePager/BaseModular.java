package com.heyi.UniversityNews.HomePager;

import android.app.Activity;
import android.view.View;

import com.heyi.UniversityNews.Pager.BasePager;

/**
 * Created by heyi on 2016/11/28.
 */

public abstract class BaseModular {
    public View mView;
    protected Activity mActivity;
    public BaseModular(Activity activity){
        mActivity=activity;
        mView=initView();
        initData();
    }
    protected abstract View initView();
    protected void initData(){

    }
}
