package com.heyi.UniversityNews.Pager;

import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.heyi.UniversityNews.NewsPager.HeadLineNewsPager;
import com.heyi.UniversityNews.R;
import com.viewpagerindicator.TabPageIndicator;

import java.util.ArrayList;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;
import static com.heyi.UniversityNews.R.drawable.news;

/**
 * Created by heyi on 2016/11/26.
 */

public class NewsPager extends BasePager {
    private  static final String[] news={"头条","校园","考研","组图","互联网","开发日记"};
    private TabPageIndicator vp_indicator;
    private ViewPager vp_main;
    public NewsPager(Activity activity) {
        super(activity);
    }

    @Override
    public View initView() {
        View view = View.inflate(mActivity, R.layout.pager_news, null);
        vp_main= (ViewPager) view.findViewById(R.id.view_pager_news_pager);
        vp_indicator= (TabPageIndicator) view.findViewById(R.id.view_pager_news_pager_indicator);

        vp_main.setAdapter(new MyAdapter());
        vp_indicator.setViewPager(vp_main);
        return view;
    }
    public class MyAdapter extends PagerAdapter{
        public CharSequence getPageTitle(int position) {
                return news[position];
        }

        public int getCount() {
            return news.length;
        }

        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }

        public Object instantiateItem(ViewGroup container, int position) {
            View view=null;
            if(position==0){
               view=new HeadLineNewsPager(mActivity).view;
            }else {
                TextView tv = new TextView(mActivity);
                tv.setText(news[position]);
                view=tv;
            }
            container.addView(view);
            return view;
        }
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
}
