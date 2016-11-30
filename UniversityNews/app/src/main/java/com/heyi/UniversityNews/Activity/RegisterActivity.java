package com.heyi.UniversityNews.Activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.heyi.UniversityNews.JavaBean.SmsResponse;
import com.heyi.UniversityNews.R;
import com.heyi.UniversityNews.Utils.GetSixRandomNumber;
import com.heyi.UniversityNews.Utils.JsonUtils;
import com.heyi.UniversityNews.Utils.SendMessageExtend;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by heyi on 2016/11/29.
 */

public class RegisterActivity extends Activity {
  private ImageView iv_back;
    private EditText et_nick_name;
    private EditText et_pwd;
    private EditText et_re_pwd;
    private  EditText et_phone;
    private  EditText et_yanzheng;
    private ImageView iv_pwd_rig;
    private ImageView iv_pwd_fal;
    private Button btn_send;
    private int time=60;
    private boolean re_pwd=false;
    private boolean yzm=false;
    private boolean pwd=false;
    private Timer timer;
    private String randomNumber;
    private String phone;
    private String nickName;
    private ImageView iv_rzm_rig;
    private ImageView iv_rzm_fla;
    private Button btn_register;
    private ImageView iv_old_pwd_rig;
    private ImageView iv_old_pwd_fal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
    }

    private void initView() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_register);
        iv_old_pwd_rig= (ImageView) findViewById(R.id.register_old_pwd_right);
        iv_old_pwd_fal= (ImageView) findViewById(R.id.register_old_pwd_false);
        iv_pwd_rig= (ImageView) findViewById(R.id.register_pwd_right);
        iv_pwd_fal= (ImageView) findViewById(R.id.register_pwd_false);
        iv_rzm_rig= (ImageView) findViewById(R.id.register_yzm_right);
        iv_rzm_fla= (ImageView) findViewById(R.id.register_yzm_false);
        btn_register= (Button) findViewById(R.id.btn_activity_register);
        iv_back= (ImageView) findViewById(R.id.go_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBack();
            }
        });

        et_nick_name= (EditText) findViewById(R.id.register_nick_name);
        et_pwd=(EditText) findViewById(R.id.register_pwd);
        et_pwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                      if(!TextUtils.isEmpty(s)){
                     if(s.toString().matches("\\w{6,}")){
                         iv_old_pwd_fal.setVisibility(View.INVISIBLE);
                         iv_old_pwd_rig.setVisibility(View.VISIBLE);
                         pwd=true;
                      }else{
                         iv_old_pwd_rig.setVisibility(View.INVISIBLE);
                         iv_old_pwd_fal.setVisibility(View.VISIBLE);
                         pwd=false;
                     }}else{
                          iv_old_pwd_fal.setVisibility(View.INVISIBLE);
                          iv_old_pwd_rig.setVisibility(View.INVISIBLE);
                          pwd=false;
                      }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        et_re_pwd=(EditText) findViewById(R.id.register_re_pwd);
        et_re_pwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String pwd = et_pwd.getText().toString().trim();
                if(!TextUtils.isEmpty(s.toString())) {
                    if (pwd.equals(s.toString())) {
                        iv_pwd_fal.setVisibility(View.INVISIBLE);
                        iv_pwd_rig.setVisibility(View.VISIBLE);
                        re_pwd=true;
                    } else {
                        iv_pwd_rig.setVisibility(View.INVISIBLE);
                        iv_pwd_fal.setVisibility(View.VISIBLE);
                        re_pwd=false;
                    }
                }else{
                    iv_pwd_fal.setVisibility(View.INVISIBLE);
                    iv_pwd_rig.setVisibility(View.INVISIBLE);
                    re_pwd=false;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        et_phone=(EditText) findViewById(R.id.register_phone);

        btn_send= (Button) findViewById(R.id.register_btn_send_message);
        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phone =  et_phone.getText().toString().trim();
                nickName =  et_nick_name.getText().toString().trim();
                if(!TextUtils.isEmpty(phone)&&!TextUtils.isEmpty(nickName)){
                    randomNumber = new GetSixRandomNumber().getNumber();
                    btn_send.setBackgroundColor(Color.GRAY);
                    btn_send.setClickable(false);
                    enterWaiting();
                    System.out.println(randomNumber);
                    new Thread(){
                        @Override
                        public void run() {
                            if(phone!=null&&nickName!=null&&randomNumber!=null) {
                                SendMessageExtend.setRecNum(phone);
                                SendMessageExtend.setSmsTemplateCode("SMS_31235033");
                                SendMessageExtend.setSmsParam(nickName,randomNumber);
                                SendMessageExtend.setSmsFreeSignName("沈师新闻");
                                try {
                                    String response = SendMessageExtend.SendMsg();
                                    System.out.println(response);
                                    Gson gson=new Gson();
                                    SmsResponse smsResponse = gson.fromJson(response,
                                            SmsResponse.class);

                                    if(smsResponse.
                                            alibaba_aliqin_fc_sms_num_send_response.
                                            result.success&&
                                            smsResponse.
                                                    alibaba_aliqin_fc_sms_num_send_response.
                                                    result!=null
                                            ){
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                Toast.makeText(RegisterActivity.this,
                                                        "短信发送成功！",Toast.LENGTH_LONG).show();
                                            }
                                        });

                                    }else{
                                       runOnUiThread(new Runnable() {
                                           @Override
                                           public void run() {
                                               Toast.makeText(RegisterActivity.this,
                                                       "短信发送失败！",Toast.LENGTH_LONG).show();
                                           }
                                       });
                                    }
                                } catch (IOException e) {
                                    Toast.makeText(getApplicationContext(),
                                            "无网络连接，无法发送短信！",Toast.LENGTH_LONG).show();
                                }

                            }
                        }
                    }.start();
                }else{
                    Toast.makeText(getApplicationContext(),
                            "电话号码和昵称不能为空!",Toast.LENGTH_LONG).show();
                }


            }
        });
        et_yanzheng=(EditText) findViewById(R.id.register_yanzhengma);

        et_yanzheng.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!TextUtils.isEmpty(s.toString())&&!TextUtils.isEmpty(randomNumber)) {
                    if (randomNumber.equals(s.toString())) {
                        iv_rzm_fla.setVisibility(View.INVISIBLE);
                        iv_rzm_rig.setVisibility(View.VISIBLE);
                        yzm=true;
                    } else {
                        iv_rzm_rig.setVisibility(View.INVISIBLE);
                        iv_rzm_fla.setVisibility(View.VISIBLE);
                        yzm=false;
                    }
                }else{
                    iv_rzm_fla.setVisibility(View.INVISIBLE);
                    iv_rzm_rig.setVisibility(View.INVISIBLE);
                    yzm=false;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    private void initData() {
      btn_register.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              if(pwd&&re_pwd&&yzm){
                  JsonUtils json=new JsonUtils();
                  json.startFirstChild("API_ID","2008Heyi");
                  json.startOtherChild("nickName",et_nick_name.getText().toString().trim());
                  json.startOtherChild("password",et_pwd.getText().toString().trim());
                  json.startOtherChild("phoneNumber",et_phone.getText().toString().trim());
                  String info = json.endJson();
                  System.out.println(info);
              }else{
                  Toast.makeText(RegisterActivity.this,"请按要求填写!"
                          ,Toast.LENGTH_LONG).show();
              }
          }
      });

    }
    /*
    * 返回主页
    * */
    private void goBack(){
        finish();
    }
    private void enterWaiting(){
        timer= new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (time > 0) {
                                btn_send.setText(time + "s后重试");
                                time--;
                            } else {
                                timer.cancel();
                                btn_send.setBackgroundResource(R.drawable.buttom_type1);
                                btn_send.setClickable(true);
                                btn_send.setText("发送验证码");
                                time=60;
                            }
                        }
                    });
                }
            }
        },0,1000);
    }
}
