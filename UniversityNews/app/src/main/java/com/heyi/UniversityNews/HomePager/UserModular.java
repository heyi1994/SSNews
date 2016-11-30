package com.heyi.UniversityNews.HomePager;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.heyi.UniversityNews.Activity.MainActivity;
import com.heyi.UniversityNews.Activity.RegisterActivity;
import com.heyi.UniversityNews.R;

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
    public UserModular(Activity activity) {
        super(activity);
    }

    @Override
    protected View initView() {
        boolean login = mActivity.getSharedPreferences("userInfo",
                Context.MODE_PRIVATE).getBoolean("login", false);
        dialog_login= View.inflate(mActivity,R.layout.dialog_login,null);
        btn_dialog_login = (Button) dialog_login.findViewById(R.id.btn_dialog_login);
        btn_dialog_cancel = (Button) dialog_login.findViewById(R.id.btn_dialog_cancel);
        View inflate=null;
                if(login) {
                    inflate = inflate(mActivity, R.layout.home_pager_user, null);
                }else {
                    inflate= inflate(mActivity,R.layout.home_pager_user_no_login,null);
                   btn_login= (Button) inflate.findViewById(R.id.btn_login);
                    btn_register= (Button) inflate.findViewById(R.id.btn_zheche);
                }

        return inflate;
    }

    @Override
    protected void initData() {
        loginOrRegister();
    }

    private void loginOrRegister() {
        if(btn_login!=null){
            btn_login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder=new AlertDialog.Builder(mActivity);
                    builder.setView(dialog_login);
                    builder.show();
                }
            });


                btn_dialog_login.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mActivity.getSharedPreferences("userInfo", Context.MODE_PRIVATE).
                                edit().putBoolean("login",true).commit();
                        mActivity.finish();
                        mActivity.startActivity(new Intent(mActivity, MainActivity.class));
                    }
                });

        }
        if(btn_register!=null){
            btn_register.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mActivity.startActivity(new Intent(mActivity, RegisterActivity.class));
                }
            });
        }
    }

}
