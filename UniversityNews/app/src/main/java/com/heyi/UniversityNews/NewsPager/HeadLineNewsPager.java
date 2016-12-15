package com.heyi.UniversityNews.NewsPager;

import android.app.Activity;
import android.os.SystemClock;
import android.provider.Settings;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.heyi.UniversityNews.R;
import com.heyi.UniversityNews.View.HeaderLineViewPager;
import com.heyi.UniversityNews.View.RefreshListView;
import com.lidroid.xutils.BitmapUtils;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.V;

/**
 * Created by heyi on 2016/12/7.
 */

public class HeadLineNewsPager extends NewsPagerDetailBasePager {

    private View mRoot;
    private RefreshListView listView;
    private  ArrayList<String> al;
    private MyAdapter myAdapter;
    private View header;
    private HeaderLineViewPager vp;
    private TextView vp_title;
    private CirclePageIndicator vp_indicator;

    public HeadLineNewsPager(Activity activity) {
        super(activity);
    }

    @Override
    protected View initView() {
        mRoot = View.inflate(mActivity, R.layout.news_pager_headline, null);
        listView= (RefreshListView) mRoot.findViewById(R.id.news_pager_head_line_list_view);
        header = View.inflate(mActivity, R.layout.header_line_view_pager, null);
        vp_title= (TextView) header.findViewById(R.id.tv_title);
        vp_indicator= (CirclePageIndicator) header.findViewById(R.id.indicator_crl);
        vp= (HeaderLineViewPager) header.findViewById(R.id.header_line_view_pager_image);
        vp.setAdapter(new TopViewPagerAdapter());
        vp_indicator.setViewPager(vp);
        listView.setOnRefreshListener(new RefreshListView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getDataFromServer();
            }
        });
        return mRoot;
    }


    @Override
    protected void initData() {
        al=new ArrayList<String>();
        al.add("heyi"+1);
        al.add("heyi"+2);
        al.add("heyi"+3);
        al.add("heyi"+4);
        if(al!=null) {
            myAdapter = new MyAdapter();
            listView.setAdapter(myAdapter);
        }
        listView.addHeaderView(header);
    }
    private void getDataFromServer(){
        al.add(0,"新增加的1");
        listView.onRefershComplete(true);
        myAdapter.notifyDataSetChanged();
    }
    class MyAdapter extends BaseAdapter{
        @Override
        public int getCount() {
            return al.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view=null;
            ViewHolder holder;
            if(convertView==null){
                view=View.inflate(mActivity,R.layout.item_news_header_line,null);
                holder=new ViewHolder();
                holder.news_icon= (ImageView) view.findViewById(R.id.header_line_news_icon);
                holder.tv_desc= (TextView) view.findViewById(R.id.header_line_news_desc);
                holder.tv_title= (TextView) view.findViewById(R.id.header_line_news_title);
                holder.tv_time= (TextView) view.findViewById(R.id.header_line_news_time);
                view.setTag(holder);
            }else{
             view=convertView;
             holder= (ViewHolder) convertView.getTag();
            }
            holder.tv_time.setText(al.get(position));
            return view;
        }
    }
    class ViewHolder{
        ImageView news_icon;
        TextView tv_title;
        TextView tv_desc;
        TextView tv_time;

    }

    /*
    * viewPager适配器
    * */
    class TopViewPagerAdapter extends PagerAdapter{
        public BitmapUtils utils;
        public TopViewPagerAdapter(){
            utils=new BitmapUtils(mActivity);

        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView image=new ImageView(mActivity);
            image.setScaleType(ImageView.ScaleType.FIT_XY);
            utils.display(image,"http://www.sinaimg.cn/dy/slidenews/1_img/2016_49/83076_757008_585878.jpg");
            container.addView(image);
            return image;
        }
    }
}
