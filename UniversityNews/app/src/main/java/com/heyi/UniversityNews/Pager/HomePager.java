package com.heyi.UniversityNews.Pager;

import android.app.Activity;
import android.content.Context;
import android.os.Environment;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.heyi.UniversityNews.Activity.MainActivity;
import com.heyi.UniversityNews.HomePager.UserModular;
import com.heyi.UniversityNews.JavaBean.History;
import com.heyi.UniversityNews.JavaBean.Weather;
import com.heyi.UniversityNews.R;
import com.heyi.UniversityNews.ServerURL.ServerURL;
import com.heyi.UniversityNews.View.HorViewPager;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import static android.view.View.inflate;

/**
 * Created by heyi on 2016/11/26.
 */

public class HomePager extends BasePager {

    private Weather weather;
    private TextView temperature;
    private TextView city;
    private TextView weather1;
    private TextView humidity;
    private TextView pollution;
    private TextView sunrise;
    private TextView sunset;
    private TextView wind;
    private TextView dress_index;
    private TextView date;
    private  TextView airCon;
    private HorViewPager viewPager;
    private TextView text_split;
    private View pager;
    private TextView tv_history;
    private ImageView toggle_sidingMenu;
    private TextView tv_exe;
    private FrameLayout user_info;
    private TextView day1_date;
    private TextView day1_dayTime;
    private TextView day1_tem;
    private  TextView day1_wind;
    private TextView day2_date;
    private TextView day2_dayTime;
    private TextView day2_tem;
    private  TextView day2_wind;
    private TextView day3_date;
    private TextView day3_dayTime;
    private TextView day3_tem;
    private  TextView day3_wind;
    private View future;

    public HomePager(Activity activity) {
        super(activity);
    }

    @Override
    public View initView() {
        View view= inflate(mActivity, R.layout.pager_home,null);
        viewPager = (HorViewPager) view.findViewById(R.id.view_pager_weather);
        tv_history = (TextView) view.findViewById(R.id.tv_history);
       toggle_sidingMenu= (ImageView) view.findViewById(R.id.toggle_silding_menu);
        user_info= (FrameLayout) view.findViewById(R.id.user_info);


        pager= inflate(mActivity,R.layout.view_pager_weather_1,null);
        temperature = (TextView) pager.findViewById(R.id.temperature);
        city = (TextView) pager.findViewById(R.id.where);
        weather1 = (TextView) pager.findViewById(R.id.weather);
        humidity = (TextView) pager.findViewById(R.id.humidity);
        pollution = (TextView) pager.findViewById(R.id.pollutionIndex);
        sunrise = (TextView) pager.findViewById(R.id.sunrise);
        sunset = (TextView) pager.findViewById(R.id.sunset);
        wind = (TextView) pager.findViewById(R.id.wind);
        dress_index = (TextView) pager.findViewById(R.id.dress);
        date = (TextView) pager.findViewById(R.id.weather_date);
        airCon=(TextView) pager.findViewById(R.id.airCon);
        text_split=(TextView) pager.findViewById(R.id.text_split);
        tv_exe = (TextView) pager.findViewById(R.id.exerciseIndex);


        future = View.inflate(mActivity, R.layout.view_pager_weather_2,null);
        day1_date = (TextView) future.findViewById(R.id.day1_date);
        day1_dayTime=(TextView) future.findViewById(R.id.day1_dayTime);
        day1_tem= (TextView) future.findViewById(R.id.day1_temperature);
        day1_wind=(TextView) future.findViewById(R.id.day1_wind);
        day2_date = (TextView) future.findViewById(R.id.day2_date);
        day2_dayTime=(TextView) future.findViewById(R.id.day2_dayTime);
        day2_tem= (TextView) future.findViewById(R.id.day2_temperature);
        day2_wind=(TextView) future.findViewById(R.id.day2_wind);
        day3_date = (TextView) future.findViewById(R.id.day3_date);
        day3_dayTime=(TextView) future.findViewById(R.id.day3_dayTime);
        day3_tem= (TextView) future.findViewById(R.id.day3_temperature);
        day3_wind=(TextView) future.findViewById(R.id.day3_wind);
        return view;
    }

    @Override
    public void initData() {
        viewPager.setAdapter(new MyAdapter());
        HttpUtils http=new HttpUtils();
        RequestParams rp=new RequestParams();
        try {
            String ip = mActivity.getSharedPreferences("config", Context.MODE_PRIVATE).getString("ip", "");
            http.send(HttpRequest.HttpMethod.GET,
                    ServerURL.API_WEATHRE_URL + "?key=195fb7aa55ba7&ip=" + ip, new RequestCallBack<String>() {

                        @Override
                        public void onSuccess(ResponseInfo<String> responseInfo) {

                            String result = responseInfo.result;
                            System.out.println(result);
                            mActivity.getSharedPreferences("homePager", Context.MODE_PRIVATE).
                                    edit().putString("weather",result).commit();
                           parseDate(result);
                        }

                        @Override
                        public void onFailure(HttpException e, String s) {
                            String re=mActivity.getSharedPreferences("homePager",
                                    Context.MODE_PRIVATE).getString("weather","");
                            Toast.makeText(mActivity,"无网络连接,无法获取最新天气信息!",
                                    Toast.LENGTH_LONG).show();
                            parseDate(re);

                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
        String his = mActivity.getSharedPreferences("config", Context.MODE_PRIVATE).getString("history", "0101");
        if(!his.equals(new SimpleDateFormat("MMdd").format(new Date()))){
            HttpUtils http_his=new HttpUtils();
            String day=new SimpleDateFormat("MMdd").format(new Date());
            http_his.send(HttpRequest.HttpMethod.GET, ServerURL.API_HISTORY_URL
                    + "?key=195fb7aa55ba7&day=" + day, new RequestCallBack<String>() {

                @Override
                public void onSuccess(ResponseInfo<String> responseInfo) {
                    String result = responseInfo.result;
                    try {
                        FileWriter writer=new FileWriter(Environment.getExternalStorageDirectory().
                                getPath()+"/com.heyi.UniversityNews/his.txt");
                        writer.write(result);
                        writer.flush();
                        writer.close();
                        mActivity.getSharedPreferences("config",Context.MODE_PRIVATE).edit().
                                putString("history",new SimpleDateFormat("MMdd").format(new Date())).commit();
                        getDataFromLocal();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(HttpException e, String s) {

                }
            });
        }else{
            getDataFromLocal();
        }
        toggleMenu();

        initUserInfo();

    }

    /*
    * 点击图标关闭侧边栏
    * */
    private void toggleMenu(){
        toggle_sidingMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity main=(MainActivity)mActivity;
                main.toggle();
            }
        });
    }

    /*
    * 解析json数据
    *
    * */
    private void parseDate(String info){
        Gson gson=new Gson();
        weather = gson.fromJson(info, Weather.class);
        if(weather!=null) {
            if (weather.retCode.equals("200")) {
                addDate();
            } else {
                Toast.makeText(mActivity, "服务器维护，无法获取到天气信息", Toast.LENGTH_LONG).show();
            }
        }
    }
    private void addDate(){

        ArrayList<Weather.TodayInfo> result = weather.result;
        if(result.get(0)!=null) {
            temperature.setText("" + result.get(0).temperature);
            city.setText("" + result.get(0).city);
            weather1.setText("" + result.get(0).weather);
            text_split.setVisibility(View.VISIBLE);
            humidity.setText("" + result.get(0).humidity.trim());
            pollution.setText("空气质量指数：" + result.get(0).pollutionIndex);
            sunrise.setText("日出：" + result.get(0).sunrise);
            sunset.setText("日落：" + result.get(0).sunset);
            wind.setText("风力：" + result.get(0).wind);
            dress_index.setText("穿衣："+result.get(0).dressingIndex);
            date.setText(""+result.get(0).date);
            airCon.setText(""+result.get(0).airCondition);
            tv_exe.setText("锻炼指数："+result.get(0).exerciseIndex);

            day1_date.setText(result.get(0).future.get(1).date);
            day1_dayTime.setText(result.get(0).future.get(1).dayTime);
            day1_tem.setText(result.get(0).future.get(1).temperature);
            day1_wind.setText(result.get(0).future.get(1).temperature);

            day2_date.setText(result.get(0).future.get(2).date);
            day2_dayTime.setText(result.get(0).future.get(2).dayTime);
            day2_tem.setText(result.get(0).future.get(2).temperature);
            day2_wind.setText(result.get(0).future.get(2).temperature);

            day3_date.setText(result.get(0).future.get(3).date);
            day3_dayTime.setText(result.get(0).future.get(3).dayTime);
            day3_tem.setText(result.get(0).future.get(3).temperature);
            day3_wind.setText(result.get(0).future.get(3).temperature);
        }
    }




    class MyAdapter extends PagerAdapter{

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view;
             if(position==0){
                view=pager;
             }else{
                    pager= future;
             }
            container.addView(pager);
            return pager;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

    /*
    * 从本地文件夹读取历史数据
    * */
    public void getDataFromLocal(){
        try {
            Gson gson = new Gson();
            String path = Environment.getExternalStorageDirectory().getPath() +
                    "/com.heyi.UniversityNews/his.txt";
            BufferedReader br = new BufferedReader(new FileReader(path));
            History history = gson.fromJson(br, History.class);
            addHistoryData(history);
        }catch (Exception e){
            Toast.makeText(mActivity,"请勿删除SK卡com.heyi.University目录下的文件"
                    ,Toast.LENGTH_LONG).show();
        }

    }
    /*
    * 添加数据到历史view
    * */
    private void addHistoryData(History his){
        ArrayList<History.Today> result = his.result;
        Random r=new Random();
        int i = r.nextInt(result.size());
        History.Today today = result.get(i);
        tv_history.setText(today.event);
    }
    /*
    * 用户模块
    * */
    private void initUserInfo(){
    user_info.addView(new UserModular(mActivity).mView);
    }
}
