package com.heyi.UniversityNews.JavaBean;

import java.util.ArrayList;

import static com.google.android.gms.internal.zzapz.boo;

/**
 * Created by heyi on 2017/1/7.
 */

public class ResponseNewsComment {
    private int retCode;
    private boolean success;
    private boolean more;

    public boolean isMore() {
        return more;
    }

    public void setMore(boolean more) {
        this.more = more;
    }

    private ArrayList<NewsComment> result;

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

    public ArrayList<NewsComment> getResult() {
        return result;
    }

    public void setResult(ArrayList<NewsComment> result) {
        this.result = result;
    }
}
