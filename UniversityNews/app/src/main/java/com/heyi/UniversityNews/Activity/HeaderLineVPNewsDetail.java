package com.heyi.UniversityNews.Activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.heyi.UniversityNews.R;
import com.heyi.UniversityNews.View.CommentItemView;
import com.lidroid.xutils.BitmapUtils;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.R.attr.cacheColorHint;
import static android.R.attr.x;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
    }

    private void initView() {
        setContentView(R.layout.header_line_vp_news_detail);
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        image = (ImageView) findViewById(R.id.header_line_vp_new_detail_image);
        type = (TextView) findViewById(R.id.header_line_vp_new_detail_type);
        webView= (WebView) findViewById(R.id.header_line_vp_new_detail_wv);
        side_menu = (LinearLayout) findViewById(R.id.view_pager_detail_side_menu);
        icon = (CircleImageView) findViewById(R.id.header_line_news_detail_icon);
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
        side_menu.setFocusable(false);


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
                    side_menu.setFocusable(true);
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
                    side_menu.setFocusable(false);
                    alphaStat=1;
                }
            }
        });
    }

    private void initData() {
        Intent intent = getIntent();
        String title= intent.getStringExtra("title");
        String new_type= intent.getStringExtra("type");
        String image_url= intent.getStringExtra("image_url");
        String news_url= intent.getStringExtra("news_url");
        BitmapUtils bitmap=new BitmapUtils(this);
        bitmap.display(image,image_url);
        type.setText(new_type);
        toolbar.setTitle(title);
        webView.loadUrl(news_url);
        initComment();

    }


    //加载设置评论条目
    private void initComment(){
        for(int i=0;i<5;i++) {
            CommentItemView item = new CommentItemView(this);
            item.setCommentBadNum(333+i);
            item.setCommentDate(new Date());
            item.setCommentEditNum(444+i);
            item.setCommentGoodNum(666+i);
            item.setUserEdit("这篇文章不错啊，哈哈！"+i);
            item.setUserNickName("随机生成"+i);
            View view= item.getView();
            web_view_and_comment.addView(view);
        }
    }

}
