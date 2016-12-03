package com.heyi.UniversityNews.HomePager;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.Result;
import com.google.gson.Gson;
import com.heyi.UniversityNews.Activity.MainActivity;
import com.heyi.UniversityNews.Activity.RegisterActivity;
import com.heyi.UniversityNews.JavaBean.LoginResult;
import com.heyi.UniversityNews.JavaBean.UserInfo;
import com.heyi.UniversityNews.R;
import com.heyi.UniversityNews.ServerURL.ServerURL;
import com.heyi.UniversityNews.Utils.JsonUtils;
import com.heyi.UniversityNews.Utils.MD5Utils;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.V;
import static android.view.View.inflate;

/**
 * Created by heyi on 2016/11/28.
 */

public class UserModular extends BaseModular {
    private Button btn_login;
    private View dialog_login;
    private Button btn_dialog_login;
    private Button btn_dialog_cancel;
    private Button btn_register;
    private EditText et_login;
    private EditText et_pwd;
    private AlertDialog.Builder builder;
    private AlertDialog alertDialog;
    private ImageView user_icon;
    private TextView user_name;
    private TextView user_sign;
    private TextView user_like;
    private TextView user_dislike;
    private TextView user_edit;
    private TextView user_collect;

    public UserModular(Activity activity) {
        super(activity);
    }

    @Override
    protected View initView() {
        boolean login = mActivity.getSharedPreferences("userInfo",
                Context.MODE_PRIVATE).getBoolean("login", false);
        View inflate=null;
                if(login) {
                    View view = View.inflate(mActivity, R.layout.home_pager_user, null);
                    user_icon= (ImageView) view.findViewById(R.id.user_icon);
                    user_name= (TextView) view.findViewById(R.id.user_name);
                    user_sign= (TextView) view.findViewById(R.id.user_signature);
                    user_like=(TextView) view.findViewById(R.id.user_like_num);
                    user_dislike=(TextView) view.findViewById(R.id.user_dislike);
                    user_edit= (TextView) view.findViewById(R.id.user_comment);
                    user_collect=(TextView) view.findViewById(R.id.user_collect);
                    updateUserInfo();
                    inflate =view;
                }else {
                    inflate= View.inflate(mActivity,R.layout.home_pager_user_no_login,null);
                   btn_login= (Button) inflate.findViewById(R.id.btn_login);
                    btn_register= (Button) inflate.findViewById(R.id.btn_zheche);
                }

        return inflate;
    }

    @Override
    protected void initData() {
        if(btn_login!=null){
            loginOrRegister();
      }
    }

    private void loginOrRegister() {
            btn_login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog_login= View.inflate(mActivity,R.layout.dialog_login,null);
                    btn_dialog_login = (Button) dialog_login.findViewById(R.id.btn_dialog_login);
                    btn_dialog_cancel = (Button) dialog_login.findViewById(R.id.btn_dialog_cancel);
                    et_login = (EditText) dialog_login.findViewById(R.id.et_login_phone);
                    et_pwd=(EditText) dialog_login.findViewById(R.id.et_login_password);
                     builder= new AlertDialog.Builder(mActivity);
                    alertDialog =builder.create();
                    alertDialog.setView(dialog_login);
                    alertDialog.setCancelable(false);
                    alertDialog.show();
                    btn_dialog_login.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String phone = et_login.getText().toString().trim();
                            String pwd=et_pwd.getText().toString().trim();
                            if(!TextUtils.isEmpty(phone)&&!TextUtils.isEmpty(pwd)){
                                JsonUtils jsonUtils=new JsonUtils();
                                jsonUtils.startFirstChild("API_ID","1995Heyi");
                                jsonUtils.startOtherChild("phone",phone);
                                jsonUtils.startOtherChild("password", MD5Utils.md5(pwd));
                                String endJson = jsonUtils.endJson();
                                HttpUtils http=new HttpUtils();
                                RequestParams params=new RequestParams();
                                params.addBodyParameter("json",endJson);
                                http.send(HttpRequest.HttpMethod.POST, ServerURL.SERVER_IP +
                                        ServerURL.USER_Login_URL, params, new RequestCallBack<String>() {

                                    @Override
                                    public void onSuccess(ResponseInfo<String> responseInfo) {
                                        String result = responseInfo.result;
                                        if(result!=null){
                                            Gson gson=new Gson();
                                            LoginResult loginResult = gson.fromJson(result,
                                                    LoginResult.class);
                                            if(loginResult.isSuccess()&&loginResult.getRetCode()==200){
                                                UserInfo userInfo = loginResult.getResult();
                                                SharedPreferences sp = mActivity.getSharedPreferences("userInfo",
                                                        Context.MODE_PRIVATE);
                                                sp.edit().putBoolean("login",true).commit();
                                                sp.edit().putString("nick_name",
                                                        userInfo.getUser_nick_name()).commit();
                                                sp.edit().putString("phone",
                                                        userInfo.getUser_phone()).commit();
                                                sp.edit().putString("signature",userInfo.getUser_signature())
                                                        .commit();
                                                sp.edit().putString("icon_url",userInfo.getUser_icon_url())
                                                        .commit();
                                                sp.edit().putString("register",userInfo.getUser_register_date())
                                                        .commit();
                                                sp.edit().putInt("user_id",userInfo.getUser_id())
                                                        .commit();
                                                sp.edit().putInt("collect_num",userInfo.getUser_collect_num())
                                                        .commit();
                                                sp.edit().putInt("like_num",userInfo.getUser_like_num())
                                                        .commit();
                                                sp.edit().putInt("dislike_num",userInfo.getUser_dislike_num())
                                                        .commit();
                                                sp.edit().putInt("comment_num",userInfo.getUser_comment_num())
                                                        .commit();
                                                Toast.makeText(mActivity,
                                                        "登录成功！",Toast.LENGTH_LONG).show();
                                                mActivity.finish();
                                                mActivity.startActivity(new Intent(mActivity, MainActivity.class));

                                            }else{
                                                Toast.makeText(mActivity,
                                                        "用户名或者密码错误！",Toast.LENGTH_LONG).show();
                                            }

                                        }
                                    }

                                    @Override
                                    public void onFailure(HttpException e, String s) {
                                        Toast.makeText(mActivity,
                                                "无网络连接！",Toast.LENGTH_LONG).show();

                                    }
                                });
                            }else{
                                Toast.makeText(mActivity,
                                        "电话号码或密码不能为空！",Toast.LENGTH_LONG).show();
                            }



                        }
                    });
                    btn_dialog_cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            alertDialog.dismiss();

                        }
                    });
                }
            });






        if(btn_register!=null){
            btn_register.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mActivity.startActivity(new Intent(mActivity, RegisterActivity.class));
                }
            });
        }
    }
    private void updateUserInfo(){
        BitmapUtils icon=new BitmapUtils(mActivity);
        String icon_url = mActivity.getSharedPreferences("userInfo",
                Context.MODE_PRIVATE).getString("icon_url", "");
        icon.display(user_icon,ServerURL.SERVER_IP+icon_url);

        String nick_name = mActivity.getSharedPreferences("userInfo",
                Context.MODE_PRIVATE).getString("nick_name", "");
        user_name.setText(nick_name);

        String sign = mActivity.getSharedPreferences("userInfo",
                Context.MODE_PRIVATE).getString("signature", "");
        user_sign.setText(sign);

        int like = mActivity.getSharedPreferences("userInfo",
                Context.MODE_PRIVATE).getInt("like_num", 0);
        user_like.setText(""+like);

        int dislike = mActivity.getSharedPreferences("userInfo",
                Context.MODE_PRIVATE).getInt("dislike_num", 0);
        user_dislike.setText(""+dislike);

        int comment = mActivity.getSharedPreferences("userInfo",
                Context.MODE_PRIVATE).getInt("comment_num", 0);
        user_edit.setText(""+comment);

        int collect = mActivity.getSharedPreferences("userInfo",
                Context.MODE_PRIVATE).getInt("collect_num", 0);
        user_collect.setText(""+collect);
    }

}
