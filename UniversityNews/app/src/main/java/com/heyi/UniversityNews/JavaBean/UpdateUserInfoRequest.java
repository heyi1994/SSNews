package com.heyi.UniversityNews.JavaBean;

/**
 * Created by heyi on 2016/12/4.
 */

public class UpdateUserInfoRequest {
    private String API_ID;
    private int requestCode;
    /*
     * requestCode:1   修改昵称
     * requestCode:2   修改签名
     * requestCode:3   修改电话
     */
    private String phone;
    private String info;//要修改的数据

    public String getAPI_ID() {
        return API_ID;
    }

    public void setAPI_ID(String API_ID) {
        this.API_ID = API_ID;
    }

    public int getRequestCode() {
        return requestCode;
    }

    public void setRequestCode(int requestCode) {
        this.requestCode = requestCode;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
