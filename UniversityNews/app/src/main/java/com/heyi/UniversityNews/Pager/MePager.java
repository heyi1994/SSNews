package com.heyi.UniversityNews.Pager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.heyi.UniversityNews.Activity.ChangeNickNameActivity;
import com.heyi.UniversityNews.Activity.ChangeSignActivity;
import com.heyi.UniversityNews.R;
import com.heyi.UniversityNews.ServerURL.ServerURL;
import com.intrusoft.library.FrissonView;
import com.lidroid.xutils.BitmapUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.view.View.inflate;
import static com.heyi.UniversityNews.R.drawable.bad;
import static com.heyi.UniversityNews.R.mipmap.nick_name;

/**
 * Created by heyi on 2016/11/26.
 */

public class MePager extends BasePager {

    private FrissonView fv;
    private CircleImageView imageView;
    private TextView tv_name;
    private TextView signature_me;
    private TextView good;
    private TextView edit;
    private TextView collect;
    private RelativeLayout rl_signature;
    private boolean login;
    private int[] color={Color.rgb(255,182,193),
            Color.rgb(139,0,139),Color.rgb(0,0,139),
            Color.rgb(255,165,0),Color.rgb(128,128,128),Color.rgb(0,124,204)};

    public MePager(Activity activity) {
        super(activity);
    }

    @Override
    public View initView() {
       View view= View.inflate(mActivity, R.layout.me_pager,null);
        fv = (FrissonView) view.findViewById(R.id.frisson_view);
        imageView= (CircleImageView) view.findViewById(R.id.me_pager_icon);
        tv_name= (TextView) view.findViewById(R.id.me_pager_name);
        signature_me= (TextView) view.findViewById(R.id.signature_me_pager);
        good= (TextView) view.findViewById(R.id.me_tv_like_num);
        edit= (TextView) view.findViewById(R.id.me_tv_edit_num);
        collect= (TextView) view.findViewById(R.id.me_tv_collect_num);
        rl_signature= (RelativeLayout) view.findViewById(R.id.rl_sighature);
        return view;
    }

    @Override
    public void initData() {

        BitmapUtils utils=new BitmapUtils(mActivity);
        String icon_url = mActivity.getSharedPreferences("userInfo",
                Context.MODE_PRIVATE).getString("icon_url", "");
        String name = mActivity.getSharedPreferences("userInfo",
                Context.MODE_PRIVATE).getString("nick_name", "");
        login = mActivity.getSharedPreferences("userInfo",
                Context.MODE_PRIVATE).getBoolean("login", false);
            if(login) {
                    BitmapUtils bitmapUtils=new BitmapUtils(mActivity);
                    bitmapUtils.display(imageView,ServerURL.SERVER_IP
                            + icon_url);
                tv_name.setText(name);
                String sign = mActivity.getSharedPreferences("userInfo",
                        Context.MODE_PRIVATE).getString("signature", "");
                signature_me.setText(sign);
                int good_num = mActivity.getSharedPreferences("userInfo",
                        Context.MODE_PRIVATE).getInt("like_num", 0);
                good.setText(""+good_num);
                int edit_num = mActivity.getSharedPreferences("userInfo",
                        Context.MODE_PRIVATE).getInt("comment_num", 0);
                edit.setText(""+edit_num);
                int collect_num = mActivity.getSharedPreferences("userInfo",
                        Context.MODE_PRIVATE).getInt("collect_num", 0);
                collect.setText(""+collect_num);

            }
        Bitmap bitmap1 = BitmapFactory.decodeResource(mActivity.getResources(), R.mipmap.me_pager_bg);
            fv.setBitmap(bitmap1);
        int bg=mActivity.getSharedPreferences("config",
                Context.MODE_PRIVATE).getInt("me_pager_bg",Color.rgb(0,124,204));
        fv.setTintColor(bg);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RotateAnimation ra=new RotateAnimation(0,360,
                        Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
                ra.setDuration(1000);
                imageView.startAnimation(ra);
                changeBackground();
            }
        });



        rl_signature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(login){
               Intent intent=new Intent(mActivity, ChangeSignActivity.class);
                    mActivity.startActivityForResult(intent,1);
                }
            }
        });
        tv_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(login){
                    Intent intent=new Intent(mActivity, ChangeNickNameActivity.class);
                    mActivity.startActivityForResult(intent,2);
                }
            }
        });
    }

    @Override
    public void backPress(String s,int result) {
       if(result==1){
           signature_me.setText(s);
           mActivity.getSharedPreferences("userInfo",
                   Context.MODE_PRIVATE).edit().putString("signature",s).commit();
       }else if(result==2){
           tv_name.setText(s);
           mActivity.getSharedPreferences("userInfo",
                   Context.MODE_PRIVATE).edit().putString("nick_name",s).commit();
       }
    }
    public void changeBackground(){
        int i = new Random().nextInt(color.length);
        fv.setTintColor(color[i]);
        mActivity.getSharedPreferences("config",
                Context.MODE_PRIVATE).edit().putInt("me_pager_bg",color[i]).commit();

    }
}
