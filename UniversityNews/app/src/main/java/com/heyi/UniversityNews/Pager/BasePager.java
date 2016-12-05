package com.heyi.UniversityNews.Pager;

import android.app.Activity;
import android.view.View;

/**
 * Created by heyi on 2016/11/26.
 */

public abstract class BasePager {
    protected Activity mActivity;
    public View mView;
    public BasePager bp;
    public BasePager(Activity activity){
        this.mActivity=activity;
        this.mView=initView();
        bp=this;
        initData();
    }
    public abstract View initView();
    public void initData(){

    }
    public void backPress(String s,int result){}

}
