package com.heyi.UniversityNews.Utils;

import java.util.Random;

/**
 * Created by heyi on 2016/11/29.
 * 随机生成6位短信验证码
 */

public class GetSixRandomNumber {
    private  StringBuffer sb=new StringBuffer();
public  String getNumber(){

    for(int i=0;i<6;i++){
        sb.append(new Random().nextInt(9));
    }
    return sb.toString();
}
}
