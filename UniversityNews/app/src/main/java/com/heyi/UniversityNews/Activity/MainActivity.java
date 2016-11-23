package com.heyi.UniversityNews.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

import com.heyi.UniversityNews.R;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.V;

/**
 * Created by heyi on 2016/11/22.
 */

public class MainActivity extends SlidingFragmentActivity{
    private SlidingMenu slidingMenu;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
    }
    private void initView(){
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.main_activity);
        setBehindContentView(R.layout.silding_menu);
    }
    private void initData(){
        SlidingMenu slidingMenu = getSlidingMenu();
        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        slidingMenu.setBehindOffset(600);

    }


}
