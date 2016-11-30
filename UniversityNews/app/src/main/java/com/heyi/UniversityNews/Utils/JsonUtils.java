package com.heyi.UniversityNews.Utils;

/**
 * Created by heyi on 2016/11/29.
 */

public class JsonUtils {
    private  StringBuffer json=new StringBuffer();
    public  void startFirstChild(String key,String value){
        json.append("{\""+key+"\""+":"+"\""+value+"\"");
    }
    public  void startOtherChild(String key,String value){
        json.append(",\""+key+"\""+":"+"\""+value+"\"");
    }
    public  String endJson(){
        json.append("}");
        return json.toString();
    }
}
