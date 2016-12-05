package com.heyi.UniversityNews.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.gson.Gson;
import com.heyi.UniversityNews.JavaBean.UpdateUserInfoRequest;
import com.heyi.UniversityNews.JavaBean.UpdateUserInfoResponse;
import com.heyi.UniversityNews.R;
import com.heyi.UniversityNews.ServerURL.ServerURL;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import static android.R.attr.x;

/**
 * Created by heyi on 2016/12/4.
 */

public class ChangeNickNameActivity extends Activity {
    private ImageView iv_back;
    private TextView tv_change;
    private EditText et_nick;
    private String trim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
    }

    private void initView() {
        setContentView(R.layout.activity_change_nick_name);
        iv_back= (ImageView) findViewById(R.id.iv_back_nn);
        tv_change= (TextView) findViewById(R.id.change_nick_name);
        et_nick= (EditText) findViewById(R.id.et_change_nick_name);
    }

    private void initData() {
        String name =getSharedPreferences("userInfo",
                Context.MODE_PRIVATE).getString("nick_name", "");
        et_nick.setText(name);
        tv_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                trim =  et_nick.getText().toString().trim();
                if(!TextUtils.isEmpty(trim)){
                    HttpUtils http=new HttpUtils();
                    RequestParams rp=new RequestParams();
                    String phone = getSharedPreferences("userInfo", MODE_PRIVATE).
                            getString("phone", "");
                    UpdateUserInfoRequest request=new UpdateUserInfoRequest();
                    request.setAPI_ID("2008Heyi");
                    request.setPhone(phone);
                    request.setInfo(trim);
                    request.setRequestCode(1);
                    Gson gson=new Gson();
                    String info = gson.toJson(request);
                    rp.addBodyParameter("json",info);
                    http.send(HttpRequest.HttpMethod.POST, ServerURL.SERVER_IP+
                            ServerURL.UPDATE_USER_INFO_URL,
                            rp, new RequestCallBack<String>() {

                        @Override
                        public void onSuccess(ResponseInfo<String> responseInfo) {
                            String result = responseInfo.result;
                            Gson gon=new Gson();
                            UpdateUserInfoResponse updateUserInfoResponse = gon.
                                    fromJson(result, UpdateUserInfoResponse.class);
                            if(updateUserInfoResponse!=null){
                            if (updateUserInfoResponse.isSuccess()){
                            Intent intent=new Intent();
                            intent.putExtra("NickName",trim);
                            setResult(2,intent);
                            finish();
                                Toast.makeText(ChangeNickNameActivity.
                                        this,"修改成功！",Toast.LENGTH_LONG).show();
                            }else{
                                Toast.makeText(ChangeNickNameActivity.
                                        this,"服务器未知异常！",Toast.LENGTH_LONG).show();
                            }
                            }else{
                                Toast.makeText(ChangeNickNameActivity.
                                        this,"服务器维护中!",Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(HttpException e, String s) {
                            Toast.makeText(ChangeNickNameActivity.this,"修改失败，无网络连接！",
                                    Toast.LENGTH_LONG).show();

                        }
                    });

                }else{
                    Toast.makeText(ChangeNickNameActivity.this,"昵称不能为空！",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
