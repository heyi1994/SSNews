package com.heyi.UniversityNews.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
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

/**
 * Created by heyi on 2016/12/4.
 */

public class ChangeSignActivity extends Activity {
    private TextView tv_change;
    private EditText et_change;
    private ImageView iv_back;
    private String si;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        initView();
        initData();
    }

    private void initView() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_change_sign);
       tv_change= (TextView) findViewById(R.id.change);
        et_change= (EditText) findViewById(R.id.change_signature);
        iv_back= (ImageView) findViewById(R.id.iv_back_me);

    }

    private void initData() {
        String sign = getSharedPreferences("userInfo",
                Context.MODE_PRIVATE).getString("signature", "");
        et_change.setText(sign);
        tv_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                si = et_change.getText().toString().toString();
                System.out.println(si);
                if(!TextUtils.isEmpty(si)){
                    HttpUtils http=new HttpUtils();
                    RequestParams rp=new RequestParams();
                    String phone = getSharedPreferences("userInfo", MODE_PRIVATE).
                            getString("phone", "");
                    UpdateUserInfoRequest request=new UpdateUserInfoRequest();
                    request.setAPI_ID("2008Heyi");
                    request.setPhone(phone);
                    request.setInfo(si);
                    request.setRequestCode(2);
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
                                            intent.putExtra("sign", si);
                                            setResult(1,intent);
                                            finish();
                                            Toast.makeText(ChangeSignActivity.
                                                    this,"修改成功！",Toast.LENGTH_LONG).show();
                                        }else{
                                            Toast.makeText(ChangeSignActivity.
                                                    this,"服务器未知异常！",Toast.LENGTH_LONG).show();
                                        }
                                    }else{
                                        Toast.makeText(ChangeSignActivity.
                                                this,"服务器维护中!",Toast.LENGTH_LONG).show();
                                    }
                                }

                                @Override
                                public void onFailure(HttpException e, String s) {
                                    Toast.makeText(ChangeSignActivity.this,"修改失败，无网络连接！",
                                            Toast.LENGTH_LONG).show();

                                }
                            });


                }else{
                    Toast.makeText(ChangeSignActivity.this,"签名不能为空！",
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
