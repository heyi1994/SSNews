package com.heyi.UniversityNews.JavaBean;

/**
 * Created by heyi on 2016/12/2.
 */

public class LoginResult {
    private boolean success;
    private int retCode;
    private UserInfo result;
    public boolean isSuccess() {
        return success;
    }
    public void setSuccess(boolean success) {
        this.success = success;
    }
    public int getRetCode() {
        return retCode;
    }
    public void setRetCode(int retCode) {
        this.retCode = retCode;
    }
    public UserInfo getResult() {
        return result;
    }
    public void setResult(UserInfo result) {
        this.result = result;
    }

}
