package com.heyi.UniversityNews.JavaBean;

/**
 * Created by heyi on 2016/12/4.
 */

public class UpdateUserInfoResponse {
    private int retCode;
    private boolean success;
    private String result;

    public int getRetCode() {
        return retCode;
    }

    public void setRetCode(int retCode) {
        this.retCode = retCode;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
