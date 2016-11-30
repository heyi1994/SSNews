package com.heyi.UniversityNews.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.heyi.UniversityNews.JavaBean.AppInfo;
import com.heyi.UniversityNews.R;
import com.heyi.UniversityNews.ServerURL.ServerURL;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import java.io.File;
import java.util.Timer;
import java.util.TimerTask;





/**
 * Created by heyi on 2016/11/22.
 */

public class SplashActivity extends Activity {
    private TextView tv_timer;
    private int time = 5;
    private RelativeLayout rl;
    private Timer timer = new Timer();
    private String versionName;
    private View view_check_version;
    private View view_check_version1;
    private int STOP_ENTER_HOME=0;

    private AppInfo appInfo;
    private BitmapUtils utils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();

    }

    private void initView() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        tv_timer = (TextView) findViewById(R.id.tv_timer);
        rl = (RelativeLayout) findViewById(R.id.rl_splash);
         initSplashImage();
    }
     private void initSplashImage(){
         File image=new File(getFilesDir()+"splash.png");
         if(image.exists()){
           rl.setBackground(Drawable.createFromPath(image.getPath()));
         }else{
           rl.setBackgroundResource(R.mipmap.default_splash);
         }
     }
    private void initData() {


        final Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (time > 0) {
                            tv_timer.setText(time + "s");
                            time--;
                        } else {
                            timer.cancel();
                            tv_timer.setVisibility(View.INVISIBLE);
                            if(STOP_ENTER_HOME==0){
                                enterHome();
                            }
                        }
                    }
                });
            }
        }, 0, 1000);
       HttpUtils ipHttp=new HttpUtils();
        ipHttp.send(HttpRequest.HttpMethod.GET, ServerURL.SERVER_IP + ServerURL.GET_IP_URL
                , new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        String result = responseInfo.result;
                        getSharedPreferences("config",MODE_PRIVATE).
                                edit().putString("ip",result).commit();
                    }

                    @Override
                    public void onFailure(HttpException e, String s) {

                    }
                });
        getAppVersion();
        getDataFromServer();

    }
    /*
    * 检测版本
    * */
    private void checkUpdate() {

        if (appInfo != null) {
            if (!versionName.equals(appInfo.getAppVersion())) {
                serialUpdateDialog();
                if(!getSharedPreferences("config",MODE_PRIVATE).getString("splash_image_url","")
                        .equals(appInfo.getAppSplashUrl())){
                    downloadSplash();
                }

            }else if(!getSharedPreferences("config",MODE_PRIVATE).getString("splash_image_url","")
                    .equals(appInfo.getAppSplashUrl())){
                downloadSplash();
            }
        } else {
            Toast.makeText(SplashActivity.this, "无法连接到服务器！", Toast.LENGTH_LONG).show();
        }
    }
 /*
 * 子线程下载图片
 * */
    private void downloadSplash(){
        File old=new File(getFilesDir()+"splash.png");
        if(old.exists()){
            old.delete();
        }
        HttpUtils http=new HttpUtils();
        http.download(ServerURL.SERVER_IP + appInfo.getAppSplashUrl(), getFilesDir() + "splash.png", new RequestCallBack<File>() {
            @Override
            public void onSuccess(ResponseInfo<File> responseInfo) {
                getSharedPreferences("config",MODE_PRIVATE).edit().putString("splash_image_url",
                        appInfo.getAppSplashUrl()).commit();
            }

            @Override
            public void onFailure(HttpException e, String s) {

            }
        });
    }

    private void serialUpdateDialog() {
        STOP_ENTER_HOME=1;
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        view_check_version1 = View.inflate(this, R.layout.dialog_check_version, null);
        dialog.setView(view_check_version1);
        dialog.setCancelable(false);
        dialog.show();
        TextView title = (TextView) view_check_version1.findViewById(R.id.version_title);
        TextView now_version = (TextView) view_check_version1.findViewById(R.id.version_now_version);
        TextView info1 = (TextView) view_check_version1.findViewById(R.id.version_info1);
        TextView info2 = (TextView) view_check_version1.findViewById(R.id.version_info2);
        TextView info3 = (TextView) view_check_version1.findViewById(R.id.version_info3);
        String[] split = appInfo.getAppUpInfo().trim().split(",");
        title.setText("已检测到新版本: " + appInfo.getAppVersion());

        now_version.setText("当前版本: "+versionName);
        info1.setText("-" + split[0]);
        info2.setText("-" + split[1]);
        info3.setText("-" + split[2]);
        doChose();
    }


    private void doChose() {

        final TextView up_version = (TextView) view_check_version1.findViewById(R.id.tv_up_version);
        TextView cancel = (TextView) view_check_version1.findViewById(R.id.tv_cancel_version);
        up_version.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                up_version.setTextColor(Color.WHITE);
                up_version.setText("下载中..");
                up_version.setBackgroundResource(R.drawable.text_bg_checkv);
                up_version.setClickable(false);
                HttpUtils http=new HttpUtils();
                http.download(ServerURL.SERVER_IP + appInfo.getAppDownlodUrl()
                        , Environment.getExternalStorageDirectory() .getPath()+
                                "/com.heyi.UniversityNews/沈师新闻."+appInfo.getAppVersion()+".apk",
                        new RequestCallBack<File>() {
                            @Override
                            public void onSuccess(ResponseInfo<File> responseInfo) {
                                up_version.setBackground(null);
                                up_version.setClickable(true);
                                up_version.setText("马上升级");
                                up_version.setTextColor(Color.rgb(0,153,204));

                                Toast.makeText(getApplicationContext(),"安装包下载完成!",
                                        Toast.LENGTH_LONG).show();
                                Intent in=new Intent(Intent.ACTION_VIEW);
                                in.addCategory(Intent.CATEGORY_DEFAULT);
                                in.setDataAndType(Uri.fromFile(responseInfo.result)
                                        ,"application/vnd.android.package-archive");
                                startActivity(in);
                            }

                            @Override
                            public void onFailure(HttpException e, String s) {
                                            Toast.makeText(getApplicationContext(),
                                                    "安装包下载失败！请检查SD卡是否挂载！",Toast.LENGTH_LONG).show();
                            }
                        });
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enterHome();
            }
        });
    }

    /*
    * 动态获取app的版本号
    * */
    private void getAppVersion() {
        PackageManager packageManager = getPackageManager();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(getPackageName(), PackageManager.GET_SHARED_LIBRARY_FILES);
            versionName = packageInfo.versionName;

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    /*
    * 从服务器获取到版本信息
    * */
    private void getDataFromServer() {
        HttpUtils http = new HttpUtils();
        String url = ServerURL.SERVER_IP + ServerURL.APP_INFO_URL;
        http.send(HttpRequest.HttpMethod.POST, url,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        String result = responseInfo.result;
                        Gson gson = new Gson();
                        appInfo = gson.fromJson(result, AppInfo.class);
                       checkUpdate();
                    }

                    @Override
                    public void onFailure(HttpException e, String s) {
                        Toast.makeText(SplashActivity.this, "网络故障，无法获取到数据", Toast.LENGTH_LONG).show();
                    }
                });
    }

    /*
    * 进入主页面
    * */
    private void enterHome() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }


}
