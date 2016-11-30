package com.heyi.UniversityNews.Pager;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

/**
 * Created by heyi on 2016/11/26.
 */

public class MePager extends BasePager {
    public MePager(Activity activity) {
        super(activity);
    }

    @Override
    public View initView() {
        TextView textView=new TextView(mActivity);
        textView.setText("呵呵，我是我你信吗！");
        return textView;
    }
}
