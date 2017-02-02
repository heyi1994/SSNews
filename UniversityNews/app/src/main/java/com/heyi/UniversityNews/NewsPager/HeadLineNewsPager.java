package com.heyi.UniversityNews.NewsPager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.heyi.UniversityNews.Activity.HeaderLineVPNewsDetail;
import com.heyi.UniversityNews.JavaBean.HeaderLineNews;
import com.heyi.UniversityNews.R;
import com.heyi.UniversityNews.ServerURL.ServerURL;
import com.heyi.UniversityNews.View.HeaderLineViewPager;
import com.heyi.UniversityNews.View.RefreshListView;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.V;

/**
 * Created by heyi on 2016/12/7.
 */

public class HeadLineNewsPager extends NewsPagerDetailBasePager {

    private View mRoot;
    private RefreshListView listView;
    private  ArrayList<String> al=new ArrayList<String>();
    private MyAdapter myAdapter;
    private View header;
    private HeaderLineViewPager vp;
    private TextView vp_title;
    private CirclePageIndicator vp_indicator;
    private ArrayList<HeaderLineNews.HeaderLineVpNews> vp_news;

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

        listView.setOnRefreshListener(new RefreshListView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                PullRefresh();
            }
        });

        return mRoot;
    }


    @Override
    protected void initData() {
        getDataFromServer();


    }

    private void getDataFromServer() {
        HttpUtils http=new HttpUtils();
        RequestParams params = new RequestParams();
        params.addBodyParameter("json","{API_ID:\"2008Heyi\",requestCode:1}");
        http.send(HttpRequest.HttpMethod.POST,
                ServerURL.SERVER_IP + ServerURL.GET_HEADER_LINE_VP_NEWS_URL,params,
                new RequestCallBack<String>() {
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        String result = responseInfo.result;
                        System.out.println(result);
                        mActivity.getSharedPreferences("config", Context.MODE_PRIVATE)
                                .edit().putString("header_line_news",result).commit();
                        parseData(result);
                    }
                    public void onFailure(HttpException e, String s) {

                        String cacheData= mActivity.getSharedPreferences("config", Context.MODE_PRIVATE).
                                getString("header_line_news", null);
                        Toast.makeText(mActivity,"无网络连接!",Toast.LENGTH_LONG).show();
                        parseData(cacheData);
                    }
                });
    }

             private void parseData(String json) {
                 Gson gson=new Gson();
                 HeaderLineNews headerLineNews = gson.fromJson(json, HeaderLineNews.class);
                 if(headerLineNews.isSuccess()&&headerLineNews.getRetCode()==200){
                     vp_news = headerLineNews.getVp_news();
                     vp.setAdapter(new TopViewPagerAdapter());
                     vp_indicator.setViewPager(vp);
                     vp_title.setText(vp_news.get(0).getVp_title());
                      al.add("1");
                     al.add("2");
                     if(al!=null) {
                         myAdapter = new MyAdapter();
                         listView.setAdapter(myAdapter);
                     }
                     listView.addHeaderView(header);

                     vp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                         @Override
                         public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                         }

                         @Override
                         public void onPageSelected(int position) {
                             vp_title.setText(vp_news.get(position).getVp_title());
                             vp_indicator.setCurrentItem(position);

                         }

                         @Override
                         public void onPageScrollStateChanged(int state) {

                         }
                     });



                 }
             }

    private void PullRefresh(){
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
            return vp_news.size();
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
            final int item=position;
            image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(mActivity,HeaderLineVPNewsDetail.class);
                    intent.putExtra("news_id",vp_news.get(item).getVp_news_id());
                    intent.putExtra("title",vp_news.get(item).getVp_title());
                    intent.putExtra("type",vp_news.get(item).getVp_type());
                    intent.putExtra("image_url",ServerURL.SERVER_IP+
                            vp_news.get(item).getVp_image_url());
                    intent.putExtra("news_url",vp_news.get(item).getVp_news_url());

                    mActivity.startActivity(intent);
                }
            });
            image.setScaleType(ImageView.ScaleType.FIT_XY);
            utils.display(image,ServerURL.SERVER_IP+vp_news.get(position).getVp_image_url());
            container.addView(image);
            return image;
        }
    }
}
