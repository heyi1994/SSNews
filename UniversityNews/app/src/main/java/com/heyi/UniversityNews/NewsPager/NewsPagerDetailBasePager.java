package com.heyi.UniversityNews.NewsPager;

import android.app.Activity;
import android.view.View;

/**
 * Created by heyi on 2016/12/7.
 */

public abstract class NewsPagerDetailBasePager {
    protected Activity mActivity;
    public View view;
    public NewsPagerDetailBasePager(Activity activity){
        mActivity=activity;
        view=initView();
        initData();
    }
    protected abstract View initView();
    protected  void initData(){}
}
