package com.heyi.UniversityNews.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.heyi.UniversityNews.Pager.FriendsPager;
import com.heyi.UniversityNews.Pager.HomePager;
import com.heyi.UniversityNews.Pager.BasePager;
import com.heyi.UniversityNews.Pager.MePager;
import com.heyi.UniversityNews.Pager.NewsPager;
import com.heyi.UniversityNews.R;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

import java.util.ArrayList;

import static android.R.attr.data;
import static android.R.attr.y;
import static android.icu.lang.UCharacter.GraphemeClusterBreak.V;
import static android.os.Build.VERSION_CODES.M;


/**
 * Created by heyi on 2016/11/22.
 */

public class MainActivity extends SlidingFragmentActivity{
    private SlidingMenu slidingMenu;
    private ViewPager viewPager;
    private RadioGroup rg_bottom;
    private ArrayList<BasePager> arrayList=new ArrayList<BasePager>();
    private int downY;

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
        viewPager= (ViewPager) findViewById(R.id.view_pager_main);
        rg_bottom= (RadioGroup) findViewById(R.id.radioGroup);
    }
    private void initData(){
        slidingMenu = getSlidingMenu();
        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        slidingMenu.setBehindOffset(300);
        rg_bottom.check(R.id.rb_home);
        arrayList.add(new HomePager(MainActivity.this));
        arrayList.add(new NewsPager(MainActivity.this));
        arrayList.add(new FriendsPager(MainActivity.this));
        arrayList.add(new MePager(MainActivity.this));
        viewPager.setAdapter(new MyAdapter());
        rg_bottom.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.rb_home:
                        viewPager.setCurrentItem(0);
                        break;
                    case R.id.rb_news:
                        viewPager.setCurrentItem(1);
                        break;
                    case R.id.rb_friends:
                        viewPager.setCurrentItem(2);
                        break;
                    case R.id.rb_me:
                        viewPager.setCurrentItem(3);
                        break;
                }
            }
        });
    }
    public void toggle(){
        slidingMenu.toggle();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==1&&resultCode==1){
            String sign = data.getStringExtra("sign");
            arrayList.get(3).backPress(sign,1);
            HomePager homePager = (HomePager) arrayList.get(0);
            homePager.changeNickOrSign(sign,1);
        }else if(requestCode==2&&resultCode==2){
            String nickName = data.getStringExtra("NickName");
            arrayList.get(3).backPress(nickName,2);
            HomePager homePager = (HomePager) arrayList.get(0);
            homePager.changeNickOrSign(nickName,2);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

  class MyAdapter extends PagerAdapter{

      @Override
      public int getCount() {
          return arrayList.size();
      }

      @Override
      public void destroyItem(ViewGroup container, int position, Object object) {
          container.removeView((View) object);
      }

      @Override
      public boolean isViewFromObject(View view, Object object) {
          return view==object;
      }

      @Override
      public Object instantiateItem(ViewGroup container, int position) {
          View mView = arrayList.get(position).mView;
          container.addView(mView);
          return mView;
      }
  }


}
