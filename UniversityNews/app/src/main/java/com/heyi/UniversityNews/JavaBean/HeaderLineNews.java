package com.heyi.UniversityNews.JavaBean;

import java.util.ArrayList;

/**
 * Created by heyi on 2016/12/20.
 */

public class HeaderLineNews {
     private String result;
    private int retCode;
    private boolean success;
    private ArrayList<HeaderLineVpNews> vp_news;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

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

    public ArrayList<HeaderLineVpNews> getVp_news() {
        return vp_news;
    }

    public void setVp_news(ArrayList<HeaderLineVpNews> vp_news) {
        this.vp_news = vp_news;
    }

    public class HeaderLineVpNews{
        private String vp_image_url;
        private int vp_news_id;
        private String vp_time;
        private String vp_title;
        private String vp_type;
        private String vp_news_url;

        public String getVp_news_url() {
            return vp_news_url;
        }

        public void setVp_news_url(String vp_news_url) {
            this.vp_news_url = vp_news_url;
        }

        public String getVp_image_url() {
            return vp_image_url;
        }

        public void setVp_image_url(String vp_image_url) {
            this.vp_image_url = vp_image_url;
        }

        public int getVp_news_id() {
            return vp_news_id;
        }

        public void setVp_news_id(int vp_news_id) {
            this.vp_news_id = vp_news_id;
        }

        public String getVp_time() {
            return vp_time;
        }

        public void setVp_time(String vp_time) {
            this.vp_time = vp_time;
        }

        public String getVp_title() {
            return vp_title;
        }

        public void setVp_title(String vp_title) {
            this.vp_title = vp_title;
        }

        public String getVp_type() {
            return vp_type;
        }

        public void setVp_type(String vp_type) {
            this.vp_type = vp_type;
        }
    }
}
