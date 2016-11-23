package com.heyi.UniversityNews.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
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

import java.util.Timer;
import java.util.TimerTask;

import static com.heyi.UniversityNews.R.id.info;


/**
 * Created by heyi on 2016/11/22.
 */

public class SplashActivity extends Activity {
    private TextView tv_timer;
    private int time = 5;
    private RelativeLayout rl;
    private Timer timer = new Timer();
    private TextView tv_version;
    private String versionName;
    private View view_check_version;
    private View view_check_version1;
    private int STOP_ENTER_HOME=0;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    private AppInfo appInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        loadSplashBackground();
        initData();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    private void initView() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash);
        tv_timer = (TextView) findViewById(R.id.tv_timer);
        rl = (RelativeLayout) findViewById(R.id.rl_splash);
        tv_version = (TextView) findViewById(R.id.tv_version);

    }

    private void initData() {


        final Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (time >= 0) {
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
        getAppVersion();
        getDataFromServer();

    }


    private void loadSplashBackground() {
        BitmapUtils utils = new BitmapUtils(getApplicationContext());
        utils.display(rl, ServerURL.SERVER_IP + ServerURL.SPLASH_BG_URL);
    }

    /*
    * 检测版本
    * */
    private void checkUpdate() {

        if (appInfo != null) {
            if (!versionName.equals(appInfo.getAppVersion())) {
                serialUpdateDialog();
            }
        } else {
            Toast.makeText(SplashActivity.this, "无法连接到服务器！", Toast.LENGTH_LONG);
        }
    }

    private void serialUpdateDialog() {
        STOP_ENTER_HOME=1;
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        view_check_version1 = View.inflate(this, R.layout.dialog_check_version, null);
        dialog.setView(view_check_version1);
        dialog.setCancelable(false);
        dialog.show();
        TextView title = (TextView) view_check_version1.findViewById(R.id.version_title);
        TextView info1 = (TextView) view_check_version1.findViewById(R.id.version_info1);
        TextView info2 = (TextView) view_check_version1.findViewById(R.id.version_info2);
        TextView info3 = (TextView) view_check_version1.findViewById(R.id.version_info3);
        String[] split = appInfo.getAppUpInfo().trim().split(",");
        title.setText("已检测到新版本:" + appInfo.getAppVersion());
        info1.setText("-" + split[0]);
        info2.setText("-" + split[1]);
        System.out.println(split[2]);
        info3.setText("-" + split[2]);
        doChose();
    }


    private void doChose() {

        TextView up_version = (TextView) view_check_version1.findViewById(R.id.tv_up_version);
        TextView cancel = (TextView) view_check_version1.findViewById(R.id.tv_cancel_version);
        up_version.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
            tv_version.setText(versionName);

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
        System.out.println(url);
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

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Splash Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }
}
