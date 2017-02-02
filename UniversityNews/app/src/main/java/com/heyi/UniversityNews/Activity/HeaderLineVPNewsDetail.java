package com.heyi.UniversityNews.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SearchRecentSuggestionsProvider;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.media.Image;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.heyi.UniversityNews.DataBase.UserInfoDataBase;
import com.heyi.UniversityNews.JavaBean.NewsComment;
import com.heyi.UniversityNews.JavaBean.RequestNewsComment;
import com.heyi.UniversityNews.JavaBean.ResponseNewsComment;
import com.heyi.UniversityNews.R;
import com.heyi.UniversityNews.ServerURL.ServerURL;
import com.heyi.UniversityNews.Utils.AnimationUtils;
import com.heyi.UniversityNews.View.CommentItemView;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.taobao.api.internal.toplink.Text;

import java.util.ArrayList;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.R.attr.bitmap;
import static android.os.Build.VERSION_CODES.N;
import static com.heyi.UniversityNews.R.layout.comment_item;


/**
 * Created by heyi on 2016/12/20.
 */

public class HeaderLineVPNewsDetail extends Activity {

    private Toolbar toolbar;
    private ImageView image;
    private TextView type;
    private WebView webView;
    private LinearLayout side_menu;
    private CircleImageView icon;
    private int alphaStat=1;
    private LinearLayout web_view_and_comment;
    private RelativeLayout loading;
    private ImageView add_comment;
    private View comment;
    private Button btn_addComment;
    private EditText et_comment;
    private int news_id;
    private LinearLayout news_comment_complete;
    private ImageView comment_complete;
    private AlertDialog alertDialog;
    private AlertDialog.Builder builder;
    private LinearLayout comment_detail;
    private HttpUtils httpUtils;
    private LinearLayout comment_more;
    private int GET_TIMES=0;
    private LinearLayout show_right_menu;
    private CircleImageView comment_icon;
    private TextView comment_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
    }

    private void initView() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.header_line_vp_news_detail);
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        image = (ImageView) findViewById(R.id.header_line_vp_new_detail_image);
        show_right_menu = (LinearLayout) findViewById(R.id.login_in_show_tool);
        type = (TextView) findViewById(R.id.header_line_vp_new_detail_type);
        webView= (WebView) findViewById(R.id.header_line_vp_new_detail_wv);
        side_menu = (LinearLayout) findViewById(R.id.view_pager_detail_side_menu);
        icon = (CircleImageView) findViewById(R.id.header_line_news_detail_icon);
        loading = (RelativeLayout) findViewById(R.id.header_line_news_detail_loading);
        add_comment = (ImageView) findViewById(R.id.header_line_news_detail_add_comment);
        comment = View.inflate(this, R.layout.dialog_comment, null);
        btn_addComment = (Button) comment.findViewById(R.id.add_comment);
        et_comment = (EditText) comment.findViewById(R.id.et_add_comment);
        comment_icon = (CircleImageView) comment.findViewById(R.id.comment_icon);
        comment_title = (TextView) comment.findViewById(R.id.header_line_comment_title);
        news_comment_complete = (LinearLayout) findViewById(R.id.news_comment_add_complete);
        comment_detail = (LinearLayout) findViewById(R.id.comment_detail);
        comment_complete = (ImageView) findViewById(R.id.news_comment_add_complete_iv);
        View more=View.inflate(HeaderLineVPNewsDetail.this,R.layout.add_comment_more,null);
        comment_more = (LinearLayout) more.findViewById(R.id.comment_more);
        //用户没登录时隐藏右边栏
        if(!getSharedPreferences("userInfo",MODE_PRIVATE).getBoolean("login",false)){
            show_right_menu.setVisibility(View.GONE);
        }



        //初始化httpUtil
        httpUtils = new HttpUtils();

        //为查看更多设置点击事件
        comment_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comment_detail.removeView(comment_more);
                getDataCommentFromServer(news_id,++GET_TIMES);
            }
        });


        btn_addComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //点击按钮添加评论
                checkAndAddComment();
            }
        });
        web_view_and_comment = (LinearLayout) findViewById(R.id.header_line_view_pager_news_wb_comment_ll);

        WebSettings settings= webView.getSettings();
        settings.setAppCachePath(getCacheDir().getPath());
        settings.setJavaScriptEnabled(true);
        settings.setAllowFileAccessFromFileURLs(true);
        settings.setLoadsImagesAutomatically(true);
        AlphaAnimation aa=new AlphaAnimation(1.0f,0.0f);
        aa.setDuration(0);
        aa.setFillAfter(true);
        side_menu.startAnimation(aa);




         //点击ICON，工具栏的显示与隐藏
        icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(alphaStat==1){

                AlphaAnimation aa=new AlphaAnimation(0.0f,1.0f);
                aa.setDuration(1000);
                    ScaleAnimation sa=new ScaleAnimation(0.0f,1.0f,0.0f,1.0f,
                            Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.0f);
                    sa.setDuration(1000);
                    AnimationSet as=new AnimationSet(false);
                    as.addAnimation(aa);
                    as.addAnimation(sa);
                    as.setFillAfter(true);
                    side_menu.startAnimation(as);
                    for(int i=0;i<side_menu.getChildCount();i++){
                        side_menu.getChildAt(i).setClickable(true);
                    }

                    alphaStat=0;
                }else {
                    AlphaAnimation aa=new AlphaAnimation(1.0f,0.0f);
                    aa.setDuration(1000);
                    ScaleAnimation sa=new ScaleAnimation(1.0f,0.0f,1.0f,0.0f,
                            Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.0f);
                    sa.setDuration(1000);
                    AnimationSet as=new AnimationSet(false);
                    as.addAnimation(aa);
                    as.addAnimation(sa);
                    as.setFillAfter(true);
                    side_menu.startAnimation(as);
                    for(int i=0;i<side_menu.getChildCount();i++){
                        side_menu.getChildAt(i).setClickable(false);
                    }
                    alphaStat=1;
                }
            }
        });

        //点击评论时评论栏的显示
        add_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showComment();
            }
        });
    }

    private void initData() {
        BitmapUtils bitmapUtils=new BitmapUtils(this);
        bitmapUtils.display(icon,ServerURL.SERVER_IP+getSharedPreferences("userInfo",
                MODE_PRIVATE).getString("icon_url",""));
        bitmapUtils.display(comment_icon,ServerURL.SERVER_IP+getSharedPreferences("userInfo",
                MODE_PRIVATE).getString("icon_url",""));
        Intent intent = getIntent();
        String title= intent.getStringExtra("title");
        String new_type= intent.getStringExtra("type");
        String image_url= intent.getStringExtra("image_url");
        String news_url= intent.getStringExtra("news_url");
        news_id = intent.getIntExtra("news_id", -1);
        comment_title.setText(title);
        bitmapUtils.display(image,image_url);
        type.setText(new_type);
        toolbar.setTitle(title);
        webView.loadUrl(news_url);





        //根据新闻的id获取评论
        getDataCommentFromServer(news_id,GET_TIMES);


        //为webView设置监听事件
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                //开始加载


                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                //加载完成
                loading.setVisibility(View.GONE);
                web_view_and_comment.setVisibility(View.VISIBLE);


                super.onPageFinished(view, url);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //所有的URL都在webview中加载
                webView.loadUrl(url);
                return true;
            }
        });




        initDialog();
    }

    private void initDialog(){
        builder = new AlertDialog.Builder(this);
        alertDialog = builder.create();
        alertDialog.setView(comment);
    }

    private void getDataCommentFromServer(int news_id,int get_times){
        //根据新闻的id获取评论
        //POST  json -- {API_ID:"2008Heyi",news_id:99,news:
        //HttpUtils utils=new HttpUtils();
        RequestNewsComment request = new RequestNewsComment();
        request.setAPI_ID("2008Heyi");
        request.setNews_id(news_id);
        request.setNews_from(0);
        request.setOperate(1);
        request.setGet_times(get_times);
        String s= new Gson().toJson(request);
        System.out.println(s);
        RequestParams requestParams=new RequestParams();
        requestParams.addBodyParameter("json",s);
        httpUtils.send(HttpRequest.HttpMethod.POST,
                ServerURL.SERVER_IP + ServerURL.NEWS_COMMENT_URL,requestParams
                , new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        String result= responseInfo.result;
                        System.out.println("result:"+result);
                        initComment(result);
                    }

                    @Override
                    public void onFailure(HttpException e, String s) {
                            Toast.makeText(HeaderLineVPNewsDetail.this,s,Toast.LENGTH_SHORT).show();
                    }
                });
    }


    //加载设置评论条目
    private void initComment(String result){
     //解析json数据
        ResponseNewsComment responseNewsComment = new Gson().
                fromJson(result, ResponseNewsComment.class);
        if(responseNewsComment.isSuccess()) {
            ArrayList<NewsComment> comment_item = responseNewsComment.getResult();
            if (comment_item.size() > 0) {
                for (int i = 0; i < comment_item.size(); i++) {
                    CommentItemView item = new CommentItemView(this);
                    item.setCommentBadNum(comment_item.get(i).getComment_bad());
                    item.setCommentDate(comment_item.get(i).getComment_time());
                    item.setCommentEditNum(comment_item.get(i).getComment_edit());
                    item.setCommentGoodNum(comment_item.get(i).getComment_good());
                    item.setUserEdit(comment_item.get(i).getComment_text());
                    item.setUserNickName(comment_item.get(i).getComment_user_nick_name());
                    item.setUserIcon(ServerURL.SERVER_IP + comment_item.get(i).
                            getComment_user_icon_url());
                    View view = item.getView();
                    comment_detail.addView(view);
                }

            }else{
                //该新闻没有评论
                comment_detail.addView(View.inflate(HeaderLineVPNewsDetail.this,
                        R.layout.comment_no_comment,null));

            }
            if(responseNewsComment.isMore()){
                //如果还有更多的评论，就添加一个可点击的显示更多的view
               comment_detail.addView(comment_more);
            }
        }
    }

    private void showComment(){

        alertDialog.show();

        //准备数据
        String phone= getSharedPreferences("userInfo", MODE_PRIVATE).getString("phone", null);
    }

    private void checkAndAddComment(){
        //校验并添加评论
        if(et_comment.getText().toString().trim().equals("")){
            //校验
            et_comment.setFocusable(true);
            et_comment.setFocusableInTouchMode(true);
            et_comment.requestFocus();
            et_comment.setHint("请认真输入评论哦....");
        }else{


            //HttpUtils http=new HttpUtils();
            RequestNewsComment request=new RequestNewsComment();
            request.setAPI_ID("2008Heyi");
            request.setComment_info(et_comment.getText().toString().trim());
            request.setNews_id(news_id);
            request.setNews_from(0);
            request.setOperate(2);
            String phone= getSharedPreferences("userInfo", MODE_PRIVATE).getString("phone", null);
            request.setPhone(phone);
            Gson gson=new Gson();
            String requestNewsComment = gson.toJson(request);
            RequestParams params=new RequestParams();
            params.addBodyParameter("json",requestNewsComment);
            httpUtils.send(HttpRequest.HttpMethod.POST,
                    ServerURL.SERVER_IP+ServerURL.NEWS_COMMENT_URL,
                    params, new RequestCallBack<String>() {
                        @Override
                        public void onSuccess(ResponseInfo<String> responseInfo) {
                            String result = responseInfo.result;
                            parseAddCommentData(result);

                        }

                        @Override
                        public void onFailure(HttpException e, String s) {
                            Toast.makeText(HeaderLineVPNewsDetail.this,"评论添加失败，请检查网络连接!",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    private void parseAddCommentData(String result){
        Gson gson=new Gson();
        ResponseNewsComment responseNewsComment = gson.fromJson(result,
                ResponseNewsComment.class);
        if(responseNewsComment.isSuccess()&&responseNewsComment.getRetCode()==200) {
            NewsCommentComplete(4);
        }
    }


    private void NewsCommentComplete(int event){
        //event:  1表示点赞，2表示踩，3表示收藏，4表示评论
     switch (event){
         case 1:
             break;
         case 2:
             break;
         case 3:
             break;
         case 4:
             comment_complete.setImageResource(R.drawable.edit);
             news_comment_complete.setVisibility(View.VISIBLE);
             AnimationUtils.hidden(news_comment_complete,0);
             alertDialog.dismiss();
             AnimationUtils.showAndHidden(news_comment_complete,1000);
             break;
     }

    }



}
