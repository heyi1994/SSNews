package com.heyi.UniversityNews.Pager;

import android.app.Activity;
import android.support.design.widget.AppBarLayout;

import android.view.View;
import android.widget.TextView;

import com.heyi.UniversityNews.R;

/**
 * Created by heyi on 2016/11/26.
 */

public class FriendsPager extends BasePager {
    private AppBarLayout app_bar;

    public FriendsPager(Activity activity) {
        super(activity);
    }

    @Override
    public View initView() {
        /*View view = View.inflate(mActivity, R.layout.header_line_vp_news_detail, null);
        Toolbar toolbar= (Toolbar) view.findViewById(R.id.toolbar);
        toolbar.setTitle("沈阳师范大学");*/
        TextView tv=new TextView(mActivity);
        tv.setText("朋友");
        return tv;
    }

    @Override
    public void initData() {
    }
}
